package vbb.models.tools.connectors;

import vbb.models.connection.connector.OneWayConnector;
import vbb.models.connection.end_point.EndPoint;

/**
 * Created by owie on 2/3/15.
 */
public class Pin extends OneWayConnector
{
    public Pin()
    {
        super();
    }

    public EndPoint getHangingPoint()
    {
        if (getReceiverEndPoint().getControlConnected() != null && getSenderEndPoint().getControlConnected() == null)
            return getSenderEndPoint();
        else if (getSenderEndPoint().getControlConnected() != null && getReceiverEndPoint().getControlConnected() == null)
            return getReceiverEndPoint();
        else
            return null;
    }
}
