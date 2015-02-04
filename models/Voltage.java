package vbb.models;

import javafx.beans.property.SimpleBooleanProperty;

/**
 * Created by owie on 1/28/15.
 */
public class Voltage extends SimpleBooleanProperty
{
    public static Voltage HIGH = new Voltage(true);
    public static Voltage LOW = new Voltage(false);

    private Voltage(boolean voltage)
    {
        super(voltage);
    }
}
