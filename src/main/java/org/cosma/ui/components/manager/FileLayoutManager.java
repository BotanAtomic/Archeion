package org.cosma.ui.components.manager;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import org.cosma.process.task.FileLoaderService;
import org.cosma.utils.FileUtils;

import java.io.File;
import java.util.Comparator;
import java.util.concurrent.Executors;

public class FileLayoutManager {

    private final static FileLoaderService service = new FileLoaderService();


    private static File currentPath;

    private static boolean hiddenFile;
    private static FileSort fileSort = FileSort.NAME_ASC;

    private static FileLayout layout;

    public static void toggleHiddenFile() {
        hiddenFile = !hiddenFile;
        reload();
    }

    public static boolean isHiddenFile() {
        return hiddenFile;
    }

    public static File getCurrentPath() {
        return currentPath;
    }

    public static void setCurrentPath(String path) {
        currentPath = new File(path);

        System.out.println("Set current path " + path + " / exists: " + currentPath.exists());

        reload();
    }

    private static void load() {
        service.restart();
    }

    private static void clear() {
        layout.getPane().getChildren().clear();
    }

    public static void nextSort() {
        fileSort = fileSort.getNext();
        reload();
    }

    private static void reload() {
        clear();
        load();
    }

    public static void init(ProgressBar progressBar) {
        service.setExecutor(Executors.newCachedThreadPool());

        service.setOnScheduled(e ->  {
            progressBar.setVisible(true);
        });

        service.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                layout.getPane().getChildren().addAll(newValue);
            }
        }));

        service.setOnSucceeded(e -> progressBar.setVisible(false));
    }

    public static FileSort getSort() {
        return fileSort;
    }

    public static FileLayout getLayout() {
        return layout;
    }

    public static void setLayout(FileLayout layout) {
        FileLayoutManager.layout = layout;
    }


    public enum FileSort {
        NAME_ASC,
        NAME_DESC,
        LAST_MODIFIED,
        FIRST_MODIFIED,
        SIZE,
        TYPE;

        public Comparator<File> getComparator() {
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
                    return Comparator.comparing(FileUtils::getExtension);
            }

            return Comparator.comparing(f -> f);
        }

        FileSort getNext() {
            return values()[(this.ordinal() + 1) % values().length];
        }
    }

}
