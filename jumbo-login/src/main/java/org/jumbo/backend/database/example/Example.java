package org.jumbo.backend.database.example;

import com.google.inject.Inject;
import lombok.Getter;
import org.jumbo.api.login.database.DatabaseService;
import org.jumbo.commons.sql.model.annotations.PrimaryQueryField;
import org.jumbo.commons.sql.model.annotations.QueryField;

/**
 * Created by Return on 03/09/2014.
 */
public class Example {
    @Getter
    @PrimaryQueryField
    private int id;
    @Getter
    @QueryField
    private String name;
    @Getter
    @QueryField
    private String objective;

    @Inject
    DatabaseService database;

    public Example() {}

    public Example(int id, String name, String objective) {
        this.id = id;
        this.name = name;
        this.objective = objective;
    }

    public void save() {
        database.getQueryManagers().get(getClass()).update(this);
    }
}
