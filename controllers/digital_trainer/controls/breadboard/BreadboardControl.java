package vbb.controllers.digital_trainer.controls.breadboard;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

    private IntegerProperty row = new SimpleIntegerProperty();

    private EventHandler<MouseEvent> socketsMouseClickedHandler;
    private EventHandler<MouseEvent> socketsMouseEnteredHandler;
    private EventHandler<MouseEvent> socketsMouseExitedHandler;

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
        setRow(64);
    }

    public void setRow(int row)
    {
        this.row.set(row);
    }

    public IntegerProperty rowProperty()
    {
        return row;
    }

    public int getRow()
    {
        return row.get();
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
        for (int col = 0; col < 2; col++)
        {
            MetalStrip powerRail1 = new MetalStrip();
            MetalStrip powerRail2 = new MetalStrip();
            for (int row = 0; row < getRow();)
            {
                if (row == 0 || row == 1 || row == getRow()/2 || row == getRow()-1 || row == getRow()-2)
                    twoColGroup.add(createSpace(), col, row++);
                else
                {
                    for (int r = 0; r < 5; r++)
                    {
                        if (row < getRow()/2)
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
        for (int row = 0; row < getRow(); row++)
        {
            MetalStrip terminalStrip = new MetalStrip();
            for (int col = 0; col < 5; col++)
            {
                BreadboardSocketControl socketControl = createHole(terminalStrip, row, col, true, inLeft);
                socketControlsGroup.add(socketControl, col, row);
            }
        }
    }

    public void connect(BreadboardSocketControl socketControl)
    {
        BreadboardSocket socket = socketControl.getSocket();
    }
}
