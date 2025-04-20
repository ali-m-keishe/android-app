package com.example.palestinianproducts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private final List<Product> products;
    private final Context con;

    public ProductAdapter(Context con, List<Product> products) {
        super(con, R.layout.product_item, products);
        this.con = con;
        this.products = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = products.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(con).inflate(R.layout.product_item, parent, false);
        }

        ImageView productImage = convertView.findViewById(R.id.productImage);
        TextView productName = convertView.findViewById(R.id.productName);
        TextView productPrice = convertView.findViewById(R.id.productPrice);
        TextView productQuantity = convertView.findViewById(R.id.productQuantity);
        Button addToCartButton = convertView.findViewById(R.id.addToCartButton);

        productImage.setImageResource(product.imageResId);
        productName.setText(product.name);
        productPrice.setText("Price: $" + product.price);
        productQuantity.setText("Quantity: " + product.quantity);



        addToCartButton.setOnClickListener(v -> {
            if (product.quantity > 0) {
                product.quantity--;

                // Now i will update the quantity, then save the product to the cart
                saveProductList(products);
                addToCart(product);
                notifyDataSetChanged();
                Toast.makeText(con, product.name + " added!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(con, CartActivity.class);
                con.startActivity(intent);
            } else {
                Toast.makeText(con, "Sorry, the product is out of stock!!", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    private void saveProductList(List<Product> updatedList) {
        SharedPreferences prefs = con.getSharedPreferences("shop", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(updatedList);
        editor.putString("products", json);
        editor.apply();
    }

    private void addToCart(Product product) {
        SharedPreferences prefs = con.getSharedPreferences("shop", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String cartJson = prefs.getString("cart", "[]");

        Type type = new TypeToken<List<Product>>(){}.getType();
        List<Product> cart = gson.fromJson(cartJson, type);
        cart.add(new Product(product.name, product.price, 1, product.imageResId)); // qty=1
        prefs.edit().putString("cart", gson.toJson(cart)).apply();
    }
}
