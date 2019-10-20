package edu.cyclone.insider.repos;

import edu.cyclone.insider.models.Comment;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentsRepository extends JpaRepository<Comment, UUID> {
    @Query(value = "SELECT * from comments p where p.post_uuid = :post_uuid", nativeQuery = true)
    List<Comment> getCommentsByPost(@Param("post_uuid") UUID post_uuid);


    @Query(value = "SELECT * from comments c where c.user_uuid = :user_uuid", nativeQuery = true)
    List<Comment> findCommentsByUser(@Param("user_uuid") UUID user_uuid);
}
