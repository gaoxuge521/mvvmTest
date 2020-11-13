package com.example.swipetestdemo.widght.swiperv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableList;
import androidx.databinding.OnRebindCallback;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swipetestdemo.R;
import com.example.swipetestdemo.utils.Utils;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionDefault;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionMoveToSwipedDirection;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractSwipeableItemViewHolder;

import java.lang.ref.WeakReference;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;

public class SwipeRvAdapter<T extends SwipeViewModel> extends RecyclerView.Adapter<SwipeRvAdapter.BindViewHolder> implements SwipeableItemAdapter<SwipeRvAdapter.BindViewHolder>, SwipeBindingAdapter<T> {
    private static final Object DATA_INVALIDATION = new Object();
    private LayoutInflater inflater;
    private SwipeItemBinding<T> itemBinding;
    private List<T> itemDatas;
    private float swipeAmount = 0.3f;
    private boolean enableSwipe = true;
    private WeakRefrenceListChangeCallBack<T> callBack = new WeakRefrenceListChangeCallBack<>(this);
    private RecyclerView recyclerView;



    public SwipeRvAdapter() {
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public BindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int layoutId) {
        if(inflater==null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        ViewDataBinding dataBinding  = onCreateBinding(inflater,layoutId,parent);
        BindViewHolder holder = new BindViewHolder(dataBinding.getRoot());
        dataBinding.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                return recyclerView!=null && recyclerView.isComputingLayout();
            }

            @Override
            public void onCanceled(ViewDataBinding binding) {
                if(recyclerView==null || recyclerView.isComputingLayout()){
                    return;
                }
                int position =holder.getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    notifyItemChanged(position,DATA_INVALIDATION);
                }
            }
        });
        return holder;
    }

    @Override
    @CallSuper
    public void onBindViewHolder(@NonNull BindViewHolder holder, int position) {
        T item = itemDatas.get(position);
        ViewDataBinding binding = DataBindingUtil.getBinding(holder.itemView);
        onBindBinding(binding,itemBinding.variableId(),itemBinding.layoutRes(),position,item);

        KLog.e("sss  "+position+"  "+swipeType +"   "+ item.isPinned());
        if(item.isPinned()){

        }else{

        }
//        holder.setMaxLeftSwipeAmount(-swipeAmount);
//        holder.setMaxRightSwipeAmount(0);
//        holder.setSwipeItemHorizontalSlideAmount(item.isPinned() ? -swipeAmount : 0);

        if(item.getSwipeType()== Swipeable.DRAWABLE_SWIPE_RIGHT_BACKGROUND){
            holder.setMaxLeftSwipeAmount(0);
            holder.setMaxRightSwipeAmount(swipeAmount);
            holder.setSwipeItemHorizontalSlideAmount(item.isPinned() ? swipeAmount : 0);
        }else if(item.getSwipeType()== Swipeable.DRAWABLE_SWIPE_LEFT_BACKGROUND){
            holder.setMaxLeftSwipeAmount(-swipeAmount);
            holder.setMaxRightSwipeAmount(0);
            holder.setSwipeItemHorizontalSlideAmount(item.isPinned() ? -swipeAmount : 0);
        }else{
            holder.setMaxLeftSwipeAmount(-swipeAmount);
            holder.setMaxRightSwipeAmount(0);
            holder.setSwipeItemHorizontalSlideAmount(item.isPinned() ? -swipeAmount : 0);
        }

    }



    @Override
    public int getItemCount() {
        return itemDatas.size();
    }

    @Override
    public void setItemBinding(SwipeItemBinding<T> itemBinding) {
        this.itemBinding = itemBinding;
    }

    @Override
    public SwipeItemBinding<T> getItemBinding() {
        return itemBinding;
    }

    @Override
    public void setItems(List<T> items) {
        if(itemDatas==items){
            return;
        }
        if(itemDatas instanceof ObservableList){
            ((ObservableList<T>) itemDatas).removeOnListChangedCallback(callBack);
        }
        if(items instanceof ObservableList){
            ((ObservableList<T>) items).addOnListChangedCallback(callBack);
        }
        this.itemDatas = items;
        notifyDataSetChanged();
    }

    @Override
    public T getAdapterItem(int position) {
        return itemDatas.get(position);
    }

    @Override
    public ViewDataBinding onCreateBinding(LayoutInflater inflater, int layoutRes, ViewGroup viewGroup) {
        return DataBindingUtil.inflate(inflater,layoutRes,viewGroup,false);
    }
    @Override
    public int getItemViewType(int position) {
        itemBinding.onItemBind(position, itemDatas.get(position));
        return itemBinding.layoutRes();
    }
    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, T item) {
        if(itemBinding.bind(binding,item)){
            binding.executePendingBindings();
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (this.recyclerView == null && itemDatas != null && itemDatas instanceof ObservableList) {
            callBack = new WeakRefrenceListChangeCallBack<>(this);
            ((ObservableList<T>) itemDatas).addOnListChangedCallback(callBack);
        }
        this.recyclerView = recyclerView;
    }

    interface Swipeable extends SwipeableItemConstants {
    }
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (this.recyclerView != null && itemDatas != null && itemDatas instanceof ObservableList) {
            ((ObservableList<T>) itemDatas).removeOnListChangedCallback(callBack);
        }
        this.recyclerView = null;
    }

    @Override
    public int onGetSwipeReactionType(@NonNull BindViewHolder holder, int position, int x, int y) {
        KLog.e("sss  "+position+"   "+ Utils.hitTest(holder.getSwipeableContainerView(), x, y));
        if (holder.getSwipeableContainerView() == null) {
            return -1;
        }
//        if (Utils.hitTest(holder.getSwipeableContainerView(), x, y)) {
//            return Swipeable.REACTION_CAN_SWIPE_BOTH_H;
//        } else {
//            return Swipeable.REACTION_CAN_NOT_SWIPE_BOTH_H;
//        }
        return Swipeable.REACTION_CAN_SWIPE_BOTH_H;
    }

    @Override
    public void onSwipeItemStarted(@NonNull BindViewHolder holder, int position) {
        notifyDataSetChanged();
    }

    private int swipeType;
    @Override
    public void onSetSwipeBackground(@NonNull BindViewHolder holder, int position, int type) {
        KLog.e("sss  "+type);
        if (type == Swipeable.DRAWABLE_SWIPE_NEUTRAL_BACKGROUND) {
            holder.getBeHindView().setVisibility(View.GONE);
        } else {
            holder.getBeHindView().setVisibility(View.VISIBLE);
        }
        itemDatas.get(position).setSwipeType(type);
        switch (type) {
            case Swipeable.DRAWABLE_SWIPE_NEUTRAL_BACKGROUND:
                holder.getBeHindView().setVisibility(View.GONE);
                holder.getLeftView().setVisibility(View.GONE);
                break;
            case Swipeable.DRAWABLE_SWIPE_LEFT_BACKGROUND:
                holder.getBeHindView().setVisibility(View.VISIBLE);
                holder.getLeftView().setVisibility(View.GONE);
                break;
            case Swipeable.DRAWABLE_SWIPE_RIGHT_BACKGROUND:
                holder.getBeHindView().setVisibility(View.GONE);
                holder.getLeftView().setVisibility(View.VISIBLE);
                break;
        }
    }

    @Nullable
    @Override
    public SwipeResultAction onSwipeItem(@NonNull BindViewHolder holder, int position, int result) {
        KLog.e("sss ", "onSwipeItem(position = " + position + ", result = " + result +"   "+itemDatas.get(position).getSwipeType()+ ")");
        switch (result) {
            // swipe left --- pin
            case Swipeable.RESULT_SWIPED_LEFT:
                if (itemDatas.get(position).isPinned()) {
                    // pinned --- back to default position
                    if(itemDatas.get(position).getSwipeType()== Swipeable.RESULT_CANCELED){
                        return null;
                    }
                    return new UnpinResultAction(this, position);
//                    return null;
                } else {
                    // not pinned --- remove
                    return new SwipeLeftResultAction(this, position);
                }

            // other --- do nothing
            case Swipeable.RESULT_SWIPED_RIGHT:
//                KLog.e("sss  +"+itemDatas.get(position).isPinned());
                if (itemDatas.get(position).isPinned()) {
                    // pinned --- back to default position
                    if(itemDatas.get(position).getSwipeType()== Swipeable.RESULT_SWIPED_UP){
                        return null;
                    }
                    return new UnpinResultAction(this, position);
//                    return null;
                } else {
                    // not pinned --- remove
                    return new SwipeRightResultAction(this, position);
                }
            case Swipeable.RESULT_CANCELED:
            default:
                if (position != RecyclerView.NO_POSITION) {
                    return new UnpinResultAction(this, position);
                } else {
                    return null;
                }
        }
    }
    private static class SwipeRightResultAction extends SwipeResultActionMoveToSwipedDirection {
        private SwipeRvAdapter mAdapter;
        private final int mPosition;

        SwipeRightResultAction(SwipeRvAdapter adapter, int position) {
            mAdapter = adapter;
            mPosition = position;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            SwipeViewModel item = (SwipeViewModel) mAdapter.itemDatas.get(mPosition);
            KLog.e("sss SwipeRightResultAction "+mPosition+"  "+item.isPinned());
            if (!item.isPinned()) {
                item.setPinned(true);
                mAdapter.notifyItemChanged(mPosition);
            }
        }

        @Override
        protected void onSlideAnimationEnd() {
            super.onSlideAnimationEnd();

        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
            // clear the references
            mAdapter = null;
        }
    }
    public static class BindViewHolder extends AbstractSwipeableItemViewHolder {

        private FrameLayout container;
        private RelativeLayout behind_views;
        private RelativeLayout behind_left_view;

        public BindViewHolder(@NonNull View itemView) {
            super(itemView);
            container =  itemView.findViewById(R.id.container);
            behind_views =  itemView.findViewById(R.id.behind_delete_view);
            behind_left_view =  itemView.findViewById(R.id.behind_left_view);
        }

        @NonNull
        @Override
        public View getSwipeableContainerView() {
            return container;
        }
        public View getBeHindView(){
            return behind_views;
        }
        private View getLeftView(){
            return behind_left_view;
        }
    }
    public static class WeakRefrenceListChangeCallBack<T  extends SwipeViewModel> extends ObservableList.OnListChangedCallback<ObservableList<T>> {
        private WeakReference<SwipeRvAdapter<T>> adapterWre;

        public WeakRefrenceListChangeCallBack(SwipeRvAdapter<T> adapterWre) {
            this.adapterWre = new WeakReference<>(adapterWre);
        }

        @Override
        public void onChanged(ObservableList<T> sender) {
            SwipeRvAdapter<T> adapter = adapterWre.get();
            if(adapter==null){
                return;
            }
            Utils.ensureChangeOnMainThread();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
            SwipeRvAdapter<T> adapter = adapterWre.get();
            if(adapter==null){
                return;
            }
            Utils.ensureChangeOnMainThread();
            adapter.notifyItemRangeChanged(positionStart,itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
            SwipeRvAdapter<T> adapter = adapterWre.get();
            if(adapter==null){
                return;
            }
            Utils.ensureChangeOnMainThread();
            adapter.notifyItemRangeInserted(positionStart,itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
            SwipeRvAdapter<T> adapter = adapterWre.get();
            if(adapter==null){
                return;
            }
            Utils.ensureChangeOnMainThread();
            for (int i = 0; i < itemCount; i++) {
                adapter.notifyItemMoved(fromPosition + i, toPosition + i);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
            SwipeRvAdapter<T> adapter = adapterWre.get();
            if(adapter==null){
                return;
            }
            Utils.ensureChangeOnMainThread();
            adapter.notifyItemRangeRemoved(positionStart,itemCount);
        }
    }

    @Override
    public long getItemId(int position) {
        return itemDatas.get(position).getId();
    }


    private static class SwipeLeftResultAction extends SwipeResultActionMoveToSwipedDirection {
        private SwipeRvAdapter mAdapter;
        private final int mPosition;
        private boolean mSetPinned;

        SwipeLeftResultAction(SwipeRvAdapter adapter, int position) {
            mAdapter = adapter;
            mPosition = position;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            SwipeViewModel item = (SwipeViewModel) mAdapter.itemDatas.get(mPosition);
            KLog.e("sss SwipeLeftResultAction "+item.isPinned());
            if (!item.isPinned()) {
                item.setPinned(true);
                mAdapter.notifyItemChanged(mPosition);
                mSetPinned = true;
            }
        }

        @Override
        protected void onSlideAnimationEnd() {
            super.onSlideAnimationEnd();

        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
            // clear the references
            mAdapter = null;
        }
    }
    private static class UnpinResultAction extends SwipeResultActionDefault {
        private SwipeRvAdapter mAdapter;
        private final int mPosition;

        UnpinResultAction(SwipeRvAdapter adapter, int position) {
            mAdapter = adapter;
            mPosition = position;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            SwipeViewModel item = (SwipeViewModel) mAdapter.itemDatas.get(mPosition);
            KLog.e("sss UnpinResultAction "+item.isPinned());
            if (item.isPinned()) {
                item.setPinned(false);
                mAdapter.notifyItemChanged(mPosition);
            }
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
            // clear the references
            mAdapter = null;
        }
    }
}
