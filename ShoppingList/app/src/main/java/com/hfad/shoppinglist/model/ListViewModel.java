package com.hfad.shoppinglist.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hfad.shoppinglist.data.database.BuyRepository;
import com.hfad.shoppinglist.data.entitys.Buy;
import com.hfad.shoppinglist.data.entitys.DeletedListNameWithBuy;
import com.hfad.shoppinglist.data.entitys.ListNameWithBuy;
import com.hfad.shoppinglist.data.entitys.ListNames;
import com.hfad.shoppinglist.data.entitys.TrashBuyEntity;

import java.util.ArrayList;
import java.util.List;

import static com.hfad.shoppinglist.model.StatusList.NO_REMOVE_BUY;
import static com.hfad.shoppinglist.model.StatusList.PART_LIST_IS_DELETED;

public class ListViewModel extends AndroidViewModel {

    private final BuyRepository mRepository;
    private final LiveData<List<ListNameWithBuy>> listLiveData;
    private final LiveData<List<DeletedListNameWithBuy>> trashListLiveData;

    private static int listPosition;

    public ListViewModel(Application application) {
        super(application);
        mRepository = new BuyRepository(application);
        listLiveData = mRepository.getListLiveData();
        trashListLiveData = mRepository.getTrashListLiveData();
        listPosition = 0;
    }


    /**
        Возвращает все списки покупок
     */
    public LiveData<List<ListNameWithBuy>> getListLiveData() {
        return listLiveData;
    }


    /**
        Возвращает корзину
     */
    public LiveData<List<DeletedListNameWithBuy>> getTrashListLiveData() { return trashListLiveData; }


    /**
        ListPosition используется логике выбора
        списка покупок при перемещении по
        BottomNavigationView
     */
    public void setListPosition(int listPos) {
        listPosition = listPos;
    }

    public int getPositionList() {
        return listPosition;
    }


    /**
        Проверяет актуальность ListPosition
     */
    public boolean isEmptyList() {
        if (listPosition != -1) {
            return listLiveData.getValue().size() > listPosition;
        }
        return false;
    }


    /**
        Меняет статс СПИСКА:
            в списке НЕТ удаленных покупок
                        ||
                        \/
            в списке ЕСТЬ удаленные покупки
     */
    public void removeList(String listNameId) {
        mRepository.removeList(listNameId);
    }


    /**
        Удалить покупку  КОРЗИНУ
     */
    public void deleteBuy(Buy buy) {
        TrashBuyEntity buyEntity = new TrashBuyEntity(buy.getBuyName(), buy.getListId());
        mRepository.removeBuy(buy, buyEntity);
    }


    /**
        Возвращает из КОРЗИНЫ в список все удаленные покупки
     */
    public void returnDeletedList(int position) {
        if (listLiveData.getValue().size() == 0) {
            listPosition = 0;
        }
        List<TrashBuyEntity> list = trashListLiveData.getValue().get(position).getBuys();
        int n = list.size();
        List<Buy> buyList = new ArrayList<>();
        String listName = trashListLiveData.getValue().get(position).getLists().getListName();

        for (int i=0; i < n; i++) {
            buyList.add(new Buy(list.get(i).getBuyName(), list.get(i).getListId()));
        }
        mRepository.returnBuyList(listName, buyList);
    }


    /**
        Удалить список из КОРЗИНЫ
     */
    public void deleteTrashList(ListNames listNames) {

        if (listNames.getDeleteFlag() == StatusList.WHOLE_LIST_DELETED) {
            mRepository.deleteTrashList(listNames.getListName());
        } else {
            mRepository.deleteListFromTrash(listNames.getListName());
        }
    }


    /**
        Вернуть покупку из КОРЗИНЫ
     */
    public void returnBuy(TrashBuyEntity buyEntity) {
        Buy buy = new Buy(buyEntity.getBuyName(), buyEntity.getListId());
        mRepository.returnBuy(buy, buyEntity);
    }


    /**
        Удалить покупку из КОРЗИНЫ
     */
    public void deleteRemoteBuy(TrashBuyEntity buyEntity) {
        mRepository.deleteRemoteBuy(buyEntity);
    }


    /**
        Удалить список покупок в корзину
     */
    public void removeListInTrash(ListNameWithBuy list, int position) {
        if (listPosition == position) listPosition--;

        String listNameId = list.getLists().getListName();
        List<Buy> buyList = list.getBuys();

        List<TrashBuyEntity> buyEntities = new ArrayList<>();
        TrashBuyEntity trashBuy;

        int n = buyList.size();
        for (int i=0; i < n; i++) {
            trashBuy = new TrashBuyEntity(buyList.get(i).getBuyName(),
                    buyList.get(i).getListId());
            buyEntities.add(trashBuy);
        }
        mRepository.remoteListInTrash(listNameId, buyList, buyEntities);
    }


    /**
        Возвращает имя выбранного списка
     */
    public String getListNameString() {
        return listLiveData.getValue()
                .get(listPosition)
                .getLists()
                .getListName();
    }


    /**
        Добавить в список покупку
     */
    public void addBuyToList(String nameBuy) {
        String listNameId = listLiveData.getValue().get(listPosition).getLists().getListName();
        mRepository.insertBuy(new Buy(nameBuy, listNameId));
    }


    /**
        Создать новый список
     */
    public void createList(String listName) {
        listPosition = listLiveData
                .getValue()
                .size();
        ListNames newListName = new ListNames(listName);
        mRepository.insertList(newListName);
    }


    /**
        Проверяет на наличие списка
        с таким же именем
     */
    public boolean isNotTheSameNames(String listName) {
        int n = listLiveData.getValue().size();
        List<ListNameWithBuy> list = listLiveData.getValue();
        for (int i=0; i < n; i++) {
            if (listName.equals(list.get(i).getLists().getListName())) {
                return false;
            }
        }
        return true;
    }


    /**
        Меняет флаг состояния покупки
     */
    public void setBuyAsPurchased(Buy buy) {
        if (buy.getShoppingFlag() == PART_LIST_IS_DELETED) mRepository.setBuyAsPurchased(buy.getBuyName(), NO_REMOVE_BUY);
        if (buy.getShoppingFlag() == NO_REMOVE_BUY) mRepository.setBuyAsPurchased(buy.getBuyName(), PART_LIST_IS_DELETED);
    }


    /**
        Меняет флаг состояния списка:
            NO_REMOVE_BUY - у списка нет удаленных в корзину покупок
            PART_LIST_IS_DELETED - у списка есть удаленные в корзину покупки
            WHOLE_LIST_DELETED - весь список удален в корзину
     */
    public void returnList(String listNameId, int stateList) {
        if (listLiveData.getValue().size() == 0) {
            listPosition = 0;
        }
        mRepository.returnList(listNameId, stateList);
    }
}
