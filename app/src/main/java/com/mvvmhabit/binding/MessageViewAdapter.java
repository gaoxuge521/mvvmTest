package com.mvvmhabit.binding;


import androidx.databinding.BindingAdapter;

import me.majiajie.pagerbottomtabstrip.internal.RoundMessageView;

public class MessageViewAdapter {

    @BindingAdapter(value = {"msgNum"},requireAll = false)
    public void setMsgNum(final RoundMessageView messageView,int num){
        messageView.setMessageNumber(num);
    }
}
