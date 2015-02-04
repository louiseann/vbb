package vbb.models.tools.connectors;

import vbb.models.tools.connectors.end_point.EndPoint;

/**
 * Created by owie on 2/3/15.
 */
public abstract class Connector
{
    private EndPoint endPoint1;
    private EndPoint endPoint2;

    public Connector()
    {
        endPoint1 = new EndPoint();
        endPoint2 = new EndPoint();
    }

    public EndPoint getEndPoint1()
    {
        return endPoint1;
    }

    public EndPoint getEndPoint2()
    {
        return endPoint2;
    }
}
