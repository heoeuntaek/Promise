package com.example.promise.group;

import static com.example.promise.retrofit.IPaddress.IPADRESS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.promise.MainActivity;
import com.example.promise.R;
import com.example.promise.listview2.User_list;
import com.example.promise.retrofit.RetrofitAPI;
import com.example.promise.retrofit.Schedule_Model;
import com.example.promise.retrofit.User_group_Model;
import com.example.promise.schedule.Get_Schedule;
import com.example.promise.schedule.Schedule_List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Management_Group extends AppCompatActivity {


    Button btn_group_out;

    TextView groupName;
    TextView groupCode;
    Button btn_list_user;
    private Long user_id;
    private Long group_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_group);

        groupName = findViewById(R.id.group_name_management);
        groupCode = findViewById(R.id.textview_group_code);


        Intent intent = getIntent(); //인텐트객체 선언
        String group_name = intent.getStringExtra("group_name"); //값 가져오기
        group_id = intent.getLongExtra("group_id", 0);
        Log.e("group_id", group_id.toString());


        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        Long user_id = sharedPref.getLong("user_id", 0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IPADRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        //스케줄 공유
        Button btn_share_schedule = findViewById(R.id.btn_share_schedule);
        btn_share_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Management_Group.this, Schedule_List.class);
                int save = 1;
                intent.putExtra("save", save);
                intent.putExtra("group_id", group_id);
                startActivity(intent);

            }
        });

        //공유 스케줄 조회
        Button btn_check_schedule = findViewById(R.id.btn_check_schedule);
        btn_check_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Schedule_Model> call = retrofitAPI.GetScheduleWithGroup(group_id, user_id);
                call.enqueue(new Callback<Schedule_Model>() {
                    @Override
                    public void onResponse(Call<Schedule_Model> call, Response<Schedule_Model> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(Management_Group.this, "공유스케줄 조회 실패", Toast.LENGTH_SHORT).show();
                            Log.e("공유스케줄 조회 실패", response.code() + "");
                        }
                        Log.e("공유스케줄 조회 성공", response.body().toString());
                        Schedule_Model schedule_model = response.body();
                        Long schedule_id = schedule_model.getId();

                        Intent intent = new Intent(Management_Group.this, Get_Schedule.class);
                        intent.putExtra("schedule_id", schedule_id);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Schedule_Model> call, Throwable t) {
                        Toast.makeText(Management_Group.this, "연결 실패", Toast.LENGTH_SHORT).show();
                        Log.e("연결 실패", t.getMessage());

                    }
                });
            }
        });

        //스케줄 매칭
        Button btn_match_schedule = findViewById(R.id.btn_match_schedule);
        btn_match_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Management_Group.this, Schedule_Match.class);
                intent.putExtra("group_id", group_id);
                startActivity(intent);
            }
        });

        //사용자 리스트 조회
        //uername으로 user 객체 불러오기 ->user_id 저장
        Call<User_group_Model> call = retrofitAPI.GetuserGroup(user_id, group_id);
        call.enqueue(new Callback<User_group_Model>() {
            @Override
            public void onResponse(Call<User_group_Model> call, Response<User_group_Model> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "연결실패", Toast.LENGTH_SHORT).show();
                    Log.e("그룹 관리에서 그룹 조회 연결실패", response.message());
                    return;
                }
                User_group_Model user_group_model = response.body();
                Log.e("user_group_model", user_group_model.toString());
                String group_code = user_group_model.getGroup_model().getGroup_code();


                groupName.setText("그룹이름 : " + group_name);
                groupCode.setText("그룹코드 : " + group_code);


            }

            @Override
            public void onFailure(Call<User_group_Model> call, Throwable t) {

            }
        });
//그룹 삭제
        btn_group_out = findViewById(R.id.btn_group_out);
        btn_group_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<User_group_Model> call2 = retrofitAPI.deleteUserGroup(user_id, group_id);
                call2.enqueue(new Callback<User_group_Model>() {
                    @Override
                    public void onResponse(Call<User_group_Model> call, Response<User_group_Model> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "연결실패", Toast.LENGTH_SHORT).show();
                            Log.e("그룹 관리에서 삭제 실패", response.message());
                            return;
                        }
                        User_group_Model user_group_model = response.body();
                        Log.e("user_group_model 삭제", user_group_model.toString());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<User_group_Model> call, Throwable t) {

                        Log.e("삭제 실패", t.getMessage());


                    }
                });


            }

        });

        //그룹원 조회

        btn_list_user = findViewById(R.id.btn_list_user);
        btn_list_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), User_list.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("group_id", group_id);
                startActivity(intent);
            }


        });


    }
}
