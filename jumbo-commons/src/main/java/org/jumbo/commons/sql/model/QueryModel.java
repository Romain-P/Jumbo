package org.jumbo.commons.sql.model;

import lombok.Getter;
import org.jumbo.commons.sql.model.annotations.PrimaryQueryField;
import org.jumbo.commons.sql.model.annotations.QueryField;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Return on 03/09/2014.
 */
public abstract class QueryModel<T> {
    @Getter
    private final String tableName;
    @Getter
    private String primaryKeyName;
    private final T schema;
    @Getter
    private final Map<String, QueryColumn> columns;

    public QueryModel(String tableName, T schema) {
        this.tableName = tableName;
        this.schema = schema;
        this.columns = new HashMap<>();
    }

    public abstract Map<String, String> getColumnModel();

    public QueryModel schematize() {
        for(Field field: schema.getClass().getDeclaredFields()) {
            boolean primary = false;

            if(field.isAnnotationPresent(PrimaryQueryField.class)) {
                primaryKeyName = field.getName();
                primary = true;
            }

            if(field.isAnnotationPresent(QueryField.class) || primary) {
                String fieldName = field.getName();
                columns.put(fieldName, new QueryColumn(fieldName, getColumnModel().get(fieldName), field.getType()));
            }
        }
        return this;
    }

    public Query createNewQuery() {
        Query query = new Query(this);
        return query;
    }
}
