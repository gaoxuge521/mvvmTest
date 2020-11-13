package com.mvvmhabit.animshopbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;


import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;

import me.goldze.mvvmhabit.utils.KLog;

public class AnimShopButtonUi extends AnimShopButton {
    private OnAddDelTextClick onAddDelTextClick;

    public void setTextColor(int color){
        mTextPaint.setColor(color);
    }


    public OnAddDelTextClick getOnAddDelTextClick() {
        return onAddDelTextClick;
    }

    public void setOnAddDelTextClick(OnAddDelTextClick onAddDelTextClick) {
        this.onAddDelTextClick = onAddDelTextClick;
    }

    public AnimShopButtonUi(Context context) {
        super(context);
    }

    public AnimShopButtonUi(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimShopButtonUi(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch(action) {
            case 0:
                if (!this.isReplenish) {
                    if (this.isHintMode) {
                        this.onAddClick();
                        return true;
                    } else if (this.mAddRegion.contains((int)event.getX(), (int)event.getY())) {
                        this.onAddClick();
                        return true;
                    } else if (this.mDelRegion.contains((int)event.getX(), (int)event.getY())) {
                        this.onDelClick();
                        return true;
                    } else  {
                        this.onTextClick();
                        return true;
                    }
                }
            case 1:
            case 2:
            case 3:
            default:
                return super.onTouchEvent(event);
        }
    }
    @Override
    protected void onAddClick() {
        KLog.e("sss   ");
        if(onAddDelTextClick!=null){
            onAddDelTextClick.onAddClick();
        }

    }

    public void OnAddNext(){
        if (this.mCount < this.mMaxCount) {
            ++this.mCount;
            this.onCountAddSuccess();
            if (null != this.mOnAddDelListener) {
                this.mOnAddDelListener.onAddSuccess(this.mCount);
            }
        } else if (null != this.mOnAddDelListener) {
            this.mOnAddDelListener.onAddFailed(this.mCount, IOnAddDelListener.FailType.COUNT_MAX);
        }
    }
    @Override
    protected void onDelClick() {
        if(onAddDelTextClick!=null){
            onAddDelTextClick.onDelClick();
        }

    }
    public void onDelNext(){
        if (this.mCount > 0) {
            --this.mCount;
            this.onCountDelSuccess();
            if (null != this.mOnAddDelListener) {
                this.mOnAddDelListener.onDelSuccess(this.mCount);
            }
        } else if (null != this.mOnAddDelListener) {
            this.mOnAddDelListener.onDelFaild(this.mCount, IOnAddDelListener.FailType.COUNT_MIN);
        }
    }
    protected void onTextClick() {
        KLog.e("sss   数字点击");
        if(onAddDelTextClick!=null){
            onAddDelTextClick.onTextClick();
        }
    }



    public interface OnAddDelTextClick{
        void onAddClick();
        void onDelClick();
        void onTextClick();
    }
}
