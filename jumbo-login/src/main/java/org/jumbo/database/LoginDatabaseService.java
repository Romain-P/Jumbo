package org.jumbo.database;

import com.google.inject.Inject;
import com.typesafe.config.Config;
import lombok.Getter;
import org.jumbo.api.database.DaoQueryManager;
import org.jumbo.api.database.DatabaseService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Return on 03/09/2014.
 */
public class LoginDatabaseService implements DatabaseService {
    @Getter
    private final ReentrantLock locker;
    @Getter
    private Connection connection;
    @Getter
    private Map<Class, DaoQueryManager> queryManagers;

    @Inject
    Config config;
    @Inject
    Set<DaoQueryManager> managers;

    public LoginDatabaseService() {
        this.locker = new ReentrantLock();
        this.queryManagers = new HashMap<>();
    }

    public LoginDatabaseService start() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://"+
                        config.getString("login.database.host")+"/"+
                        config.getString("login.database.name"),
                        config.getString("login.database.user"),
                        config.getString("login.database.pass")
        );
        if (!connection.isValid(1000)) return null;
        connection.setAutoCommit(true);

        for(DaoQueryManager manager: managers)
            queryManagers.put(manager.getClass(), manager);
        return this;
    }

    public void stop() {
        try {
            connection.close();
        } catch(SQLException exception){}
    }
}
