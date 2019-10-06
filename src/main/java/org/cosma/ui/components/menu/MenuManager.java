package org.cosma.ui.components.menu;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.AnchorPane;

public class MenuManager {

    private static Menu currentMenu;
    private static AnchorPane root;

    private static BooleanProperty active = new SimpleBooleanProperty(false);

    public static boolean show(Menu menu, double x, double y) {
        if (currentMenu != null && currentMenu.getClass().equals(menu.getClass()) && active.get())
            return false;

        hideCurrent();

        currentMenu = menu;
        root.getChildren().add(menu.getNode());

        menu.getNode().setLayoutX(x);
        menu.getNode().setLayoutY(y);

        active.setValue(true);
        return true;
    }

    public static void hideCurrent() {
        if (currentMenu != null && active.get()) {
            root.getChildren().remove(currentMenu.getNode());
            currentMenu = null;
            active.setValue(false);
        }
    }

    public static void setRoot(AnchorPane root) {
        MenuManager.root = root;
    }

    public static BooleanProperty isVisible() {
        return active;
    }
}
