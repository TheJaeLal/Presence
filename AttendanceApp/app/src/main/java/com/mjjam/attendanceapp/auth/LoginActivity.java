package com.mjjam.attendanceapp.auth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mjjam.attendanceapp.MainActivity;
import com.mjjam.attendanceapp.R;
import com.mjjam.attendanceapp.common.AttendanceApp;
import com.mjjam.attendanceapp.common.BaseActivity;
import com.mjjam.attendanceapp.data.local.SharedPreferenceManager;
import com.mjjam.attendanceapp.data.models.UserLoginResponse;
import com.mjjam.attendanceapp.data.models.UserResponse;
import com.mjjam.attendanceapp.data.repository.UserRepository;
import com.mjjam.attendanceapp.helper.DatabaseHelper;
import com.mjjam.attendanceapp.widgets.BaseButton;
import com.mjjam.attendanceapp.widgets.BaseEditText;
import com.mjjam.attendanceapp.widgets.BaseRadioButton;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class LoginActivity extends BaseActivity implements LoginContract.LoginView {

    BaseEditText etUsername, etPassword;
    BaseButton bLogin;
    LoginPresenter loginPresenter;
    RadioGroup rgType;
    BaseRadioButton rType;

    @Override
    public void onNetworkException(Throwable e) {
        super.onNetworkException(e);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermission();
        if (new SharedPreferenceManager(getApplicationContext()).getMainPage() != 0) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        setContentView(R.layout.activity_login);
        initViews();
        UserRepository userRepository = ((AttendanceApp) getApplication()).getComponent().userRepository();
        loginPresenter = new LoginPresenter(userRepository, this);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean status = validate();
                if (status) {
                    showProgressDialog();
                    loginPresenter.login(etUsername.getText().toString(), etPassword.getText().toString(), checkType());
                }
            }
        });

    }

    private int checkType() {
        int selectedId = rgType.getCheckedRadioButtonId();
        rType = (BaseRadioButton) findViewById(selectedId);
        if (rType.getText().toString().equals(getString(R.string.as_teacher)))
            return 1;
        return 2;
    }

    private void initViews() {
        etUsername = (BaseEditText) findViewById(R.id.etUsername);
        etPassword = (BaseEditText) findViewById(R.id.etPassword);
        rgType = (RadioGroup) findViewById(R.id.rgType);
        bLogin = (BaseButton) findViewById(R.id.bLogin);
    }

    @Override
    public void onLogin(UserLoginResponse userLoginResponse) {
        dismissProgressDialog();
        if (userLoginResponse.isSuccess()) {
            new SharedPreferenceManager(getApplicationContext()).saveMainPage(1);
            new SharedPreferenceManager(getApplicationContext()).saveAccessToken(userLoginResponse.getToken());
            new SharedPreferenceManager(getApplicationContext()).saveCategory(userLoginResponse.getType());
            new SharedPreferenceManager(getApplicationContext()).saveCourseList(userLoginResponse.getCourses());
            //new SharedPreferenceManager(get)
            if (userLoginResponse.getType() == 1) {
                new SharedPreferenceManager(getApplicationContext()).saveFirstName(userLoginResponse.getFirstname());
                new SharedPreferenceManager(getApplicationContext()).saveLastName(userLoginResponse.getLastname());
                new SharedPreferenceManager(getApplicationContext()).saveUserName(userLoginResponse.getUsername());
                //new SharedPreferenceManager(getApplicationContext()).save
//                for (int i = 0; i < userLoginResponse.getData().size(); i++) {
//                    DatabaseHelper db = DatabaseHelper.getDbInstance(getApplicationContext());
//                    db.insertToAttendance(userLoginResponse.getData().get(i).getTid(),
//                            userLoginResponse.getData().get(i).getDay()
//                            , userLoginResponse.getData().get(i).getStart_time()
//                            ,userLoginResponse.getData().get(i).getEnd_time()
//                            , userLoginResponse.getData().get(i).getCourse());
//                }

            } else {
                new SharedPreferenceManager(getApplicationContext()).saveFirstName(userLoginResponse.getFirstname());
                new SharedPreferenceManager(getApplicationContext()).saveLastName(userLoginResponse.getLastname());
                new SharedPreferenceManager(getApplicationContext()).saveRollNo(userLoginResponse.getRollno());
                new SharedPreferenceManager(getApplicationContext()).saveUserName(userLoginResponse.getUsername());
            }



            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, userLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }


    private boolean validate() {
        if (etUsername.getText().toString().isEmpty()) {
            etUsername.setError("Username cannot be empty");
            etUsername.setFocusable(true);
            return false;
        } else if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Password cannot be empty");
            etPassword.setFocusable(true);
            return false;
        }
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, GET_ACCOUNTS}, 1001);

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1001:
                if (grantResults.length > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                            showMessageOKCancel("You need to allow access all the permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, GET_ACCOUNTS},
                                                        1001);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }
                break;
        }
    }

}
