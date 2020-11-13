package com.example.swipetestdemo.widght.swiperv;

public interface  SwipeViewModel {
    public abstract void setPinned(boolean pinned);

    public abstract boolean isPinned();

    public abstract long getId();

    public abstract void setSwipeType(int type);

    public abstract int getSwipeType();

}
