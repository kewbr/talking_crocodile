package by.fabric.kewbr.talking_crocodile.Database;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DatabaseConfigurator {

    private static final String DATABASE_NAME = "talking_crocodile_database.realm";

    public static void configure() {

        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(23)
                .migration(new DatabaseMigration())
                .assetFile(DATABASE_NAME)
                .build();

        Realm.setDefaultConfiguration(config);
    }

}
