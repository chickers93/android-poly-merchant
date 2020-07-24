package com.example.polymerchant.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.polymerchant.Fragment.ThongKe.Doanh_thu;
import com.example.polymerchant.Fragment.ThongKe.Danh_gia;
import com.example.polymerchant.Fragment.ThongKe.Mon_an;
import com.example.polymerchant.R;
import com.google.android.material.tabs.TabLayout;

import adapter.ViewPagerAdapter;

public class BaoCao_Fragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.baocao_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ánh xạ
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tablayout);

        // attach tablayout with viewpager
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        // add your fragments
        adapter.addFrag(new Doanh_thu(), "Doanh thu");
        adapter.addFrag(new Mon_an(), "Món ăn");
        adapter.addFrag(new Danh_gia(), "Đánh giá");
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

    }
}
