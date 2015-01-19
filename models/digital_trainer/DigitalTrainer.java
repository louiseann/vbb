package vbb.models.digital_trainer;

/**
 * Created by owie on 1/11/15.
 */
public class DigitalTrainer
{
    private static String currentTool;

    public static void setCurrentTool(String tool)
    {
        currentTool = tool;
    }

    public static String getCurrentTool()
    {
        return currentTool;
    }
}
