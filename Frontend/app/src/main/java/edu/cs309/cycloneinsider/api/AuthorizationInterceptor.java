package edu.cs309.cycloneinsider.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {
    Session session;

    public AuthorizationInterceptor(Session session) {
        this.session = session;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response mainResponse = chain.proceed(chain.request());
        if (mainResponse.code() == 401 || mainResponse.code() == 403) {
            session.invalidate();
        }
        return mainResponse;
    }
}
