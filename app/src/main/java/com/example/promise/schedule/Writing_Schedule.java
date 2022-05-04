package com.example.promise.schedule;

import static com.example.promise.retrofit.IPaddress.IPADRESS;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


public class Writing_Schedule extends AppCompatActivity {
    //private boolean State = false;
    boolean bool[] = new boolean[120]; //true or false
    TextView[] textViews = new TextView[120];
    String color_data[] = new String[120];
    EditText scheduleName;
    Long user_id;
    String schedule_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_schedule);
        Button btn_schedule_register = (Button) findViewById(R.id.btn_schedule_register);

        String[] null_data = color_data;
        // 배열의 값 일괄 선언
        Arrays.fill(bool, false);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        user_id = sharedPref.getLong("user_id", 0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IPADRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);


        // view로부터 tv[i] 선언
        for (int i = 1; i <= textViews.length - 1; i++) {
            int getID = getResources().getIdentifier("text" + i, "id", "com.example.promise");
            textViews[i] = (TextView) findViewById(getID); // tv[i]에 text1, text2, text3, text4,
        }
        TextView[] nullText = textViews;


        for (int i = 1; i <= textViews.length - 1; i++) {
            int finalI = i;
            textViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bool[finalI]) { // 데이터가 있다면?
                        bool[finalI] = false;
                        textViews[finalI].setBackgroundResource(R.drawable.table_touch_again);
                        Writing_Schedule.this.color_data[finalI] = "0";
                    } else {
                        bool[finalI] = true;
                        textViews[finalI].setBackgroundResource(R.drawable.table_touch);
                        Writing_Schedule.this.color_data[finalI] = "1";
                    }
                }
            });
        }
        btn_schedule_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Map<String,Object> map=new HashMap<>();
                for(int index=1;index<color_data.length-1;index++){
                    map.put("color_data["+index+"]=",color_data[index]);
                }*/
                scheduleName = findViewById(R.id.schedule_name);

                if (scheduleName.getText().toString().equals("")) {
                    Toast.makeText(Writing_Schedule.this, "스케줄 이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    //스케줄 저장
                    Schedule_Model schedule_model = new Schedule_Model();
                    schedule_model.setSchedule_name(scheduleName.getText().toString());
                    String schedule_data = Arrays.toString(color_data);
                    schedule_model.setSchedule_data(schedule_data);

                    Log.e("color_data", schedule_data);


                    Call<Schedule_Model> call = retrofitAPI.createSchedule(user_id, schedule_model);
                    call.enqueue(new Callback<Schedule_Model>() {
                        @Override
                        public void onResponse(Call<Schedule_Model> call, Response<Schedule_Model> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(Writing_Schedule.this, "스케줄 저장 실패", Toast.LENGTH_SHORT).show();
                                Log.e("스케줄 저장 실패", response.code() + "");
                            } else {
                                Toast.makeText(Writing_Schedule.this, "스케줄 저장 성공", Toast.LENGTH_SHORT).show();
                                Schedule_Model scheduleModel_saved = response.body();
                                Log.e("스케줄 저장 성공", scheduleModel_saved.toString());


                            }
                        }

                        @Override
                        public void onFailure(Call<Schedule_Model> call, Throwable t) {
                            Toast.makeText(Writing_Schedule.this, "스케줄 저장 연결 실패", Toast.LENGTH_SHORT).show();
                            Log.e("스케줄 저장 연결 실패", t.getMessage());

                        }
                    });


//                        HashMap<String, Object> param = new HashMap<String, Object>();
//                        param.put("schedule_name", schedule_name);
//                        param.put("color_data", Writing_Schedule.this.color_data);


                    ;


//                    retrofitAPI.createSchedule(param).enqueue(new Callback<Schedule_Model>() {
//                        @Override
//                        public void onResponse(Call<Schedule_Model> call, Response<Schedule_Model> response) {
//                            if (!response.isSuccessful()) {
//                                Log.e("연결이 비정상적 : ", "error code : " + response.code());
//                                return;
//                            }
//
//                            Log.d("보낸데이터", createSchedule.toString());
//                            Log.d("연결이 성공적 : ", response.body().toString());
//
//                            Toast.makeText(getApplicationContext(), "저장되었습니다.",
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<Schedule_Model> call, Throwable t) {
//                            Log.e("연결실패", t.getMessage());
//                        }
//                    });
                }


            }
        });

        ;

    }

    ;


}

;







