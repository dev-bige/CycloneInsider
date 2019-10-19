package edu.cs309.cycloneinsider;

import android.app.Application;
import android.content.SharedPreferences;

import edu.cs309.cycloneinsider.api.AuthorizationInterceptor;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.Session;
import edu.cs309.cycloneinsider.api.TokenRenewInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CycloneInsiderApp extends Application {
    private static String baseUrl = "http://coms-309-sb-5.misc.iastate.edu:8080";
    private Session session;
    private CycloneInsiderService apiService;

    public CycloneInsiderService getApiService() {
        if (apiService == null) {
            apiService = provideRetrofit(baseUrl).create(CycloneInsiderService.class);
        }
        return apiService;
    }

    public Session getSession() {
        if (session == null) {
            session = new Session() {
                SharedPreferences preferences = getSharedPreferences("CycloneInsiderPrefs", 0);

                @Override
                public String getToken() {
                    return preferences.getString("token", null);
                }

                @Override
                public void invalidate() {
                    preferences.edit().putString("token", null).apply();
                }

                @Override
                public boolean isLoggedIn() {
                    return getToken() != null;
                }

                @Override
                public void saveToken(String token) {
                    preferences.edit().putString("token", token).apply();
                }
            };
        }
        return session;
    }

    private OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.addInterceptor(chain -> {
            if (getSession().isLoggedIn()) {
                Request request = chain.request().newBuilder().addHeader("Authorization", getSession().getToken()).build();
                return chain.proceed(request);
            }
            return chain.proceed(chain.request());
        });
        okhttpClientBuilder.addInterceptor(new TokenRenewInterceptor(getSession()));
        okhttpClientBuilder.addInterceptor(new AuthorizationInterceptor(getSession()));
        return okhttpClientBuilder.build();
    }

    private Retrofit provideRetrofit(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(provideOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
