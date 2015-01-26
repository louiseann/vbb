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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import vbb.controllers.digital_trainer.DigitalTrainerController;
import vbb.controllers.digital_trainer.controls.breadboard.BreadboardSocketControl;
import vbb.controllers.tools.ToolsController;
import vbb.models.tools.Tool;
import vbb.models.tools.Wire;
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
    private StackPane digitalTrainer;

    private Pane toolCursor;

    private Line wireLine;

    @FXML
    public void initialize()
    {
        TTL74SeriesIC.AND_7408();
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
                        wireLine.setEndX(event.getX());
                        wireLine.setEndY(event.getY());
                    }
                }
            }
        });

        toolsAreaController.currentTool().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
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
                BreadboardSocketControl socket = (BreadboardSocketControl) event.getSource();
                if (currentToolClass.equals(IntegratedCircuit.class.getSimpleName()))
                    handleIntegratedCircuitEvent(event.getEventType(), socket);
                else if (currentToolClass.equals(Wire.class.getSimpleName()))
                    handleWireClicked(socket);
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
                if (currentToolClass.equals(Wire.class.getSimpleName()))
                    handleWireClicked((Node) event.getSource());
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
    }

    private void handleWireClicked(Node socket)
    {
        final Bounds nodeBounds = socket.localToScene(socket.getBoundsInLocal());
        Point2D position = getPointAtCenterPane(nodeBounds);

        if (!Wire.isStartSet())
        {
            wireLine = new Line();
            wireLine.setStroke(toolsAreaController.getWireColor());
            wireLine.setStrokeWidth(4);

            wireLine.setStartX(position.getX());
            wireLine.setStartY(position.getY());

            wireLine.setEndX(position.getX());
            wireLine.setEndY(position.getY());

            digitalTrainerController.applyTool(wireLine);
            Wire.startSet(true);
        }
        else
        {
            wireLine.setEndX(position.getX());
            wireLine.setEndY(position.getY());

            Wire.startSet(false);
        }
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

                        digitalTrainerController.applyTool(chipView);
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
