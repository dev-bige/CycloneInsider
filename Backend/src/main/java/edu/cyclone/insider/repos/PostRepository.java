package edu.cyclone.insider.repos;

import edu.cyclone.insider.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    @Query(value = "SELECT * from post p where p.room_uuid is NULL", nativeQuery = true)
    List<Post> getFrontPagePosts();


    @Query(value = "SELECT * from post p where p.room_uuid is :room_uuid", nativeQuery = true)
    List<Post> getPostsByRoom(@Param("room_uuid") UUID room_uuid);

    @Query(value = "SELECT * from post p where p.post_uuid is !NULL", nativeQuery = true)
    List<Post> getPost();


}
