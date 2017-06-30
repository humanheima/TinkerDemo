package com.brotherd.tinkerdemo;

import android.app.Application;

import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;

/**
 * Created by dumingwei on 2017/6/29.
 */
public class App extends Application {

    ApplicationLike applicationLike;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.TINKER_ENABLE) {
            // 我们可以从这里获得Tinker加载过程的信息
            applicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();

            // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
            TinkerPatch.init(applicationLike)
                    .reflectPatchLibrary()
                    //设置收到后台回退要求时,锁屏清除补丁,默认是等主进程重启时自动清除
                    .setPatchRollbackOnScreenOff(true)
                    //设置补丁合成成功后,锁屏重启程序,默认是等应用自然重启
                    .setPatchRestartOnSrceenOff(true)
                    .setPatchCondition("userId", "20170630")
                    .setFetchPatchIntervalByHours(1);//一个小时查询一次

            // 每隔1个小时(通过setFetchPatchIntervalByHours设置)去访问后台时候有更新,通过handler实现轮询的效果
            TinkerPatch.with()
                    .fetchPatchUpdateAndPollWithInterval();
        }
    }

}
