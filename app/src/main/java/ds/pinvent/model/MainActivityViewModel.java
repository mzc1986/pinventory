package ds.pinvent.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ds.pinvent.model.database.DatabaseClient;

public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<List<Product>> products;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Product>> getProductsList() {
        if (products == null) {
            products = new MutableLiveData<List<Product>>();
            getProducts();
        }
        return products;
    }

    private void getProducts() {
        class GetProducts extends AsyncTask<Void, Void, List<Product>> {

            @Override
            protected List<Product> doInBackground(Void... voids) {
                List<Product> productList = DatabaseClient
                        .getInstance(getApplication().getApplicationContext())
                        .getAppDatabase()
                        .productDAO()
                        .getAll();

                return productList;
            }

            @Override
            protected void onPostExecute(List<Product> productList) {
                super.onPostExecute(productList);
                products.setValue(productList);
            }
        }

        GetProducts getProducts = new GetProducts();
        getProducts.execute();
    }
}