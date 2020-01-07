package com.example.moigioitiges.view.broker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moigioitiges.R;
import com.example.moigioitiges.adapter.ListProductAdapter;
import com.example.moigioitiges.adapter.ListProductToBrokerAdapter;
import com.example.moigioitiges.model.Broker;
import com.example.moigioitiges.model.ProductResponse;
import com.example.moigioitiges.remote.ApiService;
import com.example.moigioitiges.remote.ApiUlti;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingPostFragment extends Fragment {
    RelativeLayout relativeLayout;
    RecyclerView rclView;
    ListProductToBrokerAdapter mAdapter;
    ImageView iv_pic_product;
    List<ProductResponse> productResponseList = new ArrayList<ProductResponse>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_pending_post, container, false);
        rclView =  view.findViewById(R.id.rv_listPostPending);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rclView.setLayoutManager(layoutManager);
        rclView.setHasFixedSize(true);
        mAdapter = new ListProductToBrokerAdapter(productResponseList,getContext());
        rclView.setAdapter(mAdapter);
        loadListProduct();
        return view;
    }


    private void loadListProduct() {
        ApiService apiService = ApiUlti.getApiService();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("PREF", Context.MODE_PRIVATE);
        String author = sharedPreferences.getString("accessToken","");
        apiService.getListPostPendingOfBroker(author).enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                if(response.isSuccessful()){
                    mAdapter.updateAnswers(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                Log.e("Fail with status", "onFailure"+ t.getMessage());
            }
        });
    }
}
