package com.example.moigioitiges.view.customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.moigioitiges.R;
import com.example.moigioitiges.adapter.ListProductAdapter;
import com.example.moigioitiges.model.ProductResponse;
import com.example.moigioitiges.remote.ApiService;
import com.example.moigioitiges.remote.ApiUlti;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyFragment extends Fragment {
    Button btnSeatch;
    RelativeLayout relativeLayout;
    RecyclerView rclView;
    ListProductAdapter mAdapter;
    ImageView iv_pic_product;
    List<ProductResponse> productResponseList = new ArrayList<ProductResponse>();
    ListProductAdapter listProductAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_buy, container, false);
        rclView =  view.findViewById(R.id.rv_products);

        btnSeatch = (Button) view.findViewById(R.id.btn_buy_search);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rclView.setLayoutManager(layoutManager);
        rclView.setHasFixedSize(true);
        mAdapter = new ListProductAdapter(productResponseList,getContext());
        rclView.setAdapter(mAdapter);
        btnSeatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new SearchFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        loadListProduct();
        return view;
    }


    private void loadListProduct() {
        ApiService apiService = ApiUlti.getApiService();
        apiService.getListProduct().enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                if(response.isSuccessful()){
                    mAdapter.updateAnswers(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {

            }
        });
        for (ProductResponse productResponse : productResponseList){
            Log.e("product id: ",productResponse.getId()+"");
        }

    }
}