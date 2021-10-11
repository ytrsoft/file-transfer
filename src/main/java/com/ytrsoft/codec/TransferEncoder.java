package com.ytrsoft.codec;

import com.ytrsoft.model.TransferModel;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransferEncoder extends ProtocolEncoderAdapter {

    private static final Logger logger = LoggerFactory.getLogger(TransferEncoder.class);

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        TransferModel model = (TransferModel) message;
        // 类型字节
        String type = model.getType().name();
        byte[] tBytes = type.getBytes();
        // 路径字节
        String path = model.getPath();
        byte[] pBytes = new byte[]{};
        if (path != null) {
            pBytes = path.getBytes();
        }
        // 主体字节
        byte[] body = model.getBody();
        // 开辟内存容量
        int capacity = body.length;
        if (path != null) {
            capacity += path.length();
        }
        capacity += type.length();
        logger.info("allocate capacity " + capacity);
        IoBuffer buffer = IoBuffer.allocate(capacity);
        buffer.setAutoExpand(true);
        // 写入类型
        buffer.putInt(tBytes.length);
        buffer.put(tBytes);
        // 写入路径
        if (path == null) {
            buffer.putInt(0);
            buffer.put(new byte[]{});
        } else {
            buffer.putInt(pBytes.length);
            buffer.put(pBytes);
        }
        // 写入内容
        buffer.putInt(body.length);
        buffer.put(body);
        // 重置标志位
        buffer.flip();
        // 写入数据
        out.write(buffer);
    }
}
