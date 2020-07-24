package dao;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.polymerchant.Fragment.ThucDon_Fragment;
import com.example.polymerchant.Home_Activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.Menu;

public class MenuDAO {
    DatabaseReference mData;
    List<Menu> menuList = new ArrayList<>();
    ThucDon_Fragment thucDon_fragment;

    public MenuDAO(ThucDon_Fragment thucDon_fragment) {
        mData = FirebaseDatabase.getInstance().getReference();
        this.thucDon_fragment = thucDon_fragment;

    }

    public MenuDAO() {
    }

    public List<Menu> getMenu() {
        mData.child("Menu").child(Home_Activity.macuahang).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                menuList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Menu menu = data.getValue(Menu.class);
                    menuList.add(menu);
                }

                thucDon_fragment.capnhatLV();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return menuList;
    }
}
