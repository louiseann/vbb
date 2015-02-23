package vbb.models.digital_trainer.breadboard;

/**
 * Created by owie on 2/4/15.
 * A 840-point breadboard
 */
public class Breadboard
{
    private final int gridCols;
    private final int powerRailsRows;
    private final int terminalHoleRows;

    private MetalStrip[] firstHalfTopPowerRail;
    private MetalStrip[] secondHalfTopPowerRail;

    private MetalStrip[] leftTerminalStrip;
    private MetalStrip[] rightTerminalStrip;

    private MetalStrip[] firstHalfBottomPowerRail;
    private MetalStrip[] secondHalfBottomPowerRail;

    public Breadboard()
    {
        gridCols = 64;
        powerRailsRows = 2;
        terminalHoleRows = 5;

        final int powerRailCols = 25;
        final int terminalStripsCols = gridCols;

        firstHalfTopPowerRail = createMetalStrips(powerRailsRows, powerRailCols);
        secondHalfTopPowerRail = createMetalStrips(powerRailsRows, powerRailCols);
        leftTerminalStrip = createMetalStrips(terminalStripsCols, terminalHoleRows);
        rightTerminalStrip = createMetalStrips(terminalStripsCols, terminalHoleRows);
        firstHalfBottomPowerRail = createMetalStrips(powerRailsRows, powerRailCols);
        secondHalfBottomPowerRail = createMetalStrips(powerRailsRows, powerRailCols);
    }

    public int getGridCols()
    {
        return gridCols;
    }

    public int getPowerRailsRows()
    {
        return powerRailsRows;
    }

    public int getTerminalHoleRows()
    {
        return terminalHoleRows;
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

    public MetalStrip[] getPowerRail(boolean top, boolean firstHalf)
    {
        if (top)
            return firstHalf ? firstHalfTopPowerRail : secondHalfTopPowerRail;
        else
            return firstHalf ? firstHalfBottomPowerRail : secondHalfBottomPowerRail;
    }

    public MetalStrip[] getTerminalStrip(boolean top)
    {
        return  top ? leftTerminalStrip : rightTerminalStrip;
    }
}
