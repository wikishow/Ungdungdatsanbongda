package com.example.duanthuctap.adapter.admin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duanthuctap.MyApplication;
import com.example.duanthuctap.activity.admin.FormActivity;
import com.example.duanthuctap.fragment.adminfragment.SignupFragment;

public class AdapterForm extends FragmentStateAdapter {

    FormActivity formActivity;

    public AdapterForm(FormActivity formActivity) {
        super(formActivity);
        this.formActivity = formActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (MyApplication.CURRENT_TYPE != MyApplication.TYPE_ADMIN) {
            if (position == 1) return new SignupFragment(formActivity);
        }
        return formActivity.loginFragment;
    }

    @Override
    public int getItemCount() {
        if(MyApplication.CURRENT_TYPE == MyApplication.TYPE_ADMIN) return 1;
        return 2;
    }
}
