package com.example.moigioitiges.view.broker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moigioitiges.R;
import com.example.moigioitiges.adapter.ListCustomerAdapter;
import com.example.moigioitiges.adapter.ListProductAdapter;
import com.example.moigioitiges.model.Broker;
import com.example.moigioitiges.model.Customer;
import com.example.moigioitiges.model.ProductResponse;
import com.example.moigioitiges.remote.ApiService;
import com.example.moigioitiges.remote.ApiUlti;
import com.example.moigioitiges.view.customer.SearchFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCustomerFragment extends Fragment {
    RecyclerView rclView;
    ListCustomerAdapter mAdapter;
    List<Customer> customers = new ArrayList<Customer>();
    SharedPreferences sharedPreferences;
    String author;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        sharedPreferences = this.getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);
        author = sharedPreferences.getString("accessToken", "");
        View view = inflater.inflate(R.layout.fragment_list_customer, container, false);
        rclView =  view.findViewById(R.id.rv_listCustomer);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rclView.setLayoutManager(layoutManager);
        rclView.setHasFixedSize(true);
        mAdapter = new ListCustomerAdapter(customers,getContext());
        rclView.setAdapter(mAdapter);
        loadListCustomer();
        return view;
    }


    private void loadListCustomer() {
        ApiService apiService = ApiUlti.getApiService();
        apiService.getListCustomer(author).enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if(response.isSuccessful()){
                    mAdapter.updateAnswers(response.body());
                }
                else Log.i("Fail with status: ",response.code()+"");
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Log.e("Fail with status", "onFailure"+ t.getMessage());
            }
        });
    }

}
