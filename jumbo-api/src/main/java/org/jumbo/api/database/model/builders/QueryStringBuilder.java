package org.jumbo.api.database.model.builders;

import org.jumbo.api.database.model.Query;
import org.jumbo.api.database.model.QueryModel;
import org.jumbo.api.database.model.enums.OnlyExecuteQueryEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Return on 06/09/2014.
 */
public interface QueryStringBuilder {
    public static PreparedStatement newQuery(Query query, OnlyExecuteQueryEnum type, Connection connection) throws SQLException {
        Object primary = query.getData().get(query.getModel().getPrimaryKeyName());

        switch(type) {
            case CREATE:
                return newCreateQuery(query, connection);
            case UPDATE:
                return newUpdateQuery(query, primary, connection);
            case DELETE:
                return newDeleteQuery(query.getModel(), primary, connection);
            default:
                return null;
        }
    }

    public static PreparedStatement newCreateQuery(Query query, Connection connection) throws SQLException {
        if(!query.checkFormation()) return null;

        Map<String, Object> data = query.getData();

        StringBuilder built = new StringBuilder("INSERT INTO "), others = new StringBuilder();
        built.append(query.getModel().getTableName()).append(" (");

        boolean first = true;
        for(String column: data.keySet()) {
            if(first)
                first = false;
            else {
                built.append(",");
                others.append(",");
            }

            built.append("`").append(query.getModel().getColumns().get(column).getColumnName()).append("`");
            others.append("?");
        }
        built.append(") VALUES(").append(others.toString()).append(");");

        PreparedStatement statement = connection.prepareStatement(built.toString());

        Object[] dataToArray = data.values().toArray();

        for(int i=0; i < data.size(); i++) {
            Object value = dataToArray[i];
            statement.setObject(i+1, value);
        }

        return statement;
    }

    public static PreparedStatement newUpdateQuery(Query query, Object primaryKey, Connection connection) throws SQLException {
        if(!query.checkFormation()) return null;

        Map<String, Object> data = query.getData();

        StringBuilder built = new StringBuilder("UPDATE ");
        built.append(query.getModel().getTableName()).append(" SET ");

        boolean first = true;
        for(String column: data.keySet()) {
            if(first)
                first = false;
            else
                built.append(",");

            built.append("`").append(query.getModel().getColumns().get(column).getColumnName()).append("` = ? ");
        }
        built.append("WHERE ").append(query.getModel().getPrimaryKeyName()).append(" = ?;");

        PreparedStatement statement = connection.prepareStatement(built.toString());

        Object[] dataToArray = data.values().toArray();

        for(int i=0; i < data.size(); i++) {
            Object value = dataToArray[i];
            statement.setObject(i+1, value);
        }
        statement.setObject(data.size()+1, primaryKey);

        return statement;
    }

    public static PreparedStatement newLoadQuery(QueryModel model, Object primaryKey, Connection connection) throws SQLException {
        String query = "SELECT * FROM "+model.getTableName() +
                " WHERE "+model.getPrimaryKeyName()+" = ?;";

        PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setObject(1, primaryKey);

        return statement;
    }

    public static PreparedStatement newDeleteQuery(QueryModel model, Object primaryKey, Connection connection) throws SQLException {
        String query = "DELETE FROM "+model.getTableName() +
                " WHERE "+model.getPrimaryKeyName()+" = ?;";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, primaryKey);

        return statement;
    }
}
