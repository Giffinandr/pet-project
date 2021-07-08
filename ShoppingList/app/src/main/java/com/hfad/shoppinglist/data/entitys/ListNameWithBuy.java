package com.hfad.shoppinglist.data.entitys;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ListNameWithBuy {
    @Embedded private ListNames listNames;
    @Relation(
            parentColumn = "name_list",
            entityColumn = "buy_from_list_id"
    )

    private List<Buy> buys;

    public List<Buy> getBuys() {
        return this.buys;
    }

    public ListNames getLists() {
        return this.listNames;
    }

    public void setListNames(ListNames listNames) {
        this.listNames = listNames;
    }

    public void setBuys(List<Buy> buys) {
        this.buys = buys;
    }

    public int getNumSelectBuy() {
        int count = 0;
        for (int i=0; i < buys.size(); i++) {
            count += buys.get(i).getShoppingFlag();
        }
        return count;
    }
}
