package com.example.duanthuctap.activity.user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.duanthuctap.MyApplication;
import com.example.duanthuctap.R;
import com.example.duanthuctap.database.MyDatabase;
import com.example.duanthuctap.fragment.adminfragment.DatSanFragment;
import com.example.duanthuctap.fragment.adminfragment.KhachHangFragment;
import com.example.duanthuctap.fragment.userfragment.HistoryFragment;
import com.example.duanthuctap.fragment.userfragment.ThongBaoFragment;
import com.example.duanthuctap.model.Customer;

public class UserMainActivity extends AppCompatActivity {

    public static Customer customer;


    private int CURRENT_FRAGMENT = 0;
    private ImageView imgAvatar;
    private TextView tvMoney,tvName;
    private ConstraintLayout constraintLayout;

    int id_pitch = 1;
    int id_notification = 2;
    int id_history = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        String account = getIntent().getStringExtra("account");
        customer = MyDatabase.getInstance(this).customerDAO().getCustomerWithPhone(account,-1).get(0);

        ImageView btnNap = findViewById(R.id.img_nap_acount_user_activity);
        imgAvatar = findViewById(R.id.img_avatar_user_activity);
        constraintLayout = findViewById(R.id.layout_top_info);
        imgAvatar.setOnClickListener(v->{
            Intent intent = new Intent(UserMainActivity.this,ProfileActivity.class);
            startActivity(intent);
        });
        tvMoney = findViewById(R.id.tv_money_user_activity);
        tvName = findViewById(R.id.tv_name_user_activity);
        setUp();

        btnNap.setOnClickListener(v->{
            Intent intent = new Intent(UserMainActivity.this,NapTienActivity.class);
            intent.putExtra("CUSTOMER_ID",customer.getId());
            startActivityForResult(intent,KhachHangFragment.NAPTIEN_CODE);
        });
        MeowBottomNavigation bottomNavigation = findViewById(R.id.meowNavi);
        bottomNavigation.add(new MeowBottomNavigation.Model(id_pitch,R.drawable.ic_pitch));
        bottomNavigation.add(new MeowBottomNavigation.Model(id_notification,R.drawable.ic_notification));
        bottomNavigation.add(new MeowBottomNavigation.Model(id_history,R.drawable.ic_history));
        bottomNavigation.setOnShowListener(model -> {
        });
        bottomNavigation.show(id_pitch,true);
        bottomNavigation.setOnClickMenuListener(this::onClickItem);

        handle(id_pitch);
    }

    public void setUp(){
        tvName.setText(customer.getName());
        tvMoney.setText(MyApplication.convertMoneyToString(customer.getCoin()));
        if(customer.getImg() == null){
            imgAvatar.setImageResource(R.drawable.ic_avatar);
        }else{
            imgAvatar.setImageBitmap(MyApplication.getBitMapFromByte(customer.getImg()));
        }
    }

    public void handle(int position){
        if(position == 1 && CURRENT_FRAGMENT!=1){
            setUp();
            constraintLayout.setVisibility(View.VISIBLE);
            imgAvatar.setVisibility(View.VISIBLE);
            replaceFragment(new DatSanFragment());
            CURRENT_FRAGMENT = 1;
        }else if(position == 2 && CURRENT_FRAGMENT!=2){
            constraintLayout.setVisibility(View.GONE);
            imgAvatar.setVisibility(View.GONE);
            replaceFragment(new ThongBaoFragment());
            CURRENT_FRAGMENT = 2;
        }else if(position == 3 && CURRENT_FRAGMENT!=3){
            constraintLayout.setVisibility(View.GONE);
            imgAvatar.setVisibility(View.GONE);
            replaceFragment(new HistoryFragment());
            CURRENT_FRAGMENT = 3;
        }
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout_user,fragment);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == KhachHangFragment.NAPTIEN_CODE && resultCode == RESULT_OK){
            customer = MyDatabase.getInstance(this).customerDAO().getCustomerWithID(customer.getId()).get(0);
            setUp();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        customer = MyDatabase.getInstance(this).customerDAO().getCustomerWithID(customer.getId()).get(0);
        setUp();
    }

    private void onClickItem(MeowBottomNavigation.Model model) {
        handle(model.getId());
    }
}