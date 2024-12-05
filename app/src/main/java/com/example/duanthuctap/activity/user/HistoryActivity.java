package com.example.duanthuctap.activity.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.duanthuctap.R;
import com.example.duanthuctap.adapter.user.HistoryBuyAdapter;
import com.example.duanthuctap.database.MyDatabase;
import com.example.duanthuctap.model.HistoryBuy;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvCount;
    private List<HistoryBuy> list;
    HistoryBuyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        list = MyDatabase.getInstance(this).historyBuyDAO().getALlWithId(UserMainActivity.customer.getId());
        tvCount = findViewById(R.id.tv_count_history_buy);
        tvCount.setText(list.size()+"");

        recyclerView = findViewById(R.id.recy_history_buy);
        adapter = new HistoryBuyAdapter(this,list);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
    }
}