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

        getEndPoint1().setCanReceive(true);
        getEndPoint1().setCanSend(false);

        getEndPoint2().setCanReceive(false);
        getEndPoint2().setCanSend(true);
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
