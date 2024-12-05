package com.example.duanthuctap.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duanthuctap.model.ManagerCategory;

import java.util.List;

@Dao
public interface ManagerCategoryDAO {
    @Query("SELECT * FROM MANAGER_CATEGORY")
    List<ManagerCategory> getAll();

    @Query("SELECT * FROM MANAGER_CATEGORY WHERE NAME != 'Admin'")
    List<ManagerCategory> getAllStaff();

    @Insert
    void insert(ManagerCategory managerCategory);

    @Delete
    void delete(ManagerCategory managerCategory);

    @Update
    void update(ManagerCategory managerCategory);

    @Query("SELECT * FROM MANAGER_CATEGORY WHERE ID = :id")
    List<ManagerCategory> getCategoryWithID(int id);

    @Query("SELECT * FROM MANAGER_CATEGORY WHERE NAME = :s")
    List<ManagerCategory> getIdAdmin(String s);

    @Query("DELETE FROM MANAGER_CATEGORY")
    void deleteAll();
}
