package com.mvvmhabit.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.swipetestdemo.R;


public class CustomDialog {

    public Context mContext;
    public Dialog dialog;
    public PositiveOnClick poClick;
    public NegativeOnClick noClick;
    public CancelOnClick caClick;


    public CustomDialog(Context context) {
        this.mContext = context;
    }

    /**
     * 领取优惠券弹出框showCouponDialog
     *
     * @param title   属于标题性质
     * @param content 提示的内容
     * @param onClick
     */
    public void showCouponDialog(Drawable drawable, String title, String content, String btn_text, final PositiveOnClick onClick) {
        dialog = new Dialog(mContext, R.style.custome_dialog_style);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.coupon_dialog, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_dialog);
        imageView.setImageDrawable(drawable);
        TextView txt_reminder = (TextView) view.findViewById(R.id.tv_title);
        TextView txt_reason = (TextView) view.findViewById(R.id.tv_content);
        ImageView iv_close = (ImageView) view.findViewById(R.id.iv_close);
        TextView txt_btn = (TextView) view.findViewById(R.id.tv_confirm);
        txt_btn.setText(btn_text);

        if (TextUtils.isEmpty(title)) {
            txt_reminder.setVisibility(View.GONE);
        } else {
            txt_reminder.setText(title);
        }

        if (TextUtils.isEmpty(content)) {
            txt_reason.setVisibility(View.GONE);
        } else {
            txt_reason.setText(content);
        }

