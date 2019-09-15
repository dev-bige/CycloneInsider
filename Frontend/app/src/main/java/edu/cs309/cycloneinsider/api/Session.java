package edu.cs309.cycloneinsider.api;

public interface Session {
    boolean isLoggedIn();

    void saveToken(String token);

    String getToken();

    void invalidate();
}
