package edu.cyclone.insider.services;

import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.repos.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RoomService {
    private RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Optional<Room> getByUUID(UUID uuid) {
        return roomRepository.findById(uuid);
    }
}
