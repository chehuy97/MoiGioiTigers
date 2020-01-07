package com.example.moigioitiges.view.broker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moigioitiges.R;
import com.example.moigioitiges.model.Customer;
import com.example.moigioitiges.remote.ApiService;
import com.example.moigioitiges.remote.ApiUlti;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailFragment extends Fragment {
    TextView tvName,tvIdCard,tvDateOfBirth,tvPhoneNumber,tvDescription, tv_address;
    SharedPreferences sharedPreferences;
    String author;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_detail, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);
        author = sharedPreferences.getString("accessToken", "");
        bindingLayout(view);

        Bundle arguments = getArguments();
        Integer id = arguments.getInt("customerID");
        loadCustomerDetail(id);
        return view;
    }

    private void bindingLayout(View view) {
        tvName = view.findViewById(R.id.tv_name);
        tvIdCard = view.findViewById(R.id.tv_idCard);
        tvDateOfBirth =  view.findViewById(R.id.tv_dateOfBirth);
        tvPhoneNumber = view.findViewById(R.id.tv_phoneNumber);
        tvDescription = view.findViewById(R.id.tv_description);
        tv_address =  view.findViewById(R.id.tv_address);
    }

    private void loadCustomerDetail(int id){
        ApiService apiService = ApiUlti.getApiService();
        apiService.getCustomerById(author,id).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if(response.isSuccessful()){
                    Customer customer = response.body();
                    tvName.setText(customer.getNameCustomer());
                    tvIdCard.setText(customer.getIdCard());
                    tvDateOfBirth.setText(customer.getDayOfBirth());
                    tvPhoneNumber.setText(customer.getPhoneNumber());
                    tvDescription.setText(customer.getDescription());
                    tv_address.setText(customer.getAddress());
                }
                else Log.i("Fail: ","with status"+response.code());
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.e("Fail with status", "onFailure"+ t.getMessage());
            }
        });
    }
}

