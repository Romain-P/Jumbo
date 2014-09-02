package org.jumbo.commons.nio;

import org.apache.mina.core.service.IoService;

import java.io.IOException;

/**
 * Created by Return on 02/09/2014.
 */
public abstract class NetworkService {
    protected final IoService service;

    public NetworkService(IoService service) {
        this.service = service;
    }

    protected abstract void configure();
    public abstract boolean start(String ip, int port) throws IOException;
    public abstract void stop();
}
