Jumbo
==========

### What is it ?
Jumbo is a 1.29 dofus emulator, developed in Java.
He's supported by gradle and is separated in 4 projects

 * **Jumbo-api** : contains all interfaces (will be util for plugins/scripts)
 * **Jumbo-commons** : contains all utils class
 * **Jumbo-Login** : is the login server, manages connections
 * **Jumbo-Game** : is the game server, manages the in-game

### Dependencies

Jumbo contains a lot of util dependencies

 * **Guice** by google: for dependency injections
 * **Acara** by blackrush: for manages events
 * **Rocket** by blackrush: for manages messages
 * **Mina** by Apache: for the network
 * **MySQL-Connector** by Apache: for JDBC mysql
 * **Slf4j & Logback** for loggers

### Something else ?

A lot of 1.29 dofus emulator are already created, but i think i can do better.
Yet, i would like to the database is "multi-template". You will be able to adapt your tables and columns
How ? you have just to annotate your object's Field which corresponds to your column by **@QueryField**
In the same example, you can also define your primary key by **@PrimaryQueryField** and your table by the supertype instanciation.

Look also that example: [click here](https://github.com/Romain-P/Jumbo/blob/master/jumbo-login/src/main/java/org/jumbo/database/example/Example.java) and [here](https://github.com/Romain-P/Jumbo/blob/master/jumbo-login/src/main/java/org/jumbo/database/example/ExampleManager.java)

These run with a model which looks like that:

    public class ExampleModel extends DefaultQueryModel<Example> {
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

### Main Authors ?
Romain-P, Baskwo