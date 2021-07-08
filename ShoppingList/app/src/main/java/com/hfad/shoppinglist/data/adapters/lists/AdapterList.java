package com.hfad.shoppinglist.data.adapters.lists;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.hfad.shoppinglist.activitys.ListsFragmentDirections;
import com.hfad.shoppinglist.data.entitys.ListNameWithBuy;
import com.hfad.shoppinglist.model.ListViewModel;

public class AdapterList extends ListAdapter<ListNameWithBuy, ListViewHolder> {
    ListViewModel model;

    public AdapterList(@NonNull DiffUtil.ItemCallback<ListNameWithBuy> diffCallback, ListViewModel model) {
        super(diffCallback);
        this.model = model;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ListViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ListNameWithBuy current = getItem(position);
        holder.bind(current.getLists().getListName());

        holder.setCount(current.getNumSelectBuy(), current.getBuys().size());

        holder.getButton()
                .setOnClickListener(v -> {
                    model.removeListInTrash(current, position);
                    notifyItemRemoved(position);
                });

        holder.getTextBuy().setOnClickListener(v -> {
            model.setListPosition(position);
            NavDirections action;
            action = ListsFragmentDirections.actionListsFragmentToEditListFragment();
            Navigation.findNavController(v).navigate(action);
        });
    }

    public static class ListDiff extends DiffUtil.ItemCallback<ListNameWithBuy> {

        @Override
        public boolean areItemsTheSame(@NonNull ListNameWithBuy oldItem, @NonNull ListNameWithBuy newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ListNameWithBuy oldItem, @NonNull ListNameWithBuy newItem) {
            return oldItem.getLists().getListName().equals(newItem.getLists().getListName());
        }
    }
}
