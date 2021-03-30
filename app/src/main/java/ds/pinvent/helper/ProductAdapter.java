package ds.pinvent.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import ds.pinvent.views.ProductDetailsActivity;
import ds.pinvent.databinding.SingleItemBinding;
import ds.pinvent.model.Product;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ExampleViewHolder> {
    private Context mContext;
    private List<Product> mProductList;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ProductAdapter(Context mContext, List<Product> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SingleItemBinding binding = SingleItemBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new ExampleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {

        Product currentItem = mProductList.get(position);

        Uri uri = Uri.parse(currentItem.getProductImageUri());

        Log.d("product", "onBindViewHolder: " + uri.toString());
        holder.binding.textName.setText(currentItem.getProductName());
        holder.binding.textDesc.setText(currentItem.getProductDescription());
        holder.binding.textPrice.setText(currentItem.getProductPrice());

        Glide.with(mContext).load(uri)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.binding.imageView);
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{

        SingleItemBinding binding;

        public ExampleViewHolder(SingleItemBinding binding){
            super(binding.getRoot());

            this.binding = binding;

            this.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Product currentItem = mProductList.get(getAdapterPosition());
                        Intent detailsActivity = new Intent(mContext, ProductDetailsActivity.class);
                        detailsActivity.putExtra("product", currentItem);
                        mContext.startActivity(detailsActivity);

                    }
            });

        }
    }
}
