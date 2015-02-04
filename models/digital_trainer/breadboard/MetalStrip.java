package vbb.models.digital_trainer.breadboard;

import vbb.models.digital_trainer.Socket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owie on 1/27/15.
 */
public class MetalStrip
{
    List<Socket> connectedSockets;

    public MetalStrip()
    {
        connectedSockets = new ArrayList<Socket>();
    }

    public void powerConnectedSockets(Socket source)
    {
        for (Socket socket : connectedSockets)
        {
            if (socket != source)
            {
                socket.powered().set(source.isPowered());
                System.out.println("fire! " + source.isPowered());
            }
        }
        System.out.println();
    }

    public void addSocket(Socket socket)
    {
        connectedSockets.add(socket);
    }

    public Socket getSocket(int index)
    {
        return connectedSockets.get(index);
    }
}
