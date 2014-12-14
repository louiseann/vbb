package vbb.tools;

import javafx.scene.image.Image;

/**
 * Created by owie on 12/11/14.
 */
public class SelectedToolView
{
    private String toolName;
    private Image toolImage;

    public SelectedToolView(String toolName, Image toolImage)
    {
        setToolName(toolName);
        setToolImage(toolImage);
    }

    public void setToolName(String name)
    {
        toolName = name;
    }

    public String getToolName()
    {
        return toolName;
    }

    public void setToolImage(Image image)
    {
        toolImage = image;
    }

    public Image getToolImage()
    {
        return toolImage;
    }
}
