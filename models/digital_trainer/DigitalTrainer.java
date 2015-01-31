package vbb.models.digital_trainer;

/**
 * Created by owie on 1/11/15.
 */
public class DigitalTrainer extends Control
{
    private static DigitalTrainer instance = new DigitalTrainer();

    private DigitalTrainer()
    {
        super();
    }

    public static DigitalTrainer getInstance()
    {
        return instance;
    }
}
