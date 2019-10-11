package org.cosma.utils;

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

    public static String getExtension(File file) {
        if (file.isDirectory())
            return "directory";
        int index = file.getName().lastIndexOf(".");
        return index > 0 ? file.getName().substring(index) : "";
    }

}

