package com.reactnative.rn;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.reactnative.BuildConfig;
import com.reactnative.R;
import com.reactnative.rn.namodule.splash.SplashModulePackage;
import com.reactnative.rn.namodule.toast.ToastExamplePackage;

/**
 * Created by paul on 2017/2/8.
 * <p>
 * https://reactnative.cn/docs/0.45/integration-with-existing-apps.html#%E9%85%8D%E7%BD%AE%E6%9D%83%E9%99%90%E4%BB%A5%E4%BE%BF%E5%BC%80%E5%8F%91%E4%B8%AD%E7%9A%84%E7%BA%A2%E5%B1%8F%E9%94%99%E8%AF%AF%E8%83%BD%E6%AD%A3%E7%A1%AE%E6%98%BE%E7%A4%BA
 */

public class CustomReactActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {
    private static final int OVERLAY_PERMISSION_REQ_CODE = 0x11;
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    private FrameLayout mFlRootView;
    private View mSplashView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mReactRootView = new ReactRootView(this);
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .addPackage(new ToastExamplePackage())
                .addPackage(new SplashModulePackage())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
        mReactRootView.startReactApplication(mReactInstanceManager, "androidrn", null);

        /**
         * RN 闪屏
         */
        mFlRootView = new FrameLayout(this);
        mSplashView = LayoutInflater.from(this).inflate(R.layout.lunch_screen, mFlRootView, false);
        mFlRootView.addView(mReactRootView);
        mFlRootView.addView(mSplashView);
        setContentView(mFlRootView);

        getOverlayPermission();
    }

    private void getOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
                }
            }
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(this, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostDestroy(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void hideSplaseView() {
        if (mFlRootView == null || mSplashView == null) return;

        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mSplashView, "alpha", 1f, 0f).setDuration(1000);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mFlRootView.removeView(mSplashView);
                mSplashView = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        objectAnimator.start();
    }

}
