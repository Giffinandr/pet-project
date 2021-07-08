package com.hfad.shoppinglist.activitys;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.hfad.shoppinglist.R;
import com.hfad.shoppinglist.data.adapters.trash.AdapterTrash;
import com.hfad.shoppinglist.model.ListViewModel;

public class TrashFragment extends Fragment {

    private ListViewModel model;
    private ExpandableListView listView;
    private AdapterTrash adapter;

    public TrashFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trash, container, false);
        model = new ViewModelProvider((ViewModelStoreOwner) view.getContext()).get(ListViewModel.class);

        listView = view.findViewById(R.id.trash_expandable_list);

        model.getTrashListLiveData().observe((LifecycleOwner) view.getContext(), deletedListNameWithBuys -> {
            adapter = new AdapterTrash(view,
                    model.getTrashListLiveData().getValue(), model);
            listView.setAdapter(adapter);
        });

        return view;
    }
}
