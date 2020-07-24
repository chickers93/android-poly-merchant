package com.example.polymerchant.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.polymerchant.Home_Activity;
import com.example.polymerchant.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import adapter.ViewPagerAdapter;
import model.CuaHang;

public class DonHang_Fragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView tv_tenquanan, tv_diachi;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.donhang_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ánh xạ
        tv_tenquanan = view.findViewById(R.id.tv_tenquanan);
        tv_diachi = view.findViewById(R.id.tv_diachi);
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tablayout);

        // attach tablayout with viewpager
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        // add your fragments
        adapter.addFrag(new Dang_xu_ly(), "Đang xử lý");
        adapter.addFrag(new Hoan_thanh(), "Hoàn thành");
        adapter.addFrag(new Da_huy(), "Đã hủy");
        // set adapter on viewpager
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        tabLayout.setTabsFromPagerAdapter(adapter);//deprecated
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        mData.child("CuaHang").child(Home_Activity.macuahang).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CuaHang cuaHang = dataSnapshot.getValue(CuaHang.class);
                tv_tenquanan.setText(cuaHang.getTencuahang());
                tv_diachi.setText(cuaHang.getDiachi());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
