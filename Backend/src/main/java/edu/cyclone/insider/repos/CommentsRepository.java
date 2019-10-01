package edu.cyclone.insider.repos;

import edu.cyclone.insider.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CommentsRepository extends JpaRepository<Comment, UUID> {
    @Query(value = "SELECT * from comments p where p.post_uuid = :post_uuid", nativeQuery = true)
    List<Comment> getComment();

}
