package vbb.controllers.tools.controls;

import javafx.scene.shape.Line;
import vbb.models.tools.Wire;

/**
 * Created by owie on 1/29/15.
 */
public class WireControl extends Line
{
    private Wire wire;

    public WireControl()
    {
        wire = new Wire();
    }

    public Wire getWire()
    {
        return wire;
    }
}
