package vbb.controllers.tools.controls;

import javafx.scene.shape.Line;
import vbb.models.tools.Wire;

/**
 * Created by owie on 1/29/15.
 */
public class WireControl extends Line
{
    private static boolean startSet = false;

    private Wire wire;

    public WireControl()
    {
        super();
    }

    public static boolean isStartSet()
    {
        return startSet;
    }

    public static void startSet(boolean started)
    {
        startSet = started;
    }

    public Wire getWire()
    {
        return wire;
    }

    public void setWire(Wire wire)
    {
        this.wire = wire;
    }
}
