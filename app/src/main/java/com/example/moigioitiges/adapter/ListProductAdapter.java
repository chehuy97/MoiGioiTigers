package com.example.moigioitiges.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moigioitiges.R;
import com.example.moigioitiges.model.Broker;
import com.example.moigioitiges.model.ProductResponse;
import com.example.moigioitiges.view.broker.BrokerActivity;
import com.example.moigioitiges.view.customer.BuyFragment;
import com.example.moigioitiges.view.customer.CustomerActivity;
import com.example.moigioitiges.view.customer.ProductDetailFragment;
import com.example.moigioitiges.view.customer.SearchFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.DataViewHolder> {
    List<ProductResponse> productResponseList;
    Context context;
    public ListProductAdapter(List<ProductResponse> productResponseList, Context context) {
        this.productResponseList = productResponseList;
        this.context = context;
    }


    @NonNull
    @Override
    public ListProductAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product,viewGroup,false);
        return new DataViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListProductAdapter.DataViewHolder dataViewHolder, final int i) {
        String productName = productResponseList.get(i).getProductName();
        int productPrice = productResponseList.get(i).getProductCost();
        String urlAnh = productResponseList.get(i).getLinkAnh();
        dataViewHolder.tv_name.setText(productName);
        dataViewHolder.tv_price.setText(productPrice+"");
        Picasso.with(context)
                .load(urlAnh)
                .into(dataViewHolder.iv_product);
        dataViewHolder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((CustomerActivity)context).getSupportFragmentManager();

                ProductDetailFragment productDetailFragment = new ProductDetailFragment();

                fm.beginTransaction().replace(R.id.frame_layout,
                        productDetailFragment,
                        productDetailFragment.getTag())
                        .addToBackStack(null)
                        .commit();
                Bundle bundle=new Bundle();
                bundle.putInt("productID",productResponseList.get(i).getId());
                productDetailFragment.setArguments(bundle);

            }
        });

    }

    @Override
    public int getItemCount() {
        if(productResponseList!=null) return productResponseList.size();
        return 0;
    }
    public void updateAnswers(List<ProductResponse> responseList) {
        productResponseList = responseList;
        notifyDataSetChanged();
    }
    public static class DataViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout relativeLayout;
        private ImageView iv_product;
        private TextView tv_name;
        private TextView tv_price;
        private Button btn_detail;


        protected Context getContext() {
            return this.getContext();
        }

        public DataViewHolder(View itemView) {
            super(itemView);


            relativeLayout = itemView.findViewById(R.id.rl_buy_frame);
            iv_product = itemView.findViewById(R.id.iv_buy_imageProduct);
            tv_name = itemView.findViewById(R.id.tv_name_buy_Product);
            tv_price = itemView.findViewById(R.id.tv_price_buy_product);
            btn_detail = itemView.findViewById(R.id.btn_buy_Detail);
        }
    }
}
