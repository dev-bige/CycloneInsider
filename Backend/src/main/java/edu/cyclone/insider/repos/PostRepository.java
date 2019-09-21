package edu.cyclone.insider.repos;

import edu.cyclone.insider.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {


    @Query(value = "SELECT * from post p where p.room_uuid is NULL", nativeQuery = true)
    List<Post> getFrontPagePosts();
}
