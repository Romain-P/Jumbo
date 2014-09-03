package org.jumbo.backend.network;

import com.google.inject.AbstractModule;
import org.jumbo.frontend.network.GameNetworkService;
import org.jumbo.commons.nio.NetworkService;
import org.jumbo.utils.annotations.GameNetwork;
import org.jumbo.utils.annotations.LoginNetwork;

/**
 * Created by Return on 02/09/2014.
 */
public class LoginNetworkModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(NetworkService.class).annotatedWith(LoginNetwork.class).to(LoginNetworkService.class).asEagerSingleton();
        bind(NetworkService.class).annotatedWith(GameNetwork.class).to(GameNetworkService.class).asEagerSingleton();
    }
}
