package com.hfad.shoppinglist.data.adapters.lists;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hfad.shoppinglist.R;

public class ListViewHolder extends RecyclerView.ViewHolder {
    private final TextView listItemView;
    private final TextView countBuy;
    private final ImageButton button;

    private ListViewHolder(View itemView) {
        super(itemView);
        listItemView = itemView.findViewById(R.id.text_buy_item);
        countBuy = itemView.findViewById(R.id.text_count);
        button = itemView.findViewById(R.id.item_button);
    }

    public void bind(String text) {
        listItemView.setText(text);
    }

    @SuppressLint("SetTextI18n")
    public void setCount(int numBuy, int totalNumBuy) {
        countBuy.setText(numBuy + " / " + totalNumBuy);
        if (numBuy == totalNumBuy) {
            countBuy.setTextColor(Color.parseColor("#000000"));
        }
        if (numBuy != totalNumBuy) {
            countBuy.setTextColor(Color.parseColor("#5A814A"));
        }
    }

    public TextView getTextBuy() {
        return listItemView;
    }

    public ImageButton getButton() {
        return button;
    }

    static ListViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_buy,parent, false);
        return new ListViewHolder(view);
    }
}
