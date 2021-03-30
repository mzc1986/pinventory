package ds.pinvent.views;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

import ds.pinvent.R;
import ds.pinvent.databinding.ActivityCreateProductBinding;
import ds.pinvent.model.database.DatabaseClient;
import ds.pinvent.model.Product;

public class CreateProductActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri mImageUri;

    private static int AUTOCOMPLETE_REQUEST_CODE = 1;

    ActivityCreateProductBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateProductBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle("List a Product");


        Places.initialize(getApplicationContext(), getString(R.string.GoogleAPIKey));
        PlacesClient placesClient = Places.createClient(this);

        binding.btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        binding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProduct();
            }
        });

        binding.etProductLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(getBaseContext());
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

            }
        });
    }

    private void saveProduct() {
        final String mName = binding.etProductName.getText().toString().trim();
        final String mDescription = binding.etDescription.getText().toString().trim();
        final String mPrice = binding.etProductPrice.getText().toString().trim();

        if (mName.isEmpty()) {
            binding.etProductName.setError("Name required");
            binding.etProductName.requestFocus();
            return;
        }

        if (mDescription.isEmpty()) {
            binding.etDescription.setError("Desc required");
            binding.etDescription.requestFocus();
            return;
        }

        if (mPrice.isEmpty()) {
            binding.etProductPrice.setError("Price required");
            binding.etProductPrice.requestFocus();
            return;
        }

        if (mImageUri.toString().isEmpty()) {
            binding.etProductPrice.setError("Image required");
            binding.etProductPrice.requestFocus();
            return;
        }

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a product
                Product product = new Product(mName, mDescription, mPrice, mImageUri.toString());

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .productDAO()
                        .insertProduct(product);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null ){

            mImageUri = data.getData();

            Glide.with(this).load(mImageUri)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.ivSelectedImage);

        } else if(requestCode == AUTOCOMPLETE_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                binding.etProductLocation.setText(place.getName()+ ", " + place.getAddress());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {

                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
    }
}
