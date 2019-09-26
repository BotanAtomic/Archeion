package org.archeion.ui.components.manager;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.archeion.ui.components.Notification;
import org.archeion.utils.ResourcesUtils;

import java.io.File;

public class GridFlowFileLayout extends FileLayout {


    private FlowPane pane = new FlowPane();

    public GridFlowFileLayout(ScrollPane root) {
        pane.prefWidthProperty().bind(root.widthProperty());

        pane.setPadding(new Insets(20, 20, 20, 20));

        pane.setHgap(5);
        pane.setVgap(5);

        root.setContent(pane);
    }

    @Override
    public void addFile(File file) {
        try {
            Pane fileView = FXMLLoader.load(ResourcesUtils.loadComponent("file/flow_item_grid"));

            Label label = (Label) fileView.lookup("#label");

            MaterialDesignIconView icon = new MaterialDesignIconView(file.isDirectory() ?
                    MaterialDesignIcon.FOLDER : MaterialDesignIcon.FILE);
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

            pane.getChildren().add(fileView);
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Cannot load file " + file.getName(), true);
        }
    }

    @Override
    public void removeFile(File file) {

    }

    @Override
    void clear() {
        pane.getChildren().clear();
    }
}
