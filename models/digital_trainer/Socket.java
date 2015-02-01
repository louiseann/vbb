package vbb.models.digital_trainer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by owie on 11/29/14.
 */
public class Socket extends Control
{
    private BooleanProperty occupied;
    private Socket socketConnected;

    public Socket()
    {
        super();
        occupied = new SimpleBooleanProperty(false);

        this.powered().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (socketConnected != null)
                    changePower(newValue);
            }
        });
    }

    public BooleanProperty occupied()
    {
        return occupied;
    }

    public boolean isOccupied()
    {
        return occupied.get();
    }

    public void setOccupied(boolean occupied)
    {
        this.occupied.set(occupied);
    }

    public Socket getSocketConnected()
    {
        return socketConnected;
    }

    public void setSocketConnected(Socket socket)
    {
        this.socketConnected = socket;
        shareEqualPower();
    }

    private void shareEqualPower()
    {
        boolean voltage = this.isPowered() || socketConnected.isPowered();
        if (voltage != this.isPowered())
        {
            this.powerUp(voltage);
            socketConnected.powerUp(voltage);
        }
        else
            socketConnected.powerUp(voltage);
    }

    private void changePower(boolean highVoltage)
    {
        this.powerUp(highVoltage);
        socketConnected.powerUp(highVoltage);
    }
}
