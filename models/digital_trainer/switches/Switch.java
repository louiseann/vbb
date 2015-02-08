package vbb.models.digital_trainer.switches;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import vbb.models.Control;

/**
 * Created by owie on 12/14/14.
 */
public class Switch extends Control
{
    public final static boolean ON = true;
    public final static boolean OFF = false;

    private BooleanProperty state = new SimpleBooleanProperty();

    public Switch()
    {
        super();
        setState(OFF);
        setInnerConnection(false, true);
    }

    public BooleanProperty state()
    {
        return state;
    }

    private void setState(boolean state)
    {
        this.state.set(state);
    }

    public boolean getState()
    {
        return state.get();
    }

    public void toggle()
    {
        setState(!getState());
    }

    public boolean isOn()
    {
        return getState();
    }
}
