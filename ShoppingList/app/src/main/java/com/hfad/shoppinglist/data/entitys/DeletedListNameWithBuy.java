package com.hfad.shoppinglist.data.entitys;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DeletedListNameWithBuy {
    @Embedded private ListNames listNames;
    @Relation(
            parentColumn = "name_list",
            entityColumn = "buy_from_list"
    )
    private List<TrashBuyEntity> trashBuy;

    public List<TrashBuyEntity> getBuys() {
        return this.trashBuy;
    }

    public ListNames getLists() {
        return this.listNames;
    }

    public void setListNames(ListNames listNames) {
        this.listNames = listNames;
    }

    public void setTrashBuy(List<TrashBuyEntity> trashBuy) {
        this.trashBuy = trashBuy;
    }
}
