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
import com.example.moigioitiges.model.Owner;

import java.util.List;

public class ListOwnerAdapter extends RecyclerView.Adapter<ListOwnerAdapter.DataViewHolder> {
    private List<Owner> owners;
    private Context context;
    public ListOwnerAdapter(List<Owner> owners, Context context) {
        this.owners = owners;
        this.context = context;
    }


    @NonNull
    @Override
    public ListOwnerAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_owner,viewGroup,false);
        return new DataViewHolder(itemview);
    }



    @Override
    public void onBindViewHolder(@NonNull final ListOwnerAdapter.DataViewHolder dataViewHolder, final int i) {
        String name = owners.get(i).getOwnerName();
        String phoneNumber = owners.get(i).getPhoneNumber();
        String idCard = owners.get(i).getIdCard();
        String address = owners.get(i).getAddress();
        dataViewHolder.tv_name.setText(name);
        dataViewHolder.tv_cmnd.setText(idCard);
        dataViewHolder.tv_phoneNumber.setText(phoneNumber);
        dataViewHolder.tv_address.setText(address);
    }

    @Override
    public int getItemCount() {
        if(owners!=null) return owners.size();
        return 0;
    }
    public void updateAnswers(List<Owner> responseList) {
        owners = responseList;
        notifyDataSetChanged();
    }
    public static class DataViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_phoneNumber, tv_cmnd, tv_address, tv_name;

        private TextView tv_detail;


        private Context getContext() {
            return this.getContext();
        }

        public DataViewHolder(View itemView) {
            super(itemView);

            tv_cmnd = itemView.findViewById(R.id.tv_cmnd);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_phoneNumber = itemView.findViewById(R.id.tv_phoneNumber);
            tv_address = itemView.findViewById(R.id.tv_address);
        }
    }
}
