package com.example.promise;

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

import com.example.promise.Intro.Login_Activity;
import com.example.promise.group.Creating_Group;
import com.example.promise.group.Group_list;
import com.example.promise.group.Participating_Group;
import com.example.promise.retrofit.RetrofitAPI;
import com.example.promise.retrofit.User_Model;
import com.example.promise.schedule.Management_Schedule;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    TextView user_login_id;
    int there_is_group = 0;

    Long user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button create_group_btn = (Button) findViewById(R.id.btn_create_group);

        user_login_id = (TextView) findViewById(R.id.user_id);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        user_id = sharedPref.getLong("user_id", 0);
        String user_login_id = sharedPref.getString("user_login_id", "");

        Log.d("여기", user_id.toString());

        this.user_login_id.setText(user_login_id + "님 환영합니다.");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IPADRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        //uername으로 user 객체 불러오기 ->user_id 저장
        Call<User_Model> call = retrofitAPI.findByUser_login_id(user_login_id);
        call.enqueue(new Callback<User_Model>() {
            @Override
            public void onResponse(Call<User_Model> call, Response<User_Model> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                user_id = response.body().getId();
                Log.d("user_id", user_id.toString());

                user_id = sharedPref.getLong("user_id", 0);
                Log.d("userId", String.valueOf(user_id));

            }

            @Override
            public void onFailure(Call<User_Model> call, Throwable t) {
                Log.e("연결실패", t.getMessage());

            }
        });

        //username으로 user객체 불러오기, user객체의 group_id가 null 인지 조회
        //   만약 null이면 생성, 아니면 "이미 그룹이 있습니다." 출력


        Log.d("2번째", String.valueOf(user_id) + "");

        //그룹 생성
        create_group_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "그룹을 생성할 수 있습니다.", Toast.LENGTH_SHORT).show();
                Log.e("그룹아이디", there_is_group + "");

                Intent intent = new Intent(getApplicationContext(), Creating_Group.class);
                startActivity(intent);

            }

        });


        //그룹 참가
        Button participate_group_btn = (Button) findViewById(R.id.btn_participe_group);
        participate_group_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(getApplicationContext(), "그룹에 참가할 수 있습니다.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), Participating_Group.class);
                startActivity(intent);

            }

        });
//        그룹 관리
        Button management_group_btn = (Button) findViewById(R.id.btn_group_management);
        management_group_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Group_list.class);

                startActivity(intent);
            }
        });

        Button management_schedule_btn = (Button) findViewById(R.id.schedule_management);
        management_schedule_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Management_Schedule.class);
                startActivity(intent);
            }
        });

        Button btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}