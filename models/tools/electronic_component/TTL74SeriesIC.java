package vbb.models.tools.electronic_component;

import vbb.models.Circuit;
import vbb.models.digital_trainer.Socket;
import vbb.models.logic_gates.*;
import vbb.models.logic_gates.wrapper.MultipleInputGateWrapper;
import vbb.models.logic_gates.wrapper.SingleInputGateWrapper;
import vbb.models.tools.connectors.Pin;

/**
 * Created by owie on 1/14/15.
 */
public final class TTL74SeriesIC
{
    private static TTL74SeriesIC instance = new TTL74SeriesIC();

    private static final int vccPin = 7;
    private static final int gndPin = 6;

    private static final int pins = 14;
    private static final int colSpan = 2;

    private TTL74SeriesIC() {}

    public static IntegratedCircuit AND_7408(Circuit circuit)
    {
        IntegratedCircuit and7408 = new IntegratedCircuit(pins, colSpan);
        and7408.setLogicGate(AndGate.getInstance());
        setPinsFunctions(and7408, 2, circuit);

        return and7408;
    }

    public static IntegratedCircuit OR_7432(Circuit circuit)
    {
        IntegratedCircuit or7432 = new IntegratedCircuit(pins, colSpan);
        or7432.setLogicGate(OrGate.getInstance());
        setPinsFunctions(or7432, 2, circuit);

        return or7432;
    }

    public static IntegratedCircuit NOT_7404(Circuit circuit)
    {
        IntegratedCircuit not7404 = new IntegratedCircuit(pins, colSpan);
        not7404.setLogicGate(NotGate.getInstance());
        setPinsFunctions(not7404, 1, circuit);

        return not7404;
    }

    public static IntegratedCircuit NAND_7400(Circuit circuit)
    {
        IntegratedCircuit nand7400 = new IntegratedCircuit(pins, colSpan);
        nand7400.setLogicGate(NandGate.getInstance());
        setPinsFunctions(nand7400, 2, circuit);

        return nand7400;
    }

    public static IntegratedCircuit NOR_7402(Circuit circuit)
    {
        IntegratedCircuit nor7402 = new IntegratedCircuit(pins, colSpan);
        nor7402.setLogicGate(NorGate.getInstance());
        setPinsFunctions(nor7402, 2, circuit);

        return nor7402;
    }

    public static IntegratedCircuit XOR_7486(Circuit circuit)
    {
        IntegratedCircuit xor7486 = new IntegratedCircuit(pins, colSpan);
        xor7486.setLogicGate(XorGate.getInstance());
        setPinsFunctions(xor7486, 2, circuit);

        return xor7486;
    }

    public static IntegratedCircuit XNOR_747266(Circuit circuit)
    {
        IntegratedCircuit xnor747266 = new IntegratedCircuit(pins, colSpan);
        xnor747266.setLogicGate(XnorGate.getInstance());
        setPinsFunctions(xnor747266, 2, circuit);

        return xnor747266;
    }

    private static void setPinsFunctions(IntegratedCircuit chip, int inputs, Circuit circuit)
    {
        for (int pin = 0; pin < chip.getPinCount()-1; pin++)
        {
            if (pin == gndPin)
                chip.set(pin, chip.createGndPin());
            else if (pin == vccPin)
                chip.set(pin, chip.createVccPin());
            else
            {
                if (inputs == 1)
                {
                    SingleInputGateWrapper gateWrapper = new SingleInputGateWrapper(chip.getLogicGate());
                    gateWrapper.setChip(chip);

                    Pin inputPin = chip.createInputPin();
                    gateWrapper.setInputSocket((Socket) inputPin.getSenderEndPoint().getControlConnected());
                    chip.set(pin++, inputPin);

                    Pin outputPin = chip.createOutputPin();
                    Socket outputSocket = (Socket) outputPin.getReceiverEndPoint().getControlConnected();
                    gateWrapper.setOutputSocket(outputSocket, circuit);
                    chip.set(pin, outputPin);
                }
                else if (inputs > 1)
                {
                    MultipleInputGateWrapper gateWrapper = new MultipleInputGateWrapper(chip.getLogicGate());
                    gateWrapper.setChip(chip);
                    for (int i = 0; i < inputs; i++)
                    {
                        Pin inputPin = chip.createInputPin();
                        gateWrapper.add((Socket) inputPin.getSenderEndPoint().getControlConnected());
                        chip.set(pin++, inputPin);
                    }
                    Pin outputPin = chip.createOutputPin();
                    Socket outputSocket = (Socket) outputPin.getReceiverEndPoint().getControlConnected();
                    gateWrapper.setOutputSocket(outputSocket, circuit);
                    chip.set(pin, outputPin);
                }
            }
        }
    }

    public static IntegratedCircuit createChip(LogicGate logicGate, Circuit circuit)
    {
        if (logicGate instanceof AndGate)
            return AND_7408(circuit);
        else if (logicGate instanceof OrGate)
            return OR_7432(circuit);
        else if (logicGate instanceof NotGate)
            return NOT_7404(circuit);
        else if (logicGate instanceof NandGate)
            return NAND_7400(circuit);
        else if (logicGate instanceof NorGate)
            return NOR_7402(circuit);
        else if ((logicGate instanceof XorGate))
            return XOR_7486(circuit);
        else if (logicGate instanceof XnorGate)
            return XNOR_747266(circuit);
        else
            return null;
    }
}
