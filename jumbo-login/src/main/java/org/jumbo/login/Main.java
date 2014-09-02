package org.jumbo.login;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("Login-server starting..");
        Injector injector = Guice.createInjector(new LoginDefaultModule());
    }
}
