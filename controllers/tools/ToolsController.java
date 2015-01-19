package vbb.controllers.tools;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vbb.controllers.tools.controls.ColorPicker;
import vbb.models.tools.Select;
import vbb.models.tools.Tool;
import vbb.models.tools.Wire;
import vbb.models.tools.electronic_component.IntegratedCircuit;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by owie on 12/10/14.
 */
public class ToolsController
{
    @FXML
    private Button selectTool;
    @FXML
    private Button andChipTool, orChipTool, notChipTool, nandChipTool, norChipTool, xorChipTool, xnorChipTool;
    @FXML
    private Button wireTool;

    @FXML
    private Rectangle wireView;

    @FXML
    private ColorPicker wireColorPicker;


    private Rectangle wireViewCopy;

    private static ObjectProperty focusedToolButton;
    private static Map<Button, Tool> tools;

    @FXML
    public void initialize()
    {
        focusedToolButton = new SimpleObjectProperty();
        setFocusedToolButton(selectTool);
        focusToolButton(selectTool);

        wireView.setFill(Color.web(wireColorPicker.getSelectedColor()));

        tools = new LinkedHashMap<Button, Tool>();

        addTool(selectTool, "select", "select", new ImageView(new Image("/vbb/views/images/tools/cursor2.png")));

        addTool(andChipTool, "and", "ic", new ImageView(new Image("/vbb/views/images/tools/chips/and_tool.png")));
        addTool(orChipTool, "or", "ic", new ImageView(new Image("/vbb/views/images/tools/chips/or_tool.png")));
        addTool(notChipTool, "not", "ic", new ImageView(new Image("/vbb/views/images/tools/chips/not_tool.png")));
        addTool(nandChipTool, "nand", "ic", new ImageView(new Image("/vbb/views/images/tools/chips/nand_tool.png")));
        addTool(norChipTool, "nor", "ic", new ImageView(new Image("/vbb/views/images/tools/chips/nor_tool.png")));
        addTool(xorChipTool, "xor", "ic", new ImageView(new Image("/vbb/views/images/tools/chips/xor_tool.png")));
        addTool(xnorChipTool, "xnor", "ic", new ImageView(new Image("/vbb/views/images/tools/chips/xnor_tool.png")));

        addTool(wireTool, "wire", "wire", createWireCursor());

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

    private StackPane createWireCursor()
    {
        Rectangle wireBG = new Rectangle(21, 21, Color.TRANSPARENT);
        wireViewCopy = new Rectangle(5, 25, wireView.getFill());
        wireViewCopy.setRotate(45);

        return new StackPane(wireBG, wireViewCopy);
    }

    private void changeWireColor(Color color)
    {
        wireView.setFill(color);
        wireViewCopy.setFill(color);
    }

    // end of wire methods

    private static void setFocusedToolButton(Object toolButton)
    {
        focusedToolButton.set(toolButton);
    }

    private static Object getFocusedToolButton()
    {
        return focusedToolButton.get();
    }

    public static ObjectProperty currentTool()
    {
        return focusedToolButton;
    }

    public static Tool getCurrentTool()
    {
        return tools.get(currentToolButton());
    }

    private static Button currentToolButton()
    {
        return (Button) getFocusedToolButton();
    }

    private static void addTool(Button toolButton, String toolName, String classification, Node toolView)
    {
        Tool tool = createTool(toolName, classification, toolView);
        tools.put(toolButton, tool);
    }

    private static Tool createTool(String name, String classification, Node view)
    {
        Tool tool = new Tool(name);
        tool.setView(view);

        if (classification.equals("select"))
            tool.setClassification(new Select());
        else if (classification.equals("ic"))
            tool.setClassification(new IntegratedCircuit());
        else if (classification.equals("wire"))
            tool.setClassification(new Wire());

        return tool;
    }
}