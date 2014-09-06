package org.jumbo.commons.sql;

import com.google.inject.Inject;
import org.jumbo.api.database.DaoQueryManager;
import org.jumbo.api.database.DatabaseService;
import org.jumbo.api.database.model.Query;
import org.jumbo.api.database.model.QueryModel;
import org.jumbo.api.database.model.builders.PreparedStatementBuilder;
import org.jumbo.api.database.model.builders.QueryObjectBuilder;
import org.jumbo.api.database.model.enums.OnlyExecuteQueryEnum;

import java.sql.*;

/**
 * Created by Return on 03/09/2014.
 */
public abstract class DefaultDaoQueryManager<T> extends DaoQueryManager<T> {
    protected QueryModel model;
    @Inject
    DatabaseService database;

    public DefaultDaoQueryManager(QueryModel model) {
        this.model = model;
    }

    protected void execute(QueryModel model, Object primary, OnlyExecuteQueryEnum type) throws SQLException {
        execute(model.createNewQuery().setData(model.getPrimaryKeyName(), primary), type);
    }

    protected void execute(Query query, OnlyExecuteQueryEnum type) throws SQLException {
        database.getLocker().lock();

        try {
            database.getConnection().setAutoCommit(false);
            PreparedStatement statement = PreparedStatementBuilder.newQuery(query, type, database.getConnection());
            statement.execute();
            statement.close();
            database.getConnection().commit();
        } catch (SQLException exception) {
            database.getConnection().rollback();
            throw exception;
        } finally {
            database.getConnection().setAutoCommit(true);
            database.getLocker().unlock();
        }
    }

    protected Query createNewQuery(Object primary) throws SQLException {
        database.getLocker().lock();

        ResultSet resultSet;
        Query query = null;
        try {
            database.getConnection().setAutoCommit(false);
            PreparedStatement statement = PreparedStatementBuilder.newLoadQuery(model, primary, database.getConnection());
            resultSet = statement.executeQuery();
            database.getConnection().commit();

            query = QueryObjectBuilder.newQuery(model, resultSet);
            resultSet.getStatement().close();
            resultSet.close();
        } catch(SQLException exception) {
            throw exception;
        } finally {
            database.getConnection().setAutoCommit(true);
            database.getLocker().unlock();
            return query;
        }
    }
}
