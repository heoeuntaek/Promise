package com.example.promise.practice;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        // 네이티브 앱 키로 초기화
        KakaoSdk.init(this, "13fdc35e90c9a79646e3de36f199f936");
    }
}
