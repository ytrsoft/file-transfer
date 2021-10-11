package com.ytrsoft.handler;

import com.ytrsoft.entity.TransferModel;
import com.ytrsoft.util.FileKit;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public abstract class BaseClientHandler extends IoHandlerAdapter {

    private final String dist;

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    public BaseClientHandler(String dist) {
        FileKit.deleteAll(dist);
        this.dist = dist;
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.warn("Unexpected exception.", cause);
        session.close(true);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        TransferModel model = (TransferModel) message;
        File file = new File(dist + model.getPath());
        switch (model.getType()) {
            case FILE_SAVE:
                onFileSave(file, model.getBody());
                break;
            case FILE_REMOVE:
                onFileRemove(file);
                break;
            case DIRECTORY_SAVE:
                onDirectorySave(file);
                break;
            case DIRECTORY_REMOVE:
                onDirectoryRemove(file);
        }
    }

    public abstract void onFileSave(File file, byte[] body);

    public abstract void onFileRemove(File file);

    public abstract void onDirectorySave(File file);

    public abstract void onDirectoryRemove(File file);

}
