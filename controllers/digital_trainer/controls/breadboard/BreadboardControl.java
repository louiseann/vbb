package vbb.controllers.digital_trainer.controls.breadboard;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import vbb.models.digital_trainer.breadboard.Breadboard;
import vbb.models.digital_trainer.breadboard.BreadboardSocket;
import vbb.models.digital_trainer.breadboard.MetalStrip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by owie on 12/6/14.
 */
public class BreadboardControl extends VBox
{
    private Breadboard soul;

    @FXML
    private GridPane rowConnectedTopGroup;
    @FXML
    private GridPane colConnectedTopGroup;
    @FXML
    private GridPane rowConnectedBottomGroup;
    @FXML
    private GridPane colConnectedBottomGroup;

    private EventHandler<MouseEvent> socketsMouseClickedHandler;
    private EventHandler<MouseEvent> socketsMouseEnteredHandler;
    private EventHandler<MouseEvent> socketsMouseExitedHandler;

    private List<List<BreadboardSocketControl>> colConnectedTopSocketControlsWrapper;
    private List<List<BreadboardSocketControl>> colConnectedBottomSocketControlsWrapper;

    private static BooleanProperty eventHandlersSet = new SimpleBooleanProperty(false);

    public BreadboardControl()
    {
        String fxmlSourceUrl = "/vbb/views/fxml/digital_trainer/custom_control/breadboard.fxml";
        FXMLLoader breadboardLoader = new FXMLLoader(getClass().getResource(fxmlSourceUrl));
        breadboardLoader.setRoot(this);
        breadboardLoader.setController(this);

        try {
            breadboardLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void initialize()
    {
        colConnectedTopSocketControlsWrapper = new ArrayList<List<BreadboardSocketControl>>();
        colConnectedBottomSocketControlsWrapper = new ArrayList<List<BreadboardSocketControl>>();

        eventHandlersSet.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (areEventHandlersSet())
                    createSocketHoles();
            }
        });
    }

    public Breadboard getSoul()
    {
        return soul;
    }

    public void setSoul(Breadboard soul)
    {
        this.soul = soul;
    }

    public void setSocketsMouseClickedHandler(EventHandler<MouseEvent> socketsMouseClickedHandler)
    {
        this.socketsMouseClickedHandler = socketsMouseClickedHandler;
        checkEventHandlers();
    }

    public void setSocketsMouseEnteredHandler(EventHandler<MouseEvent> socketsMouseEnteredHandler)
    {
        this.socketsMouseEnteredHandler = socketsMouseEnteredHandler;
        checkEventHandlers();
    }

    public void setSocketsMouseExitedHandler(EventHandler<MouseEvent> socketsMouseExitedHandler)
    {
        this.socketsMouseExitedHandler = socketsMouseExitedHandler;
        checkEventHandlers();
    }

    private static boolean areEventHandlersSet()
    {
        return eventHandlersSet.get();
    }

    private void checkEventHandlers()
    {
        if (socketsMouseClickedHandler != null &&
            socketsMouseEnteredHandler != null &&
            socketsMouseExitedHandler != null)
            eventHandlersSet.set(true);
        else
            eventHandlersSet.set(false);
    }

    private void createSocketHoles()
    {
        addRowConnectedHoles(rowConnectedTopGroup, true);
        addRowConnectedHoles(rowConnectedBottomGroup, false);
        addColConnectedHoles(colConnectedTopGroup, true);
        addColConnectedHoles(colConnectedBottomGroup, false);
    }

    private void addRowConnectedHoles(GridPane rowConnectedGroup, boolean top)
    {
        for (int row = 0; row < soul.getPowerRailsRows(); row++)
        {
            MetalStrip firstHalfPowerRail = soul.getPowerRail(top, true)[row];
            MetalStrip secondHalfPowerRail = soul.getPowerRail(top, false)[row];

            createPowerRailHoles(row, firstHalfPowerRail, 0, soul.getGridCols()/2, rowConnectedGroup, top);
            createPowerRailHoles(row, secondHalfPowerRail, soul.getGridCols()/2, soul.getGridCols(),
                                 rowConnectedGroup, top);
        }
    }

    private void createPowerRailHoles(int row, MetalStrip powerRail, int startCol, int endCol, GridPane socketGroup,
                                  boolean topGroup)
    {
        int socketIndex = 0;
        for (int col = startCol; col < endCol;)
        {
            if (col == 0 || col == 1 || col == soul.getGridCols()/2 ||
                col == soul.getGridCols()-1 || col == soul.getGridCols()-2)
                socketGroup.add(createSpace(), col++, row);
            else
            {
                for (int c = 0; c < 5; c++)
                {
                    BreadboardSocketControl socketControl = createHole(powerRail, socketIndex++, row, col, topGroup);
                    socketGroup.add(socketControl, col++, row);
                }
                socketGroup.add(createSpace(), col++, row);
            }
        }
    }

    private void addColConnectedHoles(GridPane colConnectedGroup, boolean top)
    {
        MetalStrip[] terminalStrips = soul.getTerminalStrip(top);

        for (int col = 0; col < soul.getGridCols(); col++)
        {
            List<BreadboardSocketControl> connectedSocketControls = new ArrayList<BreadboardSocketControl>();
            MetalStrip terminalStrip = terminalStrips[col];
            for (int row = 0; row < soul.getTerminalHoleRows(); row++)
            {
                BreadboardSocketControl socketControl = createHole(terminalStrip, row, row, col, top);
                colConnectedGroup.add(socketControl, col, row);
                connectedSocketControls.add(row, socketControl);
            }
            if (top)
                colConnectedTopSocketControlsWrapper.add(col, connectedSocketControls);
            else
                colConnectedBottomSocketControlsWrapper.add(col, connectedSocketControls);
        }
    }

    private BreadboardSocketControl createHole(MetalStrip metalStrip, int socketIndex, int row, int col, boolean top)
    {
        BreadboardSocket socket = metalStrip.getSocket(socketIndex);

        BreadboardSocketControl socketControl = new BreadboardSocketControl(socket, row, col);
        socketControl.setTopGroupElement(top);

        socketControl.addEventHandler(MouseEvent.MOUSE_CLICKED, socketsMouseClickedHandler);
        socketControl.addEventHandler(MouseEvent.MOUSE_ENTERED, socketsMouseEnteredHandler);
        socketControl.addEventHandler(MouseEvent.MOUSE_EXITED, socketsMouseExitedHandler);

        socketControl.getHoleBox().setStyle("-fx-fill: #212121;");
        return socketControl;
    }

    private BreadboardSocketControl createSpace()
    {
        BreadboardSocketControl socketHole = new BreadboardSocketControl();
        socketHole.getHoleBox().setStyle("-fx-fill: #ffffff;");
        socketHole.getHoleBox().setDisable(true);
        return socketHole;
    }

    public BreadboardSocketControl getSocketFromColConnectedGroup(int col, int row, boolean top)
    {
        if (top)
            return colConnectedTopSocketControlsWrapper.get(col).get(row);
        else
            return colConnectedBottomSocketControlsWrapper.get(col).get(row);
    }
}
