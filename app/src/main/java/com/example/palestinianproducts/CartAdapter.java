package com.example.palestinianproducts;

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.List;

public class CartAdapter extends ArrayAdapter<Product> {
    private final Context context;
    private final List<Product> cartItems;

    public CartAdapter(Context context, List<Product> items) {
        super(context, 0, items);
        this.context = context;
        this.cartItems = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product p = cartItems.get(position);

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);

        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);

        text1.setText(p.name);
        text2.setText("Price: $" + p.price + " | Qty: " + p.quantity);

        return convertView;
    }
}
