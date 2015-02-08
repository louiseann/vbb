package vbb.models.connection.connector;

import vbb.models.connection.end_point.EndPoint;
import vbb.models.tools.connectors.Wire;

/**
 * Created by owie on 2/6/15.
 */
public class OneWayConnector extends Wire
{
    public OneWayConnector()
    {
        super();

        getEndPoint1().setCanReceive(false);
        getEndPoint1().setCanSend(true);

        getEndPoint2().setCanReceive(true);
        getEndPoint2().setCanSend(false);
    }

    public EndPoint getReceiverEndPoint()
    {
        return getEndPoint1().canReceive() ? getEndPoint1() : getEndPoint2();
    }

    public EndPoint getSenderEndPoint()
    {
        return getEndPoint1().canSend() ? getEndPoint1() : getEndPoint2();
    }
}
