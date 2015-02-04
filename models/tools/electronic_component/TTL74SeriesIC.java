package vbb.models.tools.electronic_component;

import vbb.models.logic_gates.*;

/**
 * Created by owie on 1/14/15.
 */
public final class TTL74SeriesIC extends IntegratedCircuit
{
    private static TTL74SeriesIC instance = new TTL74SeriesIC();

    private final int vccPin;
    private final int gndPin;

    private static IntegratedCircuit and7408;
    private static IntegratedCircuit or7432;
    private static IntegratedCircuit not7404;
    private static IntegratedCircuit nand7400;
    private static IntegratedCircuit nor7402;
    private static IntegratedCircuit xor7486;
    private static IntegratedCircuit xnor747266;

    private TTL74SeriesIC()
    {
        super(14, 2);

        vccPin = 7;
        gndPin = 6;

        createAND_7408();
        createOR_7432();
        createNOT_7404();
        createNAND_7400();
        createNOR_7402();
        createXOR_7486();
        createXNOR_747266();
    }

    private void createAND_7408()
    {
        and7408 = new IntegratedCircuit(this.getPinCount(), this.getColSpan());
        and7408.setLogicGate(AndGate.getInstance());
        setPinsFunctions(and7408, 2);
    }

    public static IntegratedCircuit AND_7408()
    {
        return and7408;
    }

    private void createOR_7432()
    {
        or7432 = new IntegratedCircuit(this.getPinCount(), this.getColSpan());
        or7432.setLogicGate(OrGate.getInstance());
        setPinsFunctions(or7432, 2);
    }

    public static IntegratedCircuit OR_7432()
    {
        return or7432;
    }

    private void createNOT_7404()
    {
        not7404 = new IntegratedCircuit(this.getPinCount(), this.getColSpan());
        not7404.setLogicGate(NotGate.getInstance());
        setPinsFunctions(not7404, 1);
    }

    public static IntegratedCircuit NOT_7404()
    {
        return not7404;
    }

    private void createNAND_7400()
    {
        nand7400 = new IntegratedCircuit(this.getPinCount(), this.getColSpan());
        nand7400.setLogicGate(NandGate.getInstance());
        setPinsFunctions(nand7400, 2);
    }

    public static IntegratedCircuit NAND_7400()
    {
        return nand7400;
    }

    private void createNOR_7402()
    {
        nor7402 = new IntegratedCircuit(this.getPinCount(), this.getColSpan());
        nor7402.setLogicGate(NorGate.getInstance());
        setPinsFunctions(nor7402, 2);
    }

    public static IntegratedCircuit NOR_7402()
    {
        return nor7402;
    }

    private void createXOR_7486()
    {
        xor7486 = new IntegratedCircuit(this.getPinCount(), this.getColSpan());
        xor7486.setLogicGate(XorGate.getInstance());
        setPinsFunctions(xor7486, 2);
    }

    public static IntegratedCircuit XOR_7486()
    {
        return xor7486;
    }

    private void createXNOR_747266()
    {
        xnor747266 = new IntegratedCircuit(this.getPinCount(), this.getColSpan());
        xnor747266.setLogicGate(XnorGate.getInstance());
        setPinsFunctions(xnor747266, 2);
    }

    public static IntegratedCircuit XNOR_747266()
    {
        return xnor747266;
    }

    private void setPinsFunctions(IntegratedCircuit chip, int inputs)
    {
        for (int pin = 0; pin < this.getPinCount()-1; pin++)
        {
            if (pin == gndPin)
                chip.set(pin, chip.createGndPin());
            else if (pin == vccPin)
                chip.set(pin, chip.createVccPin());
            else
            {
                for (int i = 0; i < inputs; i++)
                {
                    chip.set(pin++, chip.createInputPin());
                }
                chip.set(pin, chip.createOutputPin());

            }
        }
    }
}
