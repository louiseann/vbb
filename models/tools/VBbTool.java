package vbb.models.tools;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import vbb.controllers.tools.controls.IntegratedCircuitControl;
import vbb.models.connection.connector.TwoWayConnector;
import vbb.models.logic_gates.*;
import vbb.models.tools.connectors.Wire;
import vbb.models.tools.electronic_component.IntegratedCircuit;
import vbb.models.tools.electronic_component.TTL74SeriesIC;

/**
 * Created by owie on 1/25/15.
 */
public final class VBbTool
{
    private static VBbTool instance = new VBbTool();

    private static Tool select;

    private static Tool andChip;
    private static Tool orChip;
    private static Tool notChip;
    private static Tool nandChip;
    private static Tool norChip;
    private static Tool xorChip;
    private static Tool xnorChip;

    private static Tool wire;

    private VBbTool()
    {
        createSelect();
        createAndChip();
        createOrChip();
        createNotChip();
        createNandChip();
        createNorChip();
        createXorChip();
        createXnorChip();
        createWire();
    }

    private static void createSelect()
    {
        Image selectPointer = new Image("/vbb/views/images/tools/select_pointer.png");
        ImageCursor selectCursor = new ImageCursor(selectPointer);
        Select selectTool = new Select();
        select = createTool("select", selectTool, new ImageView(), 0, 0, selectCursor);
    }

    public static Tool Select()
    {
        return select;
    }

    private static void createAndChip()
    {
        ImageView andToolImage = new ImageView(new Image("/vbb/views/images/tools/chips/and_tool.png"));
        IntegratedCircuit andIC = new IntegratedCircuit(AndGate.getInstance());
        andChip = createTool("and", andIC, andToolImage, -3, -3.5, Cursor.NONE);
    }

    public static Tool AndChip()
    {
        return andChip;
    }

    private static void createOrChip()
    {
        ImageView orToolImage = new ImageView(new Image("/vbb/views/images/tools/chips/or_tool.png"));
        IntegratedCircuit orIC = new IntegratedCircuit(OrGate.getInstance());
        orChip = createTool("or", orIC, orToolImage, -3, -3.5, Cursor.NONE);
    }

    public static Tool OrChip()
    {
        return orChip;
    }

    private static void createNotChip()
    {
        ImageView notToolImage = new ImageView(new Image("/vbb/views/images/tools/chips/not_tool.png"));
        IntegratedCircuit notIC = new IntegratedCircuit(NotGate.getInstance());
        notChip = createTool("or", notIC, notToolImage, -3, -3.5, Cursor.NONE);
    }

    public static Tool NotChip()
    {
        return notChip;
    }

    private static void createNandChip()
    {
        ImageView nandToolImage = new ImageView(new Image("/vbb/views/images/tools/chips/nand_tool.png"));
        IntegratedCircuit nandIC = new IntegratedCircuit(NandGate.getInstance());
        nandChip = createTool("or", nandIC, nandToolImage, -3, -3.5, Cursor.NONE);
    }

    public static Tool NandChip()
    {
        return nandChip;
    }

    private static void createNorChip()
    {
        ImageView norToolImage = new ImageView(new Image("/vbb/views/images/tools/chips/nor_tool.png"));
        IntegratedCircuit norIC = new IntegratedCircuit(NorGate.getInstance());
        norChip = createTool("or", norIC, norToolImage, -3, -3.5, Cursor.NONE);
    }

    public static Tool NorChip()
    {
        return norChip;
    }

    private static void createXorChip()
    {
        ImageView xorToolImage = new ImageView(new Image("/vbb/views/images/tools/chips/xor_tool.png"));
        IntegratedCircuit xorIC = new IntegratedCircuit(XorGate.getInstance());
        xorChip = createTool("or", xorIC, xorToolImage, -3, -3.5, Cursor.NONE);
    }

    public static Tool XorChip()
    {
        return xorChip;
    }

    private static void createXnorChip()
    {
        ImageView xnorToolImage = new ImageView(new Image("/vbb/views/images/tools/chips/xor_tool.png"));
        IntegratedCircuit xnorIC = new IntegratedCircuit(XnorGate.getInstance());
        xnorChip = createTool("or", xnorIC, xnorToolImage, -3, -3.5, Cursor.NONE);
    }

    public static Tool XnorChip()
    {
        return xnorChip;
    }

    private static void createWire()
    {
        Image wirePointer = new Image("/vbb/views/images/tools/wire_pointer.png");
        ImageCursor wireCursor = new ImageCursor(wirePointer, 0, wirePointer.getHeight());
        Wire wireTool = new TwoWayConnector();
        wire = createTool("wire", wireTool, wireCursor);

        double yHotSpot = ImageCursor.getBestSize(wirePointer.getWidth(), wirePointer.getHeight()).getHeight();
        wire.setViewHotSpot(3, -yHotSpot);
    }

    public static Tool Wire()
    {
        return wire;
    }

    private static Tool createTool(String name, Object classification, Node view,
                                   double xViewHotSpot, double yViewHotSpot, Cursor cursor)
    {
        Tool tool = new Tool(name);
        tool.setClassification(classification);
        tool.setView(view, xViewHotSpot, yViewHotSpot);
        tool.setCursor(cursor);

        return tool;
    }

    private static Tool createTool(String name, Object classification, Cursor cursor)
    {
        Tool tool = new Tool(name);
        tool.setClassification(classification);
        tool.setCursor(cursor);

        return tool;
    }
}
