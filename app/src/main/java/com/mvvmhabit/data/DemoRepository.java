package com.mvvmhabit.data;



import androidx.annotation.VisibleForTesting;

import com.mvvmhabit.data.source.HttpDataSource;
import com.mvvmhabit.data.source.LocalDataSource;
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
import io.reactivex.annotations.NonNull;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.http.BaseResponse;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MVVM的Model层，统一模块的数据仓库，包含网络数据和本地数据（一个应用可以有多个Repositor）
 * Created by goldze on 2019/3/26.
 */
public class DemoRepository extends BaseModel implements HttpDataSource, LocalDataSource {
    private volatile static DemoRepository INSTANCE = null;
    private final HttpDataSource mHttpDataSource;

    private final LocalDataSource mLocalDataSource;

    private DemoRepository(@NonNull HttpDataSource httpDataSource,
                           @NonNull LocalDataSource localDataSource) {
        this.mHttpDataSource = httpDataSource;
        this.mLocalDataSource = localDataSource;
    }

    public static DemoRepository getInstance(HttpDataSource httpDataSource,
                                             LocalDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (DemoRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DemoRepository(httpDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public Observable<Object> login() {
        return mHttpDataSource.login();
    }

    @Override
    public Observable<DemoEntity> loadMore() {
        return mHttpDataSource.loadMore();
    }

    @Override
    public Observable<BaseResponse<DemoEntity>> demoGet() {
        return mHttpDataSource.demoGet();
    }

    @Override
    public Observable<BaseResponse<DemoEntity>> demoPost(String catalog) {
        return mHttpDataSource.demoPost(catalog);
    }

    @Override
    public Observable<BaseResponse<HomeEntity>> getHome(int page) {
        return mHttpDataSource.getHome(page);
    }

    @Override
    public Observable<BaseResponse<HomeHeadEntity>> getHomeHeadData() {
        return mHttpDataSource.getHomeHeadData();
    }

    @Override
    public Observable<BaseResponse<UserInfoEntity>> loginByUsernamePwd(String username, String pwd) {
        return mHttpDataSource.loginByUsernamePwd(username,pwd);
    }

    @Override
    public Observable<BaseResponse<UserInfoEntity>> getUserInfo() {
        return mHttpDataSource.getUserInfo();
    }

    @Override
    public Observable<BaseResponse<LogoutEntity>> logout() {
        return mHttpDataSource.logout();
    }

    @Override
    public Observable<BaseResponse<GoodNumChangeEntity>> goodAdd(String productId, String num) {
        return mHttpDataSource.goodAdd(productId,num);
    }

    @Override
    public Observable<BaseResponse<FlashCartEntity>> flashCart() {
        return mHttpDataSource.flashCart();
    }

    @Override
    public Observable<BaseResponse<CartInfoEntity>> cartInfo() {
        return mHttpDataSource.cartInfo();
    }

    @Override
    public Observable<BaseResponse<GoodNumChangeEntity>> selectAll(int checked) {
        return mHttpDataSource.selectAll(checked);
    }

    @Override
    public Observable<BaseResponse<GoodNumChangeEntity>> selectOne(int checked, String itemIdList) {
        return mHttpDataSource.selectOne(checked,itemIdList);
    }

    @Override
    public Observable<BaseResponse<GoodNumChangeEntity>> updateInfo(String itemId, String opType) {
        return mHttpDataSource.updateInfo(itemId,opType);
    }

    @Override
    public Observable<BaseResponse<GoodNumChangeEntity>> updateInfo(String itemId, String opType, String qty) {
        return mHttpDataSource.updateInfo(itemId,opType,qty);
    }

    @Override
    public Observable<BaseResponse<GoodDetailEntity>> goodDetail(String productId) {
        return mHttpDataSource.goodDetail(productId);
    }

    @Override
    public Observable<BaseResponse<FavoriteEntity>> favorite(String productId) {
        return mHttpDataSource.favorite(productId);
    }

    @Override
    public Observable<BaseResponse<NoticeEntity>> getNotice() {
        return mHttpDataSource.getNotice();
    }

    @Override
    public Observable<BaseResponse<OrderGenerationEntity>> initOrderByCart(String addressId) {
        return mHttpDataSource.initOrderByCart(addressId);
    }

    @Override
    public Observable<BaseResponse<AddressListEntity>> getAddressList() {
        return mHttpDataSource.getAddressList();
    }

    @Override
    public Observable<BaseResponse<SaveAddressEntity>> saveAddress(String id, String area, String provinces, String lng, String city, String address_details, String mobile, String is_default, String lat, String realname) {
        return mHttpDataSource.saveAddress(id,area,provinces,lng,city,address_details,mobile,is_default,lat,realname);
    }

    @Override
    public Observable<BaseResponse<SaveAddressEntity>> removeAddress(String id) {
        return mHttpDataSource.removeAddress(id);
    }

    @Override
    public Observable<BaseResponse<ActivityEntity>> getHomeActivity() {
        return mHttpDataSource.getHomeActivity();
    }

    @Override
    public Observable<BaseResponse<SubmitOrderEntity>> submitOrder(String orderRemark, String paymentMethod, String addressId) {
        return mHttpDataSource.submitOrder(orderRemark,paymentMethod,addressId);
    }

    @Override
    public void saveUserName(String userName) {
        mLocalDataSource.saveUserName(userName);
    }

    @Override
    public void savePassword(String password) {
        mLocalDataSource.savePassword(password);
    }

    @Override
    public String getUserName() {
        return mLocalDataSource.getUserName();
    }

    @Override
    public String getPassword() {
        return mLocalDataSource.getPassword();
    }

    @Override
    public boolean saveIsLogin(boolean isLogin) {
        return mLocalDataSource.saveIsLogin(isLogin);
    }


    @Override
    public boolean isLogin() {
        return mLocalDataSource.isLogin();
    }
}
