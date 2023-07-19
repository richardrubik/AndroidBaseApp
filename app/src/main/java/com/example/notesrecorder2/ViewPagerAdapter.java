package com.example.notesrecorder2;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private static final int PAGES_ITEM_SIZE = 2;

    public ViewPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public Fragment createFragment(int position) {
        Log.i("Adapter", "position: " + position);
        if (position == 0) {
            return RecordsCreateFragment.newInstance("111", "222");
        } else {
            return RecordsListFragment.newInstance("111", "222");
        }
    }

    @Override
    public int getItemCount() {
        return PAGES_ITEM_SIZE;
    }
}
