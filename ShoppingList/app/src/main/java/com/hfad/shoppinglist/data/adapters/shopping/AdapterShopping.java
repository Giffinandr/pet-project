package com.hfad.shoppinglist.data.adapters.shopping;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.hfad.shoppinglist.R;
import com.hfad.shoppinglist.data.entitys.Buy;
import com.hfad.shoppinglist.model.ListViewModel;

public class AdapterShopping extends ListAdapter<Buy, ShoppingViewHolder> {
    ListViewModel model;

    public AdapterShopping(@NonNull DiffUtil.ItemCallback<Buy> diffCallback, ListViewModel model) {
        super(diffCallback);
        this.model = model;
    }

    @NonNull
    @Override
    public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ShoppingViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingViewHolder holder, int position) {
       Buy current = getItem(position);

       if (current.getShoppingFlag() == 1) {
            holder.itemView.setBackgroundResource(R.drawable.item_layout);
       } else {
            holder.itemView.setBackgroundResource(R.drawable.item_buy_white);
       }

       holder.bind(current.getBuyName());
       holder.getNameBuy().setOnClickListener(v -> model.setBuyAsPurchased(current));
    }

    public static class BuyDiff extends DiffUtil.ItemCallback<Buy> {

        @Override
        public boolean areItemsTheSame(@NonNull Buy oldItem, @NonNull Buy newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Buy oldItem, @NonNull Buy newItem) {
            return oldItem.getBuyName().equals(newItem.getBuyName());
        }
    }
}
