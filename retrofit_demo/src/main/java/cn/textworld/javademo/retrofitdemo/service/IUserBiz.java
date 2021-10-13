package cn.textworld.javademo.retrofitdemo.service;

import cn.textworld.javademo.retrofitdemo.model.User;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface IUserBiz {
    @GET("users")
    Call<List<User>> getUsers();
}
