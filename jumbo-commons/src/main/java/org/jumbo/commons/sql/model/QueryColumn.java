package org.jumbo.commons.sql.model;

import lombok.Getter;

/**
 * Created by Return on 03/09/2014.
 */
public class QueryColumn {
    @Getter
    private final String fieldName;
    @Getter
    private final String columnName;
    @Getter
    private final Class type;

    public QueryColumn(String fieldName, String columnName, Class type) {
        this.fieldName = fieldName;
        this.columnName = columnName;
        this.type = type;
    }
}
