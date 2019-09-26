package org.archeion.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

    public static String toString(String filename) {
        try {
            return new String(Files.readAllBytes(new File(filename).toPath()));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
            return "";
        }
    }

    public static String getType(File file) {
        try {
            return Files.probeContentType(file.toPath());
        } catch (IOException e) {
            return null;
        }
    }

}

