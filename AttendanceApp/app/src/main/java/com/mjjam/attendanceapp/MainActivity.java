package com.mjjam.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mjjam.attendanceapp.auth.LoginActivity;
import com.mjjam.attendanceapp.auth.LoginContract;
import com.mjjam.attendanceapp.auth.LoginPresenter;
import com.mjjam.attendanceapp.common.AttendanceApp;
import com.mjjam.attendanceapp.common.BaseActivity;
import com.mjjam.attendanceapp.common.Config;
import com.mjjam.attendanceapp.common.CustomFontLoader;
import com.mjjam.attendanceapp.dashboard.FacultyHomeFragment;
import com.mjjam.attendanceapp.data.local.SharedPreferenceManager;
import com.mjjam.attendanceapp.data.models.UserLoginResponse;
import com.mjjam.attendanceapp.data.models.UserResponse;
import com.mjjam.attendanceapp.data.repository.UserRepository;


/***
 * MainActivity
 * includes
 * NavigationDrawer.
 */

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, LoginContract.LoginView {
    NavigationView navigationView = null;
    Toolbar toolbar = null;

    @Override
    public void onNetworkException(Throwable e) {
        super.onNetworkException(e);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            UserRepository userRepository = ((AttendanceApp) getApplication()).getComponent().userRepository();
            LoginPresenter loginPresenter = new LoginPresenter(userRepository, this);
            loginPresenter.logout(new SharedPreferenceManager(getApplicationContext()).getAccessToken());
            showProgressDialog();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        RepairRequestsListActivity fragment = new RepairRequestsListActivity();
//        android.support.v4.app.FragmentTransaction fragmentTransaction =
//                getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.commit();
        FacultyHomeFragment fragment = new FacultyHomeFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Config.changeFontInViewGroup(drawer, CustomFontLoader.MONTSERRAT_BOLD);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        //How to change elements in the header programatically
        View headerView = navigationView.getHeaderView(0);
        TextView role = (TextView) headerView.findViewById(R.id.email);
        if (new SharedPreferenceManager(getApplicationContext()).getCategory() == 1)
            role.setText(getString(R.string.as_teacher));
        else if (new SharedPreferenceManager(getApplicationContext()).getCategory() == 2)
            role.setText(getString(R.string.as_student));

        navigationView.setNavigationItemSelectedListener(this);
        //if (new SharedPreferenceManager(getApplicationContext()).getCategory() == 3)
            //navigationView.getMenu().findItem(R.id.nav_subscription).setVisible(false);
        //else if (new SharedPreferenceManager(getApplicationContext()).getCategory() == 4)
            //navigationView.getMenu().findItem(R.id.nav_assignment).setVisible(false);
        Config.changeFontInViewGroup(drawer, CustomFontLoader.MONTSERRAT);


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                FacultyHomeFragment fragment = new FacultyHomeFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.nav_time_table:
//                Intent i = new Intent(MainActivity.this, NewsFeedActivity.class);
//                startActivity(i);
                break;
            case R.id.nav_view_student:
//                Intent subscription = new Intent(MainActivity.this, SubscriptionsActivity.class);
//                startActivity(subscription);
                break;
            case R.id.nav_settings:
//                Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
//                startActivity(settings);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onLogin(UserLoginResponse userResponse) {

    }

    @Override
    public void onLogout(UserResponse userResponse) {
        if (userResponse.isStatus()) {
            dismissProgressDialog();
            new SharedPreferenceManager(getApplicationContext()).removeAccessToken();
            new SharedPreferenceManager(getApplicationContext()).removeCategory();
            new SharedPreferenceManager(getApplicationContext()).removeMainPage();
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();


        } else {
            dismissProgressDialog();
            Toast.makeText(MainActivity.this, "Oops something went wrong.", Toast.LENGTH_SHORT).show();
        }
    }

}
