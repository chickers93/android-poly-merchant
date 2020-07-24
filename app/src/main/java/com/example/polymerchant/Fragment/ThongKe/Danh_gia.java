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
import java.util.List;

import adapter.CommentAdapter;
import model.Comment;

public class Danh_gia extends Fragment {
    ListView lv_comment;
    CommentAdapter commentAdapter;
    List<Comment> commentList = new ArrayList<>();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    public Danh_gia() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_danh_gia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ánh xạ
        lv_comment = view.findViewById(R.id.lv_comment);

        //sự kiện
        mData.child("Comment").child(Home_Activity.macuahang).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Comment comment = data.getValue(Comment.class);
                    commentList.add(0, comment);
                }
                commentAdapter = new CommentAdapter(getActivity(), R.layout.item_comment2, commentList);
                lv_comment.setAdapter(commentAdapter);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
