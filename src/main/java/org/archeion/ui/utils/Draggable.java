package org.archeion.ui.utils;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.util.concurrent.atomic.AtomicReference;

public class Draggable {

    public static void setPaneHorizontalResizable(Pane node, Region region) {
        AtomicReference<Double> xOffset = new AtomicReference<>(0d);

        region.setOnMouseDragEntered(event -> xOffset.set(event.getX()));

        region.setOnMouseDragged(event -> {
            double delta = event.getX() - xOffset.get();
            node.setPrefWidth(node.getWidth() + delta);
            node.setMaxWidth(node.getScene().getWidth() / 2);
        });
    }

}
