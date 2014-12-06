package vbb.digital_trainer.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

/**
 * Created by owie on 12/6/14.
 */
public class DigitalTrainerController
{
    @FXML
    private HBox digitalTrainer;

    @FXML
    private BreadboardController breadboardController;

    @FXML
    private void initizalize()
    {
        assert digitalTrainer != null : "not initialized!";
        breadboardController.createSocketHoles();
    }
}
