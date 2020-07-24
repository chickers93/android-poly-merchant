package com.example.polymerchant.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.polymerchant.Home_Activity;
import com.example.polymerchant.Login;
import com.example.polymerchant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import model.CuaHang;

public class TaiKhoan_Fragment extends Fragment {
    ImageView img_cuahang;
    TextView tv_tenquanan, tv_diachi;
    Button btn_sign_out;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.taikhoan_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        //ánh xạ
        img_cuahang = view.findViewById(R.id.img_cuahang);
        tv_tenquanan = view.findViewById(R.id.tv_tenquanan);
        tv_diachi = view.findViewById(R.id.tv_diachi);
        btn_sign_out = view.findViewById(R.id.btn_sign_out);

        //get info
        mData.child("CuaHang").child(Home_Activity.macuahang).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CuaHang cuaHang = dataSnapshot.getValue(CuaHang.class);
                try {
                    Picasso.with(getActivity()).load(cuaHang.getHinhanh()).into(img_cuahang);
                } catch (Exception e) {

                }
                tv_tenquanan.setText(cuaHang.getTencuahang());
                tv_diachi.setText(cuaHang.getDiachi());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //đăng xuất
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });


    }
}
