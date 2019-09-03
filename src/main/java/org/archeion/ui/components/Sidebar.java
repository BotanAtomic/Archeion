package org.archeion.ui.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import org.archeion.utils.ResourcesUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sidebar {

    private static final String SELECTED_COLOR = "#1565c0";
    private static final String UNSELECTED_COLOR = "#575757";

    private final static List<HBox> bookmarksView = new ArrayList<>();

    private static void applyColor(HBox bookmarkView, String color) {
        StackPane first = (StackPane) bookmarkView.getChildren().get(0);
        StackPane second = (StackPane) bookmarkView.getChildren().get(1);

        if (first.getChildren().size() > 0) {
            SVGPath chevron = (SVGPath) first.getChildren().get(0);
            chevron.setFill(Color.valueOf(color));
        }

        SVGPath icon = (SVGPath) second.getChildren().get(0);
        icon.setFill(Color.valueOf(color));

        Label label = (Label) bookmarkView.getChildren().get(2);
        label.setTextFill(Color.valueOf(color));

        bookmarkView.setStyle("");
    }

    private static void onBookmarkSelected(HBox bookmarkView) {
        bookmarksView.add(bookmarkView);
        bookmarkView.setOnMouseClicked(e -> {
            bookmarksView.forEach(b -> applyColor(b, UNSELECTED_COLOR));
            applyColor(bookmarkView, SELECTED_COLOR);
            bookmarkView.setStyle(bookmarkView.getStyle() + "-fx-background-color: rgba(21, 101, 192, 0.2)");
        });
    }

    public static void insert(AnchorPane root) throws IOException {
        ScrollPane sidebar = FXMLLoader.load(ResourcesUtils.loadComponent("sidebar"));

        root.getChildren().add(sidebar);

        AnchorPane.setBottomAnchor(sidebar, 0d);
        AnchorPane.setLeftAnchor(sidebar, 0d);
        AnchorPane.setRightAnchor(sidebar, 0d);
        AnchorPane.setTopAnchor(sidebar, 0d);

        VBox box = (VBox) sidebar.getContent();
        box.prefWidthProperty().bind(sidebar.widthProperty());

        box.getChildren().filtered(node -> !(node instanceof Separator)).forEach(node -> {
            if (node instanceof VBox) {
                ((VBox) node).getChildrenUnmodifiable().forEach(hBox -> onBookmarkSelected((HBox) hBox));
            } else if (node instanceof HBox) {
                onBookmarkSelected((HBox) node);
            }
        });
    }

}
