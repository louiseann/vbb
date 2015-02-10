package vbb.models.logic_gates.wrapper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import vbb.models.Voltage;
import vbb.models.digital_trainer.Socket;
import vbb.models.logic_gates.LogicGate;

/**
 * Created by owie on 2/6/15.
 */
public class SingleInputGateWrapper extends GateWrapper
{
    private Socket inputSocket;

    public SingleInputGateWrapper(LogicGate logicGate)
    {
        super(logicGate);
    }

    public void setInputSocket(Socket socket)
    {
        this.inputSocket = socket;
        inputSocket.runningVoltageChanged().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println("input socket power changed");
                if (getChip().isPowered())
                {
                    Voltage output = getLogicGate().getOutput(inputSocket.runningVoltage());
                    System.out.println(output);
                    getOutputSocket().run(output);
                }
                else
                    getOutputSocket().run(Voltage.NONE);
            }
        });
    }
}
