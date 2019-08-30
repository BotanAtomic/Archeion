package org.archeion.ui.components;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.util.stream.Stream;

public class Navigation {

    private static Node backward, forward;

    public static void initialize(HBox buttons) {
        ObservableList<Node> children = buttons.getChildren();
        backward = children.get(0);
        forward = children.get(1);

        Stream.of(backward, forward).forEach(node -> {
            node.disabledProperty().addListener((observable, oldValue, newValue) ->
                    node.setOpacity(newValue ? .5 : 1));
        });

        backward.setDisable(true);
        forward.setDisable(true);
    }

}
