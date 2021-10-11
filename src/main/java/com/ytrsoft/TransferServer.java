package com.ytrsoft;

import com.ytrsoft.codec.TransferCodecFactory;
import com.ytrsoft.entity.BootModel;
import com.ytrsoft.handler.ServerHandler;
import com.ytrsoft.ui.BaseServer;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;

public class TransferServer extends BaseServer {

    @Override
    protected void onBootstrap(BootModel model) {
        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
        chain.addLast("logger", new LoggingFilter());
        TransferCodecFactory factory = new TransferCodecFactory();
        ProtocolCodecFilter filter = new ProtocolCodecFilter(factory);
        chain.addLast("filter", filter);
        acceptor.setHandler(new ServerHandler(model.getPath(), model.getInterval()));
        InetSocketAddress address = new InetSocketAddress(model.getHost(), model.getPort());
        try {
            acceptor.bind(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(TransferServer.class);
    }

}
