package org.archeion.ui.components.manager;

import org.archeion.utils.FileUtils;

import java.io.File;
import java.util.Comparator;
import java.util.stream.Stream;

public abstract class FileLayout {

    private boolean showHidden = true;
    private FileSort fileSort = FileSort.NAME_ASC;
    private File currentPath;

    public abstract void addFile(File file);

    public abstract void removeFile(File file);

    abstract void clear();

    public void load() {
        File[] files = currentPath.listFiles();

        if (files != null && files.length > 0) {
            Stream<File> stream = Stream.of(files);

            stream.filter(file -> file.isHidden() && showHidden || !file.isHidden())
                    .sorted(fileSort.getComparator()).forEach(this::addFile);
        }
    }

    public void reload() {
        clear();
        load();
    }

    public void setCurrentPath(String path, boolean reload) {
        this.currentPath = new File(path);

        if (reload)
            reload();
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
