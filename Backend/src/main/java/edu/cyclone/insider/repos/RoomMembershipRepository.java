package edu.cyclone.insider.repos;

import edu.cyclone.insider.models.RoomMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomMembershipRepository extends JpaRepository<RoomMembership, UUID> {
    @Query(value = "SELECT * from room_membership r where r.user_uuid = :user_uuid AND r.pending = FALSE", nativeQuery = true)
    List<RoomMembership> getUserMemberships(@Param("user_uuid") UUID uuid);

    @Query(value = "SELECT * from room_membership r where r.user_uuid = :user_uuid AND r.pending = TRUE", nativeQuery = true)
    List<RoomMembership> getPendingUserMemberships(@Param("user_uuid") UUID uuid);

    @Query(value = "SELECT * from room_membership r where r.user_uuid = :user_uuid and r.room_uuid = :room_uuid", nativeQuery = true)
    Optional<RoomMembership> getMembership(@Param("user_uuid") UUID user_uuid, @Param("room_uuid") UUID room_uuid);

    @Query(value = "SELECT * from room_membership r where r.room_uuid = :room_uuid", nativeQuery = true)
    List<RoomMembership> getMembersInRoom(@Param("room_uuid") UUID room_uuid);

}
