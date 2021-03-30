package ds.pinvent.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ds.pinvent.model.Product;


@Dao
public interface ProductDAO {

    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Insert
    void insertProduct(Product product);

}