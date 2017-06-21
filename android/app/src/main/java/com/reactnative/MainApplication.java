package com.reactnative;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.reactnative.rn.namodule.splash.SplashModulePackage;
import com.reactnative.rn.namodule.toast.ToastExamplePackage;

import java.util.Arrays;
import java.util.List;

/**
 * Created by paul on 2017/2/9.
 */

public class MainApplication extends Application implements ReactApplication {

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return true;
        }

        /**
         * 利用 ReactActivity 时使用该 ReactNativeHost，若自定义 ReactInstanceManager 则需将NativeModule在初始化时加载进入
         * @return
         */
        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    new ToastExamplePackage(),
                    new SplashModulePackage());
        }
    };
    private Activity mCurrentActivity;

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, false);
        registerActivityLifecycleCallbacks(new ActivityLifeCycle());
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    private class ActivityLifeCycle implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            mCurrentActivity = activity;
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            mCurrentActivity = null;
        }
    }
}
