package com.hfad.shoppinglist.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hfad.shoppinglist.data.DAO.BuyDao;
import com.hfad.shoppinglist.data.entitys.Buy;
import com.hfad.shoppinglist.data.entitys.ListNameWithBuy;
import com.hfad.shoppinglist.data.entitys.ListNames;
import com.hfad.shoppinglist.data.entitys.TrashBuyEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Buy.class, ListNames.class, TrashBuyEntity.class}, version = 1, exportSchema = false)
abstract class BuyRoomDatabase extends RoomDatabase {

    abstract BuyDao buyDao();

    private static volatile BuyRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static BuyRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (BuyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BuyRoomDatabase.class, "list_database")
                            .fallbackToDestructiveMigration()
                           // .addCallback(sRoomDBCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

   /* private static final RoomDatabase.Callback sRoomDBCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

           /* databaseWriteExecutor.execute(() -> {
                BuyDao dao = INSTANCE.buyDao();

                ListNames listNames = new ListNames("TEST NAME 1");
                dao.insertList(listNames);
                listNames = new ListNames("TEST NAME 2");
                dao.insertList(listNames);

                Buy buy = new Buy("Hello");
                dao.insertBuy(buy);
                buy = new Buy("test_1");
                dao.insertBuy(buy);
            });
        }
    };*/
}
