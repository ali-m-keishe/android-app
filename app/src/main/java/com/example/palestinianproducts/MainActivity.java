package com.example.palestinianproducts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText searchET;
    private Spinner spnCat;
    private RadioGroup radio;
    private CheckBox checkbox;
    private Button searchBtn;

    String[] categories = {"All","Jewelry", "Clothes", "Flags", "Pins", "Stickers"};


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchET = findViewById(R.id.searchET);
        spnCat = findViewById(R.id.spnCat);
        radio = findViewById(R.id.radio);
        checkbox = findViewById(R.id.checkbox);
        searchBtn = findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCat.setAdapter(adapter);


        Button searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(v -> {
            String category = spnCat.getSelectedItem().toString();
            Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
            intent.putExtra("category", category);
            startActivity(intent);
        });


    }


    private void performSearch() {
        String search = searchET.getText().toString();
        String cat = spnCat.getSelectedItem().toString();
        String priceFilter = ((RadioButton) findViewById(radio.getCheckedRadioButtonId())).getText().toString();
        boolean isInStock = checkbox.isChecked();



        // Now this part of code is to save to the SharedPreferences
        SharedPreferences preferences = getSharedPreferences("userPreferences", MODE_PRIVATE); // This is for reading
        SharedPreferences.Editor editor = preferences.edit(); // This is for writing
        editor.putString("search", search);
        editor.putString("category", cat);
        editor.putString("priceFilter", priceFilter);
        editor.putBoolean("isInStock", isInStock);
        editor.apply();
    }
}
