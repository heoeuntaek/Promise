package com.example.promise.schedule;

import static com.example.promise.retrofit.IPaddress.IPADRESS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.promise.R;
import com.example.promise.group.Management_Group;
import com.example.promise.retrofit.RetrofitAPI;
import com.example.promise.retrofit.Schedule_Model;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Get_Schedule extends AppCompatActivity {

    private boolean bool[] = new boolean[120]; //true or false
    private TextView[] textViews = new TextView[120];
    private Long color_data[] = new Long[120];

    private Long user_id;
    private Long schedule_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_schedule);

        //user_id 가져오기
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        user_id = sharedPref.getLong("user_id", 0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IPADRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Button btn_save = findViewById(R.id.btn_save);

        Intent intent = getIntent();
        int save = intent.getIntExtra("save", 5);

        schedule_id = intent.getLongExtra("schedule_id", 0);

        Log.e("save", save + "");

        btn_save.setVisibility(View.INVISIBLE);

        if (save == 1) { //그룹에서 온 경우
            btn_save.setVisibility(View.VISIBLE);
        }


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long group_id = intent.getLongExtra("group_id", 0);

                Call<Schedule_Model> call = retrofitAPI.UpdateScheduleWithGroup(group_id, schedule_id, user_id);
                call.enqueue(new Callback<Schedule_Model>() {
                    @Override
                    public void onResponse(Call<Schedule_Model> call, Response<Schedule_Model> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "스케줄 저장 실패", Toast.LENGTH_SHORT).show();
                            Log.e("스케줄 공유 실패", response.code() + "");
                            return;
                        }

                        Log.e("스케줄 공유 성공", response.body().toString());
                        Toast.makeText(getApplicationContext(), "스케줄이 공유되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Get_Schedule.this, Management_Group.class);
                        intent.putExtra("group_id", group_id);
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<Schedule_Model> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "연결실패", Toast.LENGTH_SHORT).show();
                        Log.e("연결실패", t.getMessage());

                    }
                });


            }
        });


        for (int i = 1; i <= textViews.length - 1; i++) {
            int getID = getResources().getIdentifier("text" + i, "id", "com.example.promise");
            textViews[i] = (TextView) findViewById(getID); // tv[i]에 text1, text2, text3, text4,
        }
        TextView[] nullText = textViews;


        Log.e("schedule_id", schedule_id.toString());

        Call<Schedule_Model> call = retrofitAPI.GetSchedule(user_id, schedule_id);
        call.enqueue(new Callback<Schedule_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Schedule_Model> call, Response<Schedule_Model> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                    Log.e("실패", response.code() + "");
                }
                Schedule_Model schedule_model = response.body();
                String schedule_data = schedule_model.schedule_data;
//                Log.e("성공", schedule_model.toString());
                Log.e("타입", schedule_model.schedule_data.getClass().getSimpleName());


                //문자열에서 [, ] 제거
                schedule_data = schedule_data.replace("[", "");
                schedule_data = schedule_data.replace("]", "");
                Log.e("schedule_data", schedule_data);

                //문자열 -> 배열로 전환
                color_data = Arrays.stream(schedule_data.split(", "))
                        .map(String::trim)
                        .map(Long::valueOf)
                        .toArray(Long[]::new);//Converting String array to Long array

                Log.e("colordata", Arrays.toString(color_data));
                Log.e("colordata.getClass().getSimpleName()", color_data.getClass().getSimpleName());

                for (int i = 1; i <= textViews.length - 1; i++) {
                    int finalI = i;
                    if (color_data[finalI] == 0) {
                        textViews[finalI].setBackgroundResource(R.drawable.table_touch_again);
                    } else if (color_data[finalI] == 1L) {
                        textViews[finalI].setBackgroundResource(R.drawable.table_touch);
                    }
                }


//                Long[] colordata = schedule_model.schedule_data.split(",");
//                Log.e("colordata", Arrays.toString(colordata));
//                Log.e("colordata.getClass().getSimpleName();",colordata.getClass().getSimpleName());
//                Log.e("colordata[0]",colordata[colordata.length-1]);


            }

            @Override
            public void onFailure(Call<Schedule_Model> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();

            }
        });

        Button btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Schedule_List.class);

                startActivity(intent);

            }
        });


        //스케줄 조회 메소드 호출


    }
}