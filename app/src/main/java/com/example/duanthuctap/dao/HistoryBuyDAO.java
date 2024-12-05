package com.example.duanthuctap.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duanthuctap.model.HistoryBuy;

import java.util.List;

@Dao
public interface HistoryBuyDAO {

    @Query("SELECT * FROM HISTORYBUY")
    List<HistoryBuy> getAll();

    @Insert
    void insert(HistoryBuy historyBuy);

    @Update
    void update(HistoryBuy historyBuy);

    @Delete
    void delete(HistoryBuy historyBuy);

    @Query("SELECT * FROM HISTORYBUY WHERE idCustomer = :id ORDER BY id DESC")
    List<HistoryBuy> getALlWithId(int id);

    @Query("SELECT * FROM HISTORYBUY WHERE status = :status ORDER BY id DESC")
    List<HistoryBuy> getAllWithStatus(int status);

    @Query("SELECT * FROM HISTORYBUY " +
            "INNER JOIN CUSTOMER ON HISTORYBUY.idCustomer = CUSTOMER.id " +
            "WHERE status = :status and CUSTOMER.name " +
            "LIKE :s ORDER BY id DESC")
    List<HistoryBuy> getAllWithCustommer(String s, int status);
}
