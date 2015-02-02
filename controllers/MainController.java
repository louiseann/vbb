package vbb.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import vbb.controllers.tools.controls.WireControl;
import vbb.models.digital_trainer.Socket;
import vbb.models.tools.Select;
import vbb.models.tools.Tool;
import vbb.models.tools.Wire;
import vbb.models.tools.electronic_component.IntegratedCircuit;

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
    private StackPane digitalTrainer;

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
                String currentToolClass = toolsAreaController.getCurrentTool().getClassificationClassName();
                if (currentToolClass.equals("Wire"))
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
                String currentToolClass = toolsAreaController.getCurrentTool().getClassificationClassName();
                BreadboardSocketControl socketControl = (BreadboardSocketControl) event.getSource();
                if (currentToolClass.equals(IntegratedCircuit.class.getSimpleName()))
                    handleIntegratedCircuitEvent(event.getEventType(), socketControl);
                else if (currentToolClass.equals(Wire.class.getSimpleName()))
                    handleWireToolClicked(socketControl, socketControl.getSocket());
            }
        };
        final EventHandler<MouseEvent> enteredHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String currentToolClass = toolsAreaController.getCurrentTool().getClassificationClassName();
                BreadboardSocketControl socket = (BreadboardSocketControl) event.getSource();
                if (currentToolClass.equals(IntegratedCircuit.class.getSimpleName()))
                    handleIntegratedCircuitEvent(event.getEventType(), socket);
                else if (currentToolClass.equals(Wire.class.getSimpleName()))
                    onEnteredOnSocket(socket.getSocket(), socket.getHoleBox());
            }
        };
        final EventHandler<MouseEvent> exitedHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String currentToolClass = toolsAreaController.getCurrentTool().getClassificationClassName();
                BreadboardSocketControl socket = (BreadboardSocketControl) event.getSource();
                if (currentToolClass.equals(IntegratedCircuit.class.getSimpleName()))
                    handleIntegratedCircuitEvent(event.getEventType(), socket);
                else if (currentToolClass.equals(Wire.class.getSimpleName()))
                    onExitedOnSocket(socket.getHoleBox());
            }
        };
        digitalTrainerController.setBreadboardEventHandlers(clickedHandler, enteredHandler, exitedHandler);

        final EventHandler<MouseEvent> clickedOnSocketHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                String currentToolClass = toolsAreaController.getCurrentTool().getClassificationClassName();
                SocketControl socketControl = (SocketControl) ((Circle) event.getSource()).getParent();
                if (currentToolClass.equals(Wire.class.getSimpleName()))
                    handleWireToolClicked(socketControl, socketControl.getSocket());
            }
        };
        final EventHandler<MouseEvent> enteredOnSocketHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String toolClass = toolsAreaController.getCurrentTool().getClassificationClassName();
                Circle socketCircle = (Circle) event.getSource();
                SocketControl socketControl = (SocketControl) socketCircle.getParent();
                if (toolClass.equals(Wire.class.getSimpleName()))
                    onEnteredOnSocket(socketControl.getSocket(), socketCircle);
            }
        };
        final EventHandler<MouseEvent> exitedOnSocketHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String toolClass = toolsAreaController.getCurrentTool().getClassificationClassName();
                Circle socketCircle = (Circle) event.getSource();
                if (toolClass.equals(Wire.class.getSimpleName()))
                    onExitedOnSocket(socketCircle);
            }
        };
        digitalTrainerController.setSocketsHandler(clickedOnSocketHandler, enteredOnSocketHandler,
                                                   exitedOnSocketHandler);

        final EventHandler<MouseEvent> clickedOnPowerSwitch = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String currentToolClass = toolsAreaController.getCurrentTool().getClassificationClassName();
                if (currentToolClass.equals(Select.class.getSimpleName()))
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
                String currentToolClass = toolsAreaController.getCurrentTool().getClassificationClassName();
                if (currentToolClass.equals(Select.class.getSimpleName()))
                {
                    DataSwitchControl dataSwitch = (DataSwitchControl) event.getSource();
                    dataSwitch.toggleSwitch();
                }
            }
        };
        digitalTrainerController.setDataSwitchesClickedHandler(clickedOnDataSwitch);

        final EventHandler<MouseEvent> enteredOnPluggedTool = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String currentToolClass = toolsAreaController.getCurrentTool().getClassificationClassName();
                if (currentToolClass.equals(Select.class.getSimpleName()))
                    System.out.println("hovered");
            }
        };
        final EventHandler<MouseEvent> exitedOnPluggedTool = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String currentToolClass = toolsAreaController.getCurrentTool().getClassificationClassName();
                if (currentToolClass.equals(Select.class.getSimpleName()))
                    System.out.println("exited");
            }
        };
    }

    private void handleWireToolClicked(Node nodeSocket, Socket socket)
    {
        if (!socket.isOccupied())
        {
            Wire wire = WireControl.isStartSet() ? wireControl.getWire() : new Wire();
            plug(wire, socket);

            setWireControlOnBoard(nodeSocket, wire);
        }
    }

    private void plug(Wire wire, Socket atSocket)
    {
        wire.plug(atSocket);
        atSocket.setOccupied(true);
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
            wireControl.setMouseTransparent(false);

            wireControl.setStartX(position.getX());
            wireControl.setStartY(position.getY());

            wireControl.setEndX(position.getX());
            wireControl.setEndY(position.getY());

            EventHandler<MouseEvent> enteredHandler = digitalTrainerController.getEnteredOnPluggedToolHandler();
            EventHandler<MouseEvent> exitedHandler = digitalTrainerController.getExitedOnPluggedToolHandler();
            wireControl.addEventHandler(MouseEvent.MOUSE_ENTERED, enteredHandler);
            wireControl.addEventHandler(MouseEvent.MOUSE_EXITED, exitedHandler);

            WireControl.startSet(true);

            digitalTrainerController.plugWire(wireControl);
        }
        else
        {
            wireControl.setEndX(position.getX());
            wireControl.setEndY(position.getY());

            WireControl.startSet(false);
        }
    }

    private void handleIntegratedCircuitEvent(EventType eventType, BreadboardSocketControl socket)
    {
        IntegratedCircuit chip = (IntegratedCircuit) toolsAreaController.getCurrentTool().getClassification();
        int maxCol = digitalTrainerController.getBreadBoard().getRowConnectedMaxCol() - 1;
        int reverseColPosition = Math.abs(socket.getCol() - maxCol - 1);

        if (socket.getRow() + chip.getRowSpan() <= digitalTrainerController.getBreadBoard().getRowCount() &&
                reverseColPosition < chip.getColSpan() &&
                reverseColPosition > 0 &&
                !occupied(socket, chip.getRowSpan(), chip.getColSpan(), reverseColPosition))
        {
            if (eventType.equals(MouseEvent.MOUSE_CLICKED))
            {
                plugChip(socket, chip.getColSpan()-reverseColPosition);
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

            if (socketControl.getSocket().isOccupied() || otherSideSocketControl.getSocket().isOccupied())
                return true;
        }

        return false;
    }

    private void plugChip(BreadboardSocketControl pointedSocketControl, int chipRemainingColSpan)
    {
        Tool currentTool = toolsAreaController.getCurrentTool();

        occupySockets(pointedSocketControl, (IntegratedCircuit) currentTool.getClassification(), chipRemainingColSpan);
        setChipOnBoard(pointedSocketControl, currentTool.getViewImage(), currentTool.getViewHotSpot());
    }

    private void occupySockets(BreadboardSocketControl pointedSocketControl, IntegratedCircuit chip,
                               int chipRemainingColSpan)
    {
        int rowBegin = pointedSocketControl.getRow();
        int rowEnd = pointedSocketControl.getRow() + chip.getRowSpan();

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
            socketControl.getSocket().setOccupied(true);
            otherSideSocketControl.getSocket().setOccupied(true);
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
                onEnteredOnSocket(socketControl.getSocket(), socketControl.getHoleBox());
                onEnteredOnSocket(otherSideSocketControl.getSocket(), otherSideSocketControl.getHoleBox());
            }
            else
            {
                onExitedOnSocket(socketControl.getHoleBox());
                onExitedOnSocket(otherSideSocketControl.getHoleBox());
            }
        }
    }

    private void setChipOnBoard(BreadboardSocketControl pointedSocketControl, Image chipImage, Point2D chipViewHotSpot)
    {
        final Bounds nodeBounds = pointedSocketControl.localToScene(pointedSocketControl.getBoundsInLocal());
        Point2D position = getPointAtCenterPane(nodeBounds);
        double xPosition = position.getX() + chipViewHotSpot.getX() + 2;
        double yPosition = position.getY() + chipViewHotSpot.getY() - 1;

        ImageView chipView = new ImageView(chipImage);
        chipView.relocate(xPosition, yPosition);
        chipView.setMouseTransparent(false);

        EventHandler<MouseEvent> enteredHandler = digitalTrainerController.getEnteredOnPluggedToolHandler();
        EventHandler<MouseEvent> exitedHandler = digitalTrainerController.getExitedOnPluggedToolHandler();
        chipView.addEventHandler(MouseEvent.MOUSE_ENTERED, enteredHandler);
        chipView.addEventHandler(MouseEvent.MOUSE_EXITED, exitedHandler);

        digitalTrainerController.plugChip(chipView);
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
        double y = (nodeBounds.getMinY()+nodeBounds.getMaxY())/2;

        return new Point2D(x, y);
    }
}
