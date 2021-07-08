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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hfad.shoppinglist.R;
import com.hfad.shoppinglist.data.adapters.lists.AdapterListRecycler;
import com.hfad.shoppinglist.model.ListViewModel;

public class ListsFragment extends Fragment {

    private ListViewModel model;
    private FloatingActionButton actionButton;

    public ListsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lists, container, false);

        model = new ViewModelProvider((ViewModelStoreOwner) view.getContext())
                .get(ListViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.list_of_lists_recycler);
        final AdapterListRecycler adapterListRecycler = new AdapterListRecycler();
        adapterListRecycler.setModel(model);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapterListRecycler);

        model.getListLiveData().observe((LifecycleOwner) view.getContext(), adapterListRecycler::setList);

        /* setting FloatingActionButton */
        actionButton = view.findViewById(R.id.add_list_button);
        actionButton.setOnClickListener(v -> {
            NavDirections action;
            action = ListsFragmentDirections.actionListsFragmentToCreateListFragment();
            Navigation.findNavController(v).navigate(action);
        });


        return view;
    }

}
