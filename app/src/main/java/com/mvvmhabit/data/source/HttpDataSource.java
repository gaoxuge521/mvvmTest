package com.mvvmhabit.data.source;



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
import retrofit2.http.Query;

/**
 * Created by goldze on 2019/3/26.
 */
public interface HttpDataSource {
    //模拟登录
    Observable<Object> login();

    //模拟上拉加载
    Observable<DemoEntity> loadMore();

    Observable<BaseResponse<DemoEntity>> demoGet();

    Observable<BaseResponse<DemoEntity>> demoPost(String catalog);

    Observable<BaseResponse<HomeEntity>>  getHome(int page);

    Observable<BaseResponse<HomeHeadEntity>>  getHomeHeadData();

    Observable<BaseResponse<UserInfoEntity>> loginByUsernamePwd(String username, String pwd);

    Observable<BaseResponse<UserInfoEntity>>  getUserInfo();

    Observable<BaseResponse<LogoutEntity>>  logout();

    Observable<BaseResponse<GoodNumChangeEntity>>  goodAdd(String productId, String num);

    Observable<BaseResponse<FlashCartEntity>>  flashCart();

    Observable<BaseResponse<CartInfoEntity>>  cartInfo();

    Observable<BaseResponse<GoodNumChangeEntity>>  selectAll(int checked);

    Observable<BaseResponse<GoodNumChangeEntity>>  selectOne(int checked, String itemIdList);

    //opType（0-删除 1-减 2-加）
    Observable<BaseResponse<GoodNumChangeEntity>>  updateInfo(String itemId, String opType);
    //opType（0-删除 1-减 2-加）
    Observable<BaseResponse<GoodNumChangeEntity>>  updateInfo(String itemId, String opType, String qty);

    Observable<BaseResponse<GoodDetailEntity>>  goodDetail(String productId);

    Observable<BaseResponse<FavoriteEntity>>  favorite(String productId);

    Observable<BaseResponse<NoticeEntity>>  getNotice();

    Observable<BaseResponse<OrderGenerationEntity>>  initOrderByCart(String addressId);

    Observable<BaseResponse<AddressListEntity>>  getAddressList();

    Observable<BaseResponse<SaveAddressEntity>>  saveAddress(String id, String area, String provinces
            , String lng
            , String city
            , String address_details
            , String mobile
            , String is_default
            , String lat
            , String realname
    );

    Observable<BaseResponse<SaveAddressEntity>>  removeAddress(String id);

    Observable<BaseResponse<ActivityEntity>>  getHomeActivity();

    Observable<BaseResponse<SubmitOrderEntity>>  submitOrder(String orderRemark, String paymentMethod, String addressId);
}
