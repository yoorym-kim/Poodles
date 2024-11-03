package org.techtown.poodles;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCreator {
    String baseUrl = "https://api.openai.com/v1/chat/completions";

    private HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    // Interceptor를 통해 Http통신에 일괄적으로 header를 삽입해준다.
    private Interceptor interceptor = new Interceptor() {
        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request.Builder request = chain.request().newBuilder();
            request.addHeader("Authorization", "Bearer" + "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");
            return chain.proceed(request.build());
        }
    };

    // OkHttpClient를 만들어 time out에 대한 시간 정의 및 위에서 만들어 주었던 interceptor를 넣어준다. >> Retrofit에 달아준다.
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build();

    public Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
