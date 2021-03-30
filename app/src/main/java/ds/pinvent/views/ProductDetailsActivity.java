package ds.pinvent.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import ds.pinvent.databinding.ActivityItemDetailsBinding;
import ds.pinvent.model.Product;


public class ProductDetailsActivity extends AppCompatActivity {

    ActivityItemDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityItemDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);
        setTitle("Product Details");

        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("product");

        binding.itemDetailsLayout.tvDetailedActivity.setText(product.getProductName());
        binding.itemDetailsLayout.tvDetailedActivity.setText(product.getProductDescription());
        binding.itemDetailsLayout.tvDetailedActivity.setText(product.getProductPrice());

        Glide.with(this).load(Uri.parse(product.getProductImageUri()))
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.itemDetailsLayout.ivDetailedActivity);
    }

}
