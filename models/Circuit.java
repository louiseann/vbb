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

        if (!connections.containsKey(point1Control) && point1.canSend())
            connections.put(point1Control, new ArrayList<Control>());
        if (!connections.containsKey(point2Control) && point2.canSend())
            connections.put(point2Control, new ArrayList<Control>());

        if (point1.canSend() && point2.canReceive())
        {
            connections.get(point1Control).add(point2Control);
            run(point1Control.runningVoltage(), point1Control);
        }
        if (point2.canSend() && point1.canReceive())
        {
            connections.get(point2Control).add(point1Control);
            run(point2Control.runningVoltage(), point1Control);
        }
    }

    public void deleteConnection(EndPoint point1, EndPoint point2)
    {
        Control point1Control = point1.getControlConnected();
        Control point2Control = point2.getControlConnected();

        deleteConnection(point1Control, point2Control);
        deleteConnection(point2Control, point1Control);
    }

    private void deleteConnection(Control fromControl, Control toControl)
    {
        if (!connections.containsKey(fromControl))
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
}