        iv_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        txt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onClick != null)
                    onClick.onPositiveClick();

                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (0.8 * ScreenSizeUtil.getScreenHeight(mContext));
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setWindowAnimations(R.style.mystyle);
        dialog.show();
    }




    /**
     * 提示性的弹出框
     *
     * @param title   属于标题性质
     * @param content 提示的内容
     * @param onClick
     */
    public void showSingleButtonDialog(String title, String content, String btn_text, final PositiveOnClick onClick) {
        dialog = new Dialog(mContext, R.style.custome_dialog_style);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.single_button_dialog, null);
        TextView txt_reminder = (TextView) view.findViewById(R.id.tv_title);
        TextView txt_reason = (TextView) view.findViewById(R.id.tv_content);
        LinearLayout ll_btn = (LinearLayout) view.findViewById(R.id.ll_btn);
        TextView txt_btn = (TextView) view.findViewById(R.id.txt_btn);
        txt_btn.setText(btn_text);

        if (TextUtils.isEmpty(title)) {
            txt_reminder.setVisibility(View.GONE);
        } else {
            txt_reminder.setText(title);
        }

        if (TextUtils.isEmpty(content)) {
            txt_reason.setVisibility(View.GONE);
        } else {
            txt_reason.setText(content);
        }

        ll_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onClick != null)
                    onClick.onPositiveClick();
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (0.8 * ScreenSizeUtil.getScreenWidth(mContext));
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setWindowAnimations(R.style.mystyle);
        dialog.show();
    }




    public void showSingleButtonDialog(String title, String content,
                                       final PositiveOnClick onClick) {
        showSingleButtonDialog(title, content, "确定", onClick);
    }

    public void openDialog() {
        dialog.show();
    }

    public void closeDialog() {
        dialog.dismiss();
    }

    public void setonItemClick(PositiveOnClick poClick, NegativeOnClick noClick, CancelOnClick caClick) {
        this.poClick = poClick;
        this.noClick = noClick;
        this.caClick = caClick;
    }

    public void baseDialog(Context context, String title, String content,
                           String btn_cancel, String btn_affirm,
                           final NegativeOnClick nOnClick, final PositiveOnClick pOnClick) {
        baseDialog(context, title, content, btn_cancel, btn_affirm, nOnClick, pOnClick, true);
    }

    /**
     * 有标题弹出框
     *
     * @param context    环境上下文
     * @param title      弹出框的标题
     * @param content    弹出框提示的内容
     * @param btn_cancel 实现功能的按钮名称（比如：删除）
     * @param btn_affirm 实现功能的按钮名称（比如：确定）
     * @param nOnClick   实现功能按钮中的事件
     * @param pOnClick   实现功能按钮中的事件
     */
    public void baseDialog(Context context, String title, String content,
                           String btn_cancel, String btn_affirm,
                           final NegativeOnClick nOnClick, final PositiveOnClick pOnClick, boolean canCanceled) {
        this.mContext = context;
        dialog = new Dialog(mContext, R.style.custome_dialog_style);
        dialog.setCancelable(canCanceled);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.double_button_dialog, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(title);
        TextView txt_content = (TextView) view.findViewById(R.id.tv_content);
        txt_content.setText(content);

        if (TextUtils.isEmpty(title)) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setText(title);
        }

        if (TextUtils.isEmpty(content)) {
            txt_content.setVisibility(View.GONE);
        } else {
            txt_content.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
            txt_content.setText(Html.fromHtml(content));
//            txt_content.setText(content);
        }

        LinearLayout ll_cancel = (LinearLayout) view.findViewById(R.id.ll_cancel);
        LinearLayout ll_affirm = (LinearLayout) view.findViewById(R.id.ll_affirm);
        TextView txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
        txt_cancel.setText(btn_cancel);
        TextView txt_ensure = (TextView) view.findViewById(R.id.txt_ensure);
        txt_ensure.setText(btn_affirm);
        ll_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (nOnClick != null) {
                    nOnClick.onNegativeClick();
                }
                dialog.dismiss();
            }
        });
        ll_affirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pOnClick != null) {

                    pOnClick.onPositiveClick();
                }
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (0.8 * ScreenSizeUtil.getScreenWidth(mContext)/3);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    public void showTwoButtonDialog(Context context,
                                    String btn_cancel, String btn_affirm,
                                    final NegativeOnClick nOnClick, final PositiveOnClick pOnClick, boolean canCanceled) {
        this.mContext = context;
        dialog = new Dialog(mContext, R.style.custome_dialog_style);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(canCanceled);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.two_button_dialog, null);

        LinearLayout ll_cancel = (LinearLayout) view.findViewById(R.id.ll_cancel);
        LinearLayout ll_affirm = (LinearLayout) view.findViewById(R.id.ll_affirm);
        TextView txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
        txt_cancel.setText(btn_cancel);
        TextView txt_ensure = (TextView) view.findViewById(R.id.txt_ensure);
        txt_ensure.setText(btn_affirm);
        ll_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (nOnClick != null) {
                    nOnClick.onNegativeClick();
                }
                dialog.dismiss();
            }
        });
        ll_affirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pOnClick != null) {

                    pOnClick.onPositiveClick();
                }
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (0.8 * ScreenSizeUtil.getScreenWidth(mContext));
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    /**
     */
    public void saveTip(Context context, String title, String content, final PositiveOnClick pOnClick) {
        baseDialog(context, title, content, "取消", "确定", null, pOnClick);
    }

    /**
     * 全部清除确认对话框
     */
    public void clearAll(Context context, final PositiveOnClick pOnClick) {
        baseDialog(context, "全部清除", "您确认要全部清除吗", "取消", "确定", null, pOnClick);
    }

    /**
     * 全部清除确认对话框
     */
    public void butlerReserveInfo(Context context, String butlerName, final NegativeOnClick nOnClick, final PositiveOnClick pOnClick) {
        baseDialogForIOS(context, null, String.format("已经为您匹配了%s管家,是否同意为您服务", butlerName), "拒绝", "同意", nOnClick, pOnClick);
    }

    /**
     * 删除订单对话框
     */
    public void deleteOrder(Context context, final NegativeOnClick nOnClick, final PositiveOnClick pOnClick) {
        baseDialog(context, "您是否要删除订单？", "", "确定", "再看看", nOnClick, pOnClick);
    }

    /**
     * 确认收货对话框
     */
    public void orderConfirm(Context context, final NegativeOnClick nOnClick, final PositiveOnClick pOnClick) {
        baseDialog(context, "您是否要确认收货？", "", "确定", "再看看", nOnClick, pOnClick);
    }
    /**
     * 删除订单对话框
     */
    public void cancleOrder(Context context, final NegativeOnClick nOnClick, final PositiveOnClick pOnClick) {
        baseDialog(context, "您是否要取消此订单？", "", "确定", "再看看", nOnClick, pOnClick);
    }

    /**
     * 取消提现对话框
     */
    public void cancleWithdraw(Context context, final NegativeOnClick nOnClick, final PositiveOnClick pOnClick) {
        baseDialog(context, "您是否要取消此次提现？", "", "确定", "再看看", nOnClick, pOnClick);
    }
    /**
     * 全部清除确认对话框
     */
    public void clearAllfootstep(Context context, final PositiveOnClick pOnClick) {
        baseDialog(context, "全部清除", "您确认要清除全部浏览记录吗", "取消", "确定", null, pOnClick);
    }
    /**
     * 删除订单对话框
     */
    public void deleteAddress(Context context, final NegativeOnClick nOnClick, final PositiveOnClick pOnClick) {
        baseDialog(context, "您是否要删除此地址？", "", "确定", "再看看", nOnClick, pOnClick);
    }
    /**
     * 删取消收藏对话框
     */
    public void remmoveCollection(Context context, final NegativeOnClick nOnClick, final PositiveOnClick pOnClick) {
        baseDialog(context, "您是否要取消收藏此商品？", "", "确定", "再看看", nOnClick, pOnClick);
    }

    /**
     * 取消体验订单对话框
     */
    public void cancelExperience(Context context, final NegativeOnClick nOnClick, final PositiveOnClick pOnClick) {
        baseDialog(context, "", "您是否要取消体验？", "确定", "再看看", nOnClick, pOnClick);
    }

    /**
     * 取消订单对话框
     */
    public void cancelOrder(Context context, final NegativeOnClick nOnClick, final PositiveOnClick pOnClick) {
        baseDialog(context, "", "您是否要取消订单？", "确定", "再看看", nOnClick, pOnClick);
    }

    /**
     * 版本更新
     *
     * @param context
     * @param title
     * @param content
     * @param pOnClick
     */
    public void versionTip(Context context, String title, String content, final PositiveOnClick pOnClick) {
        baseDialogForJiuxi(context, title, content, "下次提醒", "马上升级", null, pOnClick);
    }

    /**
     * 版本更新-强制升级
     *
     * @param context
     * @param title
     * @param content
     * @param pOnClick
     */
    public void versionTip(Context context, String title, String content, final PositiveOnClick pOnClick, final NegativeOnClick nOnClick, DialogInterface.OnKeyListener onKeyListener) {
        baseDialogForJiuxi(context, title, content, "取消", "马上升级", nOnClick, pOnClick, onKeyListener,false);
    }

    /**
     * 有标题弹出框
     *
     * @param context    环境上下文
     * @param title      弹出框的标题
     * @param content    弹出框提示的内容
     * @param btn_cancel 实现功能的按钮名称（比如：删除）
     * @param btn_affirm 实现功能的按钮名称（比如：确定）
     * @param nOnClick   实现功能按钮中的事件
     * @param pOnClick   实现功能按钮中的事件
     */
    public void baseDialogForJiuxi(Context context, String title, String content,
                                   String btn_cancel, String btn_affirm,
                                   final NegativeOnClick nOnClick, final PositiveOnClick pOnClick, boolean canCanceled) {
        baseDialogForJiuxi(context,title,content,btn_cancel,btn_affirm,nOnClick,pOnClick,null,canCanceled);
    }


    /**
     * 有标题弹出框
     *
     * @param context    环境上下文
     * @param title      弹出框的标题
     * @param content    弹出框提示的内容
     * @param btn_cancel 实现功能的按钮名称（比如：删除）
     * @param btn_affirm 实现功能的按钮名称（比如：确定）
     * @param nOnClick   实现功能按钮中的事件
     * @param pOnClick   实现功能按钮中的事件
     */
    public void baseDialogForJiuxi(Context context, String title, String content,
                                   String btn_cancel, String btn_affirm,
                                   final NegativeOnClick nOnClick, final PositiveOnClick pOnClick, DialogInterface.OnKeyListener onKeyListener, boolean canCanceled) {
        this.mContext = context;
        dialog = new Dialog(mContext, R.style.custome_dialog_style);
        if(onKeyListener != null)
            dialog.setOnKeyListener(onKeyListener);
        dialog.setCancelable(canCanceled);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.double_button_dialog_for_jiuxi, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(title);
        TextView txt_content = (TextView) view.findViewById(R.id.tv_content);
        txt_content.setText(content);

        if (TextUtils.isEmpty(title)) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setText(title);
        }

        if (TextUtils.isEmpty(content)) {
            txt_content.setVisibility(View.GONE);
        } else {
//            txt_content.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
            txt_content.setText(Html.fromHtml(content));
        }

        LinearLayout ll_cancel = (LinearLayout) view.findViewById(R.id.ll_cancel);
        LinearLayout ll_affirm = (LinearLayout) view.findViewById(R.id.ll_affirm);
        TextView txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
        txt_cancel.setText(btn_cancel);
        TextView txt_ensure = (TextView) view.findViewById(R.id.txt_ensure);
        txt_ensure.setText(btn_affirm);
        ll_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();

                if (nOnClick != null) {
                    nOnClick.onNegativeClick();
                }

            }
        });

        ll_affirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pOnClick != null) {

                    pOnClick.onPositiveClick();
                }
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (0.8 * ScreenSizeUtil.getScreenWidth(mContext));
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    public void baseDialogForJiuxi(Context context, String title, String content,
                                   String btn_cancel, String btn_affirm,
                                   final NegativeOnClick nOnClick, final PositiveOnClick pOnClick) {
        baseDialogForJiuxi(context, title, content, btn_cancel, btn_affirm, nOnClick, pOnClick, true);
    }

    /**
     * 有标题弹出框
     *
     * @param context    环境上下文
     * @param title      弹出框的标题
     * @param content    弹出框提示的内容
     * @param btn_cancel 实现功能的按钮名称（比如：删除）
     * @param btn_affirm 实现功能的按钮名称（比如：确定）
     * @param nOnClick   实现功能按钮中的事件
     * @param pOnClick   实现功能按钮中的事件
     */
    public void baseDialogForJiuxiCustom(Context context, String title, String content,
                                         String btn_cancel, String btn_affirm,
                                         final NegativeOnClick nOnClick, final PositiveOnClick pOnClick, boolean canCanceled) {
        this.mContext = context;
        dialog = new Dialog(mContext, R.style.custome_dialog_style);
        dialog.setCancelable(canCanceled);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.double_button_dialog_for_custom, null);
        TextView txt_content = (TextView) view.findViewById(R.id.tv_content);
        txt_content.setText(content);

        if (TextUtils.isEmpty(content)) {
            txt_content.setVisibility(View.GONE);
        } else {
//            txt_content.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
            txt_content.setText(Html.fromHtml(content));
        }

        LinearLayout ll_cancel = (LinearLayout) view.findViewById(R.id.ll_cancel);
        LinearLayout ll_affirm = (LinearLayout) view.findViewById(R.id.ll_affirm);
        TextView txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
        txt_cancel.setText(btn_cancel);
        TextView txt_ensure = (TextView) view.findViewById(R.id.txt_ensure);
        txt_ensure.setText(btn_affirm);
        ll_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (nOnClick != null) {
                    nOnClick.onNegativeClick();
                }
                dialog.dismiss();
            }
        });
        ll_affirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pOnClick != null) {

                    pOnClick.onPositiveClick();
                }
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (0.8 * ScreenSizeUtil.getScreenWidth(mContext));
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }


    /**
     * 有标题弹出框
     *
     * @param context    环境上下文
     * @param content    弹出框提示的内容
     * @param btn_cancel 实现功能的按钮名称（比如：删除）
     * @param btn_affirm 实现功能的按钮名称（比如：确定）
     * @param nOnClick   实现功能按钮中的事件
     * @param pOnClick   实现功能按钮中的事件
     */
    public void dialogForPermission(Context context, String content,
                                    String btn_cancel, String btn_affirm,
                                    final NegativeOnClick nOnClick, final PositiveOnClick pOnClick) {
        this.mContext = context;
        dialog = new Dialog(mContext, R.style.custome_dialog_style);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.double_button_dialog_for_permission, null);
        TextView txt_content = (TextView) view.findViewById(R.id.tv_content);
        txt_content.setText(content);

        if (TextUtils.isEmpty(content)) {
            txt_content.setVisibility(View.GONE);
        } else {
            txt_content.setText(Html.fromHtml(content));
        }

        LinearLayout ll_cancel = (LinearLayout) view.findViewById(R.id.ll_cancel);
        LinearLayout ll_affirm = (LinearLayout) view.findViewById(R.id.ll_affirm);
        TextView txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
        txt_cancel.setText(btn_cancel);
        TextView txt_ensure = (TextView) view.findViewById(R.id.txt_ensure);
        txt_ensure.setText(btn_affirm);
        ll_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (nOnClick != null) {
                    nOnClick.onNegativeClick();
                }
                dialog.dismiss();
            }
        });
        ll_affirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pOnClick != null) {

                    pOnClick.onPositiveClick();
                }
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (0.8 * ScreenSizeUtil.getScreenWidth(mContext));
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }



    public void baseDialogForEtShopCustom(Context context, String title, String content,
                                          String btn_cancel, String btn_affirm,
                                          final NegativeOnClick nOnClick, final PositiveOnClick pOnClick) {
        baseDialogForJiuxiCustom(context, title, content, btn_cancel, btn_affirm, nOnClick, pOnClick, true);
    }







    /**
     * 有标题弹出框
     *
     * @param context    环境上下文
     * @param content    弹出框提示的内容
     */
    public void baseDialogForThirdLogin(Context context, String content) {
        this.mContext = context;
        dialog = new Dialog(mContext, R.style.custome_dialog_style);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.single_button_dialog_for_third, null);
        TextView txt_content = (TextView) view.findViewById(R.id.tv_content);
        Button txt_suer = (Button) view.findViewById(R.id.btn_sure);
        txt_content.setText(content);
        txt_suer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
