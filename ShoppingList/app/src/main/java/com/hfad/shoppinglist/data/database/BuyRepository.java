package com.hfad.shoppinglist.data.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.hfad.shoppinglist.data.DAO.BuyDao;
import com.hfad.shoppinglist.data.entitys.Buy;
import com.hfad.shoppinglist.data.entitys.DeletedListNameWithBuy;
import com.hfad.shoppinglist.data.entitys.ListNameWithBuy;
import com.hfad.shoppinglist.data.entitys.ListNames;
import com.hfad.shoppinglist.data.entitys.TrashBuyEntity;

import java.util.List;

public class BuyRepository {
    private final LiveData<List<ListNameWithBuy>> listLiveData;
    private final LiveData<List<DeletedListNameWithBuy>> trashListLiveData;
    private final BuyDao mBuyDao;

    public BuyRepository(Application context) {
        BuyRoomDatabase db = BuyRoomDatabase.getDatabase(context);
        mBuyDao = db.buyDao();
        listLiveData = mBuyDao.getBuyList();
        trashListLiveData = mBuyDao.getTrashList();
    }

   public LiveData<List<ListNameWithBuy>> getListLiveData() {
       return listLiveData;
   }

   public LiveData<List<DeletedListNameWithBuy>> getTrashListLiveData() { return trashListLiveData; }

   public void insertBuy(Buy buy) {
       BuyRoomDatabase.databaseWriteExecutor.execute(() -> mBuyDao.insertBuy(buy));
   }

   public void insertList(ListNames listNames) {
       BuyRoomDatabase.databaseWriteExecutor.execute(() -> mBuyDao.insertList(listNames));
   }

   public void removeList(String listNameId) {
        BuyRoomDatabase.databaseWriteExecutor.execute(() -> mBuyDao.updateListName(listNameId, 1));
   }

   public void removeBuy(Buy buy, TrashBuyEntity buyEntity) {
       BuyRoomDatabase.databaseWriteExecutor.execute(() -> {
           mBuyDao.deleteBuy(buy);
           mBuyDao.insertRemoteBuy(buyEntity);
       });
   }

   public void returnBuyList(String listNameId, List<Buy> buyList) {
        BuyRoomDatabase.databaseWriteExecutor.execute(() -> mBuyDao.returnBuyList(listNameId, buyList));
   }

   public void deleteTrashList(String listNameId) {
        BuyRoomDatabase.databaseWriteExecutor.execute(() -> mBuyDao.deleteAllListFromTrash(listNameId));
   }

   public void returnBuy(Buy buy, TrashBuyEntity buyEntity){
        BuyRoomDatabase.databaseWriteExecutor.execute(() -> mBuyDao.returnBuy(buy, buyEntity));
   }

   public void deleteRemoteBuy(TrashBuyEntity buyEntity) {
        BuyRoomDatabase.databaseWriteExecutor.execute(() -> mBuyDao.deleteRemoteBuy(buyEntity));
   }

   public void deleteListFromTrash(String listNameId) {
        BuyRoomDatabase.databaseWriteExecutor.execute(() -> mBuyDao.deleteListFromTrash(listNameId));
   }

   public void remoteListInTrash(String listNameId, List<Buy> buyList, List<TrashBuyEntity> buyEntities) {
        BuyRoomDatabase.databaseWriteExecutor.execute(() -> mBuyDao.remoteListInTrash(listNameId, buyList, buyEntities));
   }

   public void setBuyAsPurchased(String buyName, int flag) {
        BuyRoomDatabase.databaseWriteExecutor.execute(() -> mBuyDao.setBuyAsPurchased(buyName, flag));
   }

   public void returnList(String listNameId, int stateList) {
        BuyRoomDatabase.databaseWriteExecutor.execute(() -> mBuyDao.updateListName(listNameId, stateList));
   }
}
