package org.jumbo.backend.database.models;

import org.jumbo.backend.database.example.Example;
import org.jumbo.commons.sql.model.QueryModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Return on 03/09/2014.
 */
public class ExampleModel extends QueryModel<Example>{
    public ExampleModel() {
        super("accounts", new Example());
    }

    @Override
    public Map<String, String> getColumnModel() {
        Map<String, String> model = new HashMap<>();
        model.put("id", "guid");
        model.put("name", "account");
        model.put("objective", "other");

        return model;
    }
}
