package vbb.digital_trainer.controls.switch_control;

/**
 * Created by owie on 12/14/14.
 */
public class Switch
{
    public final static boolean ON = true;
    public final static boolean OFF = false;

    protected boolean state;

    public Switch()
    {
        state = OFF;
    }

    public boolean getState()
    {
        return state;
    }

    protected void toggle()
    {
        state = !state;
    }
}
