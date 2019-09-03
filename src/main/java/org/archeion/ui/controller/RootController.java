package org.archeion.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.archeion.ui.components.Navigation;
import org.archeion.ui.components.SearchBar;
import org.archeion.ui.components.Sidebar;
import org.archeion.ui.keymap.Keymaps;
import org.archeion.ui.skin.LinearProgressBarSkin;
import org.archeion.ui.utils.Animation;
import org.archeion.ui.utils.Draggable;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RootController {
    @FXML
    private HBox topButtons, navigateButtons;

    @FXML
    private Region regionResizer;

    @FXML
    private AnchorPane sidebarRoot;

    @FXML
    private ProgressBar mainProgressBar;

    @FXML
    private TextField searchBar;

    @FXML
    private StackPane resetSearchRoot;


    public void attachStage(Stage stage) {
        stage.getScene().widthProperty().addListener((observable, oldValue, newValue) -> {
            sidebarRoot.setMinWidth(Math.min(270, newValue.doubleValue() * 0.3));
            sidebarRoot.setMaxWidth(newValue.doubleValue() / 2);
        });

        Keymaps.bind(stage.getScene());

        stage.getScene().setOnMouseClicked(e -> SearchBar.hide());
    }

    @FXML
    private void initialize() throws IOException {
        Stream.concat(
                topButtons.getChildren().stream(),
                navigateButtons.getChildren().stream())
                .collect(Collectors.toList()).forEach(node -> {
            if (node instanceof StackPane) {
                Animation.opacityAnimationHover(.7, node);
                Animation.scaleAnimationPressed(.9, node);
            }
        });

        Navigation.initialize(navigateButtons);
        Draggable.setPaneHorizontalResizable(sidebarRoot, regionResizer);
        SearchBar.initialize(searchBar, resetSearchRoot);

        searchBar.setOpacity(0);

        Sidebar.insert(sidebarRoot);

        mainProgressBar.setSkin(new LinearProgressBarSkin(mainProgressBar));
    }

    @FXML
    private void toggleSearch() {
        SearchBar.toggle();
    }

}
