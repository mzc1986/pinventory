package ds.pinvent.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import ds.pinvent.R
import ds.pinvent.databinding.ActivityMainBinding
import ds.pinvent.helper.ProductAdapter
import ds.pinvent.model.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mProductAdapter: ProductAdapter

    var viewModel: MainActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel!!.productsList.observe(this) { productList ->
            mProductAdapter = ProductAdapter(this, productList)
            binding.recyclerView.adapter = mProductAdapter
        }

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.create_new) {
            val intent = Intent(this, CreateProductActivity::class.java)
            startActivity(intent)
        }
        return true
    }
}