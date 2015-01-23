package vbb.models.tools;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

/**
 * Created by owie on 1/21/15.
 */
public class WireView
{
    private Point2D startPoint;
    private Point2D endPoint;

    private Line line;

    public Point2D getStartPoint()
    {
        return startPoint;
    }

    public void setStartPoint(double x, double y)
    {
        endPoint = new Point2D(x, y);
    }

    public Point2D getEndPoint()
    {
        return startPoint;
    }

    public void setEndPoint(double x, double y)
    {
        endPoint = new Point2D(x, y);
    }

    public Line getLine()
    {
        return line;
    }

    public void reset()
    {
        setStartPoint(-1, -1);
        setEndPoint(-1, -1);
    }
}
