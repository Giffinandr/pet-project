package com.hfad.shoppinglist.activitys;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hfad.shoppinglist.R;
import com.hfad.shoppinglist.data.adapters.buys.AdapterEditBuy;
import com.hfad.shoppinglist.model.ListViewModel;

public class EditListFragment extends Fragment {

    private Button addButton;
    private TextView listLabel;
    private EditText addBuyText;
    private ListViewModel model;

    public EditListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_list, container, false);

        addBuyText = view.findViewById(R.id.enter_buy_text_view);

        model = new ViewModelProvider((ViewModelStoreOwner) view.getContext()).get(ListViewModel.class);
        /* setting RecycleView and AdapterEditBuy */
        RecyclerView recyclerView = view.findViewById(R.id.buy_recycler_viewer);
        final AdapterEditBuy adapterEditBuy = new AdapterEditBuy(new AdapterEditBuy.BuyDiff(), model);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterEditBuy);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        listLabel = view.findViewById(R.id.list_label_text);

        /* ListViewModel */
        model.getListLiveData().observe((LifecycleOwner) view.getContext(), listNameWithBuys -> {
                if (model.isEmptyList()) {
                    adapterEditBuy.submitList(listNameWithBuys.get(model.getPositionList()).getBuys());
                    listLabel.setText(model.getListNameString());
                }
        });

        /* setting button */
        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            if (!model.isEmptyList()){
                NavDirections action;
                action = EditListFragmentDirections.actionEditListFragmentToCreateListFragment();
                Navigation.findNavController(view).navigate(action);
            } else {
                String buyName = addBuyText.getText().toString();
                addBuyText.setText("");
                model.addBuyToList(buyName);
            }
        });

        return view;
    }


}