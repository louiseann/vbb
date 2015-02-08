package vbb.models.connection.connector;

import vbb.models.Control;
import vbb.models.connection.end_point.EndPoint;

/**
 * Created by owie on 2/3/15.
 */
public abstract class Connector
{
    public static Connector ONE_WAY = new OneWayConnector();
    public static Connector TWO_WAY = new TwoWayConnector();

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

    public static Connector createConnection(Connector connectorType, Control control1, Control control2)
    {
        connectorType.getEndPoint1().setControlConnected(control1);
        connectorType.getEndPoint2().setControlConnected(control2);

        return connectorType;
    }
}
