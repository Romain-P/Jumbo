package org.jumbo.login;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jumbo.frontend.network.LoginNetworkModule;
import org.jumbo.database.DatabaseModule;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new LoginDefaultModule(), new LoginNetworkModule(), new DatabaseModule());
        final LoginManager manager = injector.getInstance(LoginManager.class).start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> manager.stop()));
    }
}
