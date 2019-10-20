package edu.cyclone.insider.repos;

import edu.cyclone.insider.models.Comment;
import edu.cyclone.insider.models.FavPost;
import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FavPostRepository extends JpaRepository<FavPost, UUID> {
    @Query(value = "SELECT * FROM fav_post f where f.user_uuid = :user_uuid", nativeQuery = true)
    List<FavPost> findFavByUser(@Param("user_uuid") UUID user_uuid);


}

