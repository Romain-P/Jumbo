package org.jumbo.backend.database.example;

import lombok.extern.slf4j.Slf4j;
import org.jumbo.backend.database.models.ExampleModel;
import org.jumbo.commons.sql.QueryManager;
import org.jumbo.commons.sql.model.Query;
import org.jumbo.commons.sql.model.enums.OnlyExecuteQueryEnum;

import java.sql.SQLException;

/**
 * Created by Return on 03/09/2014.
 */
@Slf4j
public class ExampleManager extends QueryManager<Example> {
    public ExampleManager() {
        super(new ExampleModel());
    }

    @Override
    public boolean create(Example obj) {
        try {
            Query query = model.createNewQuery()
                    .setData("id", obj.getId())
                    .setData("name", obj.getName())
                    .setData("objective", obj.getObjective());

            execute(query, OnlyExecuteQueryEnum.CREATE);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Example obj) {
        try {
            execute(model, obj.getId(), OnlyExecuteQueryEnum.DELETE);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Example obj) {
        try {
            Query query = model.createNewQuery()
                    .setData("id", obj.getId())
                    .setData("name", obj.getName())
                    .setData("objective", obj.getObjective());

            execute(query, OnlyExecuteQueryEnum.UPDATE);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Example load(Object primary) {
        try {
            Query query = createNewQuery(primary);
            return new Example(
                    (int) query.getData().get("id"),
                    (String) query.getData().get("name"),
                    (String) query.getData().get("objective"));
        } catch (SQLException exception) {
            log.error(exception.getMessage());
            return null;
        }
    }
}
