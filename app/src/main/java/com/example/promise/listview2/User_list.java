package com.example.promise.listview2;

import static com.example.promise.retrofit.IPaddress.IPADRESS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.promise.R;
import com.example.promise.retrofit.RetrofitAPI;
import com.example.promise.retrofit.User_Model;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class User_list extends AppCompatActivity {

    private Long user_id;

    public List<User_Model> users = new ArrayList<>();
    private Long group_id;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);


        Intent intent = getIntent(); //인텐트객체 선언
        String group_name = intent.getStringExtra("group_name");
        group_id = intent.getLongExtra("group_id", 0);
        String type = group_id.getClass().getName();
        Log.d("group_id type", type);


        Log.e("group_id", group_id.toString());

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        user_id = sharedPref.getLong("user_id", 0);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IPADRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build();


        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        //uername으로 user 객체 불러오기 ->user_id 저장
        Call<List<User_Model>> call = retrofitAPI.GetUserList(group_id);
        call.enqueue(new Callback<List<User_Model>>() {
            @Override
            public void onResponse(Call<List<User_Model>> call, Response<List<User_Model>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(User_list.this, "유저리스트에서 연결실패", Toast.LENGTH_SHORT).show();
                    Log.e("유저리스트에서 연결실패 1", "error code : "+response.code());
                    return;
                }
                users = response.body();
                Log.e("유저리스트에서 연결성공", response.body().toString());

                UserAdapter adapter = new UserAdapter(getApplicationContext(), users);
                ListView listView = (ListView) findViewById(R.id.listView_userList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<User_Model>> call, Throwable t) {
                Log.e("유저리스트에서 연결실패2", t.getMessage());
            }
        });

//        userList.add(new User(R.drawable.android, "홍길동", "28", "안녕하세요"));
//        userList.add(new User(R.drawable.android, "홍남원", "21", "반갑습니다"));
//        userList.add(new User(R.drawable.android, "임꺽정", "22", "뒤질래요"));


    }

}