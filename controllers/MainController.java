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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

        toolsAreaController.currentTool().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (WireControl.isStartSet())
                {
                    digitalTrainerController.unplugTool(wireControl);
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

    private void setDigitalTrainerEventHandlers()
    {
        digitalTrainer.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();

                digitalTrainer.setCursor(currentTool.getCursor());

                digitalTrainer.getChildren().add(toolCursor);
                Point2D hotSpot = currentTool.getViewHotSpot();

                toolCursor.setTranslateX(event.getX() + hotSpot.getX());
                toolCursor.setTranslateY(event.getY() + hotSpot.getY());
            }
        });

        digitalTrainer.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                Point2D hotSpot = currentTool.getViewHotSpot();
                toolCursor.setTranslateX(event.getX() + hotSpot.getX());
                toolCursor.setTranslateY(event.getY() + hotSpot.getY());

                if (currentTool.getClassName().equals(Select.class.getSimpleName()))
                    digitalTrainerController.handleOnSelectToolMove(event.getX(), event.getY());
                else if (currentTool.getSuperClassName().equals(Wire.class.getSimpleName()))
                    digitalTrainerController.handleOnWireToolMove(event.getX(), event.getY());
            }
        });

        digitalTrainer.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                digitalTrainer.getChildren().remove(toolCursor);
            }
        });

        final EventHandler<MouseEvent> clickedHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                BreadboardSocketControl socketControl = (BreadboardSocketControl) event.getSource();
                Point2D position = getPointAtDigitalTrainer(socketControl);
                if (currentTool.getClassName().equals(IntegratedCircuit.class.getSimpleName()))
                {
                    digitalTrainerController.handleIntegratedCircuitEvent(event.getEventType(), currentTool,
                                                                          socketControl, position);
                }
                else if (currentTool.getSuperClassName().equals(Wire.class.getSimpleName()))
                {
                    Color wireColor = toolsAreaController.getWireColor();
                    digitalTrainerController.handleWireToolClicked(position, socketControl.getSoul(), wireColor);
                }
                else if (currentTool.getClassName().equals(Select.class.getSimpleName()))
                    digitalTrainerController.handleSelectToolClicked(socketControl.getSoul(), position);
            }
        };
        final EventHandler<MouseEvent> enteredHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                BreadboardSocketControl socket = (BreadboardSocketControl) event.getSource();
                if (currentTool.getClassName().equals(IntegratedCircuit.class.getSimpleName()))
                {
                    Point2D position = getPointAtDigitalTrainer(socket);
                    digitalTrainerController.handleIntegratedCircuitEvent(event.getEventType(), currentTool, socket,
                                                                          position);
                }
                else if (currentTool.getSuperClassName().equals(Wire.class.getSimpleName()))
                    digitalTrainerController.onEnteredOnSocket(socket.getSoul(), socket.getHoleBox());
                else if (currentTool.getClassName().equals(Select.class.getSimpleName()))
                    digitalTrainerController.handleOnSelectEnteredOnSocket(socket.getSoul(), socket.getHoleBox());
            }
        };
        final EventHandler<MouseEvent> exitedHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                BreadboardSocketControl socket = (BreadboardSocketControl) event.getSource();
                if (currentTool.getClassName().equals(IntegratedCircuit.class.getSimpleName()))
                {
                    Point2D position = getPointAtDigitalTrainer(socket);
                    digitalTrainerController.handleIntegratedCircuitEvent(event.getEventType(), currentTool, socket,
                                                                          position);
                }
                else if (currentTool.getSuperClassName().equals(Wire.class.getSimpleName()) ||
                         currentTool.getClassName().equals(Select.class.getSimpleName()))
                    digitalTrainerController.onExitedOnSocket(socket.getHoleBox());
            }
        };
        digitalTrainerController.setBreadboardEventHandlers(clickedHandler, enteredHandler, exitedHandler);

        final EventHandler<MouseEvent> clickedOnSocketHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                SocketControl socketControl = (SocketControl) ((Circle) event.getSource()).getParent();
                Point2D position = getPointAtDigitalTrainer(socketControl);
                if (currentTool.getSuperClassName().equals(Wire.class.getSimpleName()))
                {
                    Color wireColor = toolsAreaController.getWireColor();
                    digitalTrainerController.handleWireToolClicked(position, socketControl.getSoul(), wireColor);
                }
                else if (currentTool.getClassName().equals(Select.class.getSimpleName()))
                    digitalTrainerController.handleSelectToolClicked(socketControl.getSoul(), position);
            }
        };
        final EventHandler<MouseEvent> enteredOnSocketHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                Circle socketCircle = (Circle) event.getSource();
                SocketControl socketControl = (SocketControl) socketCircle.getParent();
                if (currentTool.getSuperClassName().equals(Wire.class.getSimpleName()))
                    digitalTrainerController.onEnteredOnSocket(socketControl.getSoul(), socketCircle);
                else if (currentTool.getClassName().equals(Select.class.getSimpleName()))
                    digitalTrainerController.handleOnSelectEnteredOnSocket(socketControl.getSoul(), socketCircle);
            }
        };
        final EventHandler<MouseEvent> exitedOnSocketHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tool currentTool = toolsAreaController.getCurrentTool();
                Circle socketCircle = (Circle) event.getSource();
                if (currentTool.getSuperClassName().equals(Wire.class.getSimpleName()) ||
                        currentTool.getClassName().equals(Select.class.getSimpleName()))
                    digitalTrainerController.onExitedOnSocket(socketCircle);
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
                    WireControl control = (WireControl) event.getSource();
                    digitalTrainerController.handleMoveOnPluggedWire(control, event.getX(), event.getY());
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

    private Point2D getPointAtDigitalTrainer(Node socketNode)
    {
        final Bounds nodeBounds = socketNode.localToScene(socketNode.getBoundsInLocal());

        double toolsAreaWidth = toolsArea.getWidth();

        double x = (nodeBounds.getMinX() + nodeBounds.getMaxX()) / 2 - toolsAreaWidth;
        double y = (nodeBounds.getMinY()+nodeBounds.getMaxY()) / 2 - generatorToggleArea.getHeight();

        return new Point2D(x, y);
    }
}
