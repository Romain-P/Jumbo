package org.jumbo.backend.network;

import com.google.inject.AbstractModule;
import org.jumbo.backend.network.game.GameNetworkService;
import org.jumbo.backend.network.login.LoginNetworkService;
import org.jumbo.commons.nio.NetworkService;
import org.jumbo.utils.annotations.GameNetwork;
import org.jumbo.utils.annotations.LoginNetwork;

/**
 * Created by Return on 02/09/2014.
 */
public class NetworkModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(NetworkService.class).annotatedWith(LoginNetwork.class).to(LoginNetworkService.class).asEagerSingleton();
        bind(NetworkService.class).annotatedWith(GameNetwork.class).to(GameNetworkService.class).asEagerSingleton();
    }
}
