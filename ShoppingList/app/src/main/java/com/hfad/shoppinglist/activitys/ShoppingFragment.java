package com.hfad.shoppinglist.activitys;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hfad.shoppinglist.R;
import com.hfad.shoppinglist.data.adapters.shopping.AdapterShopping;
import com.hfad.shoppinglist.model.ListViewModel;

public class ShoppingFragment extends Fragment {

    private TextView nameList;
    private ListViewModel model;

    public ShoppingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);

        model = new ViewModelProvider((ViewModelStoreOwner) getContext()).get(ListViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.shopping_recycler_viewer);
        final AdapterShopping adapterShopping = new AdapterShopping(new AdapterShopping.BuyDiff(), model);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterShopping);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        nameList = view.findViewById(R.id.list_label_shopping_text);

        model.getListLiveData().observe((LifecycleOwner) view.getContext(), listNameWithBuys -> {
            if (model.isEmptyList()) {
                adapterShopping.submitList(listNameWithBuys.get(model.getPositionList()).getBuys());
                nameList.setText(model.getListNameString());
            }
        });

        return view;
    }
}