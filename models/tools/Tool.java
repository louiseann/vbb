package vbb.models.tools;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * Created by owie on 12/11/14.
 */
public class Tool
{
    private String name;
    private Object classification;
    private Pane view;
    private Cursor cursor;
    private Button button;

    private static double xHotSpot;
    private static double yHotSpot;

    public Tool(String name)
    {
        setName(name);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setView(Node view)
    {
        this.view = new Pane(view);
    }

    public Pane getView()
    {
        return view;
    }

    public Object getClassification()
    {
        return classification;
    }

    public void setClassification(Object classification)
    {
        this.classification = classification;
    }

    public Button getButton()
    {
        return button;
    }

    public void setButton(Button button)
    {
        this.button = button;
    }

    public String getClassificationClassName()
    {
        return classification.getClass().getSimpleName();
    }
}
