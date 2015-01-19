package vbb.controllers.tools.controls;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Created by owie on 1/17/15.
 */
public class ColorPicker extends GridPane
{
    @FXML
    private Rectangle color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12;
    @FXML
    private Rectangle color1Box, color2Box, color3Box, color4Box, color5Box, color6Box, color7Box, color8Box, color9Box,
            color10Box, color11Box, color12Box;

    private StringProperty selectedColor;

    public ColorPicker()
    {
        FXMLLoader switchLoader = new FXMLLoader(getClass().getResource("/vbb/views/fxml/tools/color_picker.fxml"));
        switchLoader.setRoot(this);
        switchLoader.setController(this);

        try {
            switchLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void initialize()
    {
        selectedColor = new SimpleStringProperty(getHex((Color) color1.getFill()));
        setSelectedColor(getHex((Color) color1.getFill()));
        focusColor(color1Box);

        color1Box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleOnMouseClick((Color) color1.getFill(), color1Box);
            }
        });
        color2Box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleOnMouseClick((Color) color2.getFill(), color2Box);
            }
        });
        color3Box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleOnMouseClick((Color) color3.getFill(), color3Box);
            }
        });
        color4Box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleOnMouseClick((Color) color4.getFill(), color4Box);
            }
        });
        color5Box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleOnMouseClick((Color) color5.getFill(), color5Box);
            }
        });
        color6Box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleOnMouseClick((Color) color6.getFill(), color6Box);
            }
        });
        color7Box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleOnMouseClick((Color) color7.getFill(), color7Box);
            }
        });
        color8Box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleOnMouseClick((Color) color8.getFill(), color8Box);
            }
        });
        color9Box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleOnMouseClick((Color) color9.getFill(), color9Box);
            }
        });
        color10Box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleOnMouseClick((Color) color10.getFill(), color10Box);
            }
        });
        color11Box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleOnMouseClick((Color) color11.getFill(), color11Box);
            }
        });
        color12Box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleOnMouseClick((Color) color12.getFill(), color12Box);
            }
        });
    }

    private void handleOnMouseClick(Color color, Rectangle colorBoxClicked)
    {
        Rectangle selectedColorBox = getBoxWithColor(getSelectedColor());
        unfocusColor(selectedColorBox);

        focusColor(colorBoxClicked);
        setSelectedColor(getHex(color));
    }

    public StringProperty selectedColor()
    {
        return selectedColor;
    }

    public String getSelectedColor()
    {
        return selectedColor.get();
    }

    private void setSelectedColor(String color)
    {
        selectedColor.set(color);
    }

    private void focusColor(Rectangle colorBox)
    {
        colorBox.setStyle("-fx-stroke: #000000; -fx-stroke-type: inside;");
    }

    private void unfocusColor(Rectangle colorBox)
    {
        colorBox.setStyle("-fx-stroke: #ffffff; -fx-stroke-type: inside;");
    }

    private Rectangle getBoxWithColor(String hexColor)
    {
        if (getHex((Color) color1.getFill()).equals(hexColor))
            return color1Box;
        else if (getHex((Color) color2.getFill()).equals(hexColor))
            return color2Box;
        else if (getHex((Color) color3.getFill()).equals(hexColor))
            return color3Box;
        else if (getHex((Color) color4.getFill()).equals(hexColor))
            return color4Box;
        else if (getHex((Color) color5.getFill()).equals(hexColor))
            return color5Box;
        else if (getHex((Color) color6.getFill()).equals(hexColor))
            return color6Box;
        else if (getHex((Color) color7.getFill()).equals(hexColor))
            return color7Box;
        else if (getHex((Color) color8.getFill()).equals(hexColor))
            return color8Box;
        else if (getHex((Color) color9.getFill()).equals(hexColor))
            return color9Box;
        else if (getHex((Color) color10.getFill()).equals(hexColor))
            return color10Box;
        else if (getHex((Color) color11.getFill()).equals(hexColor))
            return color11Box;
        else if (getHex((Color) color12.getFill()).equals(hexColor))
            return color12Box;
        else
            return null;
    }

    private String getHex(Color color)
    {
        return String.format( "#%02X%02X%02X",
               (int)( color.getRed() * 255 ),
               (int)( color.getGreen() * 255 ),
               (int)( color.getBlue() * 255 ) );
    }
}
