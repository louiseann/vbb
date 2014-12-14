package vbb;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import vbb.tools.ToolsController;

public class MainController
{
    @FXML
    private BorderPane mainPane;
    @FXML
    private StackPane digitalTrainerPane;
    @FXML
    private VBox tools;
    @FXML
    private ToolsController toolsController;

    private Canvas digitalTrainerArea;

    @FXML
    public void initialize()
    {
        digitalTrainerPane.getChildren().add(digitalTrainerArea = new Canvas(digitalTrainerPane.getWidth(), digitalTrainerPane.getHeight()));
        final GraphicsContext digitalTrainerAreaContext = digitalTrainerArea.getGraphicsContext2D();

        //setDigitalTrainerPaneCursor(toolsController.getCurrentTool());

        digitalTrainerPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("hey!");
                showTool(digitalTrainerAreaContext, event.getX(), event.getY());
            }
        });

        toolsController.currentTool().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object prevSelectedTool, Object selectedTool) {
                //setDigitalTrainerPaneCursor(selectedTool);
            }
        });
    }

    private void setDigitalTrainerPaneCursor(Object tool)
    {
        Image toolImage = toolsController.getSelectedToolView((Button) tool).getToolImage();
        ImageCursor toolCursor = new ImageCursor(toolImage, 0, 0);
        toolCursor.getBestSize(100, 100);
        digitalTrainerPane.setCursor(new ImageCursor(toolImage, 0, 0));
    }

    private Image getCurrentToolImage(Object tool)
    {
        Image toolImage = toolsController.getSelectedToolView((Button) tool).getToolImage();
        return toolImage;
    }

    private void showTool(GraphicsContext graphicsContext, double x, double y)
    {
        graphicsContext.drawImage(getCurrentToolImage(toolsController.getCurrentTool()), x, y);
    }
}
