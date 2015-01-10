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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import vbb.tools.ToolsController;

public class MainController
{
    @FXML
    private BorderPane digitalTrainerPane;
    @FXML
    private ToolsController toolsController;

    private Pane toolCursor;

    @FXML
    public void initialize()
    {
        setUpToolCursorImage();

        digitalTrainerPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                digitalTrainerPane.setCursor(Cursor.NONE);
                digitalTrainerPane.getChildren().add(toolCursor);
                moveTool(event.getX(), event.getY());
            }
        });

        digitalTrainerPane.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                digitalTrainerPane.getChildren().remove(toolCursor);
            }
        });

        digitalTrainerPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                moveTool(event.getX(), event.getY());
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
        toolCursor = toolsController.getSelectedToolView(toolsController.currentToolButton()).getToolView();
        toolCursor.setMouseTransparent(true);
    }

    private void moveTool(double x, double y)
    {
        toolCursor.setTranslateX(x);
        toolCursor.setTranslateY(y);
    }
}
