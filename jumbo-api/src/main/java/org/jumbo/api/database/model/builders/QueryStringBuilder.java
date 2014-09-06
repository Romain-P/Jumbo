package org.jumbo.api.database.model.builders;

import org.jumbo.api.database.model.Query;
import org.jumbo.api.database.model.QueryModel;
import org.jumbo.api.database.model.enums.OnlyExecuteQueryEnum;
import org.jumbo.api.database.model.exceptions.BadQueryFormationException;

import java.util.Map;

/**
 * Created by Return on 06/09/2014.
 */
public interface QueryStringBuilder {
    public static String newQuery(Query query, OnlyExecuteQueryEnum type) {
        Object primary = query.getData().get(query.getModel().getPrimaryKeyName());
        Object primaryKey = primary instanceof String ? "'"+primary+"'" :  primary;

        switch(type) {
            case CREATE:
                return String.format(newCreateQuery(query), primaryKey);
            case UPDATE:
                return String.format(newUpdateQuery(query), primaryKey);
            case DELETE:
                return String.format(newDeleteQuery(query.getModel()), primaryKey);
            default:
                return null;
        }
    }

    public static String newQuery(QueryModel model, Object primary) {
        Object primaryKey = primary instanceof String ? "'"+primary+"'" : primary;
        return String.format(newLoadQuery(model), primaryKey);
    }

    public static String newCreateQuery(Query query) {
        if(!query.checkFormation()) return null;

        Map<String, Object> data = query.getData();

        StringBuilder built = new StringBuilder("INSERT INTO ");
        built.append(query.getModel().getTableName()).append(" VALUES(");

        boolean first = true;
        for(Object value: data.values()) {
            if(first)
                first = false;
            else
                built.append(",");

            if(value instanceof String)
                built.append("'").append(value).append("'");
            else
                built.append(value);
        }
        built.append(");");

        return built.toString();
    }

    public static String newUpdateQuery(Query query) {
        if(!query.checkFormation()) return null;

        Map<String, Object> data = query.getData();

        StringBuilder built = new StringBuilder("UPDATE ");
        built.append(query.getModel().getTableName()).append(" SET ");

        boolean first = true;
        for(Map.Entry entry: data.entrySet()) {
            if(first)
                first = false;
            else
                built.append(",");

            built.append("`").append(query.getModel().getColumns().get(entry.getKey()).getColumnName()).append("` = ");

            Object value = entry.getValue();
            if(value instanceof String)
                built.append("'").append(value).append("' ");
            else
                built.append(value).append(" ");
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
