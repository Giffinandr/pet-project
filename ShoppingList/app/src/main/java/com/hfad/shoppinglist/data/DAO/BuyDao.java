package com.hfad.shoppinglist.data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.hfad.shoppinglist.data.entitys.Buy;
import com.hfad.shoppinglist.data.entitys.DeletedListNameWithBuy;
import com.hfad.shoppinglist.data.entitys.ListNameWithBuy;
import com.hfad.shoppinglist.data.entitys.ListNames;
import com.hfad.shoppinglist.data.entitys.TrashBuyEntity;

import java.util.List;

@Dao
public interface BuyDao {

    /**
        Добавить в спивок ОДНУ покупку
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertBuy(Buy buy);


    /**
        Добавить в список НЕСКОЛЬКО покупок
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertBuyList(List<Buy> buyList);


    /**
        Создать новый список
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertList(ListNames listNames);


    /**
        Удалить в КОРЗИНУ ОДНУ покупку
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertRemoteBuy(TrashBuyEntity buy);


    /**
        Удалить в КОРЗИНУ НЕСКОЛЬКО покупок
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTrashList(List<TrashBuyEntity> buyEntities);


    /**
        Удалить покупку
     */
    @Delete
    void deleteBuy(Buy buy);


    /**
        Удалить несколько покупок
     */
    @Delete
    void deleteBuyList(List<Buy> buyList);


    /**
        Удалить список
     */
    @Query("DELETE FROM list_names WHERE name_list = :listName")
    void deleteList(String listName);


    /**
        Вернуть в список удаленную покупку
     */
    @Delete
    void deleteRemoteBuy(TrashBuyEntity buy);


    /**
        Удалить список покупок из корзины
     */
    @Query("DELETE FROM trash_buy_table WHERE buy_from_list = :listNameId")
    void deleteBuyListFromTrash(String listNameId);


    /**
        Если все покупки из списка
        находятся в корзине, этот метод удаляет
        ВЕСЬ список из корзины
     */
    @Transaction
    default void deleteAllListFromTrash(String listNameId){
        deleteBuyListFromTrash(listNameId);
        deleteList(listNameId);
    }


    /**
        Возвращает из корзины в список список покупок
     */
    @Transaction
    default void returnBuyList(String listNameId, List<Buy> buyList) {
        updateListName(listNameId, 0);
        insertBuyList(buyList);
        deleteBuyListFromTrash(listNameId);
    }


    /**
        Если часть списка в корзине,
        этот метот УДАЛЯЕТ эту часть
        из КОРЗИНЫ
     */
    @Transaction
    default void deleteListFromTrash(String listNameId){
        updateListName(listNameId, 0);
        deleteBuyListFromTrash(listNameId);
    }


    /**
        Возвращает одну покупку из
        корзины в свой список
     */
    @Transaction
    default void returnBuy(Buy buy, TrashBuyEntity buyEntity) {
        insertBuy(buy);
        deleteRemoteBuy(buyEntity);
    }


    /**
        Меняет флаг состояния списка:
            NO_REMOVE_BUY - у списка нет удаленных в корзину покупок
            PART_LIST_IS_DELETED - у списка есть удаленные в корзину покупки
            WHOLE_LIST_DELETED - весь список удален в корзину
     */
    @Query("UPDATE list_names SET delete_flag = :deleteFlag " +
            "WHERE name_list = :listNameId")
    void updateListName(String listNameId, int deleteFlag);

    /**
        Удаляет ВЕСЬ список в КОРЗИНУ
     */
    @Transaction
    default void remoteListInTrash(String listNameId, List<Buy> buyList,
                                   List<TrashBuyEntity> buyEntity) {
        updateListName(listNameId, 2);
        deleteBuyList(buyList);
        insertTrashList(buyEntity);
    }

    /**
        Возврващает списки покупок
     */
    @Transaction
    @Query("SELECT * FROM list_names WHERE delete_flag < 2")
    LiveData<List<ListNameWithBuy>> getBuyList();

    /**
        Возвращает КОРЗИНУ
     */
    @Transaction
    @Query("SELECT * FROM list_names WHERE delete_flag > 0")
    LiveData<List<DeletedListNameWithBuy>> getTrashList();

    /**
        Устанавливает состояние покупки:
            0 - не куплен
            1 - куплен
     */
    @Query("UPDATE buy_table SET shopping_flag = :flag " +
            "WHERE buy_name = :nameBuy")
    void setBuyAsPurchased(String nameBuy, int flag);
}
