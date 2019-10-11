package org.cosma.process.task;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.layout.Pane;
import org.cosma.ui.components.manager.FileLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileLoaderService extends Service<List<Pane>> {


    @Override
    protected Task<List<Pane>> createTask() {
        return new Task<>() {
            @Override
            protected List<Pane> call() throws Exception {
                File[] files = FileLayoutManager.getCurrentPath().listFiles();

                if (files != null && files.length > 0) {
                    Stream<File> stream = Stream.of(files);

                    return stream
                            .filter(file -> file.isHidden() && FileLayoutManager.isHiddenFile() || !file.isHidden())
                            .sorted(FileLayoutManager.getSort().getComparator())
                            .map(file -> FileLayoutManager.getLayout().buildFileView(file))
                            .collect(Collectors.toList());
                }
                return new ArrayList<>();
            }
        };
    }
}
