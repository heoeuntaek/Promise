package com.example.promise.group;

import static com.example.promise.retrofit.IPaddress.IPADRESS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.promise.R;
import com.example.promise.retrofit.Group_Model;
import com.example.promise.retrofit.RetrofitAPI;
import com.example.promise.retrofit.Schedule_Model;
import com.example.promise.schedule.Get_Schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Schedule_Match extends AppCompatActivity {

    private List<Schedule_Model> schedules;
    private Long color_data[] = new Long[120];
    private List<Long[]> color_data_list = new ArrayList<>();
    private Long[] matched_schedule_array = new Long[120];

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
                Call<List<Schedule_Model>> call = retrofitAPI.GetScheduleListWithGroup(group_id);
                call.enqueue(new Callback<List<Schedule_Model>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<List<Schedule_Model>> call, Response<List<Schedule_Model>> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                            Log.e("response", response.code() + "");
                        }
                        Log.e("response", response.body().toString());
                        schedules = response.body();

                        //                2. schedule_list에서 scheduledata - > colordata 변환 -> colordata 리스트 뽑기
                        for (Schedule_Model schedule : schedules) {
                            String schedule_data = schedule.getSchedule_data();
                            Log.e("schedule_data", schedule_data);

                            schedule_data = schedule_data.replace("[", "");
                            schedule_data = schedule_data.replace("]", "");
                            Log.e("schedule_data", schedule_data);

                            //문자열 -> 리스트로 전환
                            //리스트 - > 배열로 전환
                            //문자열 -> 배열로 전환
                            color_data = Arrays.stream(schedule_data.split(", "))
                                    .map(String::trim)
                                    .map(Long::valueOf)
                                    .toArray(Long[]::new);//Converting String array to Long array

                            Log.e("colordata", Arrays.toString(color_data));


                            color_data_list.getClass().getName();
                            Log.e("color_data_list.getClass().getName()", color_data_list.getClass().getName());
                            Log.e("color_data_list", color_data_list.toString());

                            color_data_list.add(color_data);
                            color_data_list.get(0).getClass().getName();
                            Log.e("color_data_list.get(0)", Arrays.toString(color_data_list.get(0)));
                            Log.e("color_data_list.get(0).getClass().getName();", color_data_list.get(0).getClass().getName());
                        }
                        for (Long[] color_data : color_data_list) {
                            Log.e("color_data--", Arrays.toString(color_data));
                        }

                        for (int i = 0; i < matched_schedule_array.length; i++) {
                            matched_schedule_array[i] = 0L;
                        }

//                            배열의 교집합 찾기(https://tinyurl.com/yyuo9wty) -> 교집합 스케줄 만들기 -> 뿌리기
                        for (int i = 0; i < color_data_list.size(); i++) {
                            if (i == color_data_list.size() - 1) continue;
                            for (int j = 1; j < color_data.length; j++) {
                                color_data_list.get(i)[j] = color_data_list.get(i)[j] & color_data_list.get(i + 1)[j];
                                matched_schedule_array[j] = color_data_list.get(i)[j];
                            }
                        }
//                        Long[] matched_schedule = color_data_list.get(color_data_list.size()-1);
                        Log.e("matched_schedule__", Arrays.toString(matched_schedule_array));
                        Log.e("group_id__", group_id + "");

                        //matched_schedule 을 스트링으로 변환
                        String matched_schedule = Arrays.toString(matched_schedule_array);
                        //matched_schedule 저장

                        Group_Model group_model = new Group_Model();
                        group_model.setMatched_schedule(matched_schedule);


                        Call<Group_Model> call2 = retrofitAPI.UpdateMatchedSchedule(group_id, group_model);
                        call2.enqueue(new Callback<Group_Model>() {
                            @Override
                            public void onResponse(Call<Group_Model> call, Response<Group_Model> response) {
                                if (!response.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "매칭 스케줄 저장 실패", Toast.LENGTH_SHORT).show();
                                    Log.e("매칭 스케줄 저장 실패", response.code() + "");
                                }
                                Group_Model group_model = response.body();
                                Log.e("매칭 스케줄 저장 성공", response.body().toString());
                                Toast.makeText(getApplicationContext(), "매칭 스케줄 저장 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Get_Schedule.class);
                                intent.putExtra("group_id", group_id);
                                int save = 3;
                                intent.putExtra("save", save);
                                startActivity(intent);

                            }

                            @Override
                            public void onFailure(Call<Group_Model> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "스케줄 저장 실패", Toast.LENGTH_SHORT).show();
                                Log.e("스케줄 저장 실패", t.getMessage());

                            }
                        });


                    }

                    @Override
                    public void onFailure(Call<List<Schedule_Model>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                        Log.e("response", t.getMessage());

                    }
                });


            }
        });

        Button btn_getMatched_Schedule = (Button) findViewById(R.id.btn_getMatched_Schedule);
        btn_getMatched_Schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Get_Schedule.class);
                intent.putExtra("group_id", group_id);
                int save = 3;
                intent.putExtra("save", save);
                startActivity(intent);
            }
        });


    }

    ;
}