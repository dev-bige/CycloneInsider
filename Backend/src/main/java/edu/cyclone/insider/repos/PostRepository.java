package edu.cyclone.insider.repos;

import edu.cyclone.insider.models.Comment;
import edu.cyclone.insider.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    @Query(value = "SELECT * from post p where p.room_uuid is NULL", nativeQuery = true)
    List<Post> getFrontPagePosts();


    @Query(value = "SELECT * from post p where p.room_uuid = :room_uuid", nativeQuery = true)
    List<Post> getPostsByRoom(@Param("room_uuid") UUID room_uuid);

    @Query(value = "SELECT * from post p where p.post_uuid is !NULL", nativeQuery = true)
    List<Post> getPost();

    @Query(value = "SELECT * from post p where p.user_uuid = :user_uuid", nativeQuery = true)
    List<Post> findPostsByUser(@Param("user_uuid") UUID user_uuid);


}
