package vbb.models.connection.end_point;

import vbb.models.Control;

/**
 * Created by owie on 2/3/15.
 */
public class EndPoint
{
    private boolean canReceive;
    private boolean canSend;

    private Control controlConnected;

    public EndPoint()
    {
        canReceive = false;
        canSend = false;
    }

    public boolean canReceive()
    {
        return canReceive;
    }

    public void setCanReceive(boolean canReceive)
    {
        this.canReceive = canReceive;
    }

    public boolean canSend()
    {
        return canSend;
    }

    public void setCanSend(boolean canSend)
    {
        this.canSend = canSend;
    }

    public Control getControlConnected()
    {
        return controlConnected;
    }

    public void setControlConnected(Control control)
    {
        this.controlConnected = control;
    }
}
