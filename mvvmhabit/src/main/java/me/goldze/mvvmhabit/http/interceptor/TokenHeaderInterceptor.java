package me.goldze.mvvmhabit.http.interceptor;

import java.io.IOException;

import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.StringUtils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = SPUtils.getInstance().getToken();
        String uuid = SPUtils.getInstance().getUUid();
        if (StringUtils.isEmpty(token)) {
            Request originalRequest = chain.request();
            return chain.proceed(originalRequest);
        }else {
            Request originalRequest = chain.request();
            Request updateRequest = originalRequest.newBuilder().header("access-token", token).addHeader("jm-uuid",uuid).build();
            return chain.proceed(updateRequest);
        }
    }
}
