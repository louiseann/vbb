package vbb.models.digital_trainer;

/**
 * Created by owie on 12/14/14.
 */
public class Switch
{
    public final static boolean ON = true;
    public final static boolean OFF = false;

    private boolean state;

    public Switch()
    {
        state = OFF;
    }

    public boolean getState()
    {
        return state;
    }

    public void toggle()
    {
        state = !state;
    }
}
