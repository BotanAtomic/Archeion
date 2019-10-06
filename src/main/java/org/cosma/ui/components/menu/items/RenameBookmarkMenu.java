package org.cosma.ui.components.menu.items;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import org.cosma.ui.components.bookmark.BookmarkModel;
import org.cosma.utils.ResourcesUtils;

public class RenameBookmarkMenu {

    private final TextField node;

    public RenameBookmarkMenu(BookmarkModel model) {
        node = new TextField() {{
            setText(model.getLabel());
            setHeight(30);
            setWidth(220);
            getStylesheets().add(ResourcesUtils.loadStyleSheet("textfield").toExternalForm());
            getStyleClass().add("round");
        }};

        String initialValue = model.getLabel();


            node.textProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue.isBlank() || newValue.isEmpty() || newValue.equals(model.getLabel())) {
                    model.getLabelProperty().set(initialValue);
                } else {
                    model.getLabelProperty().set(newValue.trim());
                }
            });

    }

    public Node getNode() {
        return node;
    }
}
