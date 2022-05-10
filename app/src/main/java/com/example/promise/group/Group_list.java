package com.example.promise.group;

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
import com.example.promise.listview2.GroupAdapter;
import com.example.promise.retrofit.Group_Model;
import com.example.promise.retrofit.RetrofitAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Group_list extends AppCompatActivity {

    private Long user_id;

    public List<Group_Model> groups = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        user_id = sharedPref.getLong("user_id", 0);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IPADRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Toast.makeText(getApplicationContext(), "그룹을 선택해주세요", Toast.LENGTH_SHORT).show();

        //uername으로 user 객체 불러오기 ->user_id 저장
        Call<List<Group_Model>> call = retrofitAPI.groupList(user_id);
        call.enqueue(new Callback<List<Group_Model>>() {
            @Override
            public void onResponse(Call<List<Group_Model>> call, Response<List<Group_Model>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Group_list.this, "연결실패", Toast.LENGTH_SHORT).show();
                    Log.e("그룹리스트에서 연결실패", response.message());
                    return;
                }
                groups = response.body();
                Log.e("response.body()", response.body().toString());

                GroupAdapter adapter = new GroupAdapter(getApplicationContext(), groups);
                ListView listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Group_Model selectItem = (Group_Model) parent.getItemAtPosition(position);
//                Toast.makeText(getApplicationContext(), selectItem.name, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), Management_Group.class);
                        intent.putExtra("group_name", selectItem.group_name);
                        intent.putExtra("group_id", selectItem.id);
                        startActivity(intent);
                        finish();

                    }
                });

            }

            @Override
            public void onFailure(Call<List<Group_Model>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

//        userList.add(new User(R.drawable.android, "홍길동", "28", "안녕하세요"));
//        userList.add(new User(R.drawable.android, "홍남원", "21", "반갑습니다"));
//        userList.add(new User(R.drawable.android, "임꺽정", "22", "뒤질래요"));




    }

}