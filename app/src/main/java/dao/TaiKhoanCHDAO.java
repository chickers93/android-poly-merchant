package dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import model.TaiKhoanCH;

public class TaiKhoanCHDAO {
    DatabaseReference mData;


    public TaiKhoanCHDAO() {
        mData = FirebaseDatabase.getInstance().getReference();

    }

    public void putData() {
        List<TaiKhoanCH> list = new ArrayList<>();
        list.add(new TaiKhoanCH("CH01@gmail.com", "ch01", "CH01"));
        list.add(new TaiKhoanCH("CH02@gmail.com", "ch02", "CH02"));
        list.add(new TaiKhoanCH("CH03@gmail.com", "ch03", "CH03"));
        list.add(new TaiKhoanCH("CH04@gmail.com", "ch04", "CH04"));
        list.add(new TaiKhoanCH("CH05@gmail.com", "ch05", "CH05"));
        list.add(new TaiKhoanCH("CH06@gmail.com", "ch06", "CH06"));
        list.add(new TaiKhoanCH("CH07@gmail.com", "ch07", "CH07"));
        list.add(new TaiKhoanCH("CH08@gmail.com", "ch08", "CH08"));
        list.add(new TaiKhoanCH("CH09@gmail.com", "ch09", "CH09"));
        list.add(new TaiKhoanCH("CH010@gmail.com", "ch010", "CH010"));
        list.add(new TaiKhoanCH("CH011@gmail.com", "ch011", "CH012"));
        list.add(new TaiKhoanCH("CH012@gmail.com", "ch012", "CH012"));
        list.add(new TaiKhoanCH("CH013@gmail.com", "ch013", "CH013"));
        list.add(new TaiKhoanCH("CH014@gmail.com", "ch014", "CH014"));
        list.add(new TaiKhoanCH("CH015@gmail.com", "ch015", "CH015"));
        list.add(new TaiKhoanCH("CH016@gmail.com", "ch016", "CH016"));
        list.add(new TaiKhoanCH("CH017@gmail.com", "ch017", "CH017"));
        list.add(new TaiKhoanCH("CH018@gmail.com", "ch018", "CH018"));
        list.add(new TaiKhoanCH("CH019@gmail.com", "ch019", "CH019"));
        list.add(new TaiKhoanCH("CH020@gmail.com", "ch020", "CH020"));
        list.add(new TaiKhoanCH("CH021@gmail.com", "ch021", "CH021"));
        list.add(new TaiKhoanCH("CH022@gmail.com", "ch022", "CH022"));
        list.add(new TaiKhoanCH("CH023@gmail.com", "ch023", "CH023"));
        list.add(new TaiKhoanCH("CH024@gmail.com", "ch024", "CH024"));
        list.add(new TaiKhoanCH("CH025@gmail.com", "ch025", "CH025"));
        list.add(new TaiKhoanCH("CH026@gmail.com", "ch026", "CH026"));
        list.add(new TaiKhoanCH("CH027@gmail.com", "ch027", "CH027"));
        list.add(new TaiKhoanCH("CH028@gmail.com", "ch028", "CH028"));
        list.add(new TaiKhoanCH("CH029@gmail.com", "ch029", "CH029"));
        list.add(new TaiKhoanCH("CH030@gmail.com", "ch030", "CH030"));
        list.add(new TaiKhoanCH("CH031@gmail.com", "ch031", "CH031"));
        list.add(new TaiKhoanCH("CH032@gmail.com", "ch032", "CH032"));
        list.add(new TaiKhoanCH("CH033@gmail.com", "ch33", "CH033"));
        list.add(new TaiKhoanCH("CH034@gmail.com", "ch034", "CH034"));
        list.add(new TaiKhoanCH("CH035@gmail.com", "ch035", "CH035"));

        for (int i = 0; i < list.size(); i++) {
            mData.child("TaiKhoanCH").child(list.get(i).getMacuahang()).setValue(list.get(i));
        }

    }

}
