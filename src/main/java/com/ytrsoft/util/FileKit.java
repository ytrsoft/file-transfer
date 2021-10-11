package com.ytrsoft.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class FileKit {

    private FileKit() {
        throw new UnsupportedOperationException();
    }

    public static void deleteAll(String dist) {
        try {
            Files.walk(Paths.get(dist))
                 .filter(Files::isRegularFile)
                 .map(Path::toFile)
                 .forEach(((file) -> {
                    if (file.isFile()) {
                        FileUtils.deleteQuietly(file);
                    } else {
                        try {
                            FileUtils.deleteDirectory(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<File> list(String dist) {
        List<File> list = new ArrayList<>();
        try {
            Files.walk(Paths.get(dist))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .forEach(list::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
