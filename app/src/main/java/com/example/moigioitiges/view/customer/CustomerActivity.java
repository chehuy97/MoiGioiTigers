package com.example.moigioitiges.view.customer;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.moigioitiges.R;


public class CustomerActivity extends AppCompatActivity{
    public static final String TAG = CustomerActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        NavigationView navigationView = findViewById(R.id.nav_view);

        fm = getSupportFragmentManager();
        //default open fragment buy for custommer
        FragmentTransaction ft_default = fm.beginTransaction();
        ft_default.add(R.id.frame_layout, new LoginFragment());
        ft_default.commit();
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        FragmentTransaction ft_add = fm.beginTransaction();
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()){
                            case R.id.nav_account:
                                ft_add.replace(R.id.frame_layout, new LoginFragment());
                                ft_add.commit();
                                Log.e(TAG, "ic_account tapped");
                                break;
                            case R.id.nav_buy:
                                ft_add.replace(R.id.frame_layout, new BuyFragment());
                                ft_add.commit();
                                Log.e(TAG, "ic_buy is tapped");
                                break;
                            case R.id.nav_sell:
                                /*ft_add.replace(R.id.frame_layout, new SellFragment());
                                ft_add.commit();
                                Log.e(TAG, "ic_sell tapped");*/
                                ft_add.replace(R.id.frame_layout, new SellFragment());
                                ft_add.commit();
                                Log.e(TAG, "ic_buy is tapped");
                                break;

                            /*case R.id.nav_help:

                                break;
                            case R.id.nav_info:*/

                             default:break;
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
}
