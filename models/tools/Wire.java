package vbb.models.tools;

import vbb.models.digital_trainer.Socket;

/**
 * Created by owie on 1/13/15.
 */
public class Wire
{
    private Socket socketAtEndPoint;
    private Socket socketAtOtherEndPoint;

    private static boolean startSet = false;

    public static boolean isStartSet()
    {
        return startSet;
    }

    public Socket getSocketAtEndPoint()
    {
        return socketAtEndPoint;
    }

    private void setSocketAtEndPoint(Socket socket)
    {
        this.socketAtEndPoint = socket;
    }

    public Socket getSocketAtOtherEndPoint()
    {
        return socketAtOtherEndPoint;
    }

    private void setSocketAtOtherEndPoint(Socket socket)
    {
        this.socketAtOtherEndPoint = socket;
    }

    public void plugAt(Socket socket)
    {
        if (!isStartSet())
        {
            setSocketAtEndPoint(socket);
            startSet = true;
        }
        else
        {
            setSocketAtOtherEndPoint(socket);
            startSet = false;
        }

    }
}