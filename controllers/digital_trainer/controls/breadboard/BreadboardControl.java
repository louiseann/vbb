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
import javafx.scene.layout.HBox;
import vbb.models.digital_trainer.Socket;
import vbb.models.digital_trainer.breadboard.Breadboard;
import vbb.models.digital_trainer.breadboard.BreadboardSocket;
import vbb.models.digital_trainer.breadboard.MetalStrip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by owie on 12/6/14.
 */
public class BreadboardControl extends HBox
{
    private Breadboard breadboard;

    @FXML
    private GridPane colConnectedLeftGroup;
    @FXML
    private GridPane rowConnectedLeftGroup;
    @FXML
    private GridPane colConnectedRightGroup;
    @FXML
    private GridPane rowConnectedRightGroup;

    private EventHandler<MouseEvent> socketsMouseClickedHandler;
    private EventHandler<MouseEvent> socketsMouseEnteredHandler;
    private EventHandler<MouseEvent> socketsMouseExitedHandler;

    private List<List<BreadboardSocketControl>> rowConnectedLeftSocketControlsWrapper;
    private List<List<BreadboardSocketControl>> rowConnectedRightSocketControlsWrapper;

    private static BooleanProperty eventHandlersSet = new SimpleBooleanProperty(false);

    public BreadboardControl()
    {
        breadboard = new Breadboard();

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
        rowConnectedLeftSocketControlsWrapper = new ArrayList<List<BreadboardSocketControl>>();
        rowConnectedRightSocketControlsWrapper = new ArrayList<List<BreadboardSocketControl>>();

        eventHandlersSet.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (areEventHandlersSet())
                    createSocketHoles();
            }
        });
    }

    public Breadboard getBreadboard()
    {
        return breadboard;
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
        addColumnConnectedHoles(colConnectedLeftGroup, true);
        addColumnConnectedHoles(colConnectedRightGroup, false);
        addRowConnectedHoles(rowConnectedLeftGroup, true);
        addRowConnectedHoles(rowConnectedRightGroup, false);
    }

    private void addColumnConnectedHoles(GridPane colConnectedGroup, boolean inLeft)
    {
        for (int col = 0; col < breadboard.getPowerRailsColumns(); col++)
        {
            MetalStrip firstHalfPowerRail = breadboard.getPowerRail(inLeft, true)[col];
            MetalStrip secondHalfPowerRail = breadboard.getPowerRail(inLeft, false)[col];

            createPowerRailHoles(col, firstHalfPowerRail, 0, breadboard.getGridRows()/2, colConnectedGroup, inLeft);
            createPowerRailHoles(col, secondHalfPowerRail, breadboard.getGridRows()/2, breadboard.getGridRows(),
                                 colConnectedGroup, inLeft);
        }
    }

    private void createPowerRailHoles(int col, MetalStrip powerRail, int startRow, int endRow, GridPane socketGroup,
                                      boolean inLeft)
    {
        int socketIndex = 0;
        for (int row = startRow; row < endRow;)
        {
            if (row == 0 || row == 1 || row == breadboard.getGridRows()/2 ||
                row == breadboard.getGridRows()-1 || row == breadboard.getGridRows()-2)
                socketGroup.add(createSpace(), col, row++);
            else
            {
                for (int r = 0; r < 5; r++)
                {
                    BreadboardSocketControl socketControl = createHole(powerRail, socketIndex++, row, col, inLeft);
                    socketGroup.add(socketControl, col, row++);
                }
                socketGroup.add(createSpace(), col, row++);
            }
        }
    }

    private void addRowConnectedHoles(GridPane rowConnectedGroup, boolean inLeft)
    {
        MetalStrip[] terminalStrips = breadboard.getTerminalStrip(inLeft);

        for (int row = 0; row < breadboard.getGridRows(); row++)
        {
            List<BreadboardSocketControl> connectedSocketControls = new ArrayList<BreadboardSocketControl>();
            MetalStrip terminalStrip = terminalStrips[row];
            for (int col = 0; col < breadboard.getTerminalHoleColumns(); col++)
            {
                BreadboardSocketControl socketControl = createHole(terminalStrip, col, row, col, inLeft);
                rowConnectedGroup.add(socketControl, col, row);
                connectedSocketControls.add(col, socketControl);
            }
            if (inLeft)
                rowConnectedLeftSocketControlsWrapper.add(row, connectedSocketControls);
            else
                rowConnectedRightSocketControlsWrapper.add(row, connectedSocketControls);
        }
    }

    private BreadboardSocketControl createHole(MetalStrip metalStrip, int socketIndex, int row, int col, boolean inLeft)
    {
        BreadboardSocket socket = (BreadboardSocket) metalStrip.getSocket(socketIndex);

        BreadboardSocketControl socketControl = new BreadboardSocketControl(socket, row, col);
        socketControl.setInLeft(inLeft);

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

    public BreadboardSocketControl getSocketFromRowConnectedGroup(int atRow, int atCol, boolean inLeft)
    {
        if (inLeft)
            return rowConnectedLeftSocketControlsWrapper.get(atRow).get(atCol);
        else
            return rowConnectedRightSocketControlsWrapper.get(atRow).get(atCol);
    }
}
