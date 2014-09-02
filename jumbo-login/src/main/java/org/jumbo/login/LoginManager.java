package org.jumbo.login;

import com.google.inject.Inject;
import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.jumbo.commons.nio.NetworkService;
import org.jumbo.utils.annotations.GameNetwork;
import org.jumbo.utils.annotations.LoginNetwork;

import java.io.IOException;

/**
 * Created by Return on 02/09/2014.
 */
@Slf4j
public class LoginManager {
    @Inject
    Config config;
    @Inject @GameNetwork
    NetworkService gameNetwork;
    @Inject @LoginNetwork
    NetworkService loginNetwork;

    public void start() {
        log.info("Initializing : login network");

        try {
            loginNetwork.start(config.getString("login.network.login.ip"), config.getInt("login.network.login.port"));
        } catch (IOException e) {
            log.info(String.format("IOException when trying to start login network: %s", e.getMessage()));
            System.exit(1);
        }

        try {
            gameNetwork.start(config.getString("login.network.game.ip"), config.getInt("login.network.game.port"));
        } catch (IOException e) {
            log.info(String.format("IOException when trying to start game network: %s", e.getMessage()));
            System.exit(1);
        }

        log.info("Login started successfully!");
    }

    public void stop() {

    }
}
