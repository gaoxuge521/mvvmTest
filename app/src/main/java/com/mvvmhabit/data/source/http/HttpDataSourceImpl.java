package com.mvvmhabit.data.source.http;



import com.mvvmhabit.data.source.HttpDataSource;
import com.mvvmhabit.data.source.http.service.DemoApiService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import me.goldze.mvvmhabit.http.BaseResponse;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by goldze on 2019/3/26.
 */
public class HttpDataSourceImpl implements HttpDataSource {
    private DemoApiService apiService;
    private volatile static HttpDataSourceImpl INSTANCE = null;

    public static HttpDataSourceImpl getInstance(DemoApiService apiService) {
        if (INSTANCE == null) {
            synchronized (HttpDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDataSourceImpl(apiService);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private HttpDataSourceImpl(DemoApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<Object> login() {
        return Observable.just(new Object()).delay(3, TimeUnit.SECONDS); //延迟3秒
    }

    @Override
    public Observable<DemoEntity> loadMore() {
        return Observable.create(new ObservableOnSubscribe<DemoEntity>() {
            @Override
            public void subscribe(ObservableEmitter<DemoEntity> observableEmitter) throws Exception {
                DemoEntity entity = new DemoEntity();
                List<DemoEntity.ItemsEntity> itemsEntities = new ArrayList<>();
                //模拟一部分假数据
                for (int i = 0; i < 10; i++) {
                    DemoEntity.ItemsEntity item = new DemoEntity.ItemsEntity();
                    item.setId(-1);
                    item.setName("模拟条目");
                    itemsEntities.add(item);
                }
                entity.setItems(itemsEntities);
                observableEmitter.onNext(entity);
            }
        }).delay(3, TimeUnit.SECONDS); //延迟3秒
    }

    @Override
    public Observable<BaseResponse<DemoEntity>> demoGet() {
        return apiService.demoGet();
    }

    @Override
    public Observable<BaseResponse<DemoEntity>> demoPost(String catalog) {
        return apiService.demoPost(catalog);
    }

    @Override
    public Observable<BaseResponse<HomeEntity>> getHome(int  page) {
        return apiService.getHome(page);
    }

    @Override
    public Observable<BaseResponse<HomeHeadEntity>> getHomeHeadData() {
        return apiService.getHomeHeadData();
    }

    @Override
    public Observable<BaseResponse<UserInfoEntity>> loginByUsernamePwd(String username, String pwd) {
        return apiService.login(username,pwd);
    }

    @Override
    public Observable<BaseResponse<UserInfoEntity>> getUserInfo() {
        return apiService.getUserInfo();
    }

    @Override
    public Observable<BaseResponse<LogoutEntity>> logout() {
        return apiService.logout();
    }

    @Override
    public Observable<BaseResponse<GoodNumChangeEntity>> goodAdd(String productId, String num) {
        return apiService.goodAdd(productId,num);
    }

    @Override
    public Observable<BaseResponse<FlashCartEntity>> flashCart() {
        return apiService.flashCart();
    }

    @Override
    public Observable<BaseResponse<CartInfoEntity>> cartInfo() {
        return apiService.cartInfo();
    }

    @Override
    public Observable<BaseResponse<GoodNumChangeEntity>> selectAll(int checked) {
        return apiService.selectAll(checked);
    }

    @Override
    public Observable<BaseResponse<GoodNumChangeEntity>> selectOne(int checked, String itemIdList) {
        return apiService.selectOne(checked,itemIdList);
    }

    @Override
    public Observable<BaseResponse<GoodNumChangeEntity>> updateInfo(String itemId, String opType) {
        return apiService.updateInfo(itemId,opType);
    }

    @Override
    public Observable<BaseResponse<GoodNumChangeEntity>> updateInfo(String itemId, String opType, String qty) {
        return apiService.updateInfo(itemId,opType,qty);
    }

    @Override
    public Observable<BaseResponse<GoodDetailEntity>> goodDetail(String productId) {
        return apiService.goodDetail(productId);
    }

    @Override
    public Observable<BaseResponse<FavoriteEntity>> favorite(String productId) {
        return apiService.favorite(productId);
    }

    @Override
    public Observable<BaseResponse<NoticeEntity>> getNotice() {
        return apiService.getNotice();
    }

    @Override
    public Observable<BaseResponse<OrderGenerationEntity>> initOrderByCart(String addressId) {
        return apiService.initOrderByCart(addressId);
    }

    @Override
    public Observable<BaseResponse<AddressListEntity>> getAddressList() {
        return apiService.getAddressList();
    }

    @Override
    public Observable<BaseResponse<SaveAddressEntity>> saveAddress(String id, String area, String provinces, String lng, String city, String address_details, String mobile, String is_default, String lat, String realname) {
        return apiService.saveAddress(id,area,provinces,lng,city,address_details,mobile,is_default,lat,realname);
    }

    @Override
    public Observable<BaseResponse<SaveAddressEntity>> removeAddress(String id) {
        return apiService.removeAddress(id);
    }

    @Override
    public Observable<BaseResponse<ActivityEntity>> getHomeActivity() {
        return apiService.getHomeActivity();
    }

    @Override
    public Observable<BaseResponse<SubmitOrderEntity>> submitOrder(String orderRemark, String paymentMethod, String addressId) {
        return apiService.submitOrder(orderRemark,paymentMethod,addressId);
    }
}
