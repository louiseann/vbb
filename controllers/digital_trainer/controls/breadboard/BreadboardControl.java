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
    @FXML
    private GridPane colConnectedLeftGroup;
    @FXML
    private GridPane rowConnectedLeftGroup;
    @FXML
    private GridPane colConnectedRightGroup;
    @FXML
    private GridPane rowConnectedRightGroup;

    private int rowCount;
    private int rowConnectedMaxCol = 5, colConnectedMaxCol = 2;

    private EventHandler<MouseEvent> socketsMouseClickedHandler;
    private EventHandler<MouseEvent> socketsMouseEnteredHandler;
    private EventHandler<MouseEvent> socketsMouseExitedHandler;

    private List<List<BreadboardSocketControl>> rowConnectedLeftSocketControlsWrapper;
    private List<List<BreadboardSocketControl>> rowConnectedRightSocketControlsWrapper;

    private static BooleanProperty eventHandlersSet = new SimpleBooleanProperty(false);

    public BreadboardControl()
    {
        FXMLLoader breadboardLoader = new FXMLLoader(getClass().getResource("/vbb/views/fxml/digital_trainer/custom_control/breadboard.fxml"));
        breadboardLoader.setRoot(this);
        breadboardLoader.setController(this);

        try {
            breadboardLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

    @FXML
    private void initialize()
    {
        setRowCount(64);
    }

    public void setRowCount(int rowCount)
    {
        this.rowCount = rowCount;
    }

    public int getRowCount()
    {
        return rowCount;
    }

    public int getRowConnectedMaxCol()
    {
        return rowConnectedMaxCol;
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

    private BreadboardSocketControl createHole(MetalStrip metalStrip, int row, int col, boolean inRowConnectedGroup, boolean inLeft)
    {
        BreadboardSocket socket = new BreadboardSocket(metalStrip);
        metalStrip.addSocket(socket);

        BreadboardSocketControl socketControl = new BreadboardSocketControl(socket, row, col);
        socketControl.setInRowConnectedGroup(inRowConnectedGroup);
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

    private void addColumnConnectedHoles(GridPane twoColGroup, boolean inLeft)
    {
        for (int col = 0; col < colConnectedMaxCol; col++)
        {
            MetalStrip powerRail1 = new MetalStrip();
            MetalStrip powerRail2 = new MetalStrip();
            for (int row = 0; row < getRowCount();)
            {
                if (row == 0 || row == 1 || row == getRowCount()/2 || row == getRowCount()-1 || row == getRowCount()-2)
                    twoColGroup.add(createSpace(), col, row++);
                else
                {
                    for (int r = 0; r < 5; r++)
                    {
                        if (row < getRowCount()/2)
                            twoColGroup.add(createHole(powerRail1, row, col, false, inLeft), col, row++);
                        else
                            twoColGroup.add(createHole(powerRail2, row, col, false, inLeft), col, row++);
                    }
                    twoColGroup.add(createSpace(), col, row++);
                }
            }
        }
    }

    private void addRowConnectedHoles(GridPane socketControlsGroup, boolean inLeft)
    {
        for (int row = 0; row < getRowCount(); row++)
        {
            List<BreadboardSocketControl> connectedSocketControls = new ArrayList<BreadboardSocketControl>();
            MetalStrip terminalStrip = new MetalStrip();
            for (int col = 0; col < rowConnectedMaxCol; col++)
            {
                BreadboardSocketControl socketControl = createHole(terminalStrip, row, col, true, inLeft);
                socketControlsGroup.add(socketControl, col, row);
                connectedSocketControls.add(col, socketControl);
            }
            if (inLeft)
                rowConnectedLeftSocketControlsWrapper.add(row, connectedSocketControls);
            else
                rowConnectedRightSocketControlsWrapper.add(row, connectedSocketControls);
        }
    }

    public BreadboardSocketControl getSocketFromRowConnectedGroup(int atRow, int atCol, boolean inLeft)
    {
        if (inLeft)
            return rowConnectedLeftSocketControlsWrapper.get(atRow).get(atCol);
        else
            return rowConnectedRightSocketControlsWrapper.get(atRow).get(atCol);
    }
}
