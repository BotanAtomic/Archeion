package org.archeion.ui.components.bookmark;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import org.archeion.ui.components.sidebar.Sidebar;
import org.archeion.ui.icon.IconFinder;
import org.archeion.utils.ResourcesUtils;

import java.io.IOException;

import static org.archeion.ui.utils.UI.newIconLabel;

public class Bookmark {

    private static final Color SELECTED_COLOR = Color.valueOf("#1565c0");
    private static final Color UNSELECTED_COLOR = Color.valueOf("#575757");

    private BookmarkModel model;
    private BooleanProperty selected;

    private Node node;

    public Bookmark(BookmarkModel model) throws IOException {
        this.node = model.isSeparator() ? new Separator() {{
            setPadding(new Insets(5, 0, 5, 0));
        }} : FXMLLoader.load(ResourcesUtils.loadComponent("sidebar_item"));
        this.model = model;
        this.selected = new SimpleBooleanProperty();
    }

    private boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public Node getNode() {
        return node;
    }

    public BookmarkModel getModel() {
        return model;
    }

    private void expend() {
        System.out.println("Expend " + model.getLabel());
    }

    public void applyStyle() {
        if (!model.isSeparator()) {
            HBox root = (HBox) node.lookup("#root");
            SVGPath expandableIcon = (SVGPath) node.lookup("#expendIcon");

            Label label = newIconLabel(IconFinder.find(model.getIconName()), model.getLabel(), "24", "14", UNSELECTED_COLOR, UNSELECTED_COLOR);

            root.getChildren().add(label);

            if (!model.isExpendable())
                expandableIcon.setContent("");


            selectedProperty().addListener((observable, oldValue, selected) -> {
                Color color = selected ? SELECTED_COLOR : UNSELECTED_COLOR;
                label.setTextFill(color);
                ((Text) label.getGraphic()).setFill(color);
                expandableIcon.setFill(color);
            });

            if (model.isSelectable()) {
                node.getStyleClass().add("selectable-bookmark");
            } else {
                node.setCursor(Cursor.HAND);
            }

            if (model.isExpendable()) {
                expandableIcon.getParent().setOnMouseClicked(e -> {
                    if (!isSelected())
                        Sidebar.selectBookmark(this);

                    expend();
                });
            }

        }
    }

    public void open() {

    }
}
