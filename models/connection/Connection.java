package vbb.models.connection;

/**
 * Created by owie on 2/8/15.
 */
public class Connection
{
    private boolean enter;
    private boolean exit;

    public Connection()
    {
        enter = false;
        exit= false;
    }

    public boolean canEnter()
    {
        return enter;
    }

    public void setEnter(boolean enter)
    {
        this.enter = enter;
    }

    public boolean canExit()
    {
        return exit;
    }

    public void setExit(boolean exit)
    {
        this.exit = exit;
    }
}
