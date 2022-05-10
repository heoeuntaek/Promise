package com.example.promise.Intro;

import static com.example.promise.retrofit.IPaddress.IPADRESS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.promise.MainActivity;
import com.example.promise.R;
import com.example.promise.retrofit.RetrofitAPI;
import com.example.promise.retrofit.User_Model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Kakao_Login_Activity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private TextView nickName;
    private ImageView profileImage;

    private EditText userLoginId;
    private EditText userPass;
    private Button btn_login;

    private Long user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_login);

//        Button list_view = findViewById(R.id.listview_btn);
//        list_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Kakao_Login_Activity.this, MainActivity3.class);
//                startActivity(intent);
//            }
//        });





        userLoginId = findViewById(R.id.user_login_id_login);
        userPass = findViewById(R.id.user_pass_login );
        btn_login = findViewById(R.id.login_btn);


        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String user_login_id = userLoginId.getText().toString();
                String user_pass = userPass.getText().toString();

                User_Model user_login = new User_Model();
                user_login.setUser_login_id(user_login_id);
                user_login.setUser_pass(user_pass);



                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(IPADRESS)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);




                Call<User_Model> call = retrofitAPI.login(user_login);
                call.enqueue(new Callback<User_Model>() {
                    @Override
                    public void onResponse(Call<User_Model> call, Response<User_Model> response) {

                        Log.e("response.code() + ", response.message());
                        Log.e("response.code() + ", response.code()+"");

                        if(response.code()==500)
                        {
                            Toast.makeText(getApplicationContext(), "아이디가 없습니다.", Toast.LENGTH_SHORT).show();
                            Log.e("아이디가 없습니다.", "아이디가 없습니다.");
                        }

                        if (response.isSuccessful()) {




                            User_Model login_user = response.body();
                            Log.d(TAG, "ID: " + response.body().toString());


                            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();

                            editor.putString("user_login_id", user_login_id);
                            Call<User_Model> call2 =retrofitAPI.findByUser_login_id(user_login_id);
                            call2.enqueue(new Callback<User_Model>() {
                                @Override
                                public void onResponse(Call<User_Model> call, Response<User_Model> response) {
                                    user_id= response.body().getId();
                                    editor.putLong("user_id", user_id);
                                    editor.apply();
                                }

                                @Override
                                public void onFailure(Call<User_Model> call, Throwable t) {
                                    Toast.makeText(Kakao_Login_Activity.this, "아이디를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();

                                }
                            });





                            Intent intent = new Intent(Kakao_Login_Activity.this, MainActivity.class);
                            startActivity(intent);

                        }
                        else if(response.code() == 400){
                            Log.d(TAG, "ID: " + response.code());
                            //비번이 틀립니다
                            Toast.makeText(getApplicationContext(), "비번이 틀립니다", Toast.LENGTH_LONG).show();

                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<User_Model> call, Throwable t) {

//                        Toast.makeText(getApplicationContext(), "비번이 틀립니다", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "에러메세지: " + t.getMessage());
                    }
                });



            }
        });



//        회원가입 으로 이동
        Button btn2 = (Button) findViewById(R.id.to_register_btn);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Kakao_Login_Activity.this, Register.class);
                startActivity(intent);
            }
        });








    }
}





//   <meta-data
//            android:name="com.kakao.sdk.AppKey"
//            android:value=	"13fdc35e90c9a79646e3de36f199f936"/>

//
