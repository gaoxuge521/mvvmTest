package com.example.swipetestdemo.widght.swiperv;

import android.util.SparseArray;

import androidx.databinding.ViewDataBinding;

import com.example.swipetestdemo.utils.Utils;


public class SwipeItemBinding<T> {
    private final SwipeOnItenBind<T> onItemBind;
    private int variableId;
    private int layoutRes;
    private SparseArray<Object> extraBindings;

    public SwipeItemBinding(SwipeOnItenBind<T> onItemBind) {
        this.onItemBind = onItemBind;
    }

    public static <T> SwipeItemBinding<T> of(int variableId, int layoutRes) {
        return (new SwipeItemBinding(null)).set(variableId, layoutRes);
    }

    public static <T> SwipeItemBinding<T> of(SwipeOnItenBind<T> onItemBind) {
        if (onItemBind == null) {
            throw new NullPointerException("onItemBind == null");
        } else {
            return new SwipeItemBinding(onItemBind);
        }
    }

    public final SwipeItemBinding<T> set(int variableId, int layoutRes) {
        this.variableId = variableId;
        this.layoutRes = layoutRes;
        return this;
    }

    public final SwipeItemBinding<T> variableId(int variableId) {
        this.variableId = variableId;
        return this;
    }

    public final SwipeItemBinding<T> layoutRes(int layoutRes) {
        this.layoutRes = layoutRes;
        return this;
    }

    public final SwipeItemBinding<T> bindExtra(int variableId, Object value) {
        if (this.extraBindings == null) {
            this.extraBindings = new SparseArray(1);
        }

        this.extraBindings.put(variableId, value);
        return this;
    }

    public final SwipeItemBinding<T> clearExtras() {
        if (this.extraBindings != null) {
            this.extraBindings.clear();
        }

        return this;
    }

    public SwipeItemBinding<T> removeExtra(int variableId) {
        if (this.extraBindings != null) {
            this.extraBindings.remove(variableId);
        }

        return this;
    }

    public final int variableId() {
        return this.variableId;
    }

    public final int layoutRes() {
        return this.layoutRes;
    }

    public final Object extraBinding(int variableId) {
        return this.extraBindings == null ? null : this.extraBindings.get(variableId);
    }

    public void onItemBind(int position, T item) {
        if (this.onItemBind != null) {
            this.variableId = -1;
            this.layoutRes = 0;
            this.onItemBind.onItenBind(this, position, item);
            if (this.variableId == -1) {
                throw new IllegalStateException("variableId not set in onItemBind()");
            }

            if (this.layoutRes == 0) {
                throw new IllegalStateException("layoutRes not set in onItemBind()");
            }
        }
    }

    public boolean bind(ViewDataBinding binding, T item) {
        if (this.variableId == 0) {
            return false;
        } else {
            boolean result = binding.setVariable(this.variableId, item);
            if (!result) {
                Utils.throwMissingVariable(binding, this.variableId, this.layoutRes);
            }

            if (this.extraBindings != null) {
                int i = 0;

                for (int size = this.extraBindings.size(); i < size; ++i) {
                    int variableId = this.extraBindings.keyAt(i);
                    Object value = this.extraBindings.valueAt(i);
                    if (variableId != 0) {
                        binding.setVariable(variableId, value);
                    }
                }
            }

            return true;
        }
    }

}
