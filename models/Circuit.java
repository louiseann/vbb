package vbb.models;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import vbb.models.connection.connector.Connector;
import vbb.models.connection.end_point.EndPoint;

import java.util.*;

/**
 * Created by owie on 2/4/15.
 */
public class Circuit
{
    private Control breaker;
    private List<Control> voltageSources;
    private Map<Control, List<Control>> connections;

    public Circuit()
    {
        voltageSources = new ArrayList<Control>();
        connections = new LinkedHashMap<Control, List<Control>>();
    }

    public void add(Connector connection)
    {
        EndPoint point1 = connection.getEndPoint1();
        EndPoint point2 = connection.getEndPoint2();

        Control point1Control = point1.getControlConnected();
        Control point2Control = point2.getControlConnected();

        if (!connections.containsKey(point1Control) && point1.canReceive())
            connections.put(point1Control, new ArrayList<Control>());
        if (!connections.containsKey(point2Control) && point2.canReceive())
            connections.put(point2Control, new ArrayList<Control>());

        if (point1.canReceive() && point2.canSend())
        {
            connections.get(point1Control).add(point2Control);
            if (point1Control.runningVoltage().equals(Voltage.HIGH) &&
                    !point2Control.runningVoltage().equals(Voltage.HIGH))
                run(point1Control.runningVoltage(), point2Control);
            else if (point1Control.runningVoltage().equals(Voltage.LOW) &&
                    point2Control.runningVoltage().equals(Voltage.NONE))
                run(point1Control.runningVoltage(), point2Control);
            else if (point1Control.runningVoltage().equals(point2Control.runningVoltage()))
                run(point1Control.runningVoltage(), point2Control);
        }
        if (point2.canReceive() && point1.canSend())
        {
            connections.get(point2Control).add(point1Control);
            if (point2Control.runningVoltage().equals(Voltage.HIGH) &&
                    !point1Control.runningVoltage().equals(Voltage.HIGH))
                run(point2Control.runningVoltage(), point1Control);
            else if (point2Control.runningVoltage().equals(Voltage.LOW) &&
                    point1Control.runningVoltage().equals(Voltage.NONE))
                run(point2Control.runningVoltage(), point1Control);
            else if (point2Control.runningVoltage().equals(point1Control.runningVoltage()))
                run(point2Control.runningVoltage(), point1Control);
        }
    }

    public void deleteConnection(Connector connection)
    {
        EndPoint point1 = connection.getEndPoint1();
        EndPoint point2 = connection.getEndPoint2();

        Control point1Control = point1.getControlConnected();
        Control point2Control = point2.getControlConnected();

        if (point1.canReceive() && point2.canSend())
        {
            run(Voltage.NONE, point2Control);
            deleteConnection(point1Control, point2Control);
        }

        if (point2.canReceive() && point1.canSend())
        {
            run(Voltage.NONE, point1Control);
            deleteConnection(point2Control, point1Control);
        }

        if (breaker.isPowered())
        {
            breaker.powerUp(false);
            breaker.powerUp(true);
        }
    }

    private void deleteConnection(Control fromControl, Control toControl)
    {
        if (connections.containsKey(fromControl))
        {
            if (connections.get(fromControl).contains(toControl))
                connections.get(fromControl).remove(toControl);
            if (connections.get(fromControl).isEmpty())
                connections.remove(fromControl);
        }
    }

    public void run(Voltage voltage, Control source)
    {
        List<Control> visited = new ArrayList<Control>();

        Queue<Control> controlQueue = new ArrayDeque<Control>();

        controlQueue.add(source);
        while (!controlQueue.isEmpty())
        {
            Control control = controlQueue.remove();

            control.run(voltage);
            visited.add(control);

            if (connections.containsKey(control))
            {
                List<Control> adjacentControls = connections.get(control);
                for (Control adjacentControl : adjacentControls)
                {
                    if (!visited.contains(adjacentControl))
                        controlQueue.add(adjacentControl);
                }
            }
        }
    }

    public void setBreaker(Control control)
    {
        this.breaker = control;
        breaker.powered().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                powerUpVoltageSources(newValue);
            }
        });
    }

    public void addVoltageSources(Control... controls)
    {
        voltageSources.addAll(Arrays.asList(controls));
    }

    private void powerUpVoltageSources(boolean powerUp)
    {
        for (Control voltageSource : voltageSources)
        {
            voltageSource.powerUp(powerUp);
        }
    }

    private void runVoltageFromSources()
    {
        for (Control voltageSource : voltageSources)
        {
            run(voltageSource.runningVoltage(), voltageSource);
        }
    }
}
