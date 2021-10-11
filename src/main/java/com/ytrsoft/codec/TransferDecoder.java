package com.ytrsoft.codec;

import com.ytrsoft.entity.TransferModel;
import com.ytrsoft.entity.TransferType;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class TransferDecoder extends CumulativeProtocolDecoder {
    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        TransferModel model = new TransferModel();
        // 读取类型
        int tLen = in.getInt();
        byte[] tBytes = new byte[tLen];
        in.get(tBytes);
        String sType = new String(tBytes);
        TransferType type = TransferType.valueOf(sType);
        model.setType(type);
        // 读取路径
        int pLen = in.getInt();
        byte[] pBytes = new byte[pLen];
        String path = new String(pBytes);
        model.setPath(path);
        // 读取主体
        int bLen = in.getInt();
        byte[] body = new byte[bLen];
        model.setBody(body);
        out.write(model);
        return true;
    }

    private byte[] read(IoBuffer in) {
        int size = in.getInt();
        byte[] bytes = new byte[size];
        if (size != 0) {
            in.flip();
            in.get(bytes);
        }
        return bytes;
    }
}

