package com.ouday.marelyspoon.core.di.modules;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.contentful.java.cda.CDAClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.ouday.marelyspoon.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ouday.marelyspoon.core.di.modules.EndPointsKt.BaseUrl;


@Module
public class NetworkModule {

    @Provides
    @Singleton
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    /**
     * This function provides an Interceptor
     * All general APIs are using this interceptor
     * You can add later on the language, Bearer and any common header
     * to this interceptor
     * @return Interceptor
     */
    @Provides
    Interceptor provideNetworkIntercepted() {
        return chain -> {
            Request.Builder builder = chain.request().newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded");
            Request request = builder.build();
            return chain.proceed(request);
        };
    }


    @Provides
    @Singleton
    @Named("hasNetwork")
    boolean hasNetwork(Context context) {
        boolean isConnected = false;// Initial Value
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (connectivityManager != null) {
            activeNetwork = connectivityManager.getActiveNetworkInfo();
        }
        if (activeNetwork != null && activeNetwork.isConnected())
            isConnected = true;
        return isConnected;
    }

    /**
     * Provides default OkHttpClient
     * @param interceptor instance
     * @return OkHttpClient instance
     */
    private OkHttpClient getOkHttpClient(Interceptor interceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logginIntercepter = new HttpLoggingInterceptor();
        logginIntercepter.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        return builder.readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(logginIntercepter)
                .build();

    }

    @Provides
    @Singleton
    Gson getGson(){
        return new GsonBuilder().setPrettyPrinting().create();
    }

    @Provides
    @Singleton
    RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }


    @Provides
    Retrofit getRetrofit(Gson gson, RxJava2CallAdapterFactory rxJava2CallAdapterFactory) {
            return new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(rxJava2CallAdapterFactory)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory.create())
                    .baseUrl(BaseUrl)
                    .client(getOkHttpClient(provideNetworkIntercepted()))
                    .build();
    }

    @Provides
    @Singleton
    CDAClient getCDAClient(){
        return CDAClient.builder()
                .setSpace("kk2bw5ojx476")
                .setToken("7ac531648a1b5e1dab6c18b0979f822a5aad0fe5f1109829b8a197eb2be4b84c")
                .build();
    }

}



