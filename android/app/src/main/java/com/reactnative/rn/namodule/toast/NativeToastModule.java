package com.reactnative.rn.namodule.toast;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by paul on 2017/2/9.
 */
@ReactModule(name = "NativeToastAndroid")
public class NativeToastModule extends ReactContextBaseJavaModule {

    private static final String DURATION_SHORT_KEY = "SHORT";
    private static final String DURATION_LONG_KEY = "LONG";

    public NativeToastModule(ReactApplicationContext reactContext) {
        super(reactContext);

        /**
         * 注册 ActivityEventListener 可用于接受 OnActivityResult
         */
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    /**
     * 注册到 NativeModule中的模块名称 -
     * 注意：模块名前的RCT前缀会被自动移除。所以如果返回的字符串为"RCTToastAndroid"，在JavaScript端依然通过React.NativeModules.ToastAndroid访问到这个模块。
     *
     * 注意 MainPackageModule中已经注册了较多内置 Module ,如果发生名称冲突可以在 ReactModule注解中配置 canOverrideExistingModule 覆盖
     * @return
     */
    @Override
    public String getName() {
        return "NativeToastAndroid";
    }

    /**
     * 导出 Js可使用的常量值，Js利用 key 值访问对应的Value
     * @return
     */
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
        constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
        return constants;
    }

    /**
     * 参数值类型对应 Js 中的值类型
         Boolean -> Bool
         Integer -> Number
         Double -> Number
         Float -> Number
         String -> String
         Callback -> function
         ReadableMap -> Object
         ReadableArray -> Array
     * @param message
     * @param duration
     */
    @ReactMethod
    public void show(String message, int duration) {
        Toast.makeText(getReactApplicationContext(), message, duration).show();
    }


    /**
     * 回调返回值
     * @param msg
     * @param duration
     * @param callback
     */
    @ReactMethod
    public void  showCallBackInfo(final String msg, final int duration, final Callback callback){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UiThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getReactApplicationContext(), msg, duration).show();
                        }
                    });
                    final int time = 2000;
                    Thread.sleep(time);

                    callback.invoke(time);

                    sendEvent(getReactApplicationContext(),"EventSend","React Fire");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    /**
     *  发送事件返回值到 Js
     *
     *  emit 发送的事件名称需要与 js中注册 addListener 中填入名称对应
     */
    public void  sendEvent(ReactContext reactContext,String eventName, Object params){

        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName,params);

    }


    /**
     * onActivityResult
     */
    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener(){

        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            super.onActivityResult(activity, requestCode, resultCode, data);

        }
    };

}