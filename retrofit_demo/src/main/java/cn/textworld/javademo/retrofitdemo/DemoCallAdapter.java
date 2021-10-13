package cn.textworld.javademo.retrofitdemo;

import cn.textworld.javademo.retrofitdemo.model.User;
import cn.textworld.javademo.retrofitdemo.service.IUserBiz;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.List;

public class DemoCallAdapter {
    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.31.242:8080/springmvc_users/user/")
                .build();
        IUserBiz userBiz = retrofit.create(IUserBiz.class);
        Call<List<User>> call = userBiz.getUsers();
        call.enqueue(new Callback<List<User>>()
        {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response)
            {
                System.out.println("xxx");
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t)
            {
                System.err.println("error");
            }
        });
    }
}
