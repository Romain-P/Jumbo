package org.jumbo.api.login.database;

import org.jumbo.commons.sql.QueryManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Return on 03/09/2014.
 */
public interface DatabaseService {
    public Connection getConnection();
    public ReentrantLock getLocker();
    public Map<Class, QueryManager> getQueryManagers();

    public DatabaseService start() throws SQLException;
    public void stop();
}
