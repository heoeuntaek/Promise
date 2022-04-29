package com.example.promise;

import static com.example.promise.retrofit.IPaddress.IPADRESS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.promise.retrofit.Group_Model;
import com.example.promise.retrofit.RetrofitAPI;
import com.example.promise.retrofit.Schedule_Model;

import java.util.Arrays;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;



public class Writing_Schedule extends AppCompatActivity {
    //private boolean State = false;
    boolean bool[]=new boolean[120];
    TextView[] tv = new TextView[120];
    int color_data[]=new int[120];
    EditText scheduleName;
    Long userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_schedule);
        Button btn = (Button) findViewById(R.id.button);
        Arrays.fill(bool,false);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String user_login_id = sharedPref.getString("user_login_id", "");

        userId= sharedPref.getLong("user_id", 0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IPADRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        for (int i = 1; i <= tv.length-1; i++) {
            int getID = getResources().getIdentifier("text" + i, "id", "com.example.promise");
            tv[i] = (TextView) findViewById(getID);
        }


        for (int i = 1; i <= tv.length-1; i++) {
            int finalI = i;
            tv[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(bool[finalI])
                    {
                        bool[finalI] = false;
                        tv[finalI].setBackgroundResource(R.drawable.table_touch_again);
                        color_data[finalI]=0;
                    }
                    else
                    {
                        bool[finalI] = true;
                        tv[finalI].setBackgroundResource(R.drawable.table_touch);
                        color_data[finalI]=1;
                    }
                }
            });
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"저장되었습니다.",
                        Toast.LENGTH_SHORT).show();
                scheduleName = findViewById(R.id.schedule_name);

                String schedule_name = Writing_Schedule.this.scheduleName.getText().toString();

                Schedule_Model schedule_create=new Schedule_Model();
                schedule_create.setSchedule_name(schedule_name);

                Schedule_Model schedule_created = new Schedule_Model();
                schedule_created.setSchedule_name(schedule_name);

                RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);










            }
        });

    }
}






