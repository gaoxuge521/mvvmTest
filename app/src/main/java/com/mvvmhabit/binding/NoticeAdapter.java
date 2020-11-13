package com.mvvmhabit.binding;


import androidx.databinding.BindingAdapter;

import com.mvvmhabit.entity.NoticeEntity;
import com.mvvmhabit.widget.NoticeView;

import java.util.List;

import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class NoticeAdapter {
    @BindingAdapter(value = {"noticeData","noticeOnItemClickCommand"},requireAll = false)
    public static void setDatas(final NoticeView noticeView, final NoticeEntity list, final BindingCommand bindingCommand){
        if(list!=null && list.getMessage().size()>0){
            noticeView.addNotice(list.getMessage());
        }


        noticeView.setOnNoticeClickListener(new NoticeView.OnNoticeClickListener() {
            @Override
            public void onNoticeClick(int position, NoticeEntity.NoticeMsg notice) {
                bindingCommand.execute(notice);
            }
        });
    }
}
