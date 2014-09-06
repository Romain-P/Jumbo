package org.jumbo.api.database.model;

import java.util.Map;

/**
 * Created by Return on 06/09/2014.
 */
public interface QueryModel<T> {
    public String getTableName();
    public String getPrimaryKeyName();
    public Map<String, QueryColumn> getColumns();

    public Map<String, String> getColumnModel();
    public QueryModel<T> schematize();
    public Query createNewQuery();
}
