package vbb.controllers.tools;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vbb.controllers.tools.controls.ColorPicker;
import vbb.models.tools.Tool;
import vbb.models.tools.VBbTool;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by owie on 12/10/14.
 */
public class ToolsController
{
    @FXML
    private VBox toolsArea;
    @FXML
    private Button selectButton;
    @FXML
    private Button andChipButton, orChipButton, notChipButton, nandChipButton, norChipButton, xorChipButton,
                   xnorChipButton;
    @FXML
    private Button wireButton;

    @FXML
    private Rectangle wireToolView;

    @FXML
    private ColorPicker wireColorPicker;


    private Rectangle wireToolViewCopy;

    private ObjectProperty focusedToolButton;
    private Map<Button, Tool> tools;

    @FXML
    public void initialize()
    {
        focusedToolButton = new SimpleObjectProperty();
        setFocusedToolButton(selectButton);
        focusToolButton(selectButton);

        wireToolView.setFill(Color.web(wireColorPicker.getSelectedColor()));
        VBbTool.Wire().setView(createWireView());

        tools = new LinkedHashMap<Button, Tool>();
        populateTools();

        wireColorPicker.selectedColor().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldSelectedColor, String newSelectedColor) {
                changeWireColor(Color.web(newSelectedColor));
            }
        });
    }

    @FXML
    public void handleToolSelection(ActionEvent event)
    {
        Button toolSelected = ((Button) event.getSource());

        if(!currentToolButton().equals(toolSelected))
        {
            unfocusToolButton(currentToolButton());
            setFocusedToolButton(toolSelected);
            focusToolButton(toolSelected);
        }
    }

    private void focusToolButton(Button tool)
    {
        tool.setStyle("-fx-background-color: #ffffff;\n" +
                "-fx-border-color: #b7b7b7;\n" +
                "-fx-border-width: 1px;");
    }

    private void unfocusToolButton(Button tool)
    {
        tool.setStyle("-fx-background-color: transparent;");
    }

    // wire //

    private StackPane createWireView()
    {
        Rectangle wireBG = new Rectangle(21, 21, Color.TRANSPARENT);
        wireToolViewCopy = new Rectangle(5, 25, wireToolView.getFill());
        wireToolViewCopy.setRotate(45);

        Image pointer = new Image("/vbb/views/images/tools/wire_pointer.png");
        ImageView pointerView = new ImageView(pointer);
        Dimension2D cursorDimension = ImageCursor.getBestSize(pointer.getWidth(), pointer.getHeight());
        pointerView.setFitWidth(cursorDimension.getWidth());
        pointerView.setFitHeight(cursorDimension.getHeight());

        return new StackPane(wireBG, pointerView, wireToolViewCopy);
    }

    private void changeWireColor(Color color)
    {
        wireToolView.setFill(color);
        wireToolViewCopy.setFill(color);
    }

    // end of wire methods

    private void setFocusedToolButton(Object toolButton)
    {
        focusedToolButton.set(toolButton);
    }

    private Object getFocusedToolButton()
    {
        return focusedToolButton.get();
    }

    public ObjectProperty currentTool()
    {
        return focusedToolButton;
    }

    public Tool getCurrentTool()
    {
        return tools.get(currentToolButton());
    }

    private Button currentToolButton()
    {
        return (Button) getFocusedToolButton();
    }

    private void populateTools()
    {
        tools.put(selectButton, VBbTool.Select());
        tools.put(andChipButton, VBbTool.AndChip());
        tools.put(orChipButton, VBbTool.OrChip());
        tools.put(notChipButton, VBbTool.NotChip());
        tools.put(nandChipButton, VBbTool.NandChip());
        tools.put(norChipButton, VBbTool.NorChip());
        tools.put(xorChipButton, VBbTool.XorChip());
        tools.put(xnorChipButton, VBbTool.XnorChip());
        tools.put(wireButton, VBbTool.Wire());
    }

    public Color getWireColor()
    {
        return (Color) wireToolView.getFill();
    }
}