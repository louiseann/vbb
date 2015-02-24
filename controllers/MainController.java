package vbb.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import truth_table_generator.CalcInterfaceController;
import truth_table_generator.toggle.ToggleAreaController;
import vbb.controllers.digital_trainer.DigitalTrainerController;
import vbb.controllers.digital_trainer.controls.SocketControl;
import vbb.controllers.digital_trainer.controls.breadboard.BreadboardControl;
import vbb.controllers.digital_trainer.controls.breadboard.BreadboardSocketControl;
import vbb.controllers.digital_trainer.controls.switch_control.DataSwitchControl;
import vbb.controllers.digital_trainer.controls.switch_control.PowerSwitchControl;
import vbb.controllers.tools.ToolsController;
import vbb.controllers.tools.controls.IntegratedCircuitControl;
import vbb.controllers.tools.controls.WireControl;
import vbb.models.Circuit;
import vbb.models.Control;
import vbb.models.connection.connector.TwoWayConnector;
import vbb.models.digital_trainer.Socket;
import vbb.models.digital_trainer.breadboard.BreadboardSocket;
import vbb.models.tools.Select;
import vbb.models.tools.Tool;
import vbb.models.tools.connectors.Pin;
import vbb.models.tools.connectors.Wire;
import vbb.models.tools.electronic_component.IntegratedCircuit;
import vbb.models.tools.electronic_component.TTL74SeriesIC;

public class MainController
{
    @FXML
    private ToolsController toolsAreaController;
    @FXML
    private VBox toolsArea;

    @FXML
    private StackPane centerPane;

    @FXML
    private DigitalTrainerController digitalTrainerController;
    @FXML
    private Pane digitalTrainer;

    @FXML
    private ToggleAreaController generatorToggleAreaController;
    @FXML
    private BorderPane generatorToggleArea;

    @FXML
    CalcInterfaceController generatorPaneController;
    @FXML
    private VBox generatorPane;

    private Pane toolCursor;

    private WireControl wireControl;

    private Label hoverMessage;

