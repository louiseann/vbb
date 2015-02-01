package vbb.models.tools;

import vbb.models.digital_trainer.Socket;

/**
 * Created by owie on 1/13/15.
 */
public class Wire
{
    private Socket socketAtEndPoint;
    private Socket socketAtOtherEndPoint;

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

    public void plug(Socket atSocket)
    {
        if (socketAtEndPoint == null)
            setSocketAtEndPoint(atSocket);
        else
        {
            setSocketAtOtherEndPoint(atSocket);
            setConnection();
        }
    }

    private void setConnection()
    {
        socketAtEndPoint.setSocketConnected(socketAtOtherEndPoint);
    }
}