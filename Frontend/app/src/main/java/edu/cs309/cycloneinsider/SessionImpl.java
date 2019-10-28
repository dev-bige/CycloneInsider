package edu.cs309.cycloneinsider;

import android.content.Context;
import android.content.SharedPreferences;

import edu.cs309.cycloneinsider.api.Session;

public class SessionImpl implements Session {

    private final Context context;
    private final SharedPreferences preferences;

    public SessionImpl(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences("CycloneInsiderPrefs", 0);
    }

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
}
