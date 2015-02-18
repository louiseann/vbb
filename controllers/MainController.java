package vbb.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import vbb.controllers.digital_trainer.DigitalTrainerController;
import vbb.controllers.digital_trainer.controls.SocketControl;
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

    private Pane toolCursor;

    private WireControl wireControl;

    @FXML
    public void initialize()
    {
        setUpToolCursorImage();

        setDigitalTrainerEventHandlers();

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
                Point2D hotSpot = toolsAreaController.getCurrentTool().getViewHotSpot();
                moveTool(event.getX() + hotSpot.getX(), event.getY() + hotSpot.getY());
                Tool currentTool = toolsAreaController.getCurrentTool();
                if (currentTool.getSuperClassName().equals(Wire.class.getSimpleName()))
                {
                    if (WireControl.isStartSet())
                    {
                        wireControl.setEndX(event.getX());
                        wireControl.setEndY(event.getY());
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
                }
            }
        });
        digitalTrainerController.setExitedOnPluggedToolHandler(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                if (currentTool.getClassName().equals(Select.class.getSimpleName()))
                {
                    Node source = (Node) event.getSource();
                    source.setEffect(null);
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
            wireControl.setStrokeWidth(4);
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

        int maxCol = digitalTrainerController.getBreadBoard().getSoul().getTerminalHoleColumns() - 1;
        int reverseColPosition = Math.abs(socket.getCol() - maxCol - 1);

        if (socket.getRow() + chip.getRowSpan() <= digitalTrainerController.getBreadBoard().getSoul().getGridRows() &&
                reverseColPosition < chip.getColSpan() &&
                reverseColPosition > 0 &&
                !occupied(socket, chip.getRowSpan(), chip.getColSpan(), reverseColPosition) &&
                socket.isInLeft())
        {
            if (eventType.equals(MouseEvent.MOUSE_CLICKED))
            {
                plug(chip, socket, chip.getColSpan() - reverseColPosition);
                int remainingChipColSpan = chip.getColSpan()-reverseColPosition;
                hoverChipOnSockets(socket, chip.getRowSpan(), remainingChipColSpan, MouseEvent.MOUSE_EXITED);
            }
            else if (eventType.equals(MouseEvent.MOUSE_ENTERED) || eventType.equals(MouseEvent.MOUSE_EXITED))
                hoverChipOnSockets(socket, chip.getRowSpan(), chip.getColSpan()-reverseColPosition, eventType);
        }
    }

    private boolean occupied(BreadboardSocketControl pointedSocketControl, int chipRowSpan, int chipColSpan,
                             int chipReverseColPosition)
    {
        int rowBegin = pointedSocketControl.getRow();
        int rowEnd = pointedSocketControl.getRow() + chipRowSpan;

        int chipRemainingColSpan = chipColSpan - chipReverseColPosition;

        for (int row = rowBegin; row < rowEnd; row++)
        {
            BreadboardSocketControl socketControl =
                    digitalTrainerController.getBreadBoard()
                            .getSocketFromRowConnectedGroup(row, pointedSocketControl.getCol(),
                                    pointedSocketControl.isInLeft());
            BreadboardSocketControl otherSideSocketControl =
                    digitalTrainerController.getBreadBoard()
                            .getSocketFromRowConnectedGroup(row, chipRemainingColSpan-1,
                                    !pointedSocketControl.isInLeft());

            if (socketControl.getSoul().isOccupied() || otherSideSocketControl.getSoul().isOccupied())
                return true;
        }

        return false;
    }

    private void plug(IntegratedCircuit chip, BreadboardSocketControl pointedSocketControl, int chipRemainingColSpan)
    {
        Tool currentTool = toolsAreaController.getCurrentTool();

        occupySockets(true, pointedSocketControl, chip, chipRemainingColSpan);

        IntegratedCircuitControl chipControl = new IntegratedCircuitControl(currentTool.getViewImage());
        chipControl.setSoul(chip);
        setChipOnBoard(pointedSocketControl, chipControl, currentTool.getViewHotSpot());
    }

    private void occupySockets(boolean occupy, BreadboardSocketControl pointedSocketControl, IntegratedCircuit chip,
                               int chipRemainingColSpan)
    {
        int rowBegin = pointedSocketControl.getRow();
        int rowEnd = pointedSocketControl.getRow() + chip.getRowSpan();

        for (int row = rowBegin, leftColPin = 0, rightColPin = 7; row < rowEnd; row++)
        {
            BreadboardSocketControl socketControl =
            digitalTrainerController.getBreadBoard()
                                    .getSocketFromRowConnectedGroup(row, pointedSocketControl.getCol(),
                                                                    pointedSocketControl.isInLeft());
            Pin leftPin = chip.getPin(leftColPin++);
            leftPin.getHangingPoint().setControlConnected(socketControl.getSoul());
            digitalTrainerController.getSoul().getCircuit().add(leftPin);
            socketControl.getSoul().getMetalStrip().connectOccupiedSockets(socketControl.getSoul(),
                    digitalTrainerController.getSoul().getCircuit());

            BreadboardSocketControl otherSideSocketControl =
            digitalTrainerController.getBreadBoard()
                                    .getSocketFromRowConnectedGroup(row, chipRemainingColSpan-1,
                                                                    !pointedSocketControl.isInLeft());
            Pin rightPin = chip.getPin(rightColPin++);
            rightPin.getHangingPoint().setControlConnected(otherSideSocketControl.getSoul());
            digitalTrainerController.getSoul().getCircuit().add(rightPin);
            socketControl.getSoul().getMetalStrip().connectOccupiedSockets(otherSideSocketControl.getSoul(),
                    digitalTrainerController.getSoul().getCircuit());

            socketControl.getSoul().setOccupied(occupy);
            otherSideSocketControl.getSoul().setOccupied(occupy);
        }
    }

    private void hoverChipOnSockets(BreadboardSocketControl pointedSocketControl, int chipRowSpan,
                                    int chipRemainingColSpan, EventType eventType)
    {
        int rowBegin = pointedSocketControl.getRow();
        int rowEnd = pointedSocketControl.getRow() + chipRowSpan;

        for (int row = rowBegin; row < rowEnd; row++)
        {
            BreadboardSocketControl socketControl =
            digitalTrainerController.getBreadBoard()
                                    .getSocketFromRowConnectedGroup(row, pointedSocketControl.getCol(),
                                                                    pointedSocketControl.isInLeft());
            BreadboardSocketControl otherSideSocketControl =
            digitalTrainerController.getBreadBoard()
                                    .getSocketFromRowConnectedGroup(row, chipRemainingColSpan-1,
                                                                    !pointedSocketControl.isInLeft());
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
        double xPosition = position.getX() + chipViewHotSpot.getX() + 2;
        double yPosition = position.getY() + chipViewHotSpot.getY() - 1;

        control.relocate(xPosition, yPosition);

        digitalTrainerController.plugChip(control);
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

        double x = (nodeBounds.getMinX() + nodeBounds.getMaxX()) / 2 - toolsAreaWidth;
        double y = (nodeBounds.getMinY()+nodeBounds.getMaxY()) / 2;

        return new Point2D(x, y);
    }
}
