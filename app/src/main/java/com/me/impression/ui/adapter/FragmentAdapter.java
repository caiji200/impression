package com.me.impression.ui.adapter;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.me.impression.ui.fragment.OverViewFragment;
import com.me.impression.ui.fragment.TranslateAudioFragment;
import com.me.impression.ui.fragment.TranslateFragment;

public class FragmentAdapter extends FragmentPagerAdapter {


    private SparseArray<Fragment> mFragments = new SparseArray<Fragment>();

    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
        mFragments.put(0, TranslateFragment.newInstance());
        mFragments.put(1, TranslateAudioFragment.newInstance());
        mFragments.put(2, OverViewFragment.newInstance());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments != null? mFragments.size() : 0;
    }

}