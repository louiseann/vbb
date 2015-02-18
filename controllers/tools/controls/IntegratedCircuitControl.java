package vbb.controllers.tools.controls;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import vbb.models.tools.electronic_component.IntegratedCircuit;

/**
 * Created by owie on 2/18/15.
 */
public class IntegratedCircuitControl extends ImageView
{
    private IntegratedCircuit soul;

    public IntegratedCircuitControl()
    {
        super();
    }

    public IntegratedCircuitControl(Image image)
    {
        super(image);
    }

    public IntegratedCircuitControl(String url)
    {
        super(url);
    }

    public IntegratedCircuit getSoul()
    {
        return soul;
    }

    public void setSoul(IntegratedCircuit chip)
    {
        this.soul = chip;
    }
}
