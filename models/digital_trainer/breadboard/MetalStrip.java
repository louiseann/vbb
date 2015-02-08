package vbb.models.digital_trainer.breadboard;

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

    public List<Connector> connectOccupiedSockets(BreadboardSocket source)
    {
        List<Connector> connections = new ArrayList<Connector>(5);
        for (BreadboardSocket socket : connectedSockets)
        {
            if (socket != source && socket.isOccupied())
            {
                System.out.println("fire!");

                Connector connection = new TwoWayConnector();
                connection.getEndPoint1().setControlConnected(source);
                connection.getEndPoint2().setControlConnected(socket);

                connections.add(connection);
            }
        }
        System.out.println();
        return connections;
    }

    public void addSocket(BreadboardSocket socket)
    {
        connectedSockets.add(socket);
    }

    public BreadboardSocket getSocket(int index)
    {
        return connectedSockets.get(index);
    }
}
