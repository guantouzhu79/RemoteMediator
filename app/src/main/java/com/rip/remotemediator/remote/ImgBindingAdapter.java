package com.rip.remotemediator.remote;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.rip.remotemediator.R;

public class ImgBindingAdapter {

    @BindingAdapter("requestImage")
    public static void requestImage(ImageView imageView,String  url){
        if(url == null || url.isEmpty()) return;
        Context context = imageView.getContext();
        Glide.with(context).load( url).error(R.drawable.aoi).into(imageView);
    }
}
