package org.archeion.ui.utils;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animation {

    public static void opacityAnimationHover(double value, Node node) {
        node.setOnMouseEntered(e -> node.setOpacity(value));
        node.setOnMouseExited(e -> node.setOpacity(1));
    }

    public static void scaleAnimationPressed(double value, Node node) {
        node.setOnMousePressed(e -> {
            node.setScaleX(value);
            node.setScaleY(value);
        });

        node.setOnMouseReleased(e -> {
            node.setScaleX(1);
            node.setScaleY(1);
        });
    }

    public static Transition fadeTransition(boolean hide, int ms, Node node) {
        if (hide && UI.isVisible(node) || !hide && !UI.isVisible(node))
            return fadeTransition(hide ? 1 : 0, hide ? 0 : 1, ms, node);
        else
            return null;
    }

    public static Transition fadeTransition(double from, double to, int ms, Node node) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setFromValue(from);
        fadeTransition.setToValue(to);
        fadeTransition.setNode(node);
        fadeTransition.setDuration(Duration.millis(ms));
        fadeTransition.play();
        return fadeTransition;
    }

}
