package org.jumbo.api.database.model;

import java.util.Map;

/**
 * Created by Return on 06/09/2014.
 */
public interface Query {
    public Map<String, Object> getData();
    public QueryModel<?> getModel();
    public Query setData(String column, Object data) throws NullPointerException;
    public boolean checkFormation();
}
