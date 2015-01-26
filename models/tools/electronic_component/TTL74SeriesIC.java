package vbb.models.tools.electronic_component;

/**
 * Created by owie on 1/14/15.
 */
public final class TTL74SeriesIC extends IntegratedCircuit
{
    private static TTL74SeriesIC instance = new TTL74SeriesIC();

    private int vccPin;
    private int gndPin;

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

        vccPin = 0;
        gndPin = 13;

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
        and7408 = new IntegratedCircuit(this.getPins(), this.getColSpan());
        and7408.setGate("and");
        setFunctionsForTwoInput(and7408);
    }

    public static IntegratedCircuit AND_7408()
    {
        return and7408;
    }

    private void createOR_7432()
    {
        or7432 = new IntegratedCircuit(this.getPins(), this.getColSpan());
        or7432.setGate("or");
        setFunctionsForTwoInput(or7432);
    }

    public static IntegratedCircuit OR_7432()
    {
        return or7432;
    }

    private void createNOT_7404()
    {
        not7404 = new IntegratedCircuit(this.getPins(), this.getColSpan());
        not7404.setGate("not");
        setFunctionsForOneInput(not7404);
    }

    public static IntegratedCircuit NOT_7404()
    {
        return not7404;
    }

    private void createNAND_7400()
    {
        nand7400 = new IntegratedCircuit(this.getPins(), this.getColSpan());
        nand7400.setGate("nand");
        setFunctionsForOneInput(nand7400);
    }

    public static IntegratedCircuit NAND_7400()
    {
        return nand7400;
    }

    private void createNOR_7402()
    {
        nor7402 = new IntegratedCircuit(this.getPins(), this.getColSpan());
        nor7402.setGate("nor");
        setFunctionsForOneInput(nor7402);
    }

    public static IntegratedCircuit NOR_7402()
    {
        return nor7402;
    }

    private void createXOR_7486()
    {
        xor7486 = new IntegratedCircuit(this.getPins(), this.getColSpan());
        xor7486.setGate("xor");
        setFunctionsForOneInput(xor7486);
    }

    public static IntegratedCircuit XOR_7486()
    {
        return xor7486;
    }

    private void createXNOR_747266()
    {
        xnor747266 = new IntegratedCircuit(this.getPins(), this.getColSpan());
        xnor747266.setGate("xnor");
        setFunctionsForOneInput(xnor747266);
    }

    public static IntegratedCircuit XNOR_747266()
    {
        return xnor747266;
    }

    private void setFunctionsForOneInput(IntegratedCircuit chip)
    {
        chip.set(vccPin, "vcc");
        for (int pin = 1; pin < this.getPins()-1; pin++)
        {
            chip.set(pin++, "in");
            chip.set(pin, "out");
        }
        chip.set(gndPin, "gnd");
    }

    private void setFunctionsForTwoInput(IntegratedCircuit chip)
    {
        chip.set(vccPin, "vcc");
        for (int pin = 1; pin < this.getPins()-1; pin++)
        {
            chip.set(pin++, "in");
            chip.set(pin++, "in");
            chip.set(pin, "out");
        }
        chip.set(gndPin, "gnd");
    }
}
