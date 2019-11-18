package edu.cyclone.insider.repos;

import edu.cyclone.insider.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    @Query(value = "SELECT * FROM room r where r.private_room = 0", nativeQuery = true)
    List<Room> getPublicRooms();
}
