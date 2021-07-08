package com.hfad.shoppinglist.data.adapters.buys;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.hfad.shoppinglist.data.entitys.Buy;
import com.hfad.shoppinglist.model.ListViewModel;

public class AdapterEditBuy extends ListAdapter<Buy, EditBuyViewHolder> {
    private final ListViewModel model;


    public AdapterEditBuy(@NonNull DiffUtil.ItemCallback<Buy> diffCallback, ListViewModel model) {
        super(diffCallback);
        this.model = model;
    }


    @NonNull
    @Override
    public EditBuyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return EditBuyViewHolder.create(parent);
    }


    @Override
    public void onBindViewHolder(@NonNull EditBuyViewHolder holder, int position) {
        Buy current = getItem(position);

        holder.bind(current.getBuyName());
        holder.getButton().setOnClickListener(v -> {
            if (model.getListLiveData().getValue().get(model.getPositionList()).getLists().getDeleteFlag() == 0) {
                model. removeList(current.getListId());
            }
            model.deleteBuy(current);
        });

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
