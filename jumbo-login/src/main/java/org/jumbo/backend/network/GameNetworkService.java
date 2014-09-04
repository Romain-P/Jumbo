package org.jumbo.backend.network;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.mina.core.service.IoHandler;
import org.jumbo.commons.nio.services.NetworkConnector;

/**
 * Created by Return on 02/09/2014.
 */
public class GameNetworkService extends NetworkConnector{
    @Inject
    Injector injector;

    @Override
    protected void configure() {
        IoHandler handler = new GameNetworkHandler();
        injector.injectMembers(handler);

        connector.setHandler(handler);
    }
}
