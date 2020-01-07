package com.example.moigioitiges.view.broker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.example.moigioitiges.R;
import com.example.moigioitiges.model.LoginResponse;
import com.example.moigioitiges.model.UserLogin;
import com.example.moigioitiges.remote.ApiService;
import com.example.moigioitiges.remote.ApiUlti;
import com.example.moigioitiges.view.customer.CustomerActivity;
import com.example.moigioitiges.view.customer.LoginFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrokerActivity extends AppCompatActivity {

    public static final String TAG = CustomerActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;
    private FragmentManager fm;
    // private TextView tv_header_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broker);

        //tv_header_email = findViewById(R.id.tv_header_email);
        SharedPreferences sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email","Chua dang nhap");
        //tv_header_email.setText(email);
        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        final ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        NavigationView navigationView = findViewById(R.id.nav_view_broker);

        fm = getSupportFragmentManager();
        //default fragment is account fragment
        FragmentTransaction ft_broker = fm.beginTransaction();
        ft_broker.add(R.id.frame_layout, new AccountFragment());
        ft_broker.commit();
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        FragmentTransaction ft_add = fm.beginTransaction();
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()){
                            case R.id.nav_account:
                                ft_add.replace(R.id.frame_layout, new AccountFragment());
                                ft_add.commit();
                                Log.e(TAG, "ic_account tapped");
                                break;
                            case R.id.nav_add_post:
                                ft_add.replace(R.id.frame_layout, new AddPostFragment());
                                ft_add.commit();
                                Log.e(TAG, "ic_account tapped");
                                break;
                            case R.id.nav_approved_post:
                                ft_add.replace(R.id.frame_layout, new ApprovedPostFragment());
                                ft_add.commit();
                                Log.e(TAG, "ic_account tapped");
                                break;
                            case R.id.nav_pendding_post:
                                ft_add.replace(R.id.frame_layout, new PendingPostFragment());
                                ft_add.commit();
                                Log.e(TAG, "ic_account tapped");
                                break;
                            case R.id.nav_payed_post:
                                ft_add.replace(R.id.frame_layout, new PayedPostFragment());
                                ft_add.commit();
                                Log.e(TAG, "ic_account tapped");
                                break;
                            case R.id.nav_customer:
                                ft_add.replace(R.id.frame_layout, new ListCustomerFragment());
                                ft_add.commit();
                                Log.e(TAG, "ic_account tapped");
                                break;
                            case R.id.nav_owners:
                                ft_add.replace(R.id.frame_layout, new ListOwnerFragment());
                                ft_add.commit();
                                Log.e(TAG, "ic_account tapped");
                                break;
                            case R.id.nav_log_out:
                                logOut();
                                Log.e(TAG, "ic_account tapped");
                                break;
                        }
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private LoginResponse login(String email, String password){
        ApiService apiService = ApiUlti.getApiService();
        LoginResponse loginResponse = null;
        UserLogin userLogin = new UserLogin();
        userLogin.setPassword(password);
        userLogin.setEmail(email);
        apiService.login(userLogin).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                //loginResponse = new LoginResponse();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
        return loginResponse;
    }
    private void logOut(){
        Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
        startActivity(intent);
        SharedPreferences sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("accessToken","");
        editor.apply();
    }
}
