package org.cosma.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.cosma.keymap.Keymaps;
import org.cosma.process.TerminalProcess;
import org.cosma.ui.components.manager.FileLayout;
import org.cosma.ui.components.manager.FileLayoutManager;
import org.cosma.ui.components.manager.GridFlowFileLayout;
import org.cosma.ui.components.menu.MenuManager;
import org.cosma.ui.components.menu.items.MainMenu;
import org.cosma.ui.components.navigation.Navigation;
import org.cosma.ui.components.search.SearchBar;
import org.cosma.ui.components.sidebar.Sidebar;
import org.cosma.ui.skin.LinearProgressBarSkin;
import org.cosma.ui.utils.Animation;
import org.cosma.ui.utils.Draggable;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RootController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private HBox topButtons, navigateButtons;

    @FXML
    private Separator regionResizer;

    @FXML
    private ProgressBar mainProgressBar;

    @FXML
    private TextField searchBar;

    @FXML
    private StackPane resetSearchRoot;

    @FXML
    private ScrollPane fileLayoutView;

    @FXML
    private AnchorPane root, searchRoot, sidebarRoot;

    public void attachStage(Stage stage) throws IOException {
        stage.getScene().widthProperty().addListener((observable, oldValue, newValue) -> {
            sidebarRoot.setMinWidth(Math.min(200, newValue.doubleValue() * 0.3));
            sidebarRoot.setMaxWidth(newValue.doubleValue() / 2);
        });

        Keymaps.bind(stage.getScene());

        stage.getScene().setOnMouseClicked(e -> {
            SearchBar.hide();
            MenuManager.hideCurrent();
        });

        Sidebar.insert(sidebarRoot, borderPane);

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

        MenuManager.setRoot(root);
        Navigation.initialize(navigateButtons);
        Draggable.setPaneHorizontalResizable(sidebarRoot, regionResizer);
        SearchBar.initialize(searchBar, resetSearchRoot);
        SearchBar.hide();

        searchBar.setOpacity(1);

        mainProgressBar.setSkin(new LinearProgressBarSkin(mainProgressBar));

        FileLayoutManager.init(mainProgressBar);
        FileLayoutManager.setLayout(new GridFlowFileLayout(fileLayoutView));
        FileLayoutManager.setCurrentPath(System.getProperty("user.home"));
    }

    @FXML
    private void openDispositionMenu() {

    }

    @FXML
    private void openMainMenu(MouseEvent event) {
        MainMenu mainMenu = new MainMenu();
        if (MenuManager.show(mainMenu, 0, 0)) {
            AnchorPane.setTopAnchor(mainMenu.getNode(), topButtons.getHeight());
            AnchorPane.setRightAnchor(mainMenu.getNode(), 0d);
            event.consume();
        }
    }

    @FXML
    private void openSortMenu() {
        FileLayoutManager.nextSort();
    }

    @FXML
    private void openTerminal() {
        TerminalProcess.open(FileLayoutManager.getCurrentPath().getAbsolutePath());
    }

    @FXML
    private void remove() {

    }

    @FXML
    private void share() {

    }

    @FXML
    private void toggleSearch() {
        SearchBar.toggle();
    }

}
