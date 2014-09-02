package org.jumbo.login;

import com.github.blackrush.acara.CoreEventBus;
import com.github.blackrush.acara.EventBus;
import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.fungsi.concurrent.Worker;
import org.fungsi.concurrent.Workers;
import org.jumbo.commons.executors.ImprovedCachedThreadPool;

import java.io.File;

/**
 * Created by Return on 02/09/2014.
 */
@Slf4j
public class LoginDefaultModule extends AbstractModule {
    @Override
    protected void configure() {
        Config config = ConfigFactory.parseFile(new File("config.conf"));
        if(config.isEmpty()) {
            log.error("Failed to load config.conf");
            System.exit(1);
        }
        bind(Config.class).toInstance(config);

        Worker worker = Workers.wrap(new ImprovedCachedThreadPool(5, 10, 10));
        bind(EventBus.class).toInstance(CoreEventBus.create(worker));
    }
}
