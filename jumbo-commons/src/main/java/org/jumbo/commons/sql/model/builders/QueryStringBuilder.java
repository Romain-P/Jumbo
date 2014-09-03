package org.jumbo.commons.sql.model.builders;

import org.jumbo.commons.sql.exceptions.BadQueryFormationException;
import org.jumbo.commons.sql.model.Query;
import org.jumbo.commons.sql.model.QueryModel;
import org.jumbo.commons.sql.model.enums.OnlyExecuteQueryEnum;

import java.util.Map;

/**
 * Created by Return on 03/09/2014.
 */
public class QueryStringBuilder {

    public static String newQuery(Query query, OnlyExecuteQueryEnum type) throws BadQueryFormationException {
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

    private static String newCreateQuery(Query query) throws BadQueryFormationException {
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

    private static String newUpdateQuery(Query query) throws BadQueryFormationException {
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

            built.append("`").append(entry.getKey()).append("` = ");

            Object value = entry.getValue();
            if(value instanceof String)
                built.append("'").append(value).append("' ");
            else
                built.append(value).append(" ");
        }

        built.append("WHERE ").append(query.getModel().getPrimaryKeyName()).append(" = %s;");

        return built.toString();
    }

    private static String newLoadQuery(QueryModel model) {
        String updateQuery = "SELECT * FROM "+model.getTableName() +
                " WHERE "+model.getPrimaryKeyName()+" = %s;";

        return updateQuery;
    }

    private static String newDeleteQuery(QueryModel model) {
        String updateQuery = "DELETE FROM "+model.getTableName() +
                " WHERE "+model.getPrimaryKeyName()+" = %s;";

        return updateQuery;
    }
}
