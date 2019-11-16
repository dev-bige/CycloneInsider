package edu.cyclone.insider.repos;

import edu.cyclone.insider.models.InsiderUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<InsiderUser, UUID> {
    @Query(value = "SELECT * from users u where u.username = :username", nativeQuery = true)
    Optional<InsiderUser> findUserByUsername(@Param("username") String username);

    @Query(value = "SELECT * from users u where u.username = :username", nativeQuery = true)
    InsiderUser findUserByUsernameNotNull(@Param("username") String username);

}
