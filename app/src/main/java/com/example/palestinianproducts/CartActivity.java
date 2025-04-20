package com.example.palestinianproducts;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.*;

public class CartActivity extends AppCompatActivity {

    ListView cartLV;
    TextView totalPrice;
    Button checkoutBtn;

    List<Product> cartItems = new ArrayList<>();
    SharedPreferences prefs;
    Gson gson = new Gson();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartLV = findViewById(R.id.cartLV);
        totalPrice = findViewById(R.id.totalPrice);
        checkoutBtn = findViewById(R.id.checkoutBtn);
        prefs = getSharedPreferences("shop", MODE_PRIVATE);

        loadCart();
        updateTotal();

        CartAdapter adapter = new CartAdapter(this, cartItems);
        cartLV.setAdapter(adapter);

        checkoutBtn.setOnClickListener(v -> {
            saveOrder();
            clearCart();
            Toast.makeText(this, "Order placed!", Toast.LENGTH_LONG).show();
            finish();
        });
    }

    private void loadCart() {
        String json = prefs.getString("cart", "[]");
        Type type = new TypeToken<List<Product>>() {}.getType();
        cartItems = gson.fromJson(json, type);
    }

    private void updateTotal() {
        int total = 0;
        for (Product p : cartItems) {
            total += p.price * p.quantity;
        }
        totalPrice.setText("Total: $" + total);
    }

    private void saveOrder() {
        String orderJson = gson.toJson(cartItems);
        prefs.edit().putString("lastOrder", orderJson).apply();
    }

    private void clearCart() {
        prefs.edit().remove("cart").apply();
    }
}
