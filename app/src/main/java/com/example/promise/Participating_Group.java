package com.example.promise;

import static com.example.promise.retrofit.IPaddress.IPADRESS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.promise.retrofit.Group_Model;
import com.example.promise.retrofit.RetrofitAPI;
import com.example.promise.retrofit.User_group_Model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Participating_Group extends AppCompatActivity {

    Button btnParticipe;
    EditText groupCode;
    Long get_id;
    Group_Model group_model;
    Long user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participate_group);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String user_login_id = sharedPref.getString("user_login_id", "");


        user_id = sharedPref.getLong("user_id", 0);



        Gson gson = new GsonBuilder() .setLenient() .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IPADRESS)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        btnParticipe = findViewById(R.id.btn_participe);
        btnParticipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //group_code로 group_id 불러오기 -get

                groupCode= findViewById(R.id.group_code);
                String group_code = groupCode.getText().toString();

                Call<User_group_Model> call2 = retrofitAPI.participe_group(user_id, group_code);
                call2.enqueue(new Callback<User_group_Model>() {
                    @Override
                    public void onResponse(Call<User_group_Model> call, Response<User_group_Model> response) {
                        if (!response.isSuccessful()) {
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                            return;
                        }
                        Log.e("response.body()", response.body().toString());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "그룹에 참여하였습니다.", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onFailure(Call<User_group_Model> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());

                    }
                });
                //user_login_id로 user객체 불러와서 객체에 user_group주입, 업데이트 -patch

                //user_login_id로 user_id 불러옴
                //user_id로 patch user 보내기



            }
        });
    }
}