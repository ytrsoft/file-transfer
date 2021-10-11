package com.ytrsoft.handler;


import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class ClientHandler extends BaseClientHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    public ClientHandler(String dist) {
        super(dist);
    }

    @Override
    public void onFileSave(File file, byte[] body) {
        synchronized (this) {
            try {
                FileUtils.writeByteArrayToFile(file, body);
                logger.info("file save - " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFileRemove(File file) {
        synchronized (this) {
            FileUtils.deleteQuietly(file);
            logger.info("file remove - " + file.getAbsolutePath());
        }
    }

    @Override
    public void onDirectorySave(File file) {
        synchronized (this) {
            try {
                FileUtils.forceMkdir(file);
                logger.info("directory save - " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDirectoryRemove(File file) {
        synchronized (this) {
            try {
                FileUtils.deleteDirectory(file);
                logger.info("directory remove - " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
