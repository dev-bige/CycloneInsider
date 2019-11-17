package edu.cyclone.insider;

import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.models.UserLevel;
import edu.cyclone.insider.repos.PostRepository;
import edu.cyclone.insider.repos.UsersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PermissionServiceTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PostRepository postRepository;
    private UsersRepository usersRepository;
    private InsiderUser user;

    @Before
    public void setUp() {
        user = new InsiderUser();
        user.setUsername("user000");
        user.setLastName("dort");
        user.setFirstName("Andrew");
        user.setUserLevel(UserLevel.USER);
        user.setProfPending(true);
        user.setAdmin(false);
        testEntityManager.persist(user);
    }


    @Test
    public void testForProfessor() {
        assertTrue(user.getProfPending());
        assertFalse(user.getProfessor());
        user.setUserLevel(UserLevel.PROFESSOR);
        assertTrue(user.getProfessor());
        assertFalse(user.getAdmin());

    }











}
