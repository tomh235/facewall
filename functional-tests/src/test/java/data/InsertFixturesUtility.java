package data;

import static data.DatabaseUtils.clearDatabase;
import static data.DatabaseUtils.initialiseDatabase;
import static data.DatabaseUtils.seedFixtures;
import static data.FixturesFactory.createDefaultFixtures;

public class InsertFixturesUtility {

    private final static String dbLocation = "http://localhost:7474/db/data";

    public static void main(String[] args) {
        clearDatabase(dbLocation);
        initialiseDatabase(dbLocation);

        seedFixtures(dbLocation, createDefaultFixtures());
    }
}
