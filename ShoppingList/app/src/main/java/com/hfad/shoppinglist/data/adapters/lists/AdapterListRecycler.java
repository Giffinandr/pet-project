package com.hfad.shoppinglist.data.adapters.lists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.shoppinglist.R;
import com.hfad.shoppinglist.activitys.ListsFragmentDirections;
import com.hfad.shoppinglist.data.entitys.Buy;
import com.hfad.shoppinglist.data.entitys.ListNameWithBuy;
import com.hfad.shoppinglist.model.ListViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterListRecycler extends RecyclerView.Adapter<AdapterListRecycler.ListHolder> {
    private List<ListNameWithBuy> list = new ArrayList<>();
    private ListViewModel model;

    @NonNull
    @Override
    public AdapterListRecycler.ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_buy, parent, false);
        return new ListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListRecycler.ListHolder holder, int position) {
        ListNameWithBuy listNames = list.get(position);
          holder.getTextViewListName().setText(listNames.getLists().getListName());
          holder.getTextViewCounterBuy().setText(getCountBuy(position));
        holder.getTextViewListName().setOnClickListener(v -> {
            model.setListPosition(position);
            NavDirections action;
            action = ListsFragmentDirections.actionListsFragmentToEditListFragment();
            Navigation.findNavController(v).navigate(action);
        });

         holder.getImageButtonDeleteList().setOnClickListener(v -> model.removeListInTrash(listNames, position));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<ListNameWithBuy> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setModel(ListViewModel model) {
        this.model = model;
    }

    private String getCountBuy(int position) {
        int intCount = 0;
        List<Buy> listBuy = list.get(position).getBuys();
        for (int i=0; i < listBuy.size(); i++) {
            if (listBuy.get(i).getShoppingFlag() == 1) {
                intCount++;
            }
        }
        return intCount + " / " + listBuy.size();
    }

    static class ListHolder extends RecyclerView.ViewHolder {
        private final TextView textViewListName;
        private final TextView textViewCounterBuy;
        private final ImageButton imageButtonDeleteList;

        public TextView getTextViewListName() {
            return textViewListName;
        }

        public TextView getTextViewCounterBuy() {
            return textViewCounterBuy;
        }

        public ImageButton getImageButtonDeleteList() {
            return imageButtonDeleteList;
        }

        public ListHolder(@NonNull View view) {
            super(view);
            textViewListName = view.findViewById(R.id.text_buy_item);
            textViewCounterBuy = view.findViewById(R.id.text_count);
            imageButtonDeleteList = view.findViewById(R.id.item_button);
        }
    }
}
