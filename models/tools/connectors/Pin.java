package vbb.models.tools.connectors;

import vbb.models.tools.connectors.end_point.EndPoint;

/**
 * Created by owie on 2/3/15.
 */
public class Pin extends Connector
{
    public Pin()
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
