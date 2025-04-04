package com.example.movieapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class pagerAdapter extends FragmentPagerAdapter {
  private   ArrayList<Fragment> fragmentsnew=new ArrayList<>();
  private Context context;
  int imageResId[];
  private String []tabsTitle= new String[]{" Movies", " Tv shows", " People"};
  private int fragmentCount=3;

    public pagerAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fragments, Context context,int tabicons[]) {
        super(fm);
        this.fragmentsnew.addAll(fragments);
        //this.fragmentsnew=fragments;
        this.context=context;
        this.imageResId=tabicons;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentsnew.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsnew.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
// Generate title based on item position
        Drawable image = context.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
// Replace blank spaces with image icon
        SpannableString sb = new SpannableString(" " + tabsTitle[position]);
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}
