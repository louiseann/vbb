package vbb.tools;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    private Rectangle color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12;
    @FXML
    private Rectangle color1BG, color2BG, color3BG, color4BG, color5BG, color6BG, color7BG, color8BG, color9BG,
                      color10BG, color11BG, color12BG;


    private Rectangle wireViewCopy;

    private ObjectProperty currentTool;
    private Map<Button, Tool> tools;

    @FXML
    public void initialize()
    {
        currentTool = new SimpleObjectProperty();
        setCurrentTool(selectTool);
        focusToolButton(selectTool);

        tools = new LinkedHashMap<Button, Tool>();

        addTool(selectTool, "select", new ImageView(new Image("/vbb/images/tools/select.png")));

        addTool(andChipTool, "and", new ImageView(new Image("/vbb/images/tools/chips/and_tool.png")));
        addTool(orChipTool, "or", new ImageView(new Image("/vbb/images/tools/chips/or_tool.png")));
        addTool(notChipTool, "not", new ImageView(new Image("/vbb/images/tools/chips/not_tool.png")));
        addTool(nandChipTool, "nand", new ImageView(new Image("/vbb/images/tools/chips/nand_tool.png")));
        addTool(norChipTool, "nor", new ImageView(new Image("/vbb/images/tools/chips/nor_tool.png")));
        addTool(xorChipTool, "xor", new ImageView(new Image("/vbb/images/tools/chips/xor_tool.png")));
        addTool(xnorChipTool, "xnor", new ImageView(new Image("/vbb/images/tools/chips/xnor_tool.png")));

        wireView.setFill(color1.getFill());
        focusWireColor(color1BG);

        addTool(wireTool, "wire", createWireCursor());

        color1BG.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeWireColor(color1, color1BG);
            }
        });
        color2BG.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeWireColor(color2, color2BG);
            }
        });
        color3BG.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeWireColor(color3, color3BG);
            }
        });
        color4BG.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeWireColor(color4, color4BG);
            }
        });
        color5BG.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeWireColor(color5, color5BG);
            }
        });
        color6BG.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeWireColor(color6, color6BG);
            }
        });
        color7BG.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeWireColor(color7, color7BG);
            }
        });
        color8BG.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeWireColor(color8, color8BG);
            }
        });
        color9BG.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeWireColor(color9, color9BG);
            }
        });
        color10BG.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeWireColor(color10, color10BG);
            }
        });
        color11BG.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeWireColor(color11, color11BG);
            }
        });
        color12BG.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeWireColor(color12, color12BG);
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
            setCurrentTool(toolSelected);
            focusToolButton(toolSelected);
        }
    }

    private void setCurrentTool(Object currentTool)
    {
        this.currentTool.set(currentTool);
    }

    public Object getCurrentTool()
    {
        return currentTool.get();
    }

    public ObjectProperty currentTool()
    {
        return currentTool;
    }

    public Button currentToolButton()
    {
        return (Button) getCurrentTool();
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

    public Tool getSelectedToolView(Button tool)
    {
        return tools.get(tool);
    }

    private void addTool(Button toolButton, String toolName, Node toolView)
    {
        tools.put(toolButton, createTool(toolName, toolView));
    }

    private static Tool createTool(String toolName, Node toolView)
    {
        Tool tool = new Tool(toolName);
        tool.setToolView(toolView);

        return tool;
    }

    // wire //

    private StackPane createWireCursor()
    {
        Rectangle wireBG = new Rectangle(24, 23, Color.TRANSPARENT);
        wireViewCopy = new Rectangle(5, 30, wireView.getFill());
        wireViewCopy.setRotate(45);

        return new StackPane(wireBG, wireViewCopy);
    }

    private void focusWireColor(Rectangle colorBG)
    {
        colorBG.setStyle("-fx-stroke: #000000; -fx-stroke-type: inside;");
    }

    private void unfocusWireColor(Rectangle colorBG)
    {
        colorBG.setStyle("-fx-stroke: #ffffff; -fx-stroke-type: inside;");
    }

    private Rectangle getRectangleWith(Color color)
    {
        if (color1.getFill().equals(color))
            return color1BG;
        else if (color2.getFill().equals(color))
            return color2BG;
        else if (color3.getFill().equals(color))
            return color3BG;
        else if (color4.getFill().equals(color))
            return color4BG;
        else if (color5.getFill().equals(color))
            return color5BG;
        else if (color6.getFill().equals(color))
            return color6BG;
        else if (color7.getFill().equals(color))
            return color7BG;
        else if (color8.getFill().equals(color))
            return color8BG;
        else if (color9.getFill().equals(color))
            return color9BG;
        else if (color10.getFill().equals(color))
            return color10BG;
        else if (color11.getFill().equals(color))
            return color11BG;
        else if (color12.getFill().equals(color))
            return color12BG;
        else
            return null;
    }

    private void changeWireColor(Rectangle color, Rectangle colorBG)
    {
        focusWireColor(colorBG);
        unfocusWireColor(getRectangleWith((Color) wireView.getFill()));

        wireView.setFill(color.getFill());
        wireViewCopy.setFill(wireView.getFill());
    }
}
