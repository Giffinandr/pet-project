package com.hfad.shoppinglist.activitys;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hfad.shoppinglist.R;
import com.hfad.shoppinglist.model.ListViewModel;

public class CreateListFragment extends Fragment {
    Button button;
    EditText editText;
    ListViewModel model;

    public CreateListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_list, container, false);

        editText = view.findViewById(R.id.editTextTextMultiLine);
        model = new ViewModelProvider((ViewModelStoreOwner) view.getContext())
                .get(ListViewModel.class);

        button = view.findViewById(R.id.create_button);
        button.setOnClickListener(v -> {
            String nameList = editText.getText().toString();
            if (model.isNotTheSameNames(nameList)) {
                model.createList(nameList);

                NavDirections action;
                action = CreateListFragmentDirections.actionCreateListFragmentToEditListFragment();
                Navigation.findNavController(v).navigate(action);
            } else {
                editText.setText("");
                Toast.makeText(getContext(), "such a list already exists, please, try again", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


}