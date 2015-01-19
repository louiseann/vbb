package vbb.models.tools.electronic_component;

/**
 * Created by owie on 12/20/14.
 */
public class ElectronicComponent
{
    protected int colSpan;
    protected int rowSpan;
    protected boolean overpassesBreadboardRavine;

    public ElectronicComponent()
    {
        colSpan = 0;
        rowSpan = 0;
        overpassesBreadboardRavine = false;
    }

    public ElectronicComponent(int rowSpan, int colSpan)
    {
        this.colSpan = colSpan;
        this.rowSpan = rowSpan;
        overpassesBreadboardRavine = false;
    }

    public int getColSpan()
    {
        return colSpan;
    }

    public void setColSpan(int colSpan)
    {
        this.colSpan = colSpan;
    }

    public int getRowSpan()
    {
        return rowSpan;
    }

    public void setRowSpan(int rowSpan)
    {
        this.rowSpan = rowSpan;
    }

    public boolean getOverpassesBreadboardRavine()
    {
        return overpassesBreadboardRavine;
    }

    public void setOverpassesBreadboardRavine(boolean overpassesBreadboardRavine)
    {
        this.overpassesBreadboardRavine = overpassesBreadboardRavine;
    }
}
