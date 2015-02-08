package vbb.models.digital_trainer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import vbb.models.Circuit;
import vbb.models.Control;
import vbb.models.Voltage;
import vbb.models.digital_trainer.breadboard.Breadboard;
import vbb.models.connection.connector.Connector;
import vbb.models.digital_trainer.switches.DataSwitch;
import vbb.models.digital_trainer.switches.Switch;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by owie on 1/11/15.
 */
public class DigitalTrainer
{
    private static DigitalTrainer instance = new DigitalTrainer();

    private Circuit circuit;

    private Switch powerSwitch;
    private Socket positiveTerminal;
    private Socket negativeTerminal;
    private LED powerIndicatorLED;

    private Map<DataSwitch, Socket> switchTerminalPairs;
    private Map<LED, Socket> ledTerminalPairs;

    private Breadboard breadboard;

    private DigitalTrainer()
    {
        super();
        circuit = new Circuit();
        setupControls();
        initializeCircuit();
        addControlListeners();
    }

    public static DigitalTrainer getInstance()
    {
        return instance;
    }

    private void setupControls()
    {
        powerSwitch = new Switch();

        positiveTerminal = new Socket();
        positiveTerminal.setOuterConnection(false, true);
        negativeTerminal = new Socket();
        negativeTerminal.setOuterConnection(false, true);

        powerIndicatorLED = new LED();

        final int pairsCount = 8;
        switchTerminalPairs = new LinkedHashMap<DataSwitch, Socket>(pairsCount);
        ledTerminalPairs = new LinkedHashMap<LED, Socket>(pairsCount);
        for (int i = 0; i < pairsCount; i++)
        {
            DataSwitch dataSwitch = new DataSwitch(circuit);
            Socket switchTerminal = new Socket();
            switchTerminal.setInnerConnection(true, false);
            switchTerminal.setOuterConnection(false, true);
            switchTerminalPairs.put(dataSwitch, switchTerminal);

            LED led = new LED();
            Socket ledTerminal = new Socket();
            ledTerminal.setInnerConnection(false, true);
            ledTerminal.setOuterConnection(true, false);
            ledTerminalPairs.put(led, ledTerminal);
        }

        breadboard = new Breadboard();
    }

    private void initializeCircuit()
    {
        circuit.setBreaker(powerSwitch);
        circuit.addVoltageSources(positiveTerminal, negativeTerminal);

        addCircuitConnection(Connector.ONE_WAY, powerSwitch, powerIndicatorLED);

        Set<DataSwitch> switchSet = switchTerminalPairs.keySet();
        for (DataSwitch dataSwitch : switchSet)
        {
            Socket terminal = switchTerminalPairs.get(dataSwitch);
            addCircuitConnection(Connector.ONE_WAY, dataSwitch, terminal);

            circuit.addVoltageSources(dataSwitch);
        }

        Set<LED> ledSet = ledTerminalPairs.keySet();
        for (LED led : ledSet)
        {
            Socket terminal = ledTerminalPairs.get(led);
            addCircuitConnection(Connector.ONE_WAY, terminal, led);
        }
    }

    private void addCircuitConnection(Connector connectorType, Control fromControl, Control toControl)
    {
        Connector connection = Connector.createConnection(connectorType, fromControl, toControl);
        circuit.add(connection);
    }

    private void addControlListeners()
    {
        powerSwitch.state().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (powerSwitch.isOn())
                    circuit.run(Voltage.HIGH, powerSwitch);
                else
                    circuit.run(Voltage.NONE, powerSwitch);
            }
        });

        positiveTerminal.powered().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (positiveTerminal.isPowered())
                    positiveTerminal.setRunningVoltage(Voltage.HIGH);
                else
                    positiveTerminal.setRunningVoltage(Voltage.NONE);
                circuit.run(positiveTerminal.runningVoltage(), positiveTerminal);
            }
        });

        negativeTerminal.powered().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (negativeTerminal.isPowered())
                    negativeTerminal.setRunningVoltage(Voltage.LOW);
                else
                    negativeTerminal.setRunningVoltage(Voltage.NONE);
                circuit.run(negativeTerminal.runningVoltage(), negativeTerminal);
            }
        });
    }

    public Switch getPowerSwitch()
    {
        return powerSwitch;
    }

    public Socket getPositiveTerminal()
    {
        return positiveTerminal;
    }

    public Socket getNegativeTerminal()
    {
        return negativeTerminal;
    }

    public LED getPowerIndicatorLED()
    {
        return powerIndicatorLED;
    }

    public Map<DataSwitch, Socket> getSwitchTerminalPairs()
    {
        return switchTerminalPairs;
    }

    public Map<LED, Socket> getLedTerminalPairs()
    {
        return ledTerminalPairs;
    }

    public Breadboard getBreadboard()
    {
        return breadboard;
    }

    public Circuit getCircuit()
    {
        return circuit;
    }
}
