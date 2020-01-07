package com.example.moigioitiges.view.customer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moigioitiges.R;
import com.example.moigioitiges.adapter.ListProductAdapter;
import com.example.moigioitiges.model.ProductResponse;
import com.example.moigioitiges.remote.ApiService;
import com.example.moigioitiges.remote.ApiUlti;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResult extends Fragment {
    Button btnBack;
    RelativeLayout relativeLayout;
    RecyclerView rclView;
    ListProductAdapter mAdapter;
    List<ProductResponse> productResponseList = new ArrayList<ProductResponse>();
    ListProductAdapter listProductAdapter;
    TextView tvZip;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        rclView =  view.findViewById(R.id.rv_products_searchresult);
        btnBack = (Button) view.findViewById(R.id.btn_searchresult_back);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rclView.setLayoutManager(layoutManager);
        rclView.setHasFixedSize(true);
        mAdapter = new ListProductAdapter(productResponseList,getContext());
        rclView.setAdapter(mAdapter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new BuyFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        Bundle arguments = getArguments();
        String zipCode = arguments.getString("ZipCode");
        int typecatalog = arguments.getInt("TypeCatalog");
        loadListProductResult(typecatalog,zipCode);
        return view;
    }


    private void loadListProductResult(int type,String zip) {
        ApiService apiService = ApiUlti.getApiService();
        apiService.getProductResult(1,zip).enqueue(new Callback<List<ProductResponse>>() {
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

    }
}