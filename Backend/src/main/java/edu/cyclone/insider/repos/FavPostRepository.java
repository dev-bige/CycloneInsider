package edu.cyclone.insider.repos;

import edu.cyclone.insider.models.FavPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavPostRepository extends JpaRepository<FavPost, UUID> {
    @Query(value = "SELECT * FROM fav_post f where f.user_uuid = :user_uuid order by f.date DESC", nativeQuery = true)
    List<FavPost> getFavoritesByUser(@Param("user_uuid") UUID user_uuid);

    @Query(value = "SELECT * FROM fav_post f where f.user_uuid = :user_uuid AND f.post_uuid = :post_uuid", nativeQuery = true)
    Optional<FavPost> getFavoritePost(@Param("user_uuid") UUID user_uuid, @Param("post_uuid") UUID postUuid);

    @Query(value = "SELECT * FROM fav_post f where  f.post_uuid = :post_uuid", nativeQuery = true)
    List<FavPost> getFavorites(@Param("post_uuid") UUID postUuid);
}

