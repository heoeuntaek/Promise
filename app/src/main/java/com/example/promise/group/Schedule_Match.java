package com.example.promise.group;

import static com.example.promise.retrofit.IPaddress.IPADRESS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.promise.R;
import com.example.promise.retrofit.RetrofitAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Schedule_Match extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_match);


        Intent intent = getIntent(); //인텐트객체 선언
        Long group_id = intent.getLongExtra("group_id", 0);


        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        Long user_id = sharedPref.getLong("user_id", 0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IPADRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);


        Button btn_match = findViewById(R.id.btn_match);
        btn_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                1. schedule db 에서 group_id 가 1인 schedule  리스트 조회


//                2. schedule_list에서 scheduledata - > colordata 변환 -> colordata 리스트 뽑기
//                3. 리스트에서 공통되는 값의 위치 조회-> 위치 리스트 만들기
//                배열의 교집합 찾기(https://tinyurl.com/yyuo9wty) -> 교집합 스케줄 만들기 -> 뿌리기
//                4. 위치 리스트를 가지고 스케줄 생성


            }
        });

    }
}