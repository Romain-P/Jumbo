package org.jumbo.commons.sql.model;

import lombok.Getter;
import org.jumbo.commons.sql.exceptions.BadPutFieldTypeException;
import org.jumbo.commons.sql.exceptions.BadQueryFormationException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Return on 03/09/2014.
 */
public class Query {
    @Getter
    private final Map<String, Object> data;
    @Getter
    private final QueryModel model;

    public Query(QueryModel model) {
        this.model = model;
        this.data = new HashMap<>();
    }

    public Query setData(String column, Object data) throws NullPointerException, BadPutFieldTypeException {
        Class type = (Class) model.getColumns().get(column);

        if(type == null)
            throw new NullPointerException(String.format("QueryModel's column not found: %s", column));
        else if(type !=  data.getClass())
            throw new BadPutFieldTypeException(String.format("QueryModel %s needed type %s", column, type));

        this.data.put(column, data);
        return this;
    }

    public boolean checkFormation() throws BadQueryFormationException {
        int size = model.getColumns().size() - data.size();

        if(size != 0)
            throw new BadQueryFormationException(String.format("Created query %s cannot be used cause model size is different %d", model.getTableName(), size));

        return true;
    }
}
