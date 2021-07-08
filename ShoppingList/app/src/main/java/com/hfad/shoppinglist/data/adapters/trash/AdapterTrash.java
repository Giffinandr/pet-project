package com.hfad.shoppinglist.data.adapters.trash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hfad.shoppinglist.R;
import com.hfad.shoppinglist.data.entitys.DeletedListNameWithBuy;
import com.hfad.shoppinglist.data.entitys.ListNames;
import com.hfad.shoppinglist.data.entitys.TrashBuyEntity;
import com.hfad.shoppinglist.model.ListViewModel;

import java.util.List;

import static com.hfad.shoppinglist.model.StatusList.NO_REMOVE_BUY;
import static com.hfad.shoppinglist.model.StatusList.PART_LIST_IS_DELETED;
import static com.hfad.shoppinglist.model.StatusList.WHOLE_LIST_DELETED;

public class AdapterTrash extends BaseExpandableListAdapter {
    private Context context;
    LayoutInflater layoutInflater;
    private List<DeletedListNameWithBuy> list;
    private ListViewModel model;

    public AdapterTrash(View view, List<DeletedListNameWithBuy> list, ListViewModel model) {
        this.context = view.getContext();
        this.list = list;
        this.model = model;
        layoutInflater = LayoutInflater.from(view.getContext());
    }

    @Override
    public TrashBuyEntity getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getBuys()
                .get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.item_trash_buy, parent, false);
        TextView textView = view.findViewById(R.id.text_trash_buy_item);
        textView.setText(getChild(groupPosition, childPosition).getBuyName());

        ImageButton button = view.findViewById(R.id.return_button);
        button.setOnClickListener(v -> {
            if (getGroup(groupPosition).getDeleteFlag() == WHOLE_LIST_DELETED) {
                model.returnList(getGroup(groupPosition).getListName(), PART_LIST_IS_DELETED);
            }
            model.returnBuy(getChild(groupPosition, childPosition));
            if (getChildrenCount(groupPosition) == 1){
                model.returnList(getGroup(groupPosition).getListName(), NO_REMOVE_BUY);
            }
        });

        ImageButton deleteButton = view.findViewById(R.id.delete_buy_button);
        deleteButton.setOnClickListener(v -> {
            if (getChildrenCount(groupPosition) == 1) {
                model.deleteTrashList(getGroup(groupPosition));
            } else {
                model.deleteRemoteBuy(getChild(groupPosition, childPosition));
            }
        });

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getBuys().size();
    }

    @Override
    public ListNames getGroup(int groupPosition) {
        return list.get(groupPosition).getLists();
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if (getGroup(groupPosition).deleteFlag == 0) {
            convertView.setVisibility(View.GONE);
            return convertView;
        }
        LayoutInflater layoutInflater1 = LayoutInflater.from(context);
        View view = layoutInflater1.inflate(R.layout.item_trash_list, parent, false);

        TextView textView = view.findViewById(R.id.text_trash_list);
        textView.setText(getGroup(groupPosition).getListName());

        ImageButton imageButton = view.findViewById(R.id.return_all_buys_button);
        imageButton.setFocusable(false);
        imageButton.setOnClickListener(v -> model.returnDeletedList(groupPosition));

        ImageButton button = view.findViewById(R.id.delete_all_button);
        button.setFocusable(false);
        button.setOnClickListener(v -> model.deleteTrashList(getGroup(groupPosition)));
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
