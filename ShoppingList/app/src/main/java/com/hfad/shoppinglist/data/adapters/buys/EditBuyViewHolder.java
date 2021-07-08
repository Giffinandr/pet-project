package com.hfad.shoppinglist.data.adapters.buys;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hfad.shoppinglist.R;

public class EditBuyViewHolder extends RecyclerView.ViewHolder {
    private final TextView buyItemView;
    private final ImageButton button;

    private EditBuyViewHolder(View itemView) {
        super(itemView);
        buyItemView = itemView.findViewById(R.id.text_buy_item);
        button = itemView.findViewById(R.id.item_button);
    }

    public void bind(String text) {
        buyItemView.setText(text);
    }

    public ImageButton getButton() {
        return button;
    }

    static EditBuyViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_edit_buy,parent, false);
        return new EditBuyViewHolder(view);
    }
}
