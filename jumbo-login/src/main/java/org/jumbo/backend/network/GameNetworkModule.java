package org.jumbo.backend.network;

import com.google.inject.AbstractModule;
import org.jumbo.commons.nio.NetworkService;
import org.jumbo.utils.annotations.GameNetwork;

/**
 * Created by Return on 02/09/2014.
 */
public class GameNetworkModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(NetworkService.class).annotatedWith(GameNetwork.class).to(GameNetworkService.class).asEagerSingleton();
    }
}
