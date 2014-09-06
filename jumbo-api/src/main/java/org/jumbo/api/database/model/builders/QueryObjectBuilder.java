package org.jumbo.api.database.model.builders;

import org.jumbo.api.database.model.Query;
import org.jumbo.api.database.model.QueryModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Return on 06/09/2014.
 */
public interface QueryObjectBuilder {
    public static Query newQuery(QueryModel<?> model, ResultSet result) throws SQLException {
        Query query = model.createNewQuery();

        if(result.next())
            for(String column: model.getColumns().keySet())
                query.getData().put(column, result.getObject(column));

        return query;
    }
}
