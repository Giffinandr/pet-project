package com.hfad.shoppinglist.data.entitys;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "list_names")
public class ListNames {
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "name_list")
        private final String listName;

        @ColumnInfo(name = "delete_flag")
         public int deleteFlag;

        public ListNames(@NonNull String listName) {
                this.listName = listName;
                this.deleteFlag = 0;
        }

        @NonNull
        public String getListName() {
                return this.listName;
        }

        public int getDeleteFlag() {
                return deleteFlag;
        }

        public void setDeleteFlag(int deleteFlag) {
                this.deleteFlag = deleteFlag;
        }
}

