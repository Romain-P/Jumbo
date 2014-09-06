package org.jumbo.api.database.model.builders;

import org.jumbo.api.database.model.Query;
import org.jumbo.api.database.model.QueryModel;
import org.jumbo.api.database.model.enums.OnlyExecuteQueryEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * Created by Return on 06/09/2014.
 */
public interface QueryStringBuilder {
    public static Statement newQuery(Query query, OnlyExecuteQueryEnum type, Connection connection) throws SQLException {
        Object primary = query.getData().get(query.getModel().getPrimaryKeyName());
        Object primaryKey = primary instanceof String ? "'"+primary+"'" :  primary;

        switch(type) {
            case CREATE:
                return newCreateQuery(query, connection);
            case UPDATE:
                return String.format(newUpdateQuery(query, connection), primaryKey);
            case DELETE:
                return String.format(newDeleteQuery(query.getModel()), connection, primaryKey);
            default:
                return null;
        }
    }

    public static String newQuery(QueryModel model, Object primary) {
        Object primaryKey = primary instanceof String ? "'"+primary+"'" : primary;
        return String.format(newLoadQuery(model), primaryKey);
    }

    public static Statement newCreateQuery(Query query, Connection connection) throws SQLException {
        if(!query.checkFormation()) return null;

        Map<String, Object> data = query.getData();

        StringBuilder built = new StringBuilder("INSERT INTO ");
        built.append(query.getModel().getTableName()).append(" (");

        boolean first = true;
        for(String column: data.keySet()) {
            if(first)
                first = false;
            else
                built.append(",");

            built.append("`").append(query.getModel().getColumns().get(column).getColumnName()).append("`");
        }
        built.append(") VALUES(");

        first = true;
        for(int i=data.size(); i > 0; i--) {
            if (first)
                first = false;
            else
                built.append(",");
            built.append("?");
        }
        built.append(");");

        PreparedStatement statement = connection.prepareStatement(built.toString());

        for(int i=0; i < data.size(); i++) {
            Object value = data.values().toArray()[i];
            statement.setObject(i+1, value);
        }

        return statement;
    }

    public static String newUpdateQuery(Query query, Statement statement) {
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

            built.append("`").append(query.getModel().getColumns().get(column).getColumnName()).append("` = ?");
        }

        built.append("WHERE ").append(query.getModel().getPrimaryKeyName()).append(" = %s;");

        return built.toString();
    }

    public static String newLoadQuery(QueryModel model) {
        String updateQuery = "SELECT * FROM "+model.getTableName() +
                " WHERE "+model.getPrimaryKeyName()+" = %s;";

        return updateQuery;
    }

    public static String newDeleteQuery(QueryModel model) {
        String updateQuery = "DELETE FROM "+model.getTableName() +
                " WHERE "+model.getPrimaryKeyName()+" = %s;";

        return updateQuery;
    }
}
