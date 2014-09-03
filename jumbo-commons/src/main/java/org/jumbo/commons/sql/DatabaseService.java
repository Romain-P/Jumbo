package org.jumbo.commons.sql;

import java.sql.Connection;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Return on 03/09/2014.
 */
public interface DatabaseService {
    public Connection getConnection();
    public ReentrantLock getLocker();
}
