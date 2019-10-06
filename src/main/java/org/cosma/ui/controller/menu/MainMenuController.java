package org.cosma.ui.controller.menu;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.cosma.ui.components.manager.FileLayoutManager;
import org.cosma.ui.components.sidebar.Sidebar;

public class MainMenuController {

    @FXML
    private Label hiddenFileLabel;

    @FXML
    private Label sidebarLabel;

    @FXML
    private void initialize() {
        ((CheckBox) hiddenFileLabel.getGraphic()).setSelected(FileLayoutManager.isHiddenFile());
        ((CheckBox) sidebarLabel.getGraphic()).setSelected(Sidebar.isVisible());
    }

    @FXML
    private void about(MouseEvent event) {
        event.consume();
    }

    @FXML
    private void openSettings(MouseEvent event) {
        event.consume();
    }

    @FXML
    private void selectAll(MouseEvent event) {
        event.consume();
    }

    @FXML
    private void toggleHiddenFile(MouseEvent event) {
        FileLayoutManager.toggleHiddenFile();
        ((CheckBox) hiddenFileLabel.getGraphic()).setSelected(FileLayoutManager.isHiddenFile());

        event.consume();
    }

    @FXML
    private void toggleSidebar(MouseEvent event) {
        Sidebar.toggleSidebar();
        ((CheckBox) sidebarLabel.getGraphic()).setSelected(Sidebar.isVisible());

        event.consume();
    }

}
