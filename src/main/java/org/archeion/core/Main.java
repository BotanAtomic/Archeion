package org.archeion.core;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import org.archeion.ui.controller.RootController;
import org.archeion.ui.utils.UI;
import org.archeion.utils.ResourcesUtils;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ResourcesUtils.loadLayout("root"));


        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);

        ((RootController) loader.getController()).attachStage(primaryStage);

        primaryStage.setMinHeight(UI.MIN_HEIGHT);
        primaryStage.setMinWidth(UI.MIN_WIDTH);

        primaryStage.show();
    }
}
