package org.jumbo.api.login.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Return on 03/09/2014.
 */
public interface DatabaseService {
    public Connection getConnection();
    public ReentrantLock getLocker();

    public DatabaseService start() throws SQLException;
    public void stop();
}
