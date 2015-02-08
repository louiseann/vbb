package vbb.models.digital_trainer.breadboard;

import vbb.models.digital_trainer.Socket;

/**
 * Created by owie on 1/27/15.
 */
public class BreadboardSocket extends Socket
{
    private MetalStrip metalStrip;

    public BreadboardSocket(MetalStrip metalStrip)
    {
        super();
        this.metalStrip = metalStrip;
        setInnerConnection(true, true);
        setOuterConnection(true, true);
    }

    public MetalStrip getMetalStrip()
    {
        return metalStrip;
    }
}
