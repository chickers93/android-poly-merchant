package com.example.polymerchant.Chi_tiet_Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.polymerchant.Home_Activity;
import com.example.polymerchant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import adapter.GioHangAdapter;
import model.CuaHang;
import model.DonHang;
import model.GioHang;

public class ChitietDonHang_Activity extends AppCompatActivity {
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;
    String madonhang, macuahang, trangthai, uid;
    TextView tv_back, tv_theloai, tv_diachi, tv_madonhang, tv_trangthai, tv_name, tv_address, tv_phonenumber, tv_tongtien;
    ListView lv_donhang;
    ImageView img_call;
    List<GioHang> gioHangList = new ArrayList<>();
    GioHangAdapter gioHangAdapter;
    Button btn_huydonhang, btn_xacnhandonhang, btn_hoanthanhdonhang, btn_back;
    LinearLayout view1, view2, view3;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    DecimalFormat formatter = new DecimalFormat("###,###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chitiet_don_hang);

        addcontrols();
        addevents();
    }

    private void addcontrols() {
        madonhang = getIntent().getStringExtra("madonhang");
        macuahang = getIntent().getStringExtra("macuahang");
        trangthai = getIntent().getStringExtra("trangthai");
        uid = getIntent().getStringExtra("uid");
        //Toast.makeText(this, madonhang, Toast.LENGTH_SHORT).show();
        tv_back = findViewById(R.id.tv_back);
        tv_theloai = findViewById(R.id.tv_theloai);
        tv_diachi = findViewById(R.id.tv_diachi);
        tv_madonhang = findViewById(R.id.tv_madonhang);
        tv_trangthai = findViewById(R.id.tv_trangthai);
        tv_name = findViewById(R.id.tv_name);
        tv_address = findViewById(R.id.tv_address);
        tv_phonenumber = findViewById(R.id.tv_phonenumber);
        lv_donhang = findViewById(R.id.lv_donhang);
        tv_tongtien = findViewById(R.id.tv_tongtien);
        btn_huydonhang = findViewById(R.id.btn_huydonhang);
        img_call = findViewById(R.id.img_call);
        btn_xacnhandonhang = findViewById(R.id.btn_xacnhandonhang);
        btn_hoanthanhdonhang = findViewById(R.id.btn_hoanthanhdonhang);
        btn_back = findViewById(R.id.btn_back);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        view3.setVisibility(View.INVISIBLE);

        if (trangthai.equals("Chờ xử lý")) {
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.INVISIBLE);
        } else if (trangthai.equals("Đã xác nhận")) {
            view1.setVisibility(View.INVISIBLE);
            view2.setVisibility(View.VISIBLE);
        } else if (trangthai.equals("Hoàn thành") || trangthai.equals("Đã hủy")) {
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            view3.setVisibility(View.VISIBLE);
        }

