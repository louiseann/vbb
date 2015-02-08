package vbb.models.tools;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private Point2D viewHotSpot;

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

    public void setClassification(Object classification)
    {
        this.classification = classification;
    }

    public Object getClassification()
    {
        return classification;
    }

    public String getClassName()
    {
        return classification.getClass().getSimpleName();
    }

    public String getSuperClassName()
    {
        return classification.getClass().getSuperclass().getSimpleName();
    }

    public void setView(Node view)
    {
        this.view = new Pane(view);
        this.view.setMouseTransparent(true);
    }

    public void setView(Node view, double xHotSpot, double yHotSpot)
    {
        this.view = new Pane(view);
        this.view.setMouseTransparent(true);

        this.viewHotSpot = new Point2D(xHotSpot, yHotSpot);
    }

    public Pane getView()
    {
        return view;
    }

    public Image getViewImage()
    {
        ImageView imageView = (ImageView) view.getChildren().get(0);
        return imageView.getImage();
    }

    public void setCursor(Cursor cursor)
    {
        this.cursor = cursor;
    }

    public Cursor getCursor()
    {
        return cursor;
    }

    public void setViewHotSpot(double x, double y)
    {
        viewHotSpot = new Point2D(x, y);
    }

    public Point2D getViewHotSpot()
    {
        return viewHotSpot;
    }
}
