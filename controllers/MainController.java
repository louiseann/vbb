package vbb.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
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
                    if (Wire.isStartSet())
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
                if (Wire.isStartSet())
                    digitalTrainerController.unplugWire(wireControl);

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
                {
                    handleWireClicked(socketControl);
                    handleWirePluggedAt(socketControl.getSocket());
                }

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
                    socket.getHoleBox().setStyle("-fx-stroke: red;");
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
                    socket.getHoleBox().setStyle("-fx-stroke: null;");
            }
        };
        digitalTrainerController.setBreadboardEventHandlers(clickedHandler, enteredHandler, exitedHandler);

        final EventHandler<MouseEvent> clickedOnSocketHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                String currentToolClass = toolsAreaController.getCurrentTool().getClassificationClassName();
                SocketControl socketControl = (SocketControl) ((Circle) event.getSource()).getParent();
                if (currentToolClass.equals(Wire.class.getSimpleName()))
                {
                    handleWireClicked(socketControl);
                    handleWirePluggedAt(socketControl.getSocket());
                }
            }
        };
        final EventHandler<MouseEvent> enteredOnSocketHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String toolClass = toolsAreaController.getCurrentTool().getClassificationClassName();
                Circle socket = (Circle) event.getSource();
                if (toolClass.equals(Wire.class.getSimpleName()))
                    socket.setStyle("-fx-stroke: red;");
            }
        };
        final EventHandler<MouseEvent> exitedOnSocketHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String toolClass = toolsAreaController.getCurrentTool().getClassificationClassName();
                Circle socket = (Circle) event.getSource();
                if (toolClass.equals(Wire.class.getSimpleName()))
                    socket.setStyle("-fx-stroke: null;");
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

    private void handleWireClicked(Node nodeSocket)
    {
        final Bounds nodeBounds = nodeSocket.localToScene(nodeSocket.getBoundsInLocal());
        Point2D position = getPointAtCenterPane(nodeBounds);

        if (!Wire.isStartSet())
        {
            wireControl = new WireControl();
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

            digitalTrainerController.plugWire(wireControl);
        }
        else
        {
            wireControl.setEndX(position.getX());
            wireControl.setEndY(position.getY());
        }
    }

    private void handleWirePluggedAt(Socket socket)
    {
        Wire wire = wireControl.getWire();
        wire.plugAt(socket);
    }

    private void handleIntegratedCircuitEvent(EventType eventType, BreadboardSocketControl socket)
    {
        Tool currentTool = toolsAreaController.getCurrentTool();
        IntegratedCircuit chip = (IntegratedCircuit) currentTool.getClassification();
        if (socket.getRow() + chip.getRowSpan() <= digitalTrainerController.getBreadBoard().getRow())
        {
            int revertedColPosition = Math.abs(socket.getCol() - 4 - 1);
            if (revertedColPosition < chip.getColSpan() && revertedColPosition > 0)
            {
                for (int row = socket.getRow(); row < socket.getRow() + chip.getRowSpan(); row++)
                {
                    if (eventType.equals(MouseEvent.MOUSE_CLICKED))
                    {
                        final Bounds nodeBounds = socket.localToScene(socket.getBoundsInLocal());
                        Point2D position = getPointAtCenterPane(nodeBounds);
                        double xPosition = position.getX() + currentTool.getViewHotSpot().getX() + 2;
                        double yPosition = position.getY() + currentTool.getViewHotSpot().getY() - 1;

                        ImageView currentToolView = (ImageView) currentTool.getView().getChildren().get(0);
                        ImageView chipView = new ImageView(currentToolView.getImage());
                        chipView.relocate(xPosition, yPosition);
                        chipView.setMouseTransparent(false);

                        EventHandler<MouseEvent> enteredHandler = digitalTrainerController.getEnteredOnPluggedToolHandler();
                        EventHandler<MouseEvent> exitedHandler = digitalTrainerController.getExitedOnPluggedToolHandler();
                        chipView.addEventHandler(MouseEvent.MOUSE_ENTERED, enteredHandler);
                        chipView.addEventHandler(MouseEvent.MOUSE_EXITED, exitedHandler);

                        digitalTrainerController.plugChip(chipView);
                    }
                    else if (eventType.equals(MouseEvent.MOUSE_ENTERED))
                        socket.getHoleBox().setStyle("-fx-stroke: red;");
                    else if (eventType.equals(MouseEvent.MOUSE_EXITED))
                        socket.getHoleBox().setStyle("-fx-stroke: null;");
                }
            }

        }
    }

    private Point2D getPointAtCenterPane(Bounds nodeBounds)
    {
        double toolsAreaWidth = toolsArea.getWidth();

        double x = (nodeBounds.getMinX() + nodeBounds.getMaxX()) / 2 - toolsAreaWidth;
        double y = (nodeBounds.getMinY()+nodeBounds.getMaxY())/2;

        return new Point2D(x, y);
    }
}
