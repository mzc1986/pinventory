package ds.pinvent.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ds.pinvent.model.Product;

@Database(entities = {Product.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDAO productDAO();
}