package com.example.duanthuctap.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duanthuctap.model.MyNotification;

import java.util.List;

@Dao
public interface NotificationDAO {

    @Query("SELECT * FROM MY_NOTIFICATION ORDER BY MY_NOTIFICATION.id DESC")
    List<MyNotification> getALl();

//    @Query("SELECT * FROM MY_NOTIFICATION WHERE customerId = :id")
//    List<MyNotification> getAllWithIdCus(int id);

    @Update
    void update(MyNotification notification);

    @Insert
    void insert(MyNotification notification);

    @Delete
    void delete(MyNotification notification);

    @Query("SELECT * FROM MY_NOTIFICATION " +
            "INNER JOIN NOTIFICATIONDETAILS " +
            "ON MY_NOTIFICATION.id = NOTIFICATIONDETAILS.notificationId " +
            "INNER JOIN CUSTOMER " +
            "ON NOTIFICATIONDETAILS.customerId = CUSTOMER.id " +
            "WHERE CUSTOMER.id = :id AND status = :status ORDER BY MY_NOTIFICATION.id DESC")
    List<MyNotification> getNotifiWithCusId(int id,int status);

    @Query("SELECT MAX(id) FROM MY_NOTIFICATION")
    int getNewNotification();
}
