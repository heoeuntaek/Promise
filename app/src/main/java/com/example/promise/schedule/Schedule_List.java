package com.example.promise.schedule;

import static com.example.promise.retrofit.IPaddress.IPADRESS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.promise.R;
import com.example.promise.listview2.ScheduleAdapter;
import com.example.promise.retrofit.RetrofitAPI;
import com.example.promise.retrofit.Schedule_Model;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Schedule_List extends AppCompatActivity {

    private Long user_id;

    public List<Schedule_Model> schedules = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        user_id = sharedPref.getLong("user_id", 0);

        Intent intent = getIntent();
        int save = intent.getIntExtra("save", 5);
        Long group_id = intent.getLongExtra("group_id", 0);


        Log.e("save", save + "");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IPADRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        //uername으로 user 객체 불러오기 ->user_id 저장
        Call<List<Schedule_Model>> call = retrofitAPI.schedule_List(this.user_id);
        call.enqueue(new Callback<List<Schedule_Model>>() {
            @Override
            public void onResponse(Call<List<Schedule_Model>> call, Response<List<Schedule_Model>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "연결실패", Toast.LENGTH_SHORT).show();
                    Log.e("그룹리스트에서 연결실패", response.message());
                    return;
                }
                schedules = response.body();
                Log.e("response.body()", response.body().toString());

                ScheduleAdapter adapter = new ScheduleAdapter(getApplicationContext(), schedules);
                ListView listView = (ListView) findViewById(R.id.listView_schedule);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Schedule_Model selectItem = (Schedule_Model) parent.getItemAtPosition(position);
//                Toast.makeText(getApplicationContext(), selectItem.name, Toast.LENGTH_SHORT).show();
                        Log.e("selectItem",selectItem.id.toString());

                        Intent intent = new Intent(getApplicationContext(), Get_Schedule.class);
                        intent.putExtra("schedule_id", selectItem.id);
                        intent.putExtra("save", save);
                        intent.putExtra("group_id", group_id);

                        startActivity(intent);
                        finish();

                    }
                });

            }

            @Override
            public void onFailure(Call<List<Schedule_Model>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

//        userList.add(new User(R.drawable.android, "홍길동", "28", "안녕하세요"));
//        userList.add(new User(R.drawable.android, "홍남원", "21", "반갑습니다"));
//        userList.add(new User(R.drawable.android, "임꺽정", "22", "뒤질래요"));


    }

}