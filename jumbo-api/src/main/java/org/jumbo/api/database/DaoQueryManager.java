package org.jumbo.api.database;

import org.jumbo.api.database.model.Query;
import org.jumbo.api.database.model.QueryModel;
import org.jumbo.api.database.model.enums.OnlyExecuteQueryEnum;

import java.sql.SQLException;

/**
 * Created by Return on 05/09/2014.
 */
public abstract class DaoQueryManager<T> implements DAO<T>{
    protected abstract void execute(QueryModel model, Object primary, OnlyExecuteQueryEnum type) throws SQLException;
    protected abstract void execute(Query query, OnlyExecuteQueryEnum type) throws SQLException;
    protected abstract Query createNewQuery(Object primary) throws SQLException;
}
