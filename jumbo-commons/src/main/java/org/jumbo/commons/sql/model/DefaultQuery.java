package org.jumbo.commons.sql.model;

import lombok.Getter;
import org.jumbo.api.database.model.Query;
import org.jumbo.api.database.model.QueryModel;
import org.jumbo.api.database.model.exceptions.BadPutFieldTypeException;
import org.jumbo.api.database.model.exceptions.BadQueryFormationException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Return on 03/09/2014.
 */
public class DefaultQuery implements Query{
    @Getter
    private final Map<String, Object> data;
    @Getter
    private final QueryModel<?> model;

    public DefaultQuery(DefaultQueryModel model) {
        this.model = model;
        this.data = new HashMap<>();
    }

    public Query setData(String column, Object data) throws NullPointerException {
        Class type = model.getColumns().get(column).getType();

        if(type == null)
            throw new NullPointerException(String.format("QueryModel's column not found: %s", column));
        else if(type !=  data.getClass())
            throw new BadPutFieldTypeException(String.format("QueryModel %s needed type %s", column, type));

        this.data.put(column, data);
        return this;
    }

    public boolean checkFormation() {
        int size = model.getColumns().size() - data.size();

        if(size != 0)
            throw new BadQueryFormationException(String.format("Created query %s cannot be used cause model size is different %d", model.getTableName(), size));

        return true;
    }
}
