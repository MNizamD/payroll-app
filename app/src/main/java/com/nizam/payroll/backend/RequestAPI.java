package com.nizam.payroll.backend;

import android.content.Context;
import android.util.Log;

import com.nizam.payroll.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestAPI {

    Retrofit retrofit;
    RetrofitInterface retrofitInterface;
    Context context;

    public RequestAPI(Context context){
        this.context = context;
    }

    public void testRF(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.197:3000/login/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        /// start here
//        HashMap<String, String> empID = new HashMap<>();
//        empID.put("id","3");
//        map.put("password","");
        Call<UserModel> call = retrofitInterface.loginById(3);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.code() == 200){
                    MainActivity.showToast(response.body().getFullName() ,context);
                    Log.e("FLAG GET",String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                MainActivity.showToast(t.getMessage(),context);
                Log.e("FLAG ERROR RF",t.getMessage());
            }
        });

    }

}
