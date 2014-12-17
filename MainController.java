package vbb;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import vbb.tools.ToolsController;

public class MainController
{
    @FXML
    private BorderPane mainPane;
    @FXML
    private Group centerArea;
    @FXML
    private BorderPane digitalTrainerPane;
    @FXML
    private VBox tools;
    @FXML
    private ToolsController toolsController;

    private Canvas toolCanvas;

    @FXML
    public void initialize()
    {
        setUpToolCursorImage();

        digitalTrainerPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                digitalTrainerPane.setCursor(Cursor.NONE);
                digitalTrainerPane.getChildren().add(toolCanvas);
                toolCanvas.setTranslateX(event.getX());
                toolCanvas.setTranslateY(event.getY());
            }
        });

        digitalTrainerPane.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                digitalTrainerPane.getChildren().remove(toolCanvas);
            }
        });

        digitalTrainerPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toolCanvas.setTranslateX(event.getX());
                toolCanvas.setTranslateY(event.getY());
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
        Image toolImage = toolsController.getSelectedToolView(toolsController.currentToolButton()).getToolImage();
        toolCanvas = new Canvas(toolImage.getWidth(), toolImage.getHeight());
        final GraphicsContext toolCanvasGraphics = toolCanvas.getGraphicsContext2D();
        toolCanvasGraphics.drawImage(toolImage, 0, 0);
    }
}
