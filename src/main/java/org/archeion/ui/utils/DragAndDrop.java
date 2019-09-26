package org.archeion.ui.utils;

import javafx.scene.Node;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.util.List;

public class DragAndDrop {

    public static void enableFileDragAndDrop(Node node, Runnable onOver, Runnable onExited, OnFileDragEnd onDragEnd) {
        node.setOnDragOver(event -> {
            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.MOVE);
                onOver.run();
            }
            event.consume();
        });

        node.setOnDragExited(event -> onExited.run());

        node.setOnDragDropped(event -> onDragEnd.perform(event.getDragboard().getFiles()));
    }

    public interface OnFileDragEnd {

        void perform(List<File> files);

    }

}
