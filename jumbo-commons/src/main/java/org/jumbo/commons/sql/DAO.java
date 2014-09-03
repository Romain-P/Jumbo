package org.jumbo.commons.sql;

/**
 * Created by Return on 03/09/2014.
 */
public interface DAO<T> {
    boolean create(T obj);
    boolean delete(T obj);
    boolean update(T obj);

    T load(Object primary);
}
