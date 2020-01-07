package com.example.moigioitiges.view.customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moigioitiges.R;
import com.example.moigioitiges.model.LoginResponse;
import com.example.moigioitiges.model.UserLogin;
import com.example.moigioitiges.remote.ApiService;
import com.example.moigioitiges.remote.ApiUlti;
import com.example.moigioitiges.view.broker.AccountFragment;
import com.example.moigioitiges.view.broker.BrokerActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private Button btnSignin;
    private FragmentActivity myContext;
    private int counter =5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        edtUsername = (EditText) view.findViewById(R.id.username_edt);
        edtPassword = (EditText) view.findViewById(R.id.password_edt);
        btnLogin = (Button) view.findViewById(R.id.login_btn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //validate(edtUsername.getText().toString(),edtPassword.getText().toString());
                UserLogin userLogin = new UserLogin();
                userLogin.setEmail(edtUsername.getText().toString());
                userLogin.setPassword(edtPassword.getText().toString());

                login(userLogin);
            }

            private void login(UserLogin userLogin) {
                ApiService apiService = ApiUlti.getApiService();
                apiService.login(userLogin).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.isSuccessful()){
                            LoginResponse loginResponse = response.body();
                            Toast.makeText(getActivity(),loginResponse.getUsername()+" đã  đăng nhập thanh cong",Toast.LENGTH_SHORT).show();


                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("PREF", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("accessToken",response.body().getAccessToken());
//                            editor.putString("email",response.body().getUsername()).apply();
                            editor.apply();
                            Toast.makeText(getActivity(),loginResponse.getUsername()+" đã đăng nhập thành công",Toast.LENGTH_SHORT).show();



                            Intent intent = new Intent(getActivity(), BrokerActivity.class);
                             startActivity(intent);

                        }
                        else{
                            Toast.makeText(getActivity(),"Bạn đã  đăng nhập that bai",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getActivity(),"Bạn đã  đăng nhập that bai",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        return view;

    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Login");
    }

}
