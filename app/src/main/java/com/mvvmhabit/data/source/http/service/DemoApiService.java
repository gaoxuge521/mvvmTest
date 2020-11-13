package com.mvvmhabit.data.source.http.service;


import com.mvvmhabit.entity.ActivityEntity;
import com.mvvmhabit.entity.AddressListEntity;
import com.mvvmhabit.entity.CartInfoEntity;
import com.mvvmhabit.entity.DemoEntity;
import com.mvvmhabit.entity.FavoriteEntity;
import com.mvvmhabit.entity.FlashCartEntity;
import com.mvvmhabit.entity.GoodDetailEntity;
import com.mvvmhabit.entity.GoodNumChangeEntity;
import com.mvvmhabit.entity.HomeEntity;
import com.mvvmhabit.entity.HomeHeadEntity;
import com.mvvmhabit.entity.LogoutEntity;
import com.mvvmhabit.entity.NoticeEntity;
import com.mvvmhabit.entity.OrderGenerationEntity;
import com.mvvmhabit.entity.SaveAddressEntity;
import com.mvvmhabit.entity.SubmitOrderEntity;
import com.mvvmhabit.entity.UserInfoEntity;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by goldze on 2017/6/15.
 */

public interface DemoApiService {
    @GET("action/apiv2/banner?catalog=1")
    Observable<BaseResponse<DemoEntity>> demoGet();


    @FormUrlEncoded
    @POST("action/apiv2/banner")
    Observable<BaseResponse<DemoEntity>> demoPost(@Field("catalog") String catalog);
    @GET("v1/catalog/home/all-product")
    Observable<BaseResponse<HomeEntity>>  getHome(@Query("p") int page);

    //商品详情
    @GET("v1/catalog/product/detail")
    Observable<BaseResponse<GoodDetailEntity>>  goodDetail(@Query("productId") String productId);

    @GET("v1/advert/home/advert")
    Observable<BaseResponse<HomeHeadEntity>>  getHomeHeadData();
    @GET("v1/catalog/home/activity-info")
    Observable<BaseResponse<ActivityEntity>>  getHomeActivity();
    @GET("v1/member/address/address-list")
    Observable<BaseResponse<AddressListEntity>>  getAddressList();

    @GET("v1/sys/message/notice-home")
    Observable<BaseResponse<NoticeEntity>>  getNotice();

    @FormUrlEncoded
    @POST("v1/app/app-program/audit-account-login")
    Observable<BaseResponse<UserInfoEntity>>  login(@Field("account") String account, @Field("pwd") String pwd);

    @GET("v1/member/member/user-info")
    Observable<BaseResponse<UserInfoEntity>>  getUserInfo();

    @POST("v1/member/member/logout")
    Observable<BaseResponse<LogoutEntity>>  logout();

    @FormUrlEncoded
    @POST("v1/cart/cart/add")
    Observable<BaseResponse<GoodNumChangeEntity>>  goodAdd(@Field("productId") String productId, @Field("num") String num);
    @FormUrlEncoded
    @POST("v1/member/address/remove")
    Observable<BaseResponse<SaveAddressEntity>>  removeAddress(@Field("id") String id);

    @FormUrlEncoded
    @POST("v1/member/address/save")
    Observable<BaseResponse<SaveAddressEntity>>  saveAddress(@Field("id") String id, @Field("area") String area, @Field("provinces") String provinces
            , @Field("lng") String lng
            , @Field("city") String city
            , @Field("address_details") String address_details
            , @Field("mobile") String mobile
            , @Field("is_default") String is_default
            , @Field("lat") String lat
            , @Field("realname") String realname
    );

    //收藏
    @FormUrlEncoded
    @POST("v1/catalog/product/favorite")
    Observable<BaseResponse<FavoriteEntity>>  favorite(@Field("productId") String productId);

    //opType（0-删除 1-减 2-加）
    @FormUrlEncoded
    @POST("v1/cart/cart/update-info")
    Observable<BaseResponse<GoodNumChangeEntity>>  updateInfo(@Field("itemId") String itemId, @Field("opType") String opType);

    //opType（0-删除 1-减 2-加）
    @FormUrlEncoded
    @POST("v1/cart/cart/update-info")
    Observable<BaseResponse<GoodNumChangeEntity>>  updateInfo(@Field("itemId") String itemId, @Field("opType") String opType, @Field("qty") String qty);

    //提交订单
    @FormUrlEncoded
    @POST("v1/checkout/onepage/submit-order")
    Observable<BaseResponse<SubmitOrderEntity>>  submitOrder(@Field("orderRemark") String orderRemark, @Field("paymentMethod") String paymentMethod, @Field("addressId") String addressId);

    @FormUrlEncoded
    @POST("v1/cart/cart/select-all")
    Observable<BaseResponse<GoodNumChangeEntity>>  selectAll(@Field("checked") int checked);


    @FormUrlEncoded
    @POST("v1/checkout/onepage/init-order-by-cart")
    Observable<BaseResponse<OrderGenerationEntity>>  initOrderByCart(@Field("addressId") String addressId);

    @FormUrlEncoded
    @POST("v1/cart/cart/select-one")
    Observable<BaseResponse<GoodNumChangeEntity>>  selectOne(@Field("checked") int checked, @Field("itemIdList") String itemIdList);

    @POST("/v1/cart/cart/flash-cart")
    Observable<BaseResponse<FlashCartEntity>>  flashCart();

    @GET("/v1/cart/cart/info")
    Observable<BaseResponse<CartInfoEntity>>  cartInfo();

}
