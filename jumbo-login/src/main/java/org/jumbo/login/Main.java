package org.jumbo.login;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jumbo.backend.network.NetworkModule;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new LoginDefaultModule(), new NetworkModule());
        injector.getInstance(LoginManager.class).start();
    }
}
