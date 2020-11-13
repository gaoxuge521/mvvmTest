package com.mvvmhabit.banner;


import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.ViewPager;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.KLog;

public class BannerAdapter {
    @BindingAdapter(value = {"images","onBannerItemClickCommand"},requireAll = false)
    public static void onItemClick(final Banner banner,final List<String> images, final BindingCommand<Integer> command){
        if(images==null){
            throw  new NullPointerException("images not null");
        }

        if(banner!=null && images.size()>0){
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(images);
            //banner设置方法全部调用完毕时最后调用
            banner.start();

            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    command.execute(position);
                }
            });

        }


    }

    @BindingAdapter(value = {"OnBannerListenerCommand"},requireAll = false)
    public static void onBannerChange(final Banner banner,final BindingCommand command){
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                command.execute(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

    }
    @BindingAdapter(value = {"isStop"},requireAll = false)
    public static void stop(final Banner banner,boolean isStop){
        if(isStop){
            banner.stopAutoPlay();
        }else{
            banner.startAutoPlay();
        }

    }

}
