package org.cosma.ui.components.bookmark;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.cosma.ui.icon.IconFinder;

import static org.cosma.ui.utils.UI.newIconLabel;

public class Bookmark {

    private static final Color SELECTED_COLOR = Color.valueOf("#1565c0");
    private static final Color UNSELECTED_COLOR = Color.valueOf("#575757");

    private BookmarkModel model;
    private BooleanProperty selected;

    private Node node;

    public Bookmark(BookmarkModel model) {
        this.model = model;
        this.selected = new SimpleBooleanProperty();

        if (model.isSeparator())
            this.node = new Separator() {{
                setPadding(new Insets(5, 0, 5, 0));
            }};
        else
            createLabel();
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

    private void createLabel() {
        Label label = newIconLabel(IconFinder.find(model.getIconName()), model.getLabel(), "24", "14",
                UNSELECTED_COLOR, UNSELECTED_COLOR);

        label.setPrefHeight(50);
        label.setPrefWidth(200);
        label.setPadding(new Insets(0, 0, 0, 20));

        node = label;

        //  root.getChildren().add(root);
    }

    public void applyStyle(VBox root) {
        if (!model.isSeparator()) {

            Label label = (Label) node;

            model.getLabelProperty().addListener(((observable, oldValue, newValue) -> {
                System.out.println(newValue + " is new");
                label.setText(newValue);
            }));

//            model.getIconProperty().addListener(((observable, oldValue, newValue) -> {
//                root.getChildren().remove(label);
//                createLabel(root);
//            }));

            selectedProperty().addListener((observable, oldValue, selected) -> {
                Color color = selected ? SELECTED_COLOR : UNSELECTED_COLOR;
                label.setTextFill(color);
                ((Text) label.getGraphic()).setFill(color);
            });

            if (model.isSelectable()) {
                node.getStyleClass().add("selectable-bookmark");
            } else {
                node.setCursor(Cursor.HAND);
            }

        }
    }

    public void open() {

    }
}
