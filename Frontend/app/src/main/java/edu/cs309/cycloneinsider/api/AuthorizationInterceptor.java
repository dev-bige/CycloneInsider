package edu.cs309.cycloneinsider.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {
    private static final String TAG = "AuthorizationIntercepto";
    Session session;

    public AuthorizationInterceptor(Session session) {
        this.session = session;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response mainResponse = chain.proceed(chain.request());
        if (mainResponse.code() == 401 || mainResponse.code() == 403) {
            Log.d(TAG, "intercept: INVALIDATE");
            //session.invalidate();
        }
        return mainResponse;
    }
}
