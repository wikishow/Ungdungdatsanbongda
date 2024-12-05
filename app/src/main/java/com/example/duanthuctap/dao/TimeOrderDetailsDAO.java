package com.example.duanthuctap.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duanthuctap.model.TimeOrderDetails;

import java.util.List;

@Dao
public interface TimeOrderDetailsDAO {

    @Query("SELECT * FROM TIMEORDERDETAILS")
    List<TimeOrderDetails> getAll();

    @Insert
    void insert(TimeOrderDetails details);

    @Update
    void update(TimeOrderDetails details);

    @Delete
    void delete(TimeOrderDetails details);

    @Query("SELECT * FROM TIMEORDERDETAILS WHERE orderId = :id")
    List<TimeOrderDetails> getTimeOrderWithOrderId(int id);

    @Query("SELECT * FROM TIMEORDERDETAILS" +
            " INNER JOIN ORDERS ON TIMEORDERDETAILS.orderId = ORDERS.id " +
            "WHERE ORDERS.datePlay = :date AND ORDERS.pitchId = :pitchId AND ORDERS.status != :status")
    List<TimeOrderDetails> getTimeOrderWithDateAndPitch(String date,int pitchId,int status);

    @Query("SELECT * FROM TIMEORDERDETAILS" +
            " INNER JOIN ORDERS ON TIMEORDERDETAILS.orderId = ORDERS.id " +
            "WHERE ORDERS.datePlay = :date AND ORDERS.pitchId = :pitchId AND ORDERS.id = :id")
    List<TimeOrderDetails> getTimeOrderWithOrderId(String date,int pitchId,int id);

    @Query("DELETE FROM TIMEORDERDETAILS WHERE orderId = :orderId AND timeId = :timeId")
    void deleteWithOrderIdAndTimeId(int orderId,int timeId);

    @Query("SELECT MYTIME.name,COUNT(MYTIME.ID) FROM MYTIME " +
            "INNER JOIN TIMEORDERDETAILS ON MYTIME.id = TIMEORDERDETAILS.timeId " +
            "INNER JOIN ORDERS ON TIMEORDERDETAILS.orderId = ORDERS.id " +
            "WHERE ORDERS.dateCreate LIKE :date GROUP BY MYTIME.ID")
    Cursor getInfoTime(String date);
}
