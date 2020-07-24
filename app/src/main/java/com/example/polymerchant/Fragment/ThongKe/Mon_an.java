package com.example.polymerchant.Fragment.ThongKe;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.polymerchant.Home_Activity;
import com.example.polymerchant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adapter.MonAnYeuThichAdapter;
import model.Menu_temp;
import model.MonAnYeuThich;

public class Mon_an extends Fragment {
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    List<Menu_temp> menuList = new ArrayList<>();
    MonAnYeuThichAdapter monAnYeuThichAdapter;
    ListView lv_monan;
    int count;


    public Mon_an() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mon_an, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ánh xạ
        lv_monan = view.findViewById(R.id.lv_monan);

        //sự kiện
        mData.child("Menu").child(Home_Activity.macuahang).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                menuList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Menu_temp menu_temp = data.getValue(Menu_temp.class);
                    final String mamonan = menu_temp.getMamonan();
                    final String tenmonan = menu_temp.getTenmonan();
                    final int gia = menu_temp.getGia();
                    final String hinhanh = menu_temp.getHinhanh();

                    mData.child("Favourite").child(Home_Activity.macuahang).child(mamonan).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            count = 0;
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                MonAnYeuThich monAnYeuThich = data.getValue(MonAnYeuThich.class);
                                count += monAnYeuThich.getSoluot();
                            }
                            menuList.add(new Menu_temp(mamonan, tenmonan, gia, hinhanh, count));

                            //sort món ăn theo số lượng đặt mua
                            Collections.sort(menuList, new Comparator<Menu_temp>() {
                                @Override
                                public int compare(Menu_temp o1, Menu_temp o2) {
                                    return Double.valueOf(o2.getSoluong()).compareTo((double) o1.getSoluong());
                                }
                            });

                            try {
                                monAnYeuThichAdapter = new MonAnYeuThichAdapter(getActivity(), R.layout.item_monan_yeuthich, menuList);
                                lv_monan.setAdapter(monAnYeuThichAdapter);
                                monAnYeuThichAdapter.notifyDataSetChanged();
                            } catch (Exception e) {

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