//        if (TextUtils.isEmpty(content)) {
//            txt_content.setVisibility(View.GONE);
//        } else {
////            txt_content.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
//            txt_content.setText(Html.fromHtml(content));
//        }

        dialog.setContentView(view);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (0.8 * ScreenSizeUtil.getScreenWidth(mContext));
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void baseDialogForThirdLogin(Context context, String title, String content) {
        baseDialogForThirdLogin(context, content);
    }






    /**
     * 提示性的弹出框
     *
     * @param title   属于标题性质
     * @param content 提示的内容
     * @param onClick
     */
    public void showSingleButtonDialogForJiuxi(String title, String content, String btn_text, final PositiveOnClick onClick) {
        dialog = new Dialog(mContext, R.style.custome_dialog_style);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.single_button_dialog_for_jiuxi, null);
        TextView txt_reminder = (TextView) view.findViewById(R.id.tv_title);
        TextView txt_reason = (TextView) view.findViewById(R.id.tv_content);
        LinearLayout ll_btn = (LinearLayout) view.findViewById(R.id.ll_btn);
        TextView txt_btn = (TextView) view.findViewById(R.id.txt_btn);
        txt_btn.setText(btn_text);

        if (TextUtils.isEmpty(title)) {
            txt_reminder.setVisibility(View.GONE);
        } else {
            txt_reminder.setText(title);
        }

        if (TextUtils.isEmpty(content)) {
            txt_reason.setVisibility(View.GONE);
        } else {
            txt_reason.setText(content);
        }

        ll_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onClick != null)
                    onClick.onPositiveClick();
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (0.8 * ScreenSizeUtil.getScreenWidth(mContext));
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setWindowAnimations(R.style.mystyle);
        dialog.show();
    }


    /**
     * 预约成功的弹出框
     * @param onClick 点击事件
     */
    public void showReservationSuccessDialog(final PositiveOnClick onClick) {
        dialog = new Dialog(mContext, R.style.custome_dialog_style);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.dialog_reservation_success, null);
        TextView img_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        img_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onClick != null)
                    onClick.onPositiveClick();
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (0.8 * ScreenSizeUtil.getScreenWidth(mContext));
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setWindowAnimations(R.style.mystyle);
        dialog.show();
    }

    public void showSingleButtonDialogForJiuxi(String title, String content,
                                               final PositiveOnClick onClick) {
        showSingleButtonDialogForJiuxi(title, content, "确定", onClick);
    }

    /**
     * 有标题弹出框
     *
     * @param context    环境上下文
     * @param title      弹出框的标题
     * @param content    弹出框提示的内容
     * @param btn_cancel 实现功能的按钮名称（比如：删除）
     * @param btn_affirm 实现功能的按钮名称（比如：确定）
     * @param nOnClick   实现功能按钮中的事件
     * @param pOnClick   实现功能按钮中的事件
     */
    public void baseDialogForIOS(Context context, String title, String content,
                                 String btn_cancel, String btn_affirm,
                                 final NegativeOnClick nOnClick, final PositiveOnClick pOnClick, boolean canCanceled) {
        this.mContext = context;
        dialog = new Dialog(mContext, R.style.custome_dialog_style);
        dialog.setCancelable(canCanceled);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.double_button_dialog_for_ios, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(title);
        TextView txt_content = (TextView) view.findViewById(R.id.tv_content);
        txt_content.setText(content);

        if (TextUtils.isEmpty(title)) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setText(title);
        }

        if (TextUtils.isEmpty(content)) {
            txt_content.setVisibility(View.GONE);
        } else {
//            txt_content.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
//            txt_content.setText(Html.fromHtml(content));
            txt_content.setText(content);
        }

        LinearLayout ll_cancel = (LinearLayout) view.findViewById(R.id.ll_cancel);
        LinearLayout ll_affirm = (LinearLayout) view.findViewById(R.id.ll_affirm);
        TextView txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
        txt_cancel.setText(btn_cancel);
        TextView txt_ensure = (TextView) view.findViewById(R.id.txt_ensure);
        txt_ensure.setText(btn_affirm);
        ll_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (nOnClick != null) {
                    nOnClick.onNegativeClick();
                }
                dialog.dismiss();
            }
        });
        ll_affirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pOnClick != null) {

                    pOnClick.onPositiveClick();
                }
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (0.8 * ScreenSizeUtil.getScreenWidth(mContext));
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }
    /**
     * 有标题弹出框
     *
     * @param context    环境上下文
     * @param title      弹出框的标题
     * @param content    弹出框提示的内容
     * @param btn_cancel 实现功能的按钮名称（比如：删除）
     * @param btn_affirm 实现功能的按钮名称（比如：确定）
     * @param nOnClick   实现功能按钮中的事件
     * @param pOnClick   实现功能按钮中的事件
     */
    public void baseDialogForEdit(Context context, String title, String content,
                                 String btn_cancel, String btn_affirm,
                                 final NegativeOnClick nOnClick, final PositiveContentOnClick pOnClick, boolean canCanceled) {
        this.mContext = context;
        dialog = new Dialog(mContext, R.style.custome_dialog_style);
        dialog.setCancelable(canCanceled);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.double_button_dialog_for_edit, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(title);
        final EditText txt_content = (EditText) view.findViewById(R.id.tv_content);
        txt_content.setText(content);

        if (TextUtils.isEmpty(title)) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setText(title);
        }


        LinearLayout ll_cancel = (LinearLayout) view.findViewById(R.id.ll_cancel);
        LinearLayout ll_affirm = (LinearLayout) view.findViewById(R.id.ll_affirm);
        TextView txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
        txt_cancel.setText(btn_cancel);
        TextView txt_ensure = (TextView) view.findViewById(R.id.txt_ensure);
        txt_ensure.setText(btn_affirm);
        ll_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (nOnClick != null) {
                    nOnClick.onNegativeClick();
                }
                dialog.dismiss();
            }
        });
        ll_affirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pOnClick != null) {

                    pOnClick.onPositiveClick(txt_content.getText().toString());
                }
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (0.8 * ScreenSizeUtil.getScreenWidth(mContext));
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    public void baseDialogForIOS(Context context, String title, String content,
                                 String btn_cancel, String btn_affirm,
                                 final NegativeOnClick nOnClick, final PositiveOnClick pOnClick) {
        baseDialogForIOS(context, title, content, btn_cancel, btn_affirm, nOnClick, pOnClick, true);
    }

    /**
     * 完善资料弹出框dialogForUserinfo
     *
     * @param context    环境上下文
     * @param title      弹出框的标题
     * @param content    弹出框提示的内容
     * @param btn_cancel 实现功能的按钮名称（比如：删除）
     * @param btn_affirm 实现功能的按钮名称（比如：确定）
     * @param nOnClick   实现功能按钮中的事件
     * @param pOnClick   实现功能按钮中的事件
     */
    public void dialogForUserinfo(Context context, String title, String content,
                                  String btn_cancel, String btn_affirm,
                                  final NegativeOnClick nOnClick, final PositiveOnClick pOnClick, boolean canCanceled) {
        this.mContext = context;
        dialog = new Dialog(mContext, R.style.custome_dialog_style);
        dialog.setCancelable(canCanceled);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.double_button_dialog_for_userinfo, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(title);
        TextView txt_content = (TextView) view.findViewById(R.id.tv_content);
        txt_content.setText(content);

        if (TextUtils.isEmpty(title)) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setText(title);
        }

        if (TextUtils.isEmpty(content)) {
            txt_content.setVisibility(View.GONE);
        } else {
            txt_content.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
            txt_content.setText(Html.fromHtml(content));
//            txt_content.setText(content);
        }

        LinearLayout ll_cancel = (LinearLayout) view.findViewById(R.id.ll_cancel);
        LinearLayout ll_affirm = (LinearLayout) view.findViewById(R.id.ll_affirm);
        TextView txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
        txt_cancel.setText(btn_cancel);
        TextView txt_ensure = (TextView) view.findViewById(R.id.txt_ensure);
        txt_ensure.setText(btn_affirm);
        ll_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (nOnClick != null) {
                    nOnClick.onNegativeClick();
                }
                dialog.dismiss();
            }
        });
        ll_affirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pOnClick != null) {

                    pOnClick.onPositiveClick();
                }
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (0.8 * ScreenSizeUtil.getScreenWidth(mContext));
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    public void dialogForUserinfo(Context context, String title, String content,
                                  String btn_cancel, String btn_affirm,
                                  final NegativeOnClick nOnClick, final PositiveOnClick pOnClick) {
        dialogForUserinfo(context, title, content, btn_cancel, btn_affirm, nOnClick, pOnClick, true);
    }

    /**
     * 有标题弹出框
     *
     * @param context 环境上下文
     */
    public void baseDialogForNewer(Context context, final CancelOnClick caClick, boolean canCanceled) {
        this.mContext = context;
        dialog = new Dialog(mContext, R.style.custome_dialog_style);
        dialog.setCancelable(canCanceled);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.no_button_dialog_for_newer, null);
        ImageView ivNewerGift = (ImageView) view.findViewById(R.id.iv_newer_gift);
        ivNewerGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (caClick != null) {
                    caClick.onCancelClick();
                }
                dialog.dismiss();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (0.8 * ScreenSizeUtil.getScreenWidth(mContext));
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void baseDialogForNewer(Context context, CancelOnClick caClick) {
        baseDialogForNewer(context, caClick, true);
    }

    public interface PositiveOnClick {
        void onPositiveClick();
    }
    public interface PositiveContentOnClick {
        void onPositiveClick(String content);
    }

    public interface PositiveClick {
        void onPositiveClick(boolean dismissed);
    }


    public interface NegativeOnClick {
        void onNegativeClick();
    }
    public interface NegativeContentOnClick {
        void onNegativeClick(String content);
    }

    public interface NegativeClick {
        void onNegativeClick(boolean dimissed);
    }

    public interface CancelOnClick {
        void onCancelClick();
    }
}
