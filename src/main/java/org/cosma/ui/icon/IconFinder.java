package org.cosma.ui.icon;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import org.cosma.utils.FileUtils;

import java.io.File;

public class IconFinder {

    public static MaterialDesignIcon find(String name) {
        try {
            return MaterialDesignIcon.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return MaterialDesignIcon.HELP;
        }
    }

    public static MaterialDesignIcon byFile(File file) {
        if(file == null)
            return MaterialDesignIcon.HELP;

        if(file.isDirectory()) {
            File[] files = file.listFiles();

            return (files != null && files.length > 0) ? MaterialDesignIcon.FOLDER : MaterialDesignIcon.FOLDER_OUTLINE;
        } else {
            String extension = FileUtils.getExtension(file);
        }

        return MaterialDesignIcon.HELP;
    }

}
