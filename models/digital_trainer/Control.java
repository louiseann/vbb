package vbb.models.digital_trainer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Created by owie on 1/29/15.
 */
public abstract class Control
{
    private BooleanProperty powered;

    public Control()
    {
        powered = new SimpleBooleanProperty(false);
    }

    public BooleanProperty powered()
    {
        return powered;
    }

    public boolean isPowered()
    {
        return powered.get();
    }

    public void powerUp(boolean voltage)
    {
        powered.set(voltage);
    }
}
