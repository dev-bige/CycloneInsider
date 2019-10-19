package edu.cs309.cycloneinsider.api;

public interface Session {
    String getToken();

    void invalidate();

    boolean isLoggedIn();

    void saveToken(String token);
}
