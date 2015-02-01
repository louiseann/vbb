package vbb.models.digital_trainer.breadboard;

import vbb.models.digital_trainer.Socket;
import vbb.models.digital_trainer.Voltage;

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
    public void powerUp(boolean voltage)
    {
        if (voltage == Voltage.HIGH)
            mediator.powerConnectedSockets(this);
    }
}
