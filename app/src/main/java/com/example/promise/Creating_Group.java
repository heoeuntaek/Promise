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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Creating_Group extends AppCompatActivity {

    EditText groupName;
    Button btn_created;
    Long user_id;
    int there_is_group = 0; //그룹 없는 상태

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_group);


        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String user_login_id = sharedPref.getString("user_login_id", "");

        user_id = sharedPref.getLong("user_id", 0);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IPADRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        btn_created = (Button) findViewById(R.id.create);
        btn_created.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                groupName = findViewById(R.id.group_name);
                String group_name = Creating_Group.this.groupName.getText().toString();
                Group_Model group_model = new Group_Model();
                group_model.setGroup_name(group_name);

                RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
                Call<User_group_Model> call = retrofitAPI.createGroup(user_id, group_model);
                call.enqueue(new Callback<User_group_Model>() {
                    @Override
                    public void onResponse(Call<User_group_Model> call, Response<User_group_Model> response) {
                        if (!response.isSuccessful()) {
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                            return;
                        }
                        Log.e("response.body())",response.body().toString());
                        Intent intent = new Intent(Creating_Group.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(Creating_Group.this, "그룹이 생성되었습니다.", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<User_group_Model> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });



            }
        });

        ;

    };
}



//                call.enqueue(new retrofit2.Callback<Model>() {






