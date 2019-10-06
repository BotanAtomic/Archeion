package org.cosma.ui.components.manager;

import javafx.scene.layout.Pane;

import java.io.File;

public interface FileLayout {

    void addFile(File file);

    void removeFile(File file);

    Pane getPane();

}
