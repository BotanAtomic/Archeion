package org.archeion.utils;

import java.net.URL;

public class ResourcesUtils {

    public static URL loadLayout(String name) {
        return ResourcesUtils.class.getClassLoader()
                .getResource(String.format("layouts/%s.fxml", name));
    }

}
