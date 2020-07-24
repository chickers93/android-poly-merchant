package com.example.polymerchant;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.TaiKhoanCH;

public class Login extends AppCompatActivity {
    private static final int MAKE_CAMERA_PERMISSION_REQUEST_CODE = 1;
    TextView tv_link_dk, tv_dangnhap;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    TextInputEditText ed_email, ed_password;
    ProgressBar progressBar;
    int login_check;
    CheckBox check_remember;
    String tenThongTinDangNhap = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(Login.this, R.color.white));
        setContentView(R.layout.activity_login);

        //ánh xạ
        tv_link_dk = findViewById(R.id.tv_link_dk);
        tv_dangnhap = findViewById(R.id.tv_dangnhap);
        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        progressBar = findViewById(R.id.spin_kit);
        progressBar.setVisibility(View.INVISIBLE);
        check_remember = findViewById(R.id.checkBox);

        if (!checkPermission(Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.CAMERA}, MAKE_CAMERA_PERMISSION_REQUEST_CODE);
        }

        //sự kiện
        tv_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_check = 0;
                progressBar.setVisibility(View.VISIBLE);

                mData.child("TaiKhoanCH").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //vòng lặp để lấy dữ liệu khi có sự thay đổi trên Firebase
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            //convert ra đối tượng TaiKhoan
                            TaiKhoanCH taiKhoanCH = data.getValue(TaiKhoanCH.class);

                            if (taiKhoanCH.getEmail().equals(ed_email.getText().toString())) {
                                if (taiKhoanCH.getPassword().equals(ed_password.getText().toString())) {
                                    login_check = 1;
                                    Intent i = new Intent(Login.this, Home_Activity.class);
                                    i.putExtra("macuahang", taiKhoanCH.getMacuahang());
                                    startActivity(i);
                                } else {
                                    login_check = 2;
                                }
                            }
                        }

                        if (login_check != 1) {
                            Toast.makeText(Login.this, "Tài khoản trên chưa đăng kí!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        if (login_check == 2) {
                            Toast.makeText(Login.this, "Sai mật khẩu!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        tv_link_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.INVISIBLE);

        //remember Account
        SharedPreferences preferences = getSharedPreferences(tenThongTinDangNhap, MODE_PRIVATE);
        String userName = preferences.getString("UserName", "");
        String passWord = preferences.getString("PassWord", "");
        boolean save = preferences.getBoolean("Save", false);
        if (save) {
            ed_email.setText(userName);
            ed_password.setText(passWord);
        }
        check_remember.setChecked(save);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //ghi nhớ tài khoản
        SharedPreferences preferences = getSharedPreferences(tenThongTinDangNhap, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("UserName", ed_email.getText().toString());
        editor.putString("PassWord", ed_password.getText().toString());
        editor.putBoolean("Save", check_remember.isChecked());
        editor.commit();
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
