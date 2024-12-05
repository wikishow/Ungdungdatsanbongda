package com.example.duanthuctap.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duanthuctap.model.NotificationDetails;

import java.util.List;

@Dao
public interface NotificationDetailsDAO {
    @Query("SELECT * FROM NOTIFICATIONDETAILS")
    List<NotificationDetails> getAll();

    @Insert
    void insert(NotificationDetails details);

    @Update
    void update(NotificationDetails details);

    @Delete
    void delete(NotificationDetails details);
}
