package org.jumbo.commons.sql;

import com.google.inject.Inject;
import org.jumbo.api.login.database.DatabaseService;
import org.jumbo.commons.sql.exceptions.BadPutFieldTypeException;
import org.jumbo.commons.sql.exceptions.BadQueryFormationException;
import org.jumbo.commons.sql.model.Query;
import org.jumbo.commons.sql.model.QueryModel;
import org.jumbo.commons.sql.model.builders.QueryObjectBuilder;
import org.jumbo.commons.sql.model.enums.OnlyExecuteQueryEnum;
import org.jumbo.commons.sql.model.builders.QueryStringBuilder;

import java.sql.*;

/**
 * Created by Return on 03/09/2014.
 */
public abstract class QueryManager<T> implements DAO<T> {
    protected QueryModel model;
    @Inject
    DatabaseService database;

    public QueryManager(QueryModel model) {
        this.model = model;
    }

    protected void execute(QueryModel model, Object primary, OnlyExecuteQueryEnum type) throws SQLException, BadQueryFormationException, BadPutFieldTypeException {
        execute(model.createNewQuery().setData(model.getPrimaryKeyName(), primary), type);
    }

    protected void execute(Query query, OnlyExecuteQueryEnum type) throws SQLException, BadQueryFormationException {
        database.getLocker().lock();

        try {
            database.getConnection().setAutoCommit(false);
            Statement statement = database.getConnection().createStatement();
            statement.execute(QueryStringBuilder.newQuery(query, type));
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
            resultSet = database.getConnection().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY).executeQuery(QueryStringBuilder.newQuery(model, primary));
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
