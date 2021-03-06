package com.ytrsoft.codec;

import com.ytrsoft.model.TransferModel;
import com.ytrsoft.model.TransferType;
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
        in.get(pBytes);
        String path = new String(pBytes);
        model.setPath(path);
        // 读取主体
        int bLen = in.getInt();
        byte[] body = new byte[bLen];
        if (bLen > 0) {
            in.get(body);
        }
        model.setBody(body);
        out.write(model);
        return false;
    }

}

