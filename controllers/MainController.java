package vbb.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import vbb.Main;
import vbb.controllers.digital_trainer.DigitalTrainerController;
import vbb.controllers.digital_trainer.controls.breadboard.SocketHoleControl;
import vbb.controllers.tools.ToolsController;
import vbb.models.tools.Wire;
import vbb.models.tools.WireView;
import vbb.models.tools.electronic_component.IntegratedCircuit;

public class MainController
{
    @FXML
    private ToolsController toolsController;

    @FXML
    private StackPane centerPane;
    @FXML
    private DigitalTrainerController digitalTrainerController;
    @FXML
    private StackPane digitalTrainer;
    @FXML
    private Pane drawingPane;

    private Pane toolCursor;

    private Line wireLine;

    private Point2D windowCoord;
    private Point2D sceneCoord;

    @FXML
    public void initialize()
    {
        setUpToolCursorImage();

        final Image wireImage = new Image("/vbb/views/images/tools/wire_pointer2.png");
        final double cursorHeight = ImageCursor.getBestSize(wireImage.getWidth(), wireImage.getHeight()).getHeight();
        final double yHotSpot = cursorHeight;
        final double xHotSpot = 3;

        final EventHandler<MouseEvent> onSocketClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                String currentToolClass = toolsController.getCurrentTool().getClassificationClassName();
                if (currentToolClass.equals(IntegratedCircuit.class.getSimpleName()))
                    System.out.println("Integrated Circuit");
                else if (currentToolClass.equals(Wire.class.getSimpleName()))
                {
                    SocketHoleControl socketHole = (SocketHoleControl) event.getSource();
                    Bounds nodeBounds = socketHole.localToScene(socketHole.getBoundsInLocal());
                    double toolsAreaWidth = toolsController.getToolsArea().getWidth();

                    if (!Wire.isStartSet())
                    {
                        wireLine = new Line();
                        wireLine.setStrokeWidth(4);

                        wireLine.setStartX((nodeBounds.getMinX()+nodeBounds.getMaxX())/2-toolsAreaWidth);
                        wireLine.setStartY((nodeBounds.getMinY()+nodeBounds.getMaxY())/2);

                        wireLine.setEndX((nodeBounds.getMinX() + nodeBounds.getMaxX()) / 2 - toolsAreaWidth);
                        wireLine.setEndY((nodeBounds.getMinY() + nodeBounds.getMaxY()) / 2);

                        drawingPane.getChildren().add(wireLine);
                        Wire.startSet(true);
                    }
                    else
                    {
                        wireLine.setEndX((nodeBounds.getMinX() + nodeBounds.getMaxX()) / 2 - toolsAreaWidth);
                        wireLine.setEndY((nodeBounds.getMinY()+nodeBounds.getMaxY())/2);

                        Wire.startSet(false);
                    }
                }
            }
        };

        digitalTrainerController.setOnClickOnSocket(onSocketClicked);
        digitalTrainerController.getBreadboard().setOnClickOnSocket(onSocketClicked);

        centerPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (toolsController.getCurrentTool().getClassificationClassName().equals("Wire")) {
                    ImageCursor wirePointer = new ImageCursor(wireImage, 0, wireImage.getHeight());
                    centerPane.setCursor(wirePointer);
                } else
                    centerPane.setCursor(Cursor.NONE);

                centerPane.getChildren().add(toolCursor);
                moveTool(event.getX() + xHotSpot, event.getY() - yHotSpot);
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
                moveTool(event.getX() + xHotSpot, event.getY() - yHotSpot);
                String currentToolClass = toolsController.getCurrentTool().getClassificationClassName();
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

        toolsController.currentTool().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                setUpToolCursorImage();
            }
        });
    }

    private void setUpToolCursorImage()
    {
        toolCursor = toolsController.getCurrentTool().getView();
        toolCursor.setMouseTransparent(true);
    }

    private void moveTool(double x, double y)
    {
        toolCursor.setTranslateX(x);
        toolCursor.setTranslateY(y);
    }

    public void setWindowCoord(double x, double y)
    {
        windowCoord = new Point2D(x, y);
    }

    public void setSceneCoord(double x, double y)
    {
        sceneCoord = new Point2D(x, y);
    }
}
