package vbb.controllers.digital_trainer;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import vbb.controllers.digital_trainer.controls.SocketControl;
import vbb.controllers.digital_trainer.controls.breadboard.BreadboardControl;
import vbb.controllers.digital_trainer.controls.breadboard.BreadboardSocketControl;
import vbb.controllers.tools.controls.IntegratedCircuitControl;
import vbb.controllers.tools.controls.WireControl;
import vbb.models.Circuit;
import vbb.models.Control;
import vbb.models.connection.connector.Connector;
import vbb.models.connection.connector.TwoWayConnector;
import vbb.models.digital_trainer.DigitalTrainer;
import vbb.models.digital_trainer.Socket;
import vbb.models.digital_trainer.breadboard.BreadboardSocket;
import vbb.models.tools.Tool;
import vbb.models.tools.connectors.Pin;
import vbb.models.tools.connectors.Wire;
import vbb.models.tools.electronic_component.IntegratedCircuit;
import vbb.models.tools.electronic_component.TTL74SeriesIC;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by owie on 1/19/15.
 */
public class DigitalTrainerController
{
    @FXML
    private Pane digitalTrainerArea;

    @FXML
    private VBox board;

    @FXML
    private BreadboardControl breadboard;
    @FXML
    private PowerSupplyAreaController powerSupplyAreaController;
    @FXML
    private VBox powerSupplyArea;
    @FXML
    private DataSwitchesAreaController dataSwitchesAreaController;
    @FXML
    private VBox dataSwitchesArea;
    @FXML
    private LedDisplayAreaController ledDisplayAreaController;
    @FXML
    private GridPane ledDisplayArea;

    private DigitalTrainer soul;

    private EventHandler<MouseEvent> enteredOnPluggedToolHandler;
    private EventHandler<MouseEvent> exitedOnPluggedToolHandler;

    private EventHandler<MouseEvent> movedOnWireHandler;

    private EventHandler<MouseEvent> clickedOnWireHandler;
    private EventHandler<MouseEvent> clickedOnChipHandler;

    private Map<Integer, Boolean> chip_zIndex;
    private int wire_zIndex;

    private final double xPosition = 76;
    private final double yPosition = 42;

    private WireControl processingWireControl;

    @FXML
    public void initialize()
    {
        soul = DigitalTrainer.getInstance();

        powerSupplyAreaController.putSoulToControls(soul.getPowerSwitch(), soul.getPositiveTerminal(),
                                                    soul.getNegativeTerminal(), soul.getPowerIndicatorLED());

        dataSwitchesAreaController.createSwitchSocketPairControls(soul.getSwitchTerminalPairs());

        ledDisplayAreaController.createLedSocketPairControls(soul.getLedTerminalPairs());

        breadboard.setSoul(soul.getBreadboard());

        initializeChipZIndex();
        wire_zIndex = 10;

        board.setTranslateX(xPosition);
        board.setTranslateY(yPosition);
    }

    private void initializeChipZIndex()
    {
        chip_zIndex = new LinkedHashMap<Integer, Boolean>(9);
        for (int i = 1; i < 10; i++)
        {
            chip_zIndex.put(i, false);
            digitalTrainerArea.getChildren().add(i, new ImageView());
        }
    }

    public double getXPosition()
    {
        return xPosition;
    }

    public double getYPosition()
    {
        return yPosition;
    }

    public DigitalTrainer getSoul()
    {
        return soul;
    }

    public BreadboardControl getBreadBoard()
    {
        return breadboard;
    }

    /* set event handlers for components
     * */
    public void setBreadboardEventHandlers(EventHandler<MouseEvent> mouseClickedHandler,
                                           EventHandler<MouseEvent> mouseEnteredHandler,
                                           EventHandler<MouseEvent> mouseExitedHandler)
    {
        breadboard.setSocketsMouseClickedHandler(mouseClickedHandler);
        breadboard.setSocketsMouseEnteredHandler(mouseEnteredHandler);
        breadboard.setSocketsMouseExitedHandler(mouseExitedHandler);
    }

