package org.archeion.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.archeion.ui.components.Navigation;
import org.archeion.ui.components.SearchBar;
import org.archeion.ui.utils.Animation;
import org.archeion.ui.utils.Draggable;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RootController {
    @FXML
    private HBox topButtons, navigateButtons;

    @FXML
    private TextField search;

    @FXML
    private ImageView resetSearchIcon;

    @FXML
    private Region regionResizer;

    @FXML
    private AnchorPane sidebar;

    public void attachStage(Stage stage) {
        stage.getScene().widthProperty().addListener((observable, oldValue, newValue) -> {
            sidebar.setMinWidth(Math.min(250, newValue.doubleValue() * 0.2));
            sidebar.setMaxWidth(newValue.doubleValue() / 2);
        });
    }

    @FXML
    private void initialize() {
        Stream.concat(
                topButtons.getChildren().stream(),
                navigateButtons.getChildren().stream())
                .collect(Collectors.toList()).forEach(node -> {
            if (node instanceof StackPane) {
                Animation.opacityAnimationHover(.7, node);
                Animation.scaleAnimationPressed(.9, node);
            }
        });

        SearchBar.initialize(search, resetSearchIcon);
        Navigation.initialize(navigateButtons);
        Draggable.setPaneHorizontalResizable(sidebar, regionResizer);
    }

    @FXML
    private void toggleSearch() {
        SearchBar.toggle();
    }

    @FXML
    private void resetSearch() {
        SearchBar.reset();
    }


}
