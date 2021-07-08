package com.hfad.shoppinglist.data.adapters.shopping;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.shoppinglist.R;

public class ShoppingViewHolder extends RecyclerView.ViewHolder {
    private final TextView nameBuy;

    public ShoppingViewHolder(@NonNull View itemView) {
        super(itemView);
        nameBuy = itemView.findViewById(R.id.text_shopping_item);
    }

    public void bind(String text) {
        nameBuy.setText(text);
    }

    public TextView getNameBuy() {
        return nameBuy;
    }

    static ShoppingViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopping,parent, false);
        return new ShoppingViewHolder(view);
    }
}
