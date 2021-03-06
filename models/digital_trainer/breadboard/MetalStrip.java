package vbb.models.digital_trainer.breadboard;

import vbb.models.Circuit;
import vbb.models.Voltage;
import vbb.models.connection.connector.Connector;
import vbb.models.connection.connector.TwoWayConnector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owie on 1/27/15.
 */
public class MetalStrip
{
    List<BreadboardSocket> connectedSockets;

    public MetalStrip()
    {
        connectedSockets = new ArrayList<BreadboardSocket>();
    }

    public void connectOccupiedSockets(BreadboardSocket source, Circuit circuit)
    {
        Voltage highestVoltage = getHighestVoltage();

        for (BreadboardSocket socket : connectedSockets)
        {
            if (socket != source && socket.isOccupied())
            {
                if (!socket.runningVoltage().equals(highestVoltage))
                    socket.setRunningVoltage(highestVoltage);

                Connector connection = new TwoWayConnector();
                connection.getEndPoint1().setControlConnected(source);
                connection.getEndPoint2().setControlConnected(socket);

                circuit.add(connection);
            }
        }
    }

    public void addSocket(BreadboardSocket socket)
    {
        connectedSockets.add(socket);
    }

    public BreadboardSocket getSocket(int index)
    {
        return connectedSockets.get(index);
    }

    private Voltage getHighestVoltage()
    {
        Voltage notHigh = Voltage.NONE;
        for (BreadboardSocket socket : connectedSockets)
        {
            if (socket.isOccupied())
            {
                if (socket.runningVoltage().equals(Voltage.HIGH))
                    return Voltage.HIGH;
                else if (socket.runningVoltage().equals(Voltage.LOW))
                    notHigh = Voltage.LOW;
            }
        }

        return notHigh;
    }
}
