package edu.cyclone.insider.repos;

import edu.cyclone.insider.models.FavPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface FavPostRepository extends JpaRepository<FavPost, UUID> {
    @Query(value = "SELECT * FROM fav_post f where f.user_uuid = :user_uuid", nativeQuery = true)
    Optional<FavPost> findFavByUser(@Param("user_uuid") UUID user_uuid);


}