    public void setSocketsHandler(EventHandler<MouseEvent> mouseClickedHandler,
                                  EventHandler<MouseEvent> mouseEnteredHandler,
                                  EventHandler<MouseEvent> mouseExitedHandler)
    {
        powerSupplyAreaController.addSocketsOnClickedHandler(mouseClickedHandler);
        powerSupplyAreaController.addSocketsOnEnteredHandler(mouseEnteredHandler);
        powerSupplyAreaController.addSocketsOnExitedHandler(mouseExitedHandler);

        dataSwitchesAreaController.addSocketsOnClickedHandler(mouseClickedHandler);
        dataSwitchesAreaController.addSocketsOnEnteredHandler(mouseEnteredHandler);
        dataSwitchesAreaController.addSocketsOnExitedHandler(mouseExitedHandler);

        ledDisplayAreaController.addSocketsOnClickedHandler(mouseClickedHandler);
        ledDisplayAreaController.addSocketsOnEnteredHandler(mouseEnteredHandler);
        ledDisplayAreaController.addSocketsOnExitedHandler(mouseExitedHandler);
    }

    public void setPowerSwitchClickedHandler(EventHandler<MouseEvent> handler)
    {
        powerSupplyAreaController.addPowerSwitchClickedHandler(handler);
    }

    public void setDataSwitchesClickedHandler(EventHandler<MouseEvent> handler)
    {
        dataSwitchesAreaController.addDataSwitchesOnClickedHandler(handler);
    }

    public void setEnteredOnPluggedToolHandler(EventHandler<MouseEvent> handler)
    {
        enteredOnPluggedToolHandler = handler;
    }

    public void setExitedOnPluggedToolHandler(EventHandler<MouseEvent> handler)
    {
        exitedOnPluggedToolHandler = handler;
    }

    public void setMovedOnWireHandler(EventHandler<MouseEvent> handler)
    {
        movedOnWireHandler = handler;
    }

    public void setClickedOnWireHandler(EventHandler<MouseEvent> handler)
    {
        clickedOnWireHandler = handler;
    }

    public void setClickedOnChipHandler(EventHandler<MouseEvent> handler)
    {
        clickedOnChipHandler = handler;
    }
    /* -end- set event handlers for components
     * */

    /* plug chip
     * */
    public void handleIntegratedCircuitEvent(EventType eventType, Tool currentTool, BreadboardSocketControl socket,
                                              Point2D position)
    {
        IntegratedCircuit toolChip = (IntegratedCircuit) currentTool.getClassification();

        IntegratedCircuit chip = TTL74SeriesIC.createChip(toolChip.getLogicGate(), soul.getCircuit());

        int maxRow = breadboard.getSoul().getTerminalHoleRows() - 1;
        int reverseRowPosition = Math.abs(socket.getRow() - maxRow - 1);

        if (socket.getCol() + chip.getColSpan() <= breadboard.getSoul().getGridCols() &&
                reverseRowPosition < chip.getRowSpan() &&
                reverseRowPosition > 0 &&
                !occupied(socket, chip.getColSpan(), chip.getRowSpan(), reverseRowPosition) &&
                socket.isTopGroupElement())
        {
            if (eventType.equals(MouseEvent.MOUSE_CLICKED))
            {
                int remainingChipRowSpan = chip.getRowSpan() - reverseRowPosition;
                plug(chip, currentTool, socket, position, remainingChipRowSpan);
                hoverChipOnSockets(socket, chip.getColSpan(), remainingChipRowSpan, MouseEvent.MOUSE_EXITED);
            }
            else if (eventType.equals(MouseEvent.MOUSE_ENTERED) || eventType.equals(MouseEvent.MOUSE_EXITED))
                hoverChipOnSockets(socket, chip.getColSpan(), chip.getRowSpan()-reverseRowPosition, eventType);
        }
    }

