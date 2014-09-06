package org.jumbo.commons.sql.model;

import lombok.Getter;
import org.jumbo.api.database.model.Query;
import org.jumbo.api.database.model.QueryColumn;
import org.jumbo.api.database.model.QueryModel;
import org.jumbo.api.database.model.annotations.PrimaryQueryField;
import org.jumbo.api.database.model.annotations.QueryField;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Return on 03/09/2014.
 */
public abstract class DefaultQueryModel<T> implements QueryModel<T> {
    @Getter
    private final String tableName;
    @Getter
    private String primaryKeyName;
    private final T schema;
    @Getter
    private final Map<String, QueryColumn> columns;

    public DefaultQueryModel(String tableName, T schema) {
        this.tableName = tableName;
        this.schema = schema;
        this.columns = new HashMap<>();
    }

    public QueryModel<T> schematize() {
        for(Field field: schema.getClass().getDeclaredFields()) {
            boolean primary = false;

            if(field.isAnnotationPresent(PrimaryQueryField.class)) {
                primaryKeyName = field.getName();
                primary = true;
            }

            if(field.isAnnotationPresent(QueryField.class) || primary) {
                String fieldName = field.getName();
                columns.put(fieldName, new DefaultQueryColumn(fieldName, getColumnModel().get(fieldName), field.getType()));
            }
        }
        return this;
    }

    public Query createNewQuery() {
        Query query = new DefaultQuery(this);
        return query;
    }
}
