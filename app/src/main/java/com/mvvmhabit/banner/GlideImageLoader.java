package com.mvvmhabit.banner;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goldze.mvvmhabit.R;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        Glide.with(context).asBitmap().apply(new RequestOptions().centerCrop().placeholder(R.drawable.img_default_long)).load(path).into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
        ImageView simpleDraweeView=new ImageView(context);
        simpleDraweeView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return simpleDraweeView;
    }
}
