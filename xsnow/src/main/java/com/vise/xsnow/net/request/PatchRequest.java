package com.vise.xsnow.net.request;

import android.content.Context;

import com.vise.utils.assist.ClassUtil;
import com.vise.xsnow.net.ViseNet;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.mode.CacheResult;
import com.vise.xsnow.net.subscriber.ApiCallbackSubscriber;

import rx.Observable;
import rx.Subscription;

/**
 * @Description: Patch请求
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 17/5/14 20:29.
 */
public class PatchRequest extends BaseRequest<PatchRequest> {
    @Override
    protected <T> Observable<T> execute(Class<T> clazz) {
        return apiService.patch(suffixUrl, params).compose(this.norTransformer(clazz));
    }

    @Override
    protected <T> Observable<CacheResult<T>> cacheExecute(Class<T> clazz) {
        return execute(clazz).compose(ViseNet.getInstance().getApiCache().transformer(cacheMode, clazz));
    }

    @Override
    protected <T> Subscription execute(Context context, ApiCallback<T> apiCallback) {
        if (isLocalCache) {
            return this.cacheExecute(ClassUtil.getTClass(apiCallback))
                    .subscribe(new ApiCallbackSubscriber(context, apiCallback));
        }
        return this.execute(ClassUtil.getTClass(apiCallback))
                .subscribe(new ApiCallbackSubscriber(context, apiCallback));
    }
}
