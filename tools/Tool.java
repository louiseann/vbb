package vbb.tools;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Created by owie on 12/11/14.
 */
public class Tool
{
    private String toolName;
    private Pane toolView;

    public Tool(String toolName)
    {
        setToolName(toolName);
    }

    public void setToolName(String name)
    {
        toolName = name;
    }

    public String getToolName()
    {
        return toolName;
    }

    public void setToolView(Node toolView)
    {
        this.toolView = new Pane(toolView);
    }

    public Pane getToolView()
    {
        return toolView;
    }
}
