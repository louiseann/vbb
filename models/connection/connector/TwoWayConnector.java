package vbb.models.connection.connector;

import vbb.models.tools.connectors.Wire;

/**
 * Created by owie on 2/6/15.
 */
public class TwoWayConnector extends Wire
{
    public TwoWayConnector()
    {
        super();

        getEndPoint1().setCanReceive(true);
        getEndPoint1().setCanSend(true);

        getEndPoint2().setCanReceive(true);
        getEndPoint2().setCanSend(true);
    }
}
