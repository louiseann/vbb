package vbb.models.tools.connectors;

import vbb.models.connection.connector.Connector;
import vbb.models.connection.connector.OneWayConnector;
import vbb.models.digital_trainer.Socket;
import vbb.models.digital_trainer.breadboard.BreadboardSocket;

/**
 * Created by owie on 1/13/15.
 */
public abstract class Wire extends Connector
{
    public static Wire finalize(Wire wire)
    {
        Socket point1Control = (Socket) wire.getEndPoint1().getControlConnected();
        Socket point2Control = (Socket) wire.getEndPoint2().getControlConnected();

        if (point1Control.getOuterConnection().canEnter() != point1Control.getOuterConnection().canExit())
            return createOneWayWire(point1Control, point2Control);

        else if (point2Control.getOuterConnection().canEnter() != point2Control.getOuterConnection().canExit())
            return createOneWayWire(point2Control, point1Control);

        else
            return wire;
    }

    private static Wire createOneWayWire(Socket referenceControl, Socket otherControl)
    {
        Wire wire = new OneWayConnector();

        if (referenceControl.getOuterConnection().canExit())
            setEndPointControls(wire, referenceControl, otherControl);
        else
            setEndPointControls(wire, otherControl, referenceControl);

        return wire;
    }

    private static void setEndPointControls(Wire wire, Socket point1Control, Socket point2Control)
    {
        wire.getEndPoint1().setControlConnected(point1Control);
        wire.getEndPoint2().setControlConnected(point2Control);
    }
}