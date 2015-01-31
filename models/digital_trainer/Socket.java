package vbb.models.digital_trainer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Created by owie on 11/29/14.
 */
public class Socket extends Control
{
    private BooleanProperty occupied;
    private Voltage voltage;

    private Socket connectedTo;

    public Socket()
    {
        occupied = new SimpleBooleanProperty(false);
        voltage = new Voltage(Voltage.LOW);
    }

    public BooleanProperty occupied()
    {
        return occupied;
    }

    public boolean getOccupied()
    {
        return occupied.get();
    }

    public void setOccupied(boolean occupied)
    {
        this.occupied.set(occupied);
    }

    public Voltage voltage()
    {
        return voltage;
    }

    public boolean getVoltage()
    {
        return voltage.get();
    }

    private void setVoltage(boolean voltage)
    {
        this.voltage.set(voltage);
    }

    public Socket getConnectedTo()
    {
        return connectedTo;
    }

    public void setConnectedTo(Socket socket)
    {
        this.connectedTo = socket;
    }

    //public void powerUp(boolean voltage)
    //{
    //    setVoltage(voltage);
    //}
}
