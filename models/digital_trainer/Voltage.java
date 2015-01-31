package vbb.models.digital_trainer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Created by owie on 1/28/15.
 */
public class Voltage extends SimpleBooleanProperty
{
    public static boolean HIGH = true;
    public static boolean LOW = false;

    public Voltage(boolean voltage)
    {
        super(voltage);
    }
}
