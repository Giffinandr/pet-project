package com.hfad.shoppinglist.data.entitys;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "buy_table")
public class Buy {

    public Buy(@NonNull String buyName, String listId) {
        this.buyName = buyName;
        this.listId = listId;
        this.buyId = 0;
        this.shoppingFlag = 0;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "buy_id") // ID name buy
    private int buyId;

    @NonNull
    @ColumnInfo(name = "buy_name") // name buy
    private String buyName;

    @ColumnInfo(name = "buy_from_list_id") // name/ID of the corresponding list
    private String listId;

    @ColumnInfo(name = "shopping_flag")
    private int shoppingFlag;

    public int getBuyId() {
        return buyId;
    }

    @NonNull
    public String getBuyName() {
        return this.buyName;
    }

    public void setBuyId(int buyId) {
        this.buyId = buyId;
    }

    public String getListId() {
        return this.listId;
    }

    public void setBuyName(@NonNull String buyName) {
        this.buyName = buyName;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public int getShoppingFlag() {
        return shoppingFlag;
    }

    public void setShoppingFlag(int i) {
        shoppingFlag = i;
    }
}
