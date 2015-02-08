package vbb.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import vbb.models.connection.Connection;
import vbb.models.Voltage;

/**
 * Created by owie on 1/29/15.
 */
public abstract class Control
{
    private BooleanProperty powered;
    private Voltage runningVoltage;
    private Connection innerConnection;

    public Control()
    {
        powered = new SimpleBooleanProperty(false);
        runningVoltage = Voltage.NONE;
        innerConnection = new Connection();
    }

    public BooleanProperty powered()
    {
        return powered;
    }

    public boolean isPowered()
    {
        return powered.get();
    }

    public void powerUp(boolean powerUp)
    {
        powered.set(powerUp);
    }

    public void run(Voltage voltage)
    {
        this.runningVoltage = voltage;

        if (!voltage.equals(Voltage.NONE))
            powerUp(true);
        else
            powerUp(false);
    }

    public Voltage runningVoltage()
    {
        return runningVoltage;
    }

    public void setRunningVoltage(Voltage voltage)
    {
        this.runningVoltage = voltage;
    }

    public Connection getInnerConnection()
    {
        return innerConnection;
    }

    public void setInnerConnection(boolean canCurrentEnter, boolean canCurrentExit)
    {
        innerConnection.setEnter(canCurrentEnter);
        innerConnection.setExit(canCurrentExit);
    }
}
