package org.cosma.ui.components.manager;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

import java.io.File;

public interface FileLayout {

    Pane buildFileView(File file);

    void removeFile(File file);

    Pane getPane();

}
