package vbb.models.logic_gates.wrapper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import vbb.models.Voltage;
import vbb.models.digital_trainer.Socket;

/**
 * Created by owie on 2/6/15.
 */
public class SingleInputGateWrapper extends GateWrapper
{
    private Socket inputSocket;

    public SingleInputGateWrapper()
    {
        super();
    }

    public Socket getInputSocket()
    {
        return inputSocket;
    }

    public void setInputSocket(Socket socket)
    {
        this.inputSocket = socket;
    }
}
