package org.jumbo.login;

import com.google.inject.Inject;
import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.jumbo.api.login.database.DatabaseService;
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
    @Inject
    DatabaseService database;
    @Inject @GameNetwork
    NetworkService gameNetwork;
    @Inject @LoginNetwork
    NetworkService loginNetwork;

    public LoginManager start() {
        log.info("Initializing : login database");



        log.info("Initializing : login network");

        try {
            loginNetwork.start(config.getString("login.network.login.ip"), config.getInt("login.network.login.port"));
        } catch (IOException e) {
            log.info(String.format("IOException when trying to start login network: %s", e.getMessage()));
            System.exit(1);
        }

        log.info("Initializing : game network");

        try {
            gameNetwork.start(config.getString("login.network.game.ip"), config.getInt("login.network.game.port"));
        } catch (IOException e) {
            log.info(String.format("IOException when trying to start game network: %s", e.getMessage()));
            System.exit(1);
        }

        log.info("Login started successfully!");
        return this;
    }

    public void stop() {
        log.info("Stopping networks..");
        gameNetwork.stop();
        loginNetwork.stop();

        log.info("Server stopped successfully");
    }
}
