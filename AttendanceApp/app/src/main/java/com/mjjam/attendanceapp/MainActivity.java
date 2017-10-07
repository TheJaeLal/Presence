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
import com.mjjam.attendanceapp.data.models.MainWrapper;
import com.mjjam.attendanceapp.data.models.UserLoginResponse;
import com.mjjam.attendanceapp.data.models.UserResponse;
import com.mjjam.attendanceapp.data.repository.UserRepository;
import com.mjjam.attendanceapp.faculty.MarkAttendanceFragment;
import com.mjjam.attendanceapp.helper.DatabaseHelper;
import com.mjjam.attendanceapp.student.StudentAttendanceActivity;
import com.mjjam.attendanceapp.student.StudentHomeFragment;
import com.mjjam.attendanceapp.timetable.TimeTableActivity;


/***
 * MainActivity
 * includes
 * NavigationDrawer.
 */

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, LoginContract.LoginView, LoginContract.MainActivityView {
    NavigationView navigationView = null;
    Toolbar toolbar = null;

    @Override
    public void onNetworkException(Throwable e) {
        super.onNetworkException(e);
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
        if (new SharedPreferenceManager(getApplicationContext()).getCategory() == 1) {
            navigationView.getMenu().findItem(R.id.nav_view_student_attendance).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_home_student).setVisible(false);
            MarkAttendanceFragment fragment = new MarkAttendanceFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (new SharedPreferenceManager(getApplicationContext()).getCategory() == 2) {
            navigationView.getMenu().findItem(R.id.nav_home).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_view_student).setVisible(false);
            StudentHomeFragment fragment = new StudentHomeFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        }
        Config.changeFontInViewGroup(drawer, CustomFontLoader.MONTSERRAT);


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                MarkAttendanceFragment fragment = new MarkAttendanceFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                break;

            case R.id.nav_home_student:
                StudentHomeFragment studentHomeFragment = new StudentHomeFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction1 =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.fragment_container, studentHomeFragment);
                fragmentTransaction1.commit();
                break;
            case R.id.nav_time_table:
                Intent i = new Intent(MainActivity.this, TimeTableActivity.class);
                startActivity(i);
                break;
            case R.id.nav_view_student:
//                Intent subscription = new Intent(MainActivity.this, Classroo.class);
//                startActivity(subscription);
                break;
            case R.id.nav_view_student_attendance:
                Intent studentAttendance = new Intent(MainActivity.this, StudentAttendanceActivity.class);
                startActivity(studentAttendance);
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
    public void onData(MainWrapper mainWrapper) {
        DatabaseHelper db = DatabaseHelper.getDbInstance(getApplicationContext());
        for (int i = 0; i < mainWrapper.data.size(); i++) {
            try {
                db.insertToAttendance(mainWrapper.data.get(i).getTid()
                        , mainWrapper.data.get(i).getTday()
                        , mainWrapper.data.get(i).getTtime());
            } catch (Exception ae) {
            }
        }
    }
}
