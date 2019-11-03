package edu.cyclone.insider.controllers.notifications;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * NotificationController handles notifications and allows you to send a message to a connected client (if they are connected)
 */
@ServerEndpoint("/user/{userUuid}/notifications")
@Component
public class NotificationController {
    private static Map<UUID, Session> userUuidSessionMap = new HashMap<>();
    private static Map<Session, UUID> sessionUUIDHashMap = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userUuid") String uuid) {
        userUuidSessionMap.put(UUID.fromString(uuid), session);
        sessionUUIDHashMap.put(session, UUID.fromString(uuid));
    }

    @OnClose
    public void onClose(Session session) {
        UUID uuid = sessionUUIDHashMap.get(session);
        sessionUUIDHashMap.remove(session);
        userUuidSessionMap.remove(uuid);
    }

    /**
     * Send a message to a user
     * @param uuid the user uuid
     * @param message the message
     */
    public static void broadcastNotificationToUUID(UUID uuid, String message)  {
        Session session = userUuidSessionMap.get(uuid);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
