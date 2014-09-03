package org.jumbo.commons.sql.model.builders;

import org.jumbo.commons.sql.model.Query;
import org.jumbo.commons.sql.model.QueryModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Return on 03/09/2014.
 */
public class QueryObjectBuilder {
    public static Query newQuery(QueryModel model, ResultSet result) throws SQLException {
        Query query = model.createNewQuery();

        if(result.next())
            for(Object column: model.getColumns().keySet())
                query.getData().put((String) column, result.getObject((String)column));

        return query;
    }
}
