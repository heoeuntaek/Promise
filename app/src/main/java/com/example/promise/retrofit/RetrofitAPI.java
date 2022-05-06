package com.example.promise.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface RetrofitAPI {

    //    Call<Model> findById(@Body Model model); //이건 바디 요청시 사용하는거
    @GET("api/users/1")
    Call<User_Model> firstUser();

    @GET("api/users/{id}")
    Call<User_Model> findById(@Path("id") Long id);


    @POST("api/users")
    Call<User_Model> register(@Body User_Model model);

    @POST("api/login")
    Call<User_Model> login(@Body User_Model model);


    @GET("api/user_id/{user_login_id}")
    Call<User_Model> findByUser_login_id(@Path("user_login_id") String user_login_id);

    @GET("api/user_id_dto/{user_login_id}")
    Call<User_Model_dto> findByUser_login_id_dto(@Path("user_login_id") String user_login_id);


//    @DELETE("api/group/{group_id}/{user_login_id}")
//    Call<User_Model> deleteGroup(@Path("group_id") String group_id
//            , @Path("user_login_id") String user_login_id);

    @GET("api/groupcode/{group_code}")
    Call<Group_Model> findBy_group_code(@Path("group_code") String group_code);

    //그룹 생성
    @POST("api/group/{user_id}")
    Call<User_group_Model> createGroup(@Path("user_id") Long user_id, @Body Group_Model group_model);

    //그룹 참가
    @PATCH("api/group/{user_id}/{group_code}")
    Call<User_group_Model> participe_group(@Path("user_id") Long user_id, @Path("group_code") String group_code );

    //그룹 리스트 조회
    @GET("api/group/grouplist/{user_id}")
    Call<List<Group_Model>>groupList(@Path("user_id") Long user_id);

    //사용자 1에 대한 그룹1조회
    @GET("api/userGroup/{user_id}/{group_id}")
    Call<User_group_Model>GetuserGroup(@Path("user_id") Long user_id, @Path("group_id") Long group_id);


    @DELETE("api/userGroup/{user_id}/{group_id}")
    Call<User_group_Model>deleteUserGroup(@Path("user_id") Long user_id, @Path("group_id") Long group_id);

    //사용자 리스트 조회
    @GET("api/group/userlist/{group_id}")
    Call<List<User_Model>>GetUserList(@Path("group_id") Long group_id);


    //스케줄 생성
    @POST("api/schedule/{user_id}")
    Call<Schedule_Model> createSchedule(@Path("user_id") Long user_id,@Body Schedule_Model schedule_model);

    //스케줄 조회
    @GET("api/{user_id}/{schedule_id}")
    Call<Schedule_Model> GetSchedule(@Path("user_id") Long user_id, @Path("schedule_id") Long schedule_id);

    @GET("api/schedules/{user_id}")
    Call<List<Schedule_Model>> schedule_List(@Path("user_id") Long user_id);

    /*@GET("api/data")
    Call<Schedule_Model> getData(@Query("shcedule_name") String schedule_name, @Query("data") String data);*/

//    @FormUrlEncoded
//    @POST("api/data")
//    Call<Schedule_Model> createSchedule(@FieldMap HashMap<String, Object> param);



    //group_code로 group_id 불러오기 -get
    //user_login_id로 user객체 불러와서 객체에 user_group주입, 업데이트 -patch


//    @FormUrlEncoded
//    @POST("api/users")
//    Call<Model> postData(
//            @Field("user_id") String user_id,
//            @Field("user_pass") String user_pass,
//            @Field("user_name") String user_name
//    );

    //@FormUrlEncoded
    //@POST("/auth/overlapChecker")
    //Call<Model__CheckAlready> postOverlapCheck(@Field("phone") String phoneNum, @Field("message") String message); //이건 요청시 사용하는거 (*데이터를 보낼때)
}



