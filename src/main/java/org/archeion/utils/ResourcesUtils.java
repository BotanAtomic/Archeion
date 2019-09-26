package org.archeion.utils;

import javafx.scene.image.Image;

import java.net.URL;

public class ResourcesUtils {

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

}
