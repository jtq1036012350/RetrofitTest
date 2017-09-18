package winning.retrofittest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        GetMyFirstData();
//        GetMyWeatherData();
        loginWeb();
    }

    private void loginWeb() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" http://192.168.56.1:8099/OMS.Service/")
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginTest loginTest = retrofit.create(LoginTest.class);
        Call<Object> call = loginTest.getLoginReturn(new User("00"));
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                response.isSuccessful();
                Log.e("infoooo", "normalGet:" + response.body() + "");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("infoooo", "normalGet:" + t.toString() + "");
            }
        });
    }

    private void GetMyFirstData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IUserBiz userBiz = retrofit.create(IUserBiz.class);
        Call<ReturnBean> call = userBiz.getReturn(1);
        call.enqueue(new Callback<ReturnBean>() {
            @Override
            public void onResponse(Call<ReturnBean> call, Response<ReturnBean> response) {
                Log.e("infoooo", "normalGet:" + response.body() + "");
            }

            @Override
            public void onFailure(Call<ReturnBean> call, Throwable t) {
                Log.e("infoooo", "normalGet:" + t.toString() + "");
            }
        });
    }

    private void GetMyWeatherData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://wthrcdn.etouch.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
        IWeatherBiz userBiz = retrofit.create(IWeatherBiz.class);
        Call<Object> call = userBiz.getWeatherReturn("芜湖");
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e("infoooo", "normalGet:" + response.body() + "");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("infoooo", "normalGet:" + t.toString() + "");
            }
        });
    }

    public interface IUserBiz {
        @GET("all/20/{page}")
        Call<ReturnBean> getReturn(@Path("page") int page);
    }

    public interface IWeatherBiz {
        @GET("weather_mini")
        Call<Object> getWeatherReturn(@Query("city") String city);
    }


    public interface LoginTest {
        @POST("Common/LoginWithoutPwd")
        Call<Object> getLoginReturn(@Body User user);
    }

    private OkHttpClient getOkHttpClient() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("zcb", "OkHttp====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);
        return httpClientBuilder.build();
    }
}