    private boolean occupied(BreadboardSocketControl pointedSocketControl, int chipColSpan, int chipRowSpan,
                             int chipReverseRowPosition)
    {
        int colBegin = pointedSocketControl.getCol();
        int colEnd = pointedSocketControl.getCol() + chipColSpan;

        int chipRemainingRowSpan = chipRowSpan - chipReverseRowPosition;

        for (int col = colBegin; col < colEnd; col++)
        {
            BreadboardSocketControl socketControl =
                    breadboard.getSocketFromColConnectedGroup(col, pointedSocketControl.getRow(),
                            pointedSocketControl.isTopGroupElement());
            BreadboardSocketControl otherSideSocketControl =
                    breadboard.getSocketFromColConnectedGroup(col, chipRemainingRowSpan - 1,
                            !pointedSocketControl.isTopGroupElement());

            if (socketControl.getSoul().isOccupied() || otherSideSocketControl.getSoul().isOccupied())
                return true;
        }

        return false;
    }

    private void plug(IntegratedCircuit chip, Tool currentTool, BreadboardSocketControl pointedSocketControl,
                      Point2D position, int chipRemainingRowSpan)
    {
        occupySockets(true, pointedSocketControl, chip, chipRemainingRowSpan);

        IntegratedCircuitControl chipControl = new IntegratedCircuitControl(currentTool.getViewImage());
        chipControl.setSoul(chip);
        setChipOnBoard(position, chipControl, currentTool.getViewHotSpot());
    }

    private void occupySockets(boolean occupy, BreadboardSocketControl pointedSocketControl, IntegratedCircuit chip,
                               int chipRemainingRowSpan)
    {
        int colBegin = pointedSocketControl.getCol();
        int colEnd = pointedSocketControl.getCol() + chip.getColSpan();

        for (int col = colBegin, topRowPin = 0, bottomRowPin = 7; col < colEnd; col++)
        {
            BreadboardSocketControl socketControl =
                    breadboard.getSocketFromColConnectedGroup(col, pointedSocketControl.getRow(),
                            pointedSocketControl.isTopGroupElement());
            socketControl.getSoul().getMetalStrip()
                    .connectOccupiedSockets(socketControl.getSoul(), soul.getCircuit());
            Pin leftPin = chip.getPin(topRowPin++);
            leftPin.getHangingPoint().setControlConnected(socketControl.getSoul());
            soul.getCircuit().add(leftPin);

            BreadboardSocketControl otherSideSocketControl =
                    breadboard.getSocketFromColConnectedGroup(col, chipRemainingRowSpan - 1,
                                                              !pointedSocketControl.isTopGroupElement());
            otherSideSocketControl.getSoul().getMetalStrip()
                    .connectOccupiedSockets(otherSideSocketControl.getSoul(), soul.getCircuit());
            Pin rightPin = chip.getPin(bottomRowPin++);
            rightPin.getHangingPoint().setControlConnected(otherSideSocketControl.getSoul());
            soul.getCircuit().add(rightPin);


            socketControl.getSoul().setOccupied(occupy);
            otherSideSocketControl.getSoul().setOccupied(occupy);
        }
    }

    private void setChipOnBoard(Point2D position, IntegratedCircuitControl control,
                                Point2D chipViewHotSpot)
    {
        double xPosition = position.getX() + chipViewHotSpot.getX();
        double yPosition = position.getY() + chipViewHotSpot.getY();

        control.relocate(xPosition, yPosition);

        plugChip(control);
    }

    public void plugChip(Node chip)
    {
        addOnHoverHandlers(chip);
        chip.addEventHandler(MouseEvent.MOUSE_CLICKED, clickedOnChipHandler);
        int zIndex = getUnoccupiedChipZIndex();
        digitalTrainerArea.getChildren().add(zIndex, chip);
        chip_zIndex.put(zIndex, true);
    }

