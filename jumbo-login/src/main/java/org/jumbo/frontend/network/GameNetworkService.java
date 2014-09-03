package org.jumbo.frontend.network;

import org.jumbo.commons.nio.services.NetworkConnector;

/**
 * Created by Return on 02/09/2014.
 */
public class GameNetworkService extends NetworkConnector{
    @Override
    protected void configure() {
        connector.setHandler(new GameNetworkHandler());
    }
}
