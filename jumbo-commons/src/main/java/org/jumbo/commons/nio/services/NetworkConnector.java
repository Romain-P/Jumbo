package org.jumbo.commons.nio.services;

import org.apache.mina.core.service.IoConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.jumbo.commons.nio.NetworkService;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by Return on 02/09/2014.
 */
public abstract class NetworkConnector extends NetworkService {
    protected final IoConnector connector;

    public NetworkConnector() {
        super(new NioSocketConnector());
        this.connector = (IoConnector) service;
    }

    @Override
    public boolean start(String ip, int port) throws IOException {
        configure();
        return connector.connect(new InetSocketAddress(ip, port)).isConnected();
    }

    @Override
    public void stop() {
        connector.getManagedSessions().values().stream().filter(session -> !session.isClosing()).forEach(session -> session.close(false));
        connector.dispose(false);
    }
}
