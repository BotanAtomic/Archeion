package org.cosma.ui.icon;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;

public class IconFinder {

    public static MaterialDesignIcon find(String name) {
        try {
            return MaterialDesignIcon.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return MaterialDesignIcon.HELP;
        }
    }

}
