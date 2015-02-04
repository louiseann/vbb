package vbb.models.digital_trainer.breadboard;

import vbb.models.digital_trainer.Socket;

/**
 * Created by owie on 1/27/15.
 */
public class BreadboardSocket extends Socket
{
    private MetalStrip mediator;

    public BreadboardSocket(MetalStrip mediator)
    {
        super();
        this.mediator = mediator;
    }

    @Override
    public void powerUp(boolean highVoltage)
    {
        powered().set(highVoltage);
        mediator.powerConnectedSockets(this);
    }
}
