package com.example.promise.listview2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.promise.R;
import com.example.promise.retrofit.User_Model;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends BaseAdapter {
    private Context context;
    //    private ArrayList<User> userList;
    public List<User_Model> users = new ArrayList<>();

    public UserAdapter(Context context, List<User_Model> users) {
        this.context = context;
        this.users = users;
    }


    //리스트뷰가 출력이 될 때 getView 호출됨
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //LayoutInflater - view를 붙이는 역할
        //inflate- Listview 의 한 컬럼당 item은 그 형태로 붙이는 것
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_item, null);


        //리스트뷰에서 사용할 아이템을 찾기
//        ImageView profile = (ImageView) view.findViewById(R.id.iv_profile);
//        TextView name = (TextView) view.findViewById(R.id.view_group_name);
//        TextView age = (TextView) view.findViewById(R.id.tv_age);
//        TextView greet = (TextView) view.findViewById(R.id.tv_greet);

        TextView user_name = (TextView) view.findViewById(R.id.view_user_name);

        //position = 현재 위치 0부터 셈
        User_Model user_model = users.get(position);
//        User user = userList.get(position);

        //위에서 생성한 뷰에 이미지를 넣어줌
//        profile.setImageResource(group_model.profile);
//        name.setText(group_model.name);
//        age.setText(group_model.age);
//        greet.setText(group_model.greet);
        user_name.setText(user_model.getUser_name());
        return view;
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
        //어떤 위치의 아이템을 반환할지 정해줌

    }

    @Override
    public long getItemId(int position) {
        return 0;

    }

    @Override
    public int getCount() {
        //  리스트뷰에 몇개의 아이템을 있는지 get
        return users.size();

    }


}
