package vbb.digital_trainer;

/**
 * Created by owie on 11/29/14.
 */
public class LED implements Connector
{
    @Override
    public void onWireConnected()
    {
        //light LED (on/off)
    }

    @Override
    public void onWireDisconnected()
    {
        //light LED (off)
    }
}
