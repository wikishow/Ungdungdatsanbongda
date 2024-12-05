package com.example.duanthuctap.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.duanthuctap.dao.CustomerDAO;
import com.example.duanthuctap.dao.HistoryBuyDAO;
import com.example.duanthuctap.dao.ManagerCategoryDAO;
import com.example.duanthuctap.dao.ManagerDAO;
import com.example.duanthuctap.dao.NotificationDAO;
import com.example.duanthuctap.dao.NotificationDetailsDAO;
import com.example.duanthuctap.dao.OrderDAO;
import com.example.duanthuctap.dao.OrderDetailsDAO;
import com.example.duanthuctap.dao.PitchCategoryDAO;
import com.example.duanthuctap.dao.PitchDAO;
import com.example.duanthuctap.dao.ServiceDAO;
import com.example.duanthuctap.dao.TimeDAO;
import com.example.duanthuctap.dao.TimeOrderDetailsDAO;
import com.example.duanthuctap.model.Customer;
import com.example.duanthuctap.model.HistoryBuy;
import com.example.duanthuctap.model.Manager;
import com.example.duanthuctap.model.ManagerCategory;
import com.example.duanthuctap.model.MyNotification;
import com.example.duanthuctap.model.MyTime;
import com.example.duanthuctap.model.NotificationDetails;
import com.example.duanthuctap.model.Order;
import com.example.duanthuctap.model.OrderDetails;
import com.example.duanthuctap.model.Pitch;
import com.example.duanthuctap.model.PithCategory;
import com.example.duanthuctap.model.ServiceBall;
import com.example.duanthuctap.model.TimeOrderDetails;

@Database(entities = {Customer.class,Manager.class, Order.class
        , OrderDetails.class, Pitch.class, PithCategory.class
        , ServiceBall.class, ManagerCategory.class, MyNotification.class
        , MyTime.class, TimeOrderDetails.class, HistoryBuy.class
        , NotificationDetails.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    private static String DB_NAME = "PITCH_MANAGER22";
    private static MyDatabase instance;

    public static synchronized MyDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),MyDatabase.class,DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract CustomerDAO customerDAO();
    public abstract ManagerDAO managerDAO();
    public abstract OrderDAO orderDAO();
    public abstract OrderDetailsDAO orderDetailsDAO();
    public abstract PitchCategoryDAO pitchCategoryDAO();
    public abstract PitchDAO pitchDao();
    public abstract ServiceDAO serviceDAO();
    public abstract ManagerCategoryDAO managerCategoryDAO();
    public abstract TimeOrderDetailsDAO timeOrderDetailsDAO();
    public abstract TimeDAO timeDAO();
    public abstract HistoryBuyDAO historyBuyDAO();
    public abstract NotificationDetailsDAO notificationDetailsDAO();
    public abstract NotificationDAO notificationDAO();
}
