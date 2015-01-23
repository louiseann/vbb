package vbb.models.tools;

/**
 * Created by owie on 1/13/15.
 */
public class Wire
{
    private static boolean startSet;

    public static boolean isStartSet()
    {
        return startSet;
    }

    public static void startSet(boolean set)
    {
        startSet = set;
    }
}