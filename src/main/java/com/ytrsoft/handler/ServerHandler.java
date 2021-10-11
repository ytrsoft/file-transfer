package com.ytrsoft.handler;

import com.ytrsoft.FileManager;
import com.ytrsoft.model.TransferModel;
import com.ytrsoft.model.TransferType;
import com.ytrsoft.util.FileKit;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerHandler extends IoHandlerAdapter {

    private final String dist;

    private final long interval;

    private static final Set<IoSession> sessions = Collections.synchronizedSet(new HashSet<>());

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);


    public ServerHandler(String dist, long interval) {
        this.dist = dist;
        this.interval = interval;
        this.initialize();
    }

    public void initialize() {
        FileManager manager = new FileManager();
        manager.setInterval(this.interval);
        manager.setDist(this.dist);
        manager.start();
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        sessions.add(session);
        sysFiles(session);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.warn("Unexpected exception.", cause);
        session.close(true);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        sessions.remove(session);
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

    private void sysFiles(IoSession session) {
        List<File> list = FileKit.list(dist);
        for (File file: list) {
            TransferModel model = new TransferModel();
            if (file.isDirectory()) {
                model.setType(TransferType.DIRECTORY_SAVE);
            } else {
                model.setType(TransferType.FILE_SAVE);
            }
            model.setPath(getPath(file));
            model.setBody(getBody(file));
            session.write(model);
        }
    }

    public static void groupWrite(TransferModel model) {
        synchronized (sessions) {
            for (IoSession session : sessions) {
                session.write(model);
            }
        }
    }

}
