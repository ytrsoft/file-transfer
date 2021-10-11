package com.ytrsoft.entity;

import java.io.Serializable;
import java.util.Arrays;

public class TransferModel implements Serializable {

    private String path;
    private byte[] body;
    private TransferType type;

    public TransferModel() {}

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public TransferType getType() {
        return type;
    }

    public void setType(TransferType type) {
        this.type = type;
    }

}
