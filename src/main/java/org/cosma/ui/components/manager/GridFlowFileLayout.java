package org.cosma.ui.components.manager;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.cosma.ui.components.notification.Notification;
import org.cosma.ui.icon.IconFinder;
import org.cosma.utils.ResourcesUtils;

import java.io.File;

public class GridFlowFileLayout implements FileLayout {

    private FlowPane pane = new FlowPane();

    public GridFlowFileLayout(ScrollPane root) {
        pane.prefWidthProperty().bind(root.widthProperty());

        pane.setPadding(new Insets(20, 20, 20, 20));

        pane.setHgap(5);
        pane.setVgap(5);

        root.setContent(pane);
    }

    @Override
    public Pane buildFileView(File file) {
        if(file == null)
            return null;

        try {
            Pane fileView = FXMLLoader.load(ResourcesUtils.loadComponent("file/flow_item_grid"));

            Label label = (Label) fileView.lookup("#label");

            MaterialDesignIconView icon = new MaterialDesignIconView(IconFinder.byFile(file));
            icon.setGlyphSize(24);
            fileView.getChildren().add(0, icon);
            icon.setFill(Color.valueOf("#575757"));

            label.setText(file.getName());

            fileView.setStyle("-fx-background-color: white;");

            fileView.setOnMouseClicked(e -> {
                if (!fileView.getStyle().contains("#BBDEFB"))
                    fileView.setStyle("-fx-background-color: #BBDEFB;");
                else
                    fileView.setStyle("-fx-background-color: white;");
            });

            fileView.getStyleClass().add("flow-grid");

            return fileView;
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Cannot load file " + file.getName(), true);
        }

        return null;
    }

    @Override
    public void removeFile(File file) {

    }

    @Override
    public Pane getPane() {
        return pane;
    }

}