    private int getUnoccupiedChipZIndex()
    {
        Set<Integer> zIndex = chip_zIndex.keySet();
        for (Integer z : zIndex)
        {
            if (!chip_zIndex.get(z))
                return z;
        }

        return -1;
    }

    private void hoverChipOnSockets(BreadboardSocketControl pointedSocketControl, int chipColSpan,
                                    int chipRemainingRowSpan, EventType eventType)
    {
        int colBegin = pointedSocketControl.getCol();
        int colEnd = pointedSocketControl.getCol() + chipColSpan;

        for (int col = colBegin; col < colEnd; col++)
        {
            BreadboardSocketControl socketControl =
                    breadboard.getSocketFromColConnectedGroup(col, pointedSocketControl.getRow(),
                            pointedSocketControl.isTopGroupElement());
            BreadboardSocketControl otherSideSocketControl =
                    breadboard.getSocketFromColConnectedGroup(col, chipRemainingRowSpan - 1,
                            !pointedSocketControl.isTopGroupElement());
            if (eventType.equals(MouseEvent.MOUSE_ENTERED))
            {
                onEnteredOnSocket(socketControl.getSoul(), socketControl.getHoleBox());
                onEnteredOnSocket(otherSideSocketControl.getSoul(), otherSideSocketControl.getHoleBox());
            }
            else
            {
                onExitedOnSocket(socketControl.getHoleBox());
                onExitedOnSocket(otherSideSocketControl.getHoleBox());
            }
        }
    }
    /* -end- plug chip
     * */

    /* plug wire
     * */
    public void handleWireToolClicked(Point2D position, Socket socket, Color wireColor)
    {
        if (!socket.isOccupied())
        {
            Wire wire;
            if (!WireControl.isStartSet())
            {
                wire = new TwoWayConnector();
                wire.setAtStartPoint(socket);
                wire.getEndPoint1().setControlConnected(socket);
            }
            else
            {
                wire = processingWireControl.getWire();
                wire.setAtEndPoint(socket);
                wire.getEndPoint2().setControlConnected(socket);

                wire = Wire.finalize(wire);
                soul.getCircuit().add(wire);

                connectMetalStripOccupiedSockets(wire);
            }
            socket.setOccupied(true);

            setWireControlOnBoard(position, wire, wireColor);
        }
    }

    public void handleSelectToolClicked(Socket socket, Point2D position)
    {
        if (!socket.isOccupied())
        {
            Wire wire = processingWireControl.getWire();
            Control connectedControl = null;
            if (!WireControl.isStartSet() && WireControl.isEndSet())
            {
                processingWireControl.setStartX(position.getX());
                processingWireControl.setStartY(position.getY());

                connectedControl = wire.getAtStartPoint();
                wire.setAtStartPoint(socket);

                WireControl.endSet(false);
            }
            else if (WireControl.isStartSet() && !WireControl.isEndSet())
            {
                processingWireControl.setEndX(position.getX());
                processingWireControl.setEndY(position.getY());

                connectedControl = wire.getAtEndPoint();
                wire.setAtEndPoint(socket);
                
                WireControl.startSet(false);
            }

            if (connectedControl != null)
            {
                if (wire.getEndPoint1().getControlConnected().equals(connectedControl))
                    wire.getEndPoint1().setControlConnected(socket);
                else
                    wire.getEndPoint2().setControlConnected(socket);

                wire = Wire.finalize(wire);
                soul.getCircuit().add(wire);

                connectMetalStripOccupiedSockets(wire);
                socket.setOccupied(true);

                processingWireControl.setMouseTransparent(false);

                processingWireControl = null;
            }
        }
    }
    private void setWireControlOnBoard(Point2D position, Wire wire, Color wireColor)
    {
        if (!WireControl.isStartSet() && !WireControl.isEndSet())
        {
            processingWireControl = new WireControl();
            processingWireControl.setWire(wire);
            processingWireControl.setStroke(wireColor);
            processingWireControl.setStrokeWidth(5);
            processingWireControl.setMouseTransparent(true);

            processingWireControl.setStartX(position.getX());
            processingWireControl.setStartY(position.getY());

            processingWireControl.setEndX(position.getX());
            processingWireControl.setEndY(position.getY());

            WireControl.startSet(true);

            plugWire(processingWireControl);
        }
        else if (WireControl.isStartSet() && !WireControl.isEndSet())
        {
            processingWireControl.setEndX(position.getX());
            processingWireControl.setEndY(position.getY());

            processingWireControl.setMouseTransparent(false);

            processingWireControl = null;

            WireControl.startSet(false);
        }
    }

