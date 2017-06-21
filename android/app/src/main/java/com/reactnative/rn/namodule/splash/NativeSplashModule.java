package com.reactnative.rn.namodule.splash;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.reactnative.rn.CustomReactActivity;

/**
 * Created by paul on 2017/6/21.
 *
 * http://www.devio.org/2016/09/15/React-Native-Android%E5%90%AF%E5%8A%A8%E5%B1%8F-%E5%90%AF%E5%8A%A8%E7%99%BD%E5%B1%8F-%E9%97%AA%E7%8E%B0%E7%99%BD%E5%B1%8F/
 */
@ReactModule(name = "SplashHideModule")
public class NativeSplashModule extends ReactContextBaseJavaModule {

    public NativeSplashModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "SplashHideModule";
    }

    @ReactMethod
    public void hideSplash() {
        /**
         *
         * http://www.lai18.com/content/2467203.html
         *
         * Only the original thread that created a view hierarchy can touch its views.
         *
         * JS 调用Native 的异步
         */
        if (getCurrentActivity() instanceof CustomReactActivity) {
            getCurrentActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((CustomReactActivity) getCurrentActivity()).hideSplaseView();
                }
            });
        }
    }
}
