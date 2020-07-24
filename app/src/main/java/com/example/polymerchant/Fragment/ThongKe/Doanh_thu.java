package com.example.polymerchant.Fragment.ThongKe;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.polymerchant.Chi_tiet_Activity.ChitietDonHang_Activity;
import com.example.polymerchant.Home_Activity;
import com.example.polymerchant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import adapter.DonHangAdapter;
import model.DonHang;

public class Doanh_thu extends Fragment {
    TextView tv_from, tv_to, tv_tongtien_giaidoan;
    ListView lv_donhang;
    Button btn_check;
    List<DonHang> donHangList;
    DonHangAdapter donHangAdapter;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    int doanhthu_giaidoan;
    Date date1, date2;

    public Doanh_thu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doanh_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ánh xạ
        tv_from = view.findViewById(R.id.tv_from);
        tv_to = view.findViewById(R.id.tv_to);
        tv_tongtien_giaidoan = view.findViewById(R.id.tv_tongtien_giaidoan);
        lv_donhang = view.findViewById(R.id.lv_donhang);
        btn_check = view.findViewById(R.id.btn_check);

        //sự kiện
        tv_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int ngay = dayOfMonth;
                        int thang = month + 1;
                        int nam = year;

                        String str_ngay = String.valueOf(ngay);
                        String str_thang = String.valueOf(thang);
                        String str_nam = String.valueOf(nam);

                        if (ngay < 10) {
                            str_ngay = "0" + str_ngay;
                        }

                        if (thang < 10) {
                            str_thang = "0" + str_thang;
                        }

                        String time = str_nam + "-" + str_thang + "-" + str_ngay;
                        tv_from.setText(time);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        tv_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int ngay = dayOfMonth;
                        int thang = month + 1;
                        int nam = year;

                        String str_ngay = String.valueOf(ngay);
                        String str_thang = String.valueOf(thang);
                        String str_nam = String.valueOf(nam);

                        if (ngay < 10) {
                            str_ngay = "0" + str_ngay;
                        }

                        if (thang < 10) {
                            str_thang = "0" + str_thang;
                        }

                        String time = str_nam + "-" + str_thang + "-" + str_ngay;
                        tv_to.setText(time);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_from.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Mời nhập ngày bắt đầu!", Toast.LENGTH_SHORT).show();
                } else {
                    if (tv_to.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Mời nhập ngày kết thúc!", Toast.LENGTH_SHORT).show();
                    } else {

                        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            date1 = dateFormat.parse(tv_from.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            date2 = dateFormat.parse(tv_to.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (date1.before(date2)) {
                            donHangList = new ArrayList<>();
                            doanhthu_giaidoan = 0;
                            //lấy hoa đơn theo giai đoạn
                            mData.child("DonHang").child("CuaHang").child(Home_Activity.macuahang).orderByChild("time").startAt(tv_from.getText().toString()).endAt(tv_to.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        DonHang donHang = data.getValue(DonHang.class);
                                        if (donHang.getStatus().equals("Hoàn thành")) {
                                            donHangList.add(0, donHang);
                                            doanhthu_giaidoan += donHang.getTotal();
                                        }
                                    }
                                    donHangAdapter.notifyDataSetChanged();
                                    tv_tongtien_giaidoan.setText(formatter.format(doanhthu_giaidoan));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        } else if (date1.after(date2)) {
                            donHangList = new ArrayList<>();
                            doanhthu_giaidoan = 0;
                            //lấy hoa đơn theo giai đoạn
                            mData.child("DonHang").child("CuaHang").child(Home_Activity.macuahang).orderByChild("time").startAt(tv_to.getText().toString()).endAt(tv_from.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        DonHang donHang = data.getValue(DonHang.class);
                                        if (donHang.getStatus().equals("Hoàn thành")) {
                                            donHangList.add(0, donHang);
                                            doanhthu_giaidoan += donHang.getTotal();
                                        }
                                    }
                                    donHangAdapter.notifyDataSetChanged();
                                    tv_tongtien_giaidoan.setText(formatter.format(doanhthu_giaidoan));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        } else if (date1.equals(date2)) {
                            donHangList = new ArrayList<>();
                            doanhthu_giaidoan = 0;
                            mData.child("DonHang").child("CuaHang").child(Home_Activity.macuahang).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        DonHang donHang = data.getValue(DonHang.class);
                                        if (donHang.getTime().substring(0, 10).equals(tv_from.getText().toString())) {
                                            if (donHang.getStatus().equals("Hoàn thành")) {
                                                donHangList.add(0, donHang);
                                                doanhthu_giaidoan += donHang.getTotal();
                                            }
                                        }
                                    }
                                    donHangAdapter.notifyDataSetChanged();
                                    tv_tongtien_giaidoan.setText(formatter.format(doanhthu_giaidoan));

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        donHangAdapter = new DonHangAdapter(getActivity(), R.layout.item_donhang, donHangList);
                        lv_donhang.setAdapter(donHangAdapter);
                    }
                }
            }
        });

        //chi tiết đơn hàng
        lv_donhang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChitietDonHang_Activity.class);
                intent.putExtra("madonhang", donHangList.get(position).getId());
                intent.putExtra("macuahang", donHangList.get(position).getMacuahang());
                intent.putExtra("trangthai", donHangList.get(position).getStatus());
                intent.putExtra("uid", donHangList.get(position).getUid());
                startActivity(intent);

            }
        });

    }
}
