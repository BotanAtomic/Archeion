package org.cosma.ui.components.manager;

import org.cosma.utils.FileUtils;

import java.io.File;
import java.util.Comparator;
import java.util.stream.Stream;

public class FileLayoutManager {

    private static File currentPath;

    private static boolean hiddenFile;
    private static FileSort fileSort = FileSort.NAME_ASC;

    private static FileLayout layout;

    public static void setLayout(FileLayout layout) {
        FileLayoutManager.layout = layout;
    }

    public static void toggleHiddenFile() {
        hiddenFile = !hiddenFile;
        reload();
    }

    public static boolean isHiddenFile() {
        return hiddenFile;
    }

    public static void setCurrentPath(String path) {
        currentPath = new File(path);

        System.out.println("Set current path " +  path + " / exists: " + currentPath.exists());

        reload();
    }

    private static void load() {
        File[] files = currentPath.listFiles();

        if (files != null && files.length > 0) {
            Stream<File> stream = Stream.of(files);

            stream.filter(file -> file.isHidden() && hiddenFile || !file.isHidden())
                    .sorted(fileSort.getComparator()).forEach(layout::addFile);
        }
    }

    private static void clear() {
        layout.getPane().getChildren().clear();
    }

    public static void reload() {
        clear();
        load();
    }


    public enum FileSort {
        NAME_ASC,
        NAME_DESC,
        LAST_MODIFIED,
        FIRST_MODIFIED,
        SIZE,
        TYPE;

        Comparator<File> getComparator() {
            switch (this) {
                case NAME_ASC:
                    return Comparator.comparing(File::getName);
                case NAME_DESC:
                    return Comparator.comparing(File::getName).reversed();
                case LAST_MODIFIED:
                    return Comparator.comparingLong(File::lastModified);
                case FIRST_MODIFIED:
                    return Comparator.comparingLong(File::lastModified).reversed();
                case SIZE:
                    return Comparator.comparingLong(File::length);
                case TYPE:
                    return Comparator.comparing(FileUtils::getType);
            }

            return Comparator.comparing(f -> f);
        }
    }

}