    @FXML
    public void initialize()
    {
        setUpToolCursorImage();

        setDigitalTrainerEventHandlers();

        generatorPaneController.toggleVisibility();

        generatorToggleAreaController.setGeneratorToggleActionHandler(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                generatorPaneController.toggleVisibility();
            }
        });

        centerPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();

                centerPane.setCursor(currentTool.getCursor());

                centerPane.getChildren().add(toolCursor);
                Point2D hotSpot = currentTool.getViewHotSpot();
                moveTool(event.getX() + hotSpot.getX(), event.getY() + hotSpot.getY());
            }
        });

        centerPane.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                centerPane.getChildren().remove(toolCursor);
            }
        });

        centerPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                Point2D hotSpot = currentTool.getViewHotSpot();
                moveTool(event.getX() + hotSpot.getX(), event.getY() + hotSpot.getY());
                if (currentTool.getSuperClassName().equals(Wire.class.getSimpleName()))
                {
                    if (WireControl.isStartSet())
                    {
                        wireControl.setEndX(event.getX()-digitalTrainerController.getXPosition());
                        wireControl.setEndY(event.getY()-digitalTrainerController.getYPosition());
                    }
                }
            }
        });

        toolsAreaController.currentTool().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (WireControl.isStartSet())
                {
                    digitalTrainerController.unplugWire(wireControl);
                    WireControl.startSet(false);
                }
                setUpToolCursorImage();
            }
        });
    }

    private void setUpToolCursorImage()
    {
        toolCursor = toolsAreaController.getCurrentTool().getView();
    }

    private void moveTool(double x, double y)
    {
        toolCursor.setTranslateX(x);
        toolCursor.setTranslateY(y);
    }

    private void setDigitalTrainerEventHandlers()
    {
        final EventHandler<MouseEvent> clickedHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                BreadboardSocketControl socketControl = (BreadboardSocketControl) event.getSource();
                if (currentTool.getClassName().equals(IntegratedCircuit.class.getSimpleName()))
                    handleIntegratedCircuitEvent(event.getEventType(), socketControl);
                else if (currentTool.getSuperClassName().equals(Wire.class.getSimpleName()))
                    handleWireToolClicked(socketControl, socketControl.getSoul());
            }
        };
        final EventHandler<MouseEvent> enteredHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                BreadboardSocketControl socket = (BreadboardSocketControl) event.getSource();
                if (currentTool.getClassName().equals(IntegratedCircuit.class.getSimpleName()))
                    handleIntegratedCircuitEvent(event.getEventType(), socket);
                else if (currentTool.getSuperClassName().equals(Wire.class.getSimpleName()))
                    onEnteredOnSocket(socket.getSoul(), socket.getHoleBox());
            }
        };
        final EventHandler<MouseEvent> exitedHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                BreadboardSocketControl socket = (BreadboardSocketControl) event.getSource();
                if (currentTool.getClassName().equals(IntegratedCircuit.class.getSimpleName()))
                    handleIntegratedCircuitEvent(event.getEventType(), socket);
                else if (currentTool.getSuperClassName().equals(Wire.class.getSimpleName()))
                    onExitedOnSocket(socket.getHoleBox());
            }
        };
        digitalTrainerController.setBreadboardEventHandlers(clickedHandler, enteredHandler, exitedHandler);

        final EventHandler<MouseEvent> clickedOnSocketHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                SocketControl socketControl = (SocketControl) ((Circle) event.getSource()).getParent();
                if (currentTool.getSuperClassName().equals(Wire.class.getSimpleName()))
                    handleWireToolClicked(socketControl, socketControl.getSoul());
            }
        };
        final EventHandler<MouseEvent> enteredOnSocketHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                Circle socketCircle = (Circle) event.getSource();
                SocketControl socketControl = (SocketControl) socketCircle.getParent();
                if (currentTool.getSuperClassName().equals(Wire.class.getSimpleName()))
                    onEnteredOnSocket(socketControl.getSoul(), socketCircle);
            }
        };
        final EventHandler<MouseEvent> exitedOnSocketHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                Circle socketCircle = (Circle) event.getSource();
                if (currentTool.getSuperClassName().equals(Wire.class.getSimpleName()))
                    onExitedOnSocket(socketCircle);
            }
        };
        digitalTrainerController.setSocketsHandler(clickedOnSocketHandler, enteredOnSocketHandler,
                                                   exitedOnSocketHandler);

        final EventHandler<MouseEvent> clickedOnPowerSwitch = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                if (currentTool.getClassName().equals(Select.class.getSimpleName()))
                {
                    PowerSwitchControl powerSwitch = (PowerSwitchControl) event.getSource();
                    powerSwitch.toggleSwitch();
                }
            }
        };
        digitalTrainerController.setPowerSwitchClickedHandler(clickedOnPowerSwitch);

        final EventHandler<MouseEvent> clickedOnDataSwitch = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                if (currentTool.getClassName().equals(Select.class.getSimpleName()))
                {
                    DataSwitchControl dataSwitch = (DataSwitchControl) event.getSource();
                    dataSwitch.toggleSwitch();
                }
            }
        };
        digitalTrainerController.setDataSwitchesClickedHandler(clickedOnDataSwitch);

        digitalTrainerController.setEnteredOnPluggedToolHandler(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                if (currentTool.getClassName().equals(Select.class.getSimpleName()))
                {
                    Node source = (Node) event.getSource();
                    final Glow glow = new Glow(.8);
                    source.setEffect(null);
                    source.setEffect(glow);

                    hoverMessage = new Label("click to remove");
                    hoverMessage.setStyle("-fx-font-size: 10px;" +
                                          "-fx-text-fill: #ffffff;" +
                                          "-fx-background-color: #000000;" +
                                          "-fx-label-padding: 2px;");
                    toolCursor.getChildren().add(hoverMessage);
                    hoverMessage.relocate(15, 0);

                }
            }
        });
        digitalTrainerController.setExitedOnPluggedToolHandler(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                if (currentTool.getClassName().equals(Select.class.getSimpleName())) {
                    Node source = (Node) event.getSource();
                    source.setEffect(null);
                    toolCursor.getChildren().remove(hoverMessage);
                    hoverMessage = null;
                }
            }
        });
        digitalTrainerController.setMovedOnWireHandler(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                if (currentTool.getClassName().equals(Select.class.getSimpleName()))
                {
                    System.out.println("on wire " + event.getX() + " " + event.getY());
                }
            }
        });
        digitalTrainerController.setClickedOnWireHandler(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                if (currentTool.getClassName().equals(Select.class.getSimpleName()))
                {
                    WireControl control = (WireControl) event.getSource();
                    digitalTrainerController.handleClickOnPluggedWire(control);
                }
            }
        });
        digitalTrainerController.setClickedOnChipHandler(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                if (currentTool.getClassName().equals(Select.class.getSimpleName()))
                {
                    IntegratedCircuitControl control = (IntegratedCircuitControl) event.getSource();
                    digitalTrainerController.handleClickOnPluggedChip(control);
                }
            }
        });
    }

    private void handleWireToolClicked(Node nodeSocket, Socket socket)
    {
        if (!socket.isOccupied())
        {
            Wire wire;
            if (!WireControl.isStartSet())
            {
                wire = new TwoWayConnector();
                wire.getEndPoint1().setControlConnected(socket);
            }
            else
            {
                wire = wireControl.getWire();
                wire.getEndPoint2().setControlConnected(socket);

                wire = Wire.finalize(wire);
                digitalTrainerController.getSoul().getCircuit().add(wire);

                Control point1Control = wire.getEndPoint1().getControlConnected();
                if (point1Control instanceof BreadboardSocket)
                {
                    BreadboardSocket breadboardSocket = (BreadboardSocket) point1Control;
                    breadboardSocket.getMetalStrip().connectOccupiedSockets(breadboardSocket, digitalTrainerController.getSoul().getCircuit());
                }

                Control point2Control = wire.getEndPoint2().getControlConnected();
                if (point2Control instanceof BreadboardSocket)
                {
                    BreadboardSocket breadboardSocket = (BreadboardSocket) point2Control;
                    breadboardSocket.getMetalStrip().connectOccupiedSockets(breadboardSocket, digitalTrainerController.getSoul().getCircuit());
                }
            }
            socket.setOccupied(true);

            setWireControlOnBoard(nodeSocket, wire);
        }
    }

    private void setWireControlOnBoard(Node socketNode, Wire wire)
    {
        final Bounds nodeBounds = socketNode.localToScene(socketNode.getBoundsInLocal());
        Point2D position = getPointAtCenterPane(nodeBounds);

        if (!WireControl.isStartSet())
        {
            wireControl = new WireControl();
            wireControl.setWire(wire);
            wireControl.setStroke(toolsAreaController.getWireColor());
            wireControl.setStrokeWidth(5);
            wireControl.setMouseTransparent(true);

            wireControl.setStartX(position.getX());
            wireControl.setStartY(position.getY());

            wireControl.setEndX(position.getX());
            wireControl.setEndY(position.getY());

            WireControl.startSet(true);

            digitalTrainerController.plugWire(wireControl);
        }
        else
        {
            wireControl.setEndX(position.getX());
            wireControl.setEndY(position.getY());

            wireControl.setMouseTransparent(false);
            WireControl.startSet(false);
        }
    }

    private void handleIntegratedCircuitEvent(EventType eventType, BreadboardSocketControl socket)
    {
        IntegratedCircuit toolChip = (IntegratedCircuit) toolsAreaController.getCurrentTool().getClassification();

        Circuit circuit = digitalTrainerController.getSoul().getCircuit();
        IntegratedCircuit chip = TTL74SeriesIC.createChip(toolChip.getLogicGate(), circuit);

        int maxRow = digitalTrainerController.getBreadBoard().getSoul().getTerminalHoleRows() - 1;
        int reverseRowPosition = Math.abs(socket.getRow() - maxRow - 1);

        if (socket.getCol() + chip.getColSpan() <= digitalTrainerController.getBreadBoard().getSoul().getGridCols() &&
                reverseRowPosition < chip.getRowSpan() &&
                reverseRowPosition > 0 &&
                !occupied(socket, chip.getColSpan(), chip.getRowSpan(), reverseRowPosition) &&
                socket.isTopGroupElement())
        {
            if (eventType.equals(MouseEvent.MOUSE_CLICKED))
            {
                int remainingChipRowSpan = chip.getRowSpan() - reverseRowPosition;
                plug(chip, socket, remainingChipRowSpan);
                hoverChipOnSockets(socket, chip.getColSpan(), remainingChipRowSpan, MouseEvent.MOUSE_EXITED);
            }
            else if (eventType.equals(MouseEvent.MOUSE_ENTERED) || eventType.equals(MouseEvent.MOUSE_EXITED))
                hoverChipOnSockets(socket, chip.getColSpan(), chip.getRowSpan()-reverseRowPosition, eventType);
        }
    }

    private boolean occupied(BreadboardSocketControl pointedSocketControl, int chipColSpan, int chipRowSpan,
                             int chipReverseRowPosition)
    {
        int colBegin = pointedSocketControl.getCol();
        int colEnd = pointedSocketControl.getCol() + chipColSpan;

        int chipRemainingRowSpan = chipRowSpan - chipReverseRowPosition;

        for (int col = colBegin; col < colEnd; col++)
        {
            BreadboardSocketControl socketControl =
                    digitalTrainerController.getBreadBoard()
                            .getSocketFromColConnectedGroup(col, pointedSocketControl.getRow(),
                                    pointedSocketControl.isTopGroupElement());
            BreadboardSocketControl otherSideSocketControl =
                    digitalTrainerController.getBreadBoard()
                            .getSocketFromColConnectedGroup(col, chipRemainingRowSpan - 1,
                                    !pointedSocketControl.isTopGroupElement());

            if (socketControl.getSoul().isOccupied() || otherSideSocketControl.getSoul().isOccupied())
                return true;
        }

        return false;
    }

    private void plug(IntegratedCircuit chip, BreadboardSocketControl pointedSocketControl, int chipRemainingRowSpan)
    {
        Tool currentTool = toolsAreaController.getCurrentTool();

        occupySockets(true, pointedSocketControl, chip, chipRemainingRowSpan);

        IntegratedCircuitControl chipControl = new IntegratedCircuitControl(currentTool.getViewImage());
        chipControl.setSoul(chip);
        setChipOnBoard(pointedSocketControl, chipControl, currentTool.getViewHotSpot());
    }

    private void occupySockets(boolean occupy, BreadboardSocketControl pointedSocketControl, IntegratedCircuit chip,
                               int chipRemainingRowSpan)
    {
        int colBegin = pointedSocketControl.getCol();
        int colEnd = pointedSocketControl.getCol() + chip.getColSpan();

        for (int col = colBegin, topRowPin = 0, bottomRowPin = 7; col < colEnd; col++)
        {
            BreadboardControl breadboardControl = digitalTrainerController.getBreadBoard();
            Circuit circuit = digitalTrainerController.getSoul().getCircuit();

            BreadboardSocketControl socketControl = breadboardControl
                                                    .getSocketFromColConnectedGroup(col, pointedSocketControl.getRow(),
                                                            pointedSocketControl.isTopGroupElement());
            socketControl.getSoul().getMetalStrip()
                                   .connectOccupiedSockets(socketControl.getSoul(), circuit);
            Pin leftPin = chip.getPin(topRowPin++);
            leftPin.getHangingPoint().setControlConnected(socketControl.getSoul());
            circuit.add(leftPin);

            BreadboardSocketControl otherSideSocketControl =
                    breadboardControl.getSocketFromColConnectedGroup(col, chipRemainingRowSpan - 1,
                            !pointedSocketControl.isTopGroupElement());
            otherSideSocketControl.getSoul().getMetalStrip()
                                   .connectOccupiedSockets(otherSideSocketControl.getSoul(), circuit);
            Pin rightPin = chip.getPin(bottomRowPin++);
            rightPin.getHangingPoint().setControlConnected(otherSideSocketControl.getSoul());
            circuit.add(rightPin);


            socketControl.getSoul().setOccupied(occupy);
            otherSideSocketControl.getSoul().setOccupied(occupy);
        }
    }

    private void hoverChipOnSockets(BreadboardSocketControl pointedSocketControl, int chipColSpan,
                                    int chipRemainingRowSpan, EventType eventType)
    {
        int colBegin = pointedSocketControl.getCol();
        int colEnd = pointedSocketControl.getCol() + chipColSpan;

        for (int col = colBegin; col < colEnd; col++)
        {
            BreadboardSocketControl socketControl =
                    digitalTrainerController.getBreadBoard()
                            .getSocketFromColConnectedGroup(col, pointedSocketControl.getRow(),
                                    pointedSocketControl.isTopGroupElement());
            BreadboardSocketControl otherSideSocketControl =
                    digitalTrainerController.getBreadBoard()
                            .getSocketFromColConnectedGroup(col, chipRemainingRowSpan - 1,
                                    !pointedSocketControl.isTopGroupElement());
            if (eventType.equals(MouseEvent.MOUSE_ENTERED))
            {
                onEnteredOnSocket(socketControl.getSoul(), socketControl.getHoleBox());
                onEnteredOnSocket(otherSideSocketControl.getSoul(), otherSideSocketControl.getHoleBox());
            }
            else
            {
                onExitedOnSocket(socketControl.getHoleBox());
                onExitedOnSocket(otherSideSocketControl.getHoleBox());
            }
        }
    }

    private void setChipOnBoard(BreadboardSocketControl pointedSocketControl, IntegratedCircuitControl control,
                                Point2D chipViewHotSpot)
    {
        final Bounds nodeBounds = pointedSocketControl.localToScene(pointedSocketControl.getBoundsInLocal());
        Point2D position = getPointAtCenterPane(nodeBounds);
        double xPosition = position.getX() + chipViewHotSpot.getX();
        double yPosition = position.getY() + chipViewHotSpot.getY();

        control.relocate(xPosition, yPosition);

        digitalTrainerController.plugChip(control);
    }

    private void showConnectedSockets(BreadboardSocketControl socketControl)
    {
        BreadboardSocket socket = socketControl.getSoul();

    }

    public void onEnteredOnSocket(Socket socket, Shape socketShape)
    {
        if (!socket.isOccupied())
            socketShape.setStyle("-fx-stroke: red;" +
                                 "-fx-stroke-type: inside;");
    }

    public void onExitedOnSocket(Shape socketShape)
    {
        socketShape.setStyle("-fx-stroke: null;");
    }

    private Point2D getPointAtCenterPane(Bounds nodeBounds)
    {
        double toolsAreaWidth = toolsArea.getWidth();


        double x = (nodeBounds.getMinX() + nodeBounds.getMaxX()) / 2 - toolsAreaWidth - digitalTrainerController.getXPosition();
        double y = (nodeBounds.getMinY()+nodeBounds.getMaxY()) / 2 - digitalTrainerController.getYPosition() - generatorToggleArea.getHeight();

        return new Point2D(x, y);
    }
}
