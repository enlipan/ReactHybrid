package com.reactnative.rn;

import android.os.Bundle;

import com.facebook.react.ReactActivity;

import javax.annotation.Nullable;

/**
 * Created by paul on 2017/6/21.
 */

public class SubReactActivity extends ReactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 注册的RN组件名称
     *
     * @return
     */
    @Nullable
    @Override
    protected String getMainComponentName() {
        return "androidrn";
    }


}
