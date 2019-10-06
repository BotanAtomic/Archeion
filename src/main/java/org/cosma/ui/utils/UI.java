package org.cosma.ui.utils;


import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class UI {

    public static final int SEARCH_TRANSITION_TIME = 300;
    public static final double MIN_WIDTH = 733, MIN_HEIGHT = 700;


    public static boolean isVisible(Node node) {
        return node.getOpacity() > 0;
    }


    public static Label newIconLabel(MaterialDesignIcon icon, String text, String iconSize, String fontSize, Color labelColor, Color graphicColor) {
        Label label = MaterialDesignIconFactory.get().createIconLabel(
                icon, text,
                iconSize, fontSize,
                ContentDisplay.LEFT
        );

        Text graphic = (Text) label.getGraphic();
        graphic.setFill(graphicColor);
        label.setTextFill(labelColor);

        return label;
    }
}
