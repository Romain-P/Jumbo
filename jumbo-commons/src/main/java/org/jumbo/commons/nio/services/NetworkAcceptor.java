package org.jumbo.commons.nio.services;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.jumbo.api.network.NetworkService;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by Return on 02/09/2014.
 */
public abstract class NetworkAcceptor extends NetworkService {
    protected final IoAcceptor acceptor;

    public NetworkAcceptor() {
        super(new NioSocketAcceptor());
        this.acceptor = (IoAcceptor) service;
    }

    @Override
    public boolean start(String ip, int port) throws IOException {
        configure();
        acceptor.bind(new InetSocketAddress(ip, port));
        return acceptor.isActive();
    }

    @Override
    public void stop() {
        acceptor.getManagedSessions().values().stream().filter(session -> !session.isClosing()).forEach(session -> session.close(false));
        acceptor.dispose(false);
    }
}
