package com.ytrsoft;

import com.ytrsoft.codec.TransferCodecFactory;
import com.ytrsoft.entity.BootModel;
import com.ytrsoft.handler.ClientHandler;
import com.ytrsoft.ui.BaseClient;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

public class TransferClient extends BaseClient {

    @Override
    protected void onBootstrap(BootModel model) {
        NioSocketConnector connector = new NioSocketConnector();
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();
        chain.addLast("logger", new LoggingFilter());
        TransferCodecFactory factory = new TransferCodecFactory();
        ProtocolCodecFilter filter = new ProtocolCodecFilter(factory);
        chain.addLast("filter", filter);
        connector.setHandler(new ClientHandler(model.getPath()));
        InetSocketAddress address = new InetSocketAddress(model.getHost(), model.getPort());
        ConnectFuture future = connector.connect(address);
        future.awaitUninterruptibly();
        future.getSession().getCloseFuture().awaitUninterruptibly();
        connector.dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(TransferClient.class);
    }

}