        if (macuahang != null && madonhang != null && trangthai != null) {
            mData.child("CuaHang").child(macuahang).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    CuaHang cuaHang = dataSnapshot.getValue(CuaHang.class);
                    tv_theloai.setText(convertString(cuaHang.getMatheloai()));
                    tv_diachi.setText(cuaHang.getDiachi());
                    tv_madonhang.setText("Mã đơn hàng: " + "#" + madonhang);
                    tv_trangthai.setText(trangthai);
                    if (trangthai.equals("Chờ xử lý") || trangthai.equals("Đã hủy")) {
                        tv_trangthai.setTextColor(getResources().getColor(R.color.profilePrimaryDark));
                    } else if (trangthai.equals("Đã xác nhận") || trangthai.equals("Hoàn thành")) {
                        tv_trangthai.setTextColor(getResources().getColor(R.color.settings));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        mData.child("DonHang").child("CuaHang").child(Home_Activity.macuahang).child(madonhang).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DonHang donHang = dataSnapshot.getValue(DonHang.class);
                try {
                    tv_name.setText(donHang.getName());
                    tv_address.setText(donHang.getAddress());
                    tv_phonenumber.setText(donHang.getPhonenumber().substring(3));
                    tv_tongtien.setText(formatter.format(donHang.getTotal()) + " VND");
                } catch (Exception e) {

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void addevents() {
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mData.child("DonHang").child("CuaHang").child(Home_Activity.macuahang).child(madonhang).child("gioHangList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gioHangList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    GioHang gioHang = data.getValue(GioHang.class);
                    gioHangList.add(gioHang);
                }

                gioHangAdapter = new GioHangAdapter(ChitietDonHang_Activity.this, R.layout.item_chitietgiohang, gioHangList);
                lv_donhang.setAdapter(gioHangAdapter);
                gioHangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //xác nhận đơn hàng
        btn_xacnhandonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.child("DonHang").child("CuaHang").child(Home_Activity.macuahang).child(madonhang).child("status").setValue("Đã xác nhận");
                mData.child("DonHang").child("User").child(uid).child(madonhang).child("status").setValue("Đã xác nhận");

                Toast.makeText(ChitietDonHang_Activity.this, "Đã xác nhận đơn hàng!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        //hủy đơn hàng
        btn_huydonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder altdial = new AlertDialog.Builder(ChitietDonHang_Activity.this);
                altdial.setTitle("Thông Báo");
                altdial.setMessage("Xác nhận từ chối đơn hàng. Bạn có muốn tiếp tục hay không?")
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mData.child("DonHang").child("CuaHang").child(Home_Activity.macuahang).child(madonhang).child("status").setValue("Đã hủy");
                                mData.child("DonHang").child("User").child(uid).child(madonhang).child("status").setValue("Đã hủy");

                                Toast.makeText(ChitietDonHang_Activity.this, "Bạn đã hủy đơn hàng!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                AlertDialog alert = altdial.create();
                alert.show();
            }
        });

        //hoàn thành đơn hàng
        btn_hoanthanhdonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.child("DonHang").child("CuaHang").child(Home_Activity.macuahang).child(madonhang).child("status").setValue("Hoàn thành");
                mData.child("DonHang").child("User").child(uid).child(madonhang).child("status").setValue("Hoàn thành");

                Toast.makeText(ChitietDonHang_Activity.this, "Đơn hàng đã giao thành công!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        //quay về
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //gọi khách hàng
        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder altdial = new AlertDialog.Builder(ChitietDonHang_Activity.this);
                altdial.setTitle("Liên Hệ");
                altdial.setMessage("Liên hệ đến khách hàng " + tv_name.getText().toString() + " với số điện thoại: " + tv_phonenumber.getText().toString() + ". Bạn có muốn tiếp tục?")
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String phoneNumber = tv_phonenumber.getText().toString();
                                if (!TextUtils.isEmpty(phoneNumber)) {
                                    if (checkPermission(Manifest.permission.CALL_PHONE)) {
                                        String dial = "tel:" + phoneNumber;
                                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                                    } else {
                                        //Toast.makeText(ChitietDonHang_Activity.this, "Permission call phone", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(ChitietDonHang_Activity.this, "PhoneNumber is empty!", Toast.LENGTH_SHORT).show();
                                }

                                if (checkPermission(Manifest.permission.CALL_PHONE)) {
                                    img_call.setEnabled(true);
                                } else {
                                    img_call.setEnabled(false);
                                    ActivityCompat.requestPermissions(ChitietDonHang_Activity.this, new String[]{Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
                                    img_call.setEnabled(true);
                                }
                            }
                        });
                AlertDialog alert = altdial.create();
                alert.show();
            }
        });


    }

    private String convertString(String matheloai) {
        String theloai = null;
        if (matheloai.equals("TL01")) {
            theloai = "Đồ ăn";
        } else if (matheloai.equals("TL02")) {
            theloai = "Đồ uống";
        } else if (matheloai.equals("TL03")) {
            theloai = "Đồ chay";
        } else if (matheloai.equals("TL04")) {
            theloai = "Thức ăn nhanh";
        } else if (matheloai.equals("TL05")) {
            theloai = "Kem";
        } else if (matheloai.equals("TL06")) {
            theloai = "Ăn vặt";
        } else if (matheloai.equals("TL07")) {
            theloai = "Bánh ngọt";
        }

        return theloai;
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

}
