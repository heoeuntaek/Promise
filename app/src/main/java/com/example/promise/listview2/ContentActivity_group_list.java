package com.example.promise.listview2;

import static com.example.promise.retrofit.IPaddress.IPADRESS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.promise.R;
import com.example.promise.retrofit.RetrofitAPI;
import com.example.promise.retrofit.User_group_Model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContentActivity_group_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);


        Intent intent = getIntent(); //인텐트객체 선언
        String result = intent.getStringExtra("name"); //값 가져오기
        TextView textView = findViewById(R.id.textView);
        textView.setText(result);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        Long user_id = sharedPref.getLong("user_id", 0);
        Long group_id = intent.getLongExtra("id", 0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IPADRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        //uername으로 user 객체 불러오기 ->user_id 저장
        Call<User_group_Model> call = retrofitAPI.GetuserGroup(user_id, group_id);
        call.enqueue(new Callback<User_group_Model>() {
            @Override
            public void onResponse(Call<User_group_Model> call, Response<User_group_Model> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "연결실패", Toast.LENGTH_SHORT).show();
                    return;
                }
                User_group_Model user_group_model = response.body();
                Log.e("user_group_model", user_group_model.toString());

            }

            @Override
            public void onFailure(Call<User_group_Model> call, Throwable t) {

            }
        });


    }
}