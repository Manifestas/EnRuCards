package dev.manifest.en_rucards.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import dev.manifest.en_rucards.data.model.Card;

@Database(entities = {Card.class}, version = 1)
public abstract class CardDatabase extends RoomDatabase {

    public static final String DB_NAME = "CardDatabase";

    public abstract CardDao cardDao();
}
