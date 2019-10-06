package org.cosma.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import org.cosma.ui.components.notification.Notification;

import java.net.URL;

public class ResourcesUtils {

    public static URL loadStyleSheet(String name) {
        return ResourcesUtils.class.getClassLoader()
                .getResource(String.format("styles/%s.css", name));
    }


    public static URL loadLayout(String name) {
        return ResourcesUtils.class.getClassLoader()
                .getResource(String.format("layouts/%s.fxml", name));
    }

    public static URL loadComponent(String name) {
        return ResourcesUtils.class.getClassLoader()
                .getResource(String.format("components/%s.fxml", name));
    }


    public static Image loadImage(String name) {
        return new Image(ResourcesUtils.class.getClassLoader()
                .getResource(String.format("assets/images/%s", name)).toExternalForm());
    }

    public static Node loadSecure(URL loadComponent) {
        try {
            return FXMLLoader.load(loadComponent);
        } catch (Exception e) {
            Notification.show("An unexpected error has occurred", true);
            return null;
        }
    }
}
