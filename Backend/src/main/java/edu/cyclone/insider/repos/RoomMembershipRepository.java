package edu.cyclone.insider.repos;

import edu.cyclone.insider.models.RoomMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RoomMembershipRepository extends JpaRepository<RoomMembership, UUID> {
    @Query(value = "SELECT * from room_membership r where r.user_uuid = :user_uuid", nativeQuery = true)
    List<RoomMembership> findUserMemberships(@Param("user_uuid") UUID uuid);
}
