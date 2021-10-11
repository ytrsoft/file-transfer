package com.ytrsoft;

import com.ytrsoft.model.TransferModel;
import com.ytrsoft.model.TransferType;
import com.ytrsoft.handler.ServerHandler;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileManager extends FileAlterationListenerAdaptor {

    private String dist;

    private long interval;

    private static final Logger logger = LoggerFactory.getLogger(FileManager.class);

    @Override
    public void onFileCreate(File file) {
        logger.info("file create - " + file.getAbsolutePath());
        TransferModel model = new TransferModel();
        model.setType(TransferType.FILE_SAVE);
        model.setPath(getPath(file));
        model.setBody(getBody(file));
        ServerHandler.groupWrite(model);
    }

    @Override
    public void onFileChange(File file) {
        logger.info("file change - " + file.getAbsolutePath());
        TransferModel model = new TransferModel();
        model.setType(TransferType.FILE_SAVE);
        model.setPath(getPath(file));
        model.setBody(getBody(file));
        ServerHandler.groupWrite(model);
    }

    @Override
    public void onFileDelete(File file) {
        logger.info("file delete - " + file.getAbsolutePath());
        TransferModel model = new TransferModel();
        model.setType(TransferType.FILE_REMOVE);
        model.setPath(getPath(file));
        ServerHandler.groupWrite(model);
    }

    @Override
    public void onDirectoryCreate(File file) {
        logger.info("directory create - " + file.getAbsolutePath());
        TransferModel model = new TransferModel();
        model.setType(TransferType.DIRECTORY_SAVE);
        model.setPath(getPath(file));
        ServerHandler.groupWrite(model);
    }

    @Override
    public void onDirectoryDelete(File file) {
        logger.info("directory delete - " + file.getAbsolutePath());
        TransferModel model = new TransferModel();
        model.setType(TransferType.DIRECTORY_REMOVE);
        model.setPath(getPath(file));
        ServerHandler.groupWrite(model);
    }

    public void start() {
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval);
        FileAlterationObserver observer = new FileAlterationObserver(dist);
        observer.addListener(this);
        monitor.addObserver(observer);
        try {
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    private String getPath(File file) {
        String path = file.getAbsolutePath();
        return path.substring(dist.length());
    }

    private byte[] getBody(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
