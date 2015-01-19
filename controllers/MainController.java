package vbb.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import vbb.controllers.tools.ToolsController;

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

        final Image wireImage = new Image("/vbb/views/images/tools/wire_pointer2.png");
        final double cursorHeight = ImageCursor.getBestSize(wireImage.getWidth(), wireImage.getHeight()).getHeight();
        final double yHotSpot = cursorHeight;
        final double xHotSpot = 3;

        digitalTrainerPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (toolsController.getCurrentTool().getClassificationClassName().equals("Wire"))
                {
                    ImageCursor wirePointer = new ImageCursor(wireImage, 0, wireImage.getHeight());
                    digitalTrainerPane.setCursor(wirePointer);
                }
                else
                    digitalTrainerPane.setCursor(Cursor.NONE);

                digitalTrainerPane.getChildren().add(toolCursor);
                moveTool(event.getX()+xHotSpot, event.getY()-yHotSpot);
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
                moveTool(event.getX()+xHotSpot, event.getY()-yHotSpot);
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
}
