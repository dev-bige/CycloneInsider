package edu.cs309.cycloneinsider.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.cs309.cycloneinsider.SessionImpl;
import edu.cs309.cycloneinsider.api.AuthorizationInterceptor;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.Session;
import edu.cs309.cycloneinsider.api.TokenRenewInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {
    private static final String baseUrl = "http://coms-309-sb-5.misc.iastate.edu:8080";

    @Singleton
    @Provides
    static Session provideSession(Context context) {
        return new SessionImpl(context);
    }

    @Singleton
    @Provides
    static OkHttpClient provideOkHttpClient(Session session) {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.addInterceptor(chain -> {
            if (session.isLoggedIn()) {
                Request request = chain.request().newBuilder().addHeader("Authorization", session.getToken()).build();
                return chain.proceed(request);
            }
            return chain.proceed(chain.request());
        });
        okhttpClientBuilder.addInterceptor(new TokenRenewInterceptor(session));
        okhttpClientBuilder.addInterceptor(new AuthorizationInterceptor(session));
        return okhttpClientBuilder.build();
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static CycloneInsiderService provideCycloneInsiderService(Retrofit retrofit) {
        return retrofit.create(CycloneInsiderService.class);
    }
}
