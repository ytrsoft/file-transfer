package com.ytrsoft.codec;

import com.ytrsoft.model.TransferModel;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class TransferEncoder extends ProtocolEncoderAdapter {
    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        TransferModel model = (TransferModel) message;
        IoBuffer buffer = IoBuffer.allocate(1024);
        buffer.setAutoExpand(true);
        // 写入类型
        String type = model.getType().name();
        byte[] tBytes = type.getBytes();
        buffer.putInt(tBytes.length);
        buffer.put(tBytes);
        // 写入路径
        String path = model.getPath();
        if (path == null) {
            buffer.putInt(0);
            buffer.put(new byte[]{});
        } else {
            byte[] pBytes = path.getBytes();
            buffer.putInt(pBytes.length);
            buffer.put(pBytes);
        }
        // 写入内容
        byte[] body = model.getBody();
        if (body == null) {
            buffer.putInt(0);
            buffer.put(new byte[]{});
        } else {
            buffer.putInt(body.length);
            buffer.put(body);
        }
        // 重置标志位
        buffer.flip();
        // 写入数据
        out.write(buffer);
    }
}
