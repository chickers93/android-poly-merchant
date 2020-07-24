package com.example.polymerchant;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.polymerchant.Fragment.BaoCao_Fragment;
import com.example.polymerchant.Fragment.DonHang_Fragment;
import com.example.polymerchant.Fragment.TaiKhoan_Fragment;
import com.example.polymerchant.Fragment.ThucDon_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.DonHang;

public class Home_Activity extends AppCompatActivity {
    public static String macuahang;
    List<DonHang> donHangList = new ArrayList<>();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    NotificationManager notifManager;
    Notification noti;
    NotificationCompat.Builder build;
    String channelId = "default_channel_id";
    String channelDescription = "Default Channel";
    Intent intent;
    private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_donhang:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new DonHang_Fragment()).commit();
                    return true;
                case R.id.navigation_baocao:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new BaoCao_Fragment()).commit();
                    return true;
                case R.id.navigation_thucdon:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new ThucDon_Fragment()).commit();
                    return true;

                case R.id.navigation_taikhoan:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new TaiKhoan_Fragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
//        getWindow().setStatusBarColor(ContextCompat.getColor(Home_Activity.this, R.color.white));
        setContentView(R.layout.activity_home_);

        //ánh xạ
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new DonHang_Fragment()).commit();
        }

//        TaiKhoanCHDAO taiKhoanCHDAO = new TaiKhoanCHDAO();
//        taiKhoanCHDAO.putData();

        macuahang = getIntent().getStringExtra("macuahang");
        //Toast.makeText(this, macuahang, Toast.LENGTH_SHORT).show();

        mData.child("DonHang").child("CuaHang").child(Home_Activity.macuahang).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mData.child("DonHang").child("CuaHang").child(Home_Activity.macuahang).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        donHangList.clear();
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            DonHang donHang = data.getValue(DonHang.class);
                            if (donHang.getStatus().equals("Chờ xử lý")) {
                                donHangList.add(donHang);
                            }
                        }

                        if (donHangList.size() > 0) {
                            if (notifManager == null) {
                                notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            }
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                NotificationChannel notificationChannel = notifManager.getNotificationChannel(channelId);
                                if (notificationChannel == null) {
                                    int importance = NotificationManager.IMPORTANCE_HIGH; //Set the importance level
                                    notificationChannel = new NotificationChannel(channelId, channelDescription, importance);
                                    notificationChannel.setLightColor(Color.GREEN); //Set if it is necesssary
                                    notificationChannel.enableVibration(true); //Set if it is necesssary
                                    notifManager.createNotificationChannel(notificationChannel);
                                }

                                build = new NotificationCompat.Builder(Home_Activity.this, channelId);
                                intent = new Intent(Home_Activity.this, Home_Activity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                                // use System.currentTimeMillis() to have a unique ID for the pending intent
                                PendingIntent pIntent = PendingIntent.getActivity(Home_Activity.this, (int) System.currentTimeMillis(), intent, 0);
                                build.setContentTitle("Bạn có đơn hàng mới!")
                                        .setSmallIcon(R.drawable.ic_logo)
                                        .setContentText("Mã đơn hàng: #" + donHangList.get(donHangList.size() - 1).getId())
                                        .setDefaults(Notification.DEFAULT_ALL)
                                        .setAutoCancel(true)
                                        .setContentIntent(pIntent);

                                Random random = new Random();
                                int m = random.nextInt(9999 - 1000) + 1000;
                                noti = build.build();
                                notifManager.notify(m, noti);
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
