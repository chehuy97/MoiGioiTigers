package com.example.moigioitiges.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moigioitiges.R;
import com.example.moigioitiges.model.Customer;
import com.example.moigioitiges.view.broker.BrokerActivity;
import com.example.moigioitiges.view.broker.CustomerDetailFragment;
import com.example.moigioitiges.view.customer.CustomerActivity;

import java.util.List;

public class ListCustomerAdapter extends RecyclerView.Adapter<ListCustomerAdapter.DataViewHolder> {
    private List<Customer> customers;
    private Context context;
    public ListCustomerAdapter(List<Customer> customers, Context context) {
        this.customers = customers;
        this.context = context;
    }


    @NonNull
    @Override
    public ListCustomerAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_customer,viewGroup,false);
        return new DataViewHolder(itemview);
    }



    @Override
    public void onBindViewHolder(@NonNull final ListCustomerAdapter.DataViewHolder dataViewHolder, final int i) {
        String name = customers.get(i).getNameCustomer();
        String phoneNumber = customers.get(i).getPhoneNumber();
        String idCard = customers.get(i).getIdCard();
        dataViewHolder.tv_name.setText(name);
        dataViewHolder.tv_cmnd.setText(idCard);
        dataViewHolder.tv_phoneNumber.setText(phoneNumber);
        dataViewHolder.tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((BrokerActivity)context).getSupportFragmentManager();

                CustomerDetailFragment customerDetailFragment = new CustomerDetailFragment();

                fm.beginTransaction().replace(R.id.frame_layout,
                        customerDetailFragment,
                        customerDetailFragment.getTag())
                        .addToBackStack(null)
                        .commit();
                Bundle bundle=new Bundle();
                bundle.putInt("customerID",customers.get(i).getId());
                customerDetailFragment.setArguments(bundle);

            }
        });

    }

    @Override
    public int getItemCount() {
        if(customers!=null) return customers.size();
        return 0;
    }
    public void updateAnswers(List<Customer> responseList) {
        customers = responseList;
        notifyDataSetChanged();
    }
    public static class DataViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_phoneNumber;
        private TextView tv_cmnd;
        private TextView tv_name;

        private TextView tv_detail;


        private Context getContext() {
            return this.getContext();
        }

        public DataViewHolder(View itemView) {
            super(itemView);

            tv_cmnd = itemView.findViewById(R.id.tv_cmnd);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_phoneNumber = itemView.findViewById(R.id.tv_phoneNumber);
            tv_detail = itemView.findViewById(R.id.tv_detail);
        }
    }
}
