package edu.cs309.cycloneinsider;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import javax.inject.Inject;

import dagger.android.DaggerService;
import edu.cs309.cycloneinsider.api.UserStateService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class NotificationService extends DaggerService {
    private static final String TAG = "NotificationService";

    @Inject
    OkHttpClient okHttpClient;

    @Inject
    UserStateService userStateService;
    private WebSocket socket;

    @SuppressLint("CheckResult")
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
        userStateService.getUserAsync().subscribe(insiderUserModel -> {
            if (socket != null) {
                socket.close(1000, "Closed");
            }
            Request build1 = new Request.Builder().url("http://coms-309-sb-5.misc.iastate.edu:8080/user/" + insiderUserModel.getUuid() + "/notifications").build();
            socket = okHttpClient.newWebSocket(build1, new WebSocketListener() {
                @Override
                public void onOpen(WebSocket webSocket, Response response) {
                    Log.d(TAG, "onOpen() called with: webSocket = [" + webSocket + "], response = [" + response + "]");
                    super.onOpen(webSocket, response);
                }

                @Override
                public void onMessage(WebSocket webSocket, String text) {
                    Log.d(TAG, "onMessage() called with: webSocket = [" + webSocket + "], text = [" + text + "]");
                    notifyMessage(text);
                    super.onMessage(webSocket, text);
                }

                @Override
                public void onMessage(WebSocket webSocket, ByteString bytes) {
                    Log.d(TAG, "onMessage() called with: webSocket = [" + webSocket + "], bytes = [" + bytes + "]");
                    super.onMessage(webSocket, bytes);
                }

                @Override
                public void onClosing(WebSocket webSocket, int code, String reason) {
                    Log.d(TAG, "onClosing() called with: webSocket = [" + webSocket + "], code = [" + code + "], reason = [" + reason + "]");
                    super.onClosing(webSocket, code, reason);
                }

                @Override
                public void onClosed(WebSocket webSocket, int code, String reason) {
                    Log.d(TAG, "onClosed() called with: webSocket = [" + webSocket + "], code = [" + code + "], reason = [" + reason + "]");
                    super.onClosed(webSocket, code, reason);
                }

                @Override
                public void onFailure(WebSocket webSocket, Throwable t, @javax.annotation.Nullable Response response) {
                    Log.d(TAG, "onFailure() called with: webSocket = [" + webSocket + "], t = [" + t + "], response = [" + response + "]");
                    super.onFailure(webSocket, t, response);
                }
            });
        });
    }

    public void notifyMessage(String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "invites")
                .setSmallIcon(R.drawable.ic_school_black_24dp)
                .setContentTitle("New Invite")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify((int) (Math.random() * 20), builder.build());
    }

    public void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Invites";
            String description = "Notifications for room invites";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("invites", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (socket != null) {
            socket.close(1000, "Closed");
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
