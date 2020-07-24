package com.example.polymerchant.Fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.polymerchant.Home_Activity;
import com.example.polymerchant.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import adapter.MenuAdapter;
import dao.MenuDAO;
import model.Menu;
import model.MonAnYeuThich;

public class ThucDon_Fragment extends Fragment {
    ListView lv_menu;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    List<Menu> menuList = new ArrayList<>();
    MenuAdapter menuAdapter;
    Button btn_add;
    int size;

    public ThucDon_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.thucdon_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ánh xạ
        lv_menu = view.findViewById(R.id.lv_menu);
        btn_add = view.findViewById(R.id.btn_add);

        //get menu
        final MenuDAO menuDAO = new MenuDAO(ThucDon_Fragment.this);
        menuList = menuDAO.getMenu();

        try {
            menuAdapter = new MenuAdapter(getActivity(), R.layout.item_menu, menuList);
            lv_menu.setAdapter(menuAdapter);
            menuAdapter.notifyDataSetChanged();
        } catch (Exception e) {

        }

        //thêm món ăn
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder altdial = new AlertDialog.Builder(getActivity());
                final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_themmonan, null);
                altdial.setView(dialogView);

                final AlertDialog dialog = altdial.create();
                dialog.show();

                //ánh xạ
                final EditText ed_tenmonan = dialogView.findViewById(R.id.ed_tenmonan);
                final EditText ed_gia = dialogView.findViewById(R.id.ed_giamoi);
                Button btn_xacnhan = dialogView.findViewById(R.id.btn_xacnhan);


                //sự kiện
                btn_xacnhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        size = menuDAO.getMenu().size() + 1;
                        String mamonan = Home_Activity.macuahang + "_" + size;
                        //Toast.makeText(getActivity(), mamonan, Toast.LENGTH_SHORT).show();
                        try {
                            if (ed_tenmonan.getText().toString().length() > 0) {
                                Menu menu = new Menu(mamonan, ed_tenmonan.getText().toString(), Integer.parseInt(ed_gia.getText().toString()), "https://firebasestorage.googleapis.com/v0/b/polyfood-7fcd7.appspot.com/o/no_image.jpg?alt=media&token=fa11b05a-5e3e-4f0b-a172-f10dad5208f6");
                                mData.child("Menu").child(Home_Activity.macuahang).child(mamonan).setValue(menu);

                                Toast.makeText(getActivity(), "Thêm món ăn thành công!", Toast.LENGTH_SHORT).show();
                                dialog.cancel();

                                //add new item to favourite list
                                mData.child("Favourite").child(Home_Activity.macuahang).child(mamonan).push().setValue(new MonAnYeuThich(0));
                            } else {
                                Toast.makeText(getActivity(), "Chưa nhập tên món món ăn!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Chưa nhập giá món ăn!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

    }

    public void capnhatLV() {
        menuAdapter.notifyDataSetChanged();
    }

}
