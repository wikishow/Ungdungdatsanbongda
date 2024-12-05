package com.example.duanthuctap.activity.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.duanthuctap.MyApplication;
import com.example.duanthuctap.R;
import com.example.duanthuctap.database.MyDatabase;
import com.example.duanthuctap.model.Manager;
import com.example.duanthuctap.model.Order;
import com.example.duanthuctap.model.OrderDetails;
import com.example.duanthuctap.model.ServiceBall;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SalaryDetailsActivityStaff extends AppCompatActivity {

    private List<Order> list;
    private RecyclerView recyclerView;
    private TextView tvDate,tvCount,tvTotalRecei;
    private TextView tvTitle;
    SalaryStaffAdapter adapter;
    private Button btnPre,btnNext;
    int year,month;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_details_staff);

        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH)+1;
        year = calendar.get(Calendar.YEAR);

        initView();

        id = getIntent().getIntExtra("ID_MANAGER",-1);
        if(id == -1){
            id = MyDatabase.getInstance(this).managerDAO().getManagerWithPhone(MainActivity.ACCOUNT,-1).get(0).getId();
        }else{
            Manager manager = MyDatabase.getInstance(this).managerDAO().getManagerWithID(id).get(0);
            month = getIntent().getIntExtra("MONTH",-1);
            year = getIntent().getIntExtra("YEAR",-1);
            tvTitle.setText(manager.getName());
        }
        list = MyDatabase.getInstance(this).orderDAO().getOrderWithManagerId(id,"%-"+month+"-"+year,MyApplication.HUY_STATUS);

        adapter = new SalaryStaffAdapter(this,list);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        setUp();
    }

    public void setUp(){
        tvDate.setText(month+"-"+year);
        tvTotalRecei.setText(MyApplication.convertMoneyToString(getTotal())+" VNĐ");
        tvCount.setText(list.size()+"");
    }

    public int getTotal(){
        int total = 0;
        for(int i =0;i<list.size();i++){
            total += (list.get(i).getTotal()/100)*3;
        }
        return total;
    }

    public void initView(){
        tvDate = findViewById(R.id.tv_date_salary_details_staff);
        tvCount = findViewById(R.id.tv_count_salary_details_staff);
        tvTotalRecei = findViewById(R.id.tv_money_recei_salary_details_staff);
        recyclerView = findViewById(R.id.recy_salary_details_staff);
        btnPre = findViewById(R.id.btn_pre_salary_details_staff);
        btnNext = findViewById(R.id.btn_next_salary_details_staff);
        tvTitle = findViewById(R.id.tv_title_activity_staff);

        btnPre.setOnClickListener(v->{
            if(--month<=0){
                year--;
                month =12;
            }
            list = MyDatabase.getInstance(this).orderDAO().getOrderWithManagerId(id,"%-"+month+"-"+year,MyApplication.HUY_STATUS);
            setUp();
            adapter.setData(list);
        });
        btnNext.setOnClickListener(v->{
            if(++month>=13){
                year++;
                month = 1;
            }
            list = MyDatabase.getInstance(this).orderDAO().getOrderWithManagerId(id,"%-"+month+"-"+year,MyApplication.HUY_STATUS);
            setUp();
            adapter.setData(list);
        });
    }

    private class SalaryStaffAdapter extends RecyclerView.Adapter<SalaryStaffAdapter.ViewHolder>{

        private Context context;
        private List<Order> orders;

        public SalaryStaffAdapter(Context context, List<Order> list) {
            this.context = context;
            this.orders = list;
        }

        @NonNull
        @Override
        public SalaryStaffAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_salary_staff,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull SalaryStaffAdapter.ViewHolder holder, int position) {
            holder.tvId.setText("Phiếu "+orders.get(position).getId());
            holder.tvMoneyTotal.setText(MyApplication.convertMoneyToString(list.get(position).getTotal())+" VNĐ");
            holder.tvMoneyRecei.setText(MyApplication.convertMoneyToString((list.get(position).getTotal()/100)*3)+" VNĐ");
            holder.tvDate.setText(list.get(position).getDateCreate());
            holder.tvCustomer.setText(MyDatabase.getInstance(context).customerDAO()
                    .getCustomerWithID(list.get(position).getCustomerId()).get(0).getName());
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        public void setData(List<Order> list){
            this.orders = list;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvId,tvDate,tvCustomer,tvMoneyTotal,tvMoneyRecei;
            private Button btnDetails;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                tvId = itemView.findViewById(R.id.tv_id_item_salary_staff);
                tvCustomer = itemView.findViewById(R.id.tv_ten_khach_hang_item_salary_staff);
                tvMoneyRecei = itemView.findViewById(R.id.tv_recei_money_item_salary_staff);
                tvMoneyTotal = itemView.findViewById(R.id.tv_total_item_salary_staff);
                tvDate = itemView.findViewById(R.id.tv_date_item_salary_staff);
                btnDetails = itemView.findViewById(R.id.btndetails_item_salary_staff);

                btnDetails.setOnClickListener(v->{
                    Intent intent = new Intent(context,DatSanChiTietActivity.class);
                    intent.putExtra("ORDER",orders.get(getAdapterPosition()));
                    intent.putExtra("IS_SHOW",true);
                    Bundle bundle = new Bundle();

                    List<ServiceBall> serviceBalls = new ArrayList<>();
                    List<Integer> numbers = new ArrayList<>();

                    List<OrderDetails> list = MyDatabase.getInstance(context).orderDetailsDAO().getOrderDetailsByOrderId(orders.get(getAdapterPosition()).getId());
                    for(int i = 0;i<list.size();i++){
                        int serviceId = list.get(i).getServiceId();
                        int number = list.get(i).getSoLuong();
                        serviceBalls.add(MyDatabase.getInstance(context).serviceDAO().getServiceWithId(serviceId).get(0));
                        numbers.add(number);
                    }

                    bundle.putSerializable("LIST_SERVICE", (Serializable) serviceBalls);
                    bundle.putSerializable("LIST_NUMBER", (Serializable) numbers);

                    intent.putExtra("bundle",bundle);
                    context.startActivity(intent);
                });
            }
        }
    }
}