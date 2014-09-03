package org.jumbo.backend.database;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import org.jumbo.commons.sql.QueryManager;
import org.jumbo.commons.sql.DatabaseService;

/**
 * Created by Return on 03/09/2014.
 */
public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DatabaseService.class).to(LoginDatabaseService.class).asEagerSingleton();
        Multibinder<QueryManager> managers = Multibinder.newSetBinder(binder(), QueryManager.class);
        //TODO: adding someone
    }
}
