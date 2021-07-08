package com.hfad.shoppinglist.data.entitys;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "trash_buy_table")
public class TrashBuyEntity {

    public TrashBuyEntity(@NonNull String buyName, String listId) {
        this.buyName = buyName;
        this.listId = listId;
        this.buyId = 0;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "buy_id") // ID name buy
    private int buyId;

    @NonNull
    @ColumnInfo(name = "buy_name") // name buy
    private final String buyName;

    @ColumnInfo(name = "buy_from_list") // name/ID of the corresponding list
    private final String listId;



    @NonNull
    public String getBuyName() {
        return this.buyName;
    }

    public String getListId() { return this.listId; }

    public int getBuyId() {
        return buyId;
    }

    public void setBuyId(int buyId) {
        this.buyId = buyId;
    }
}
