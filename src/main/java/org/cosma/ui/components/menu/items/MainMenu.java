package org.cosma.ui.components.menu.items;

import javafx.scene.layout.VBox;
import org.cosma.ui.components.menu.Menu;
import org.cosma.utils.ResourcesUtils;

public class MainMenu implements Menu {

    private final VBox menu;

    public MainMenu() {
        menu = (VBox) ResourcesUtils.loadSecure(ResourcesUtils.loadComponent("menu/main"));
        if (menu != null)
            menu.applyCss();
    }

    @Override
    public VBox getNode() {
        return menu;
    }
}
