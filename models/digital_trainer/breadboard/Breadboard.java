package vbb.models.digital_trainer.breadboard;

/**
 * Created by owie on 2/4/15.
 * A 840-point breadboard
 */
public class Breadboard
{
    private final int gridRows;
    private final int powerRailsColumns;
    private final int terminalHoleColumns;

    private MetalStrip[] firstHalfLeftPowerRail;
    private MetalStrip[] secondHalfLeftPowerRail;

    private MetalStrip[] leftTerminalStrip;
    private MetalStrip[] rightTerminalStrip;

    private MetalStrip[] firstHalfRightPowerRail;
    private MetalStrip[] secondHalfRightPowerRail;

    public Breadboard()
    {
        gridRows = 64;
        powerRailsColumns = 2;
        terminalHoleColumns = 5;

        final int powerRailRows = 25;
        final int terminalStripsRows = gridRows;

        firstHalfLeftPowerRail = createMetalStrips(powerRailsColumns, powerRailRows);
        secondHalfLeftPowerRail = createMetalStrips(powerRailsColumns, powerRailRows);
        leftTerminalStrip = createMetalStrips(terminalStripsRows, terminalHoleColumns);
        rightTerminalStrip = createMetalStrips(terminalStripsRows, terminalHoleColumns);
        firstHalfRightPowerRail = createMetalStrips(powerRailsColumns, powerRailRows);
        secondHalfRightPowerRail = createMetalStrips(powerRailsColumns, powerRailRows);
    }

    public int getGridRows()
    {
        return gridRows;
    }

    public int getPowerRailsColumns()
    {
        return powerRailsColumns;
    }

    public int getTerminalHoleColumns()
    {
        return terminalHoleColumns;
    }

    private MetalStrip[] createMetalStrips(int rowCount, int colCount)
    {
        MetalStrip[] metalStrips = new MetalStrip[rowCount];
        for (int row = 0; row < rowCount; row++)
        {
            MetalStrip metalStrip = new MetalStrip();
            for (int col = 0; col < colCount; col++)
            {
                BreadboardSocket socket = new BreadboardSocket(metalStrip);
                metalStrip.addSocket(socket);
            }
            metalStrips[row] = metalStrip;
        }

        return metalStrips;
    }

    public MetalStrip[] getPowerRail(boolean left, boolean firstHalf)
    {
        if (left)
            return firstHalf ? firstHalfLeftPowerRail : secondHalfLeftPowerRail;
        else
            return firstHalf ? firstHalfRightPowerRail : secondHalfRightPowerRail;
    }

    public MetalStrip[] getTerminalStrip(boolean left)
    {
        return  left ? leftTerminalStrip : rightTerminalStrip;
    }
}