    public void plugWire(Node wire)
    {
        addOnHoverHandlers(wire);
        wire.addEventHandler(MouseEvent.MOUSE_MOVED, movedOnWireHandler);
        wire.addEventHandler(MouseEvent.MOUSE_CLICKED, clickedOnWireHandler);
        digitalTrainerArea.getChildren().add(wire_zIndex++, wire);
    }

    private void connectMetalStripOccupiedSockets(Wire wire)
    {
        Control point1Control = wire.getEndPoint1().getControlConnected();
        if (point1Control instanceof BreadboardSocket)
        {
            BreadboardSocket breadboardSocket = (BreadboardSocket) point1Control;
            breadboardSocket.getMetalStrip().connectOccupiedSockets(breadboardSocket, soul.getCircuit());
        }

        Control point2Control = wire.getEndPoint2().getControlConnected();
        if (point2Control instanceof BreadboardSocket)
        {
            BreadboardSocket breadboardSocket = (BreadboardSocket) point2Control;
            breadboardSocket.getMetalStrip().connectOccupiedSockets(breadboardSocket, soul.getCircuit());
        }
    }
    /* -end- plug wire
     * */

    private void addOnHoverHandlers(Node pluggedTool)
    {
        pluggedTool.addEventHandler(MouseEvent.MOUSE_ENTERED, enteredOnPluggedToolHandler);
        pluggedTool.addEventHandler(MouseEvent.MOUSE_EXITED, exitedOnPluggedToolHandler);
    }

    /* removing plugged tools
     * */
    public void handleClickOnPluggedChip(IntegratedCircuitControl control)
    {
        IntegratedCircuit chip = control.getSoul();
        List<Pin> pins = chip.getPins();
        for (Pin pin : pins)
        {
            remove(pin);
        }

        int zIndex = digitalTrainerArea.getChildren().indexOf(control);
        unplugTool(control);

        chip_zIndex.put(zIndex, false);
        digitalTrainerArea.getChildren().add(zIndex, new IntegratedCircuitControl());
    }

    public void handleClickOnPluggedWire(WireControl control)
    {
        remove(control.getWire());

        unplugTool(control);
        wire_zIndex--;
    }

    public void unplugTool(Node tool)
    {
        digitalTrainerArea.getChildren().remove(tool);
    }

    private void remove(Connector connection)
    {
        soul.getCircuit().deleteConnection(connection);

        ((Socket) connection.getEndPoint1().getControlConnected()).setOccupied(false);
        ((Socket) connection.getEndPoint2().getControlConnected()).setOccupied(false);
    }
    /* -end- removing plugged tools
     * */

