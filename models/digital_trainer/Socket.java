package vbb.models.digital_trainer;

import vbb.models.Control;
import vbb.models.connection.Connection;

/**
 * Created by owie on 11/29/14.
 */
public class Socket extends Control
{
    private boolean occupied;
    private Connection outerConnection;

    public Socket()
    {
        super();
        occupied = false;
        outerConnection = new Connection();
    }

    public boolean isOccupied()
    {
        return occupied;
    }

    public void setOccupied(boolean occupied)
    {
        this.occupied = occupied;
    }

    public Connection getOuterConnection()
    {
        return outerConnection;
    }

    public void setOuterConnection(boolean canCurrentEnter, boolean canCurrentExit)
    {
        outerConnection.setEnter(canCurrentEnter);
        outerConnection.setExit(canCurrentExit);
    }
}
