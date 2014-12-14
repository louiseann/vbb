package vbb.tools;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by owie on 12/10/14.
 */
public class ToolsController
{
    @FXML
    Button selectTool;
    @FXML
    Button andChipTool;
    @FXML
    Button orChipTool;
    @FXML
    Button notChipTool;
    @FXML
    Button nandChipTool;
    @FXML
    Button norChipTool;
    @FXML
    Button xorChipTool;
    @FXML
    Button xnorChipTool;
    @FXML
    Button wireTool;

    private ObjectProperty currentTool;
    private Map<Button, SelectedToolView> toolViews;

    @FXML
    public void initialize()
    {
        currentTool = new SimpleObjectProperty();
        setCurrentTool(selectTool);
        focusToolButton(selectTool);

        toolViews = new LinkedHashMap<Button, SelectedToolView>();
        toolViews.put(selectTool, new SelectedToolView("select", new Image("/vbb/images/tools/select.png")));
        toolViews.put(andChipTool, new SelectedToolView("and", new Image("/vbb/images/tools/chips/chip.png")));
        toolViews.put(orChipTool, new SelectedToolView("or", new Image("/vbb/images/tools/chips/chip.png")));
        toolViews.put(notChipTool, new SelectedToolView("not", new Image("/vbb/images/tools/chips/chip.png")));
        toolViews.put(nandChipTool, new SelectedToolView("nand", new Image("/vbb/images/tools/chips/chip.png")));
        toolViews.put(norChipTool, new SelectedToolView("nor", new Image("/vbb/images/tools/chips/chip.png")));
        toolViews.put(xorChipTool, new SelectedToolView("xor", new Image("/vbb/images/tools/chips/chip.png")));
        toolViews.put(xnorChipTool, new SelectedToolView("xnor", new Image("/vbb/images/tools/chips/chip.png")));
        toolViews.put(wireTool, new SelectedToolView("wire", new Image("/vbb/images/tools/wire.png")));
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

    public SelectedToolView getSelectedToolView(Button tool)
    {
        return toolViews.get(tool);
    }
}