    public void handleMoveOnPluggedWire(WireControl wireControl, double xPosition, double yPosition)
    {
        double halfSocket = breadboard.socketSide() / 2;

        boolean nearStartPoint = ((wireControl.getStartX() <= xPosition && wireControl.getStartX()+halfSocket >= xPosition) ||
                                 (wireControl.getStartX() >= xPosition && wireControl.getStartX()-halfSocket <= xPosition)) &&
                                 ((wireControl.getStartY() <= yPosition && wireControl.getStartY()+halfSocket >= yPosition) ||
                                 (wireControl.getStartY() >= yPosition && wireControl.getStartY()-halfSocket <= yPosition));

        boolean nearEndPoint = ((wireControl.getEndX() <= xPosition && wireControl.getEndX()+halfSocket >= xPosition) ||
                               (wireControl.getEndX() >= xPosition && wireControl.getEndX()-halfSocket <= xPosition)) &&
                               ((wireControl.getEndY() <= yPosition && wireControl.getEndY()+halfSocket >= yPosition) ||
                               (wireControl.getEndY() >= yPosition && wireControl.getEndY()-halfSocket <= yPosition));

        if (nearStartPoint)
            createCircle(breadboard.socketSide(), true, wireControl);
        else if (nearEndPoint)
            createCircle(breadboard.socketSide(), false, wireControl);
    }

    private void createCircle(double radius, boolean nearStartPoint, WireControl wireControl)
    {
        double xPosition, yPosition;
        if (nearStartPoint)
        {
            xPosition = wireControl.getStartX();
            yPosition = wireControl.getStartY();
        }
        else
        {
            xPosition = wireControl.getEndX();
            yPosition = wireControl.getEndY();
        }

        final Circle circle = new Circle(radius);
        circle.setStyle("-fx-fill: rgba(0, 0, 0, .4);");

        circle.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                digitalTrainerArea.getChildren().remove(circle);
            }
        });

        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wireControl.setMouseTransparent(true);
                Wire wire = wireControl.getWire();
                if (nearStartPoint)
                {
                    WireControl.startSet(false);
                    WireControl.endSet(true);

                    processingWireControl = wireControl;
                    processingWireControl.setStartX(xPosition);
                    processingWireControl.setStartY(yPosition);

                    Socket atStartPoint = (Socket) wire.getAtStartPoint();
                    atStartPoint.setOccupied(false);
                }
                else
                {
                    WireControl.startSet(true);
                    WireControl.endSet(false);

                    processingWireControl = wireControl;
                    processingWireControl.setEndX(xPosition);
                    processingWireControl.setEndY(yPosition);

                    Socket atEndPoint = (Socket) wire.getAtEndPoint();
                    atEndPoint.setOccupied(false);
                }
                soul.getCircuit().deleteConnection(wire);
            }
        });

        digitalTrainerArea.getChildren().add(circle);
        circle.setTranslateX(xPosition);
        circle.setTranslateY(yPosition);
    }

    public void handleOnSelectToolMove(double xPosition, double yPosition)
    {
        if (!WireControl.isStartSet() && WireControl.isEndSet() && processingWireControl != null)
        {
            processingWireControl.setStartX(xPosition);
            processingWireControl.setStartY(yPosition);
        }
        else if (!WireControl.isEndSet() && WireControl.isStartSet() && processingWireControl != null)
        {
            processingWireControl.setEndX(xPosition);
            processingWireControl.setEndY(yPosition);
        }
    }

    public void handleOnWireToolMove(double xPosition, double yPosition)
    {
        if (WireControl.isStartSet() && !WireControl.isEndSet())
        {
            processingWireControl.setEndX(xPosition);
            processingWireControl.setEndY(yPosition);
        }
    }

    /* hover on socket
     * */
    public void handleOnSelectEnteredOnSocket(Socket socket, Shape socketShape)
    {
        if (processingWireControl != null)
            onEnteredOnSocket(socket, socketShape);
    }

    public void onEnteredOnSocket(Socket socket, Shape socketShape)
    {
        if (!socket.isOccupied())
            socketShape.setStyle("-fx-stroke: red;" +
                    "-fx-stroke-type: inside;");
    }

    public void onExitedOnSocket(Shape socketShape)
    {
        socketShape.setStyle("-fx-stroke: null;");
    }
    /* -end- hover on socket
     * */
}