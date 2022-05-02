package com.example.promise;

import static com.example.promise.retrofit.IPaddress.IPADRESS;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.promise.retrofit.RetrofitAPI;
import com.example.promise.retrofit.User_Model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    EditText testtext;
    EditText textinput;
    Button testbtn;
    Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        testtext = (EditText) findViewById(R.id.text11);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IPADRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);



        Call<User_Model> call = retrofitAPI.firstUser();

        call.enqueue(new Callback<User_Model>() {
            @Override
            public void onResponse(Call<User_Model> call, Response<User_Model> response) {
//                if(response.isSuccessful()){
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }

                Log.d("TAG", response.body() + "");

                testtext.setText(response.body().toString());


            }

            @Override
            public void onFailure(Call<User_Model> call, Throwable t) {

                Log.e("연결실패", t.getMessage());

            }
        });


        testbtn = (Button) findViewById(R.id.get_method_btn);
        textinput = (EditText) findViewById(R.id.page_input_edit_text);

        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<User_Model> call = retrofitAPI.findById(Long.parseLong(textinput.getText().toString()));

                call.enqueue(new Callback<User_Model>() {
                    @Override
                    public void onResponse(Call<User_Model> call, Response<User_Model> response) {

                        if(!response.isSuccessful()){
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                            return;
                        }

                        Log.d("TAG", response.body() + "");

                        testtext.setText(response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<User_Model> call, Throwable t) {

                        Log.e("연결실패", t.getMessage());
                    }
                });

            }




        });





        register_btn = (Button) findViewById(R.id.btn_register);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userLoginId = findViewById(R.id.user_login_id_register);
                EditText userPass =  findViewById(R.id.user_pass_register);
                EditText userName = findViewById(R.id.user_name_register);

                String user_login_id = userLoginId.getText().toString();
                String user_pass = userPass.getText().toString();
                String user_name = userName.getText().toString();


                User_Model user_create = new User_Model();
                user_create.setUser_login_id(user_login_id);
                user_create.setUser_pass(user_pass);
                user_create.setUser_name(user_name);

                Log.d("보낼데이터", user_create.toString());

//                Call<Model> call = retrofitAPI.postData(user_login_id, user_pass, user_name);
                Call<User_Model> call = retrofitAPI.register(user_create);
                call.enqueue(new Callback<User_Model>() {
                    @Override
                    public void onResponse(Call<User_Model> call, Response<User_Model> response) {
                        if(!response.isSuccessful()){
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                            Toast.makeText(getApplicationContext(), "중복된 id입니다.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Log.d("보낸데이터", user_create.toString());
                        Log.d("연결이 성공적 : ", response.body().toString());

                        testtext.setText(response.body().getUser_login_id()+"님 환영합니다");
                        Toast.makeText(getApplicationContext(), "회원가입이 정상적으로 되었습니다.", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), Kakao_Login_Activity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<User_Model> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });

                }
            });
        };

    }







