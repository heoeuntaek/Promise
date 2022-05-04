package com.example.promise.schedule;

import static com.example.promise.retrofit.IPaddress.IPADRESS;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.promise.R;
import com.example.promise.retrofit.RetrofitAPI;
import com.example.promise.retrofit.Schedule_Model;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Checking_Schedule extends AppCompatActivity {

    private Long user_id;
    private Long schedule_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking_schedule);

        //user_id 가져오기
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        user_id = sharedPref.getLong("user_id", 0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IPADRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        schedule_id = 1L;
        Call<Schedule_Model> call = retrofitAPI.GetSchedule(user_id, schedule_id);
        call.enqueue(new Callback<Schedule_Model>() {
            @Override
            public void onResponse(Call<Schedule_Model> call, Response<Schedule_Model> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                    Log.e("실패", response.code()+"");
                }
                Schedule_Model schedule_model = response.body();
                Log.e("성공", schedule_model.toString());
                Log.e("타입",schedule_model.schedule_data.getClass().getSimpleName());

                String[] colordata = schedule_model.schedule_data.split(", ");
                Log.e("colordata", Arrays.toString(colordata));
                Log.e("colordata.getClass().getSimpleName();",colordata.getClass().getSimpleName());


            }

            @Override
            public void onFailure(Call<Schedule_Model> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();

            }
        });


        //스케줄 조회 메소드 호출


    }
}