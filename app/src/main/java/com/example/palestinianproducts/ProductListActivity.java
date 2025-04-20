package com.example.palestinianproducts;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    ListView productLV;
    List<Product> productList = new ArrayList<>();
    SharedPreferences prefs;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        productLV = findViewById(R.id.productLV);
        prefs = getSharedPreferences("shop", MODE_PRIVATE);

       // Intent intent = getIntent();
       // String low = intent.getStringExtra("low");

        loadProducts();

        String category = getIntent().getStringExtra("category");
        if (category == null) category = "All";

        List<Product> filtered = new ArrayList<>();
        for (Product p : productList) {
            boolean sameCategory;

            switch (category) {
                case "Clothes":
                    sameCategory = p.getName().equalsIgnoreCase("Kufiya")
                            || p.getName().equalsIgnoreCase("Rope")
                            || p.getName().equalsIgnoreCase("Hoodie");
                    break;

                case "Jewelry":
                    sameCategory = p.getName().equalsIgnoreCase("Bracelet")
                            || p.getName().equalsIgnoreCase("Necklace")
                            || p.getName().equalsIgnoreCase("Ring");                    break;

                case "Flags":
                    sameCategory = p.getName().equalsIgnoreCase("Flag");
                    break;

                case "Pins":
                    sameCategory = p.getName().equalsIgnoreCase("Pins");
                    break;

                case "Stickers":
                    sameCategory = p.getName().equalsIgnoreCase("Stickers");
                    break;

                case "All":
                default:
                    sameCategory = true;
            }

            if (sameCategory) {
                filtered.add(p);
            }
        }



        productLV.setAdapter(new ProductAdapter(this, filtered));


    }

    private void loadProducts() {
        Gson gson = new Gson();
        String json = prefs.getString("products", null);

       // prefs.edit().clear().apply();

        if (json == null) {
            productList.add(new Product("Kufiya", 20, 9, R.drawable.kufiya));
            productList.add(new Product("Bracelet", 10, 10, R.drawable.bracelet));
            productList.add(new Product("Stickers", 5, 20, R.drawable.stickers));
            productList.add(new Product("Necklace", 30, 4, R.drawable.necklace));
            productList.add(new Product("Ring", 15, 10, R.drawable.ring));
            productList.add(new Product("Flag", 10, 7, R.drawable.flag));
            productList.add(new Product("Rope", 100, 5, R.drawable.rope));
            productList.add(new Product("Hoodie", 70, 10, R.drawable.hoodie));
            productList.add(new Product("Accessories", 20, 2, R.drawable.accessories));
            productList.add(new Product("Pins", 5, 10, R.drawable.pins));

            prefs.edit().putString("products", gson.toJson(productList)).apply();
        } else {
            Type type = new TypeToken<List<Product>>(){}.getType();
            productList = gson.fromJson(json, type);
        }
    }
}
