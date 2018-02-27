package xyy.retrofitdemo.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    public static final String url = "http://www.kuaidi100.com/";
    private static final int TIMEOUT = 15;
    private HttpService httpService;
    private volatile static RetrofitManager singleton;
    private boolean debugMode;

    private RetrofitManager() {
        //OkHttp初始化
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        //debug模式打印网络请求日志
        if (debugMode){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        //Retrofit初始化
        Retrofit retrofit = new Retrofit.Builder()
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(url)
            .build();
        httpService = retrofit.create(HttpService.class);
    }

    public static RetrofitManager getInstance() {
        if (singleton == null) {
            synchronized (RetrofitManager.class) {
                if (singleton == null) {
                    singleton = new RetrofitManager();
                }
            }
        }
        return singleton;
    }

    public static HttpService getService(){
        return getInstance().httpService;
    }
}
