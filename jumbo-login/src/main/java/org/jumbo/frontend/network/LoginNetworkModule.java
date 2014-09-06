package org.jumbo.frontend.network;

import com.google.inject.AbstractModule;
import org.jumbo.backend.network.GameNetworkService;
import org.jumbo.api.network.NetworkService;
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
