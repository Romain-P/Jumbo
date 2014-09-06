package org.jumbo.api.database;

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
    public Map<Class, DaoQueryManager> getQueryManagers();

    public DatabaseService start() throws SQLException;
    public void stop();
}
