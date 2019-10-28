package edu.cyclone.insider;

import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.repos.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PostRepository postRepository;
    private Room room;
    private Post post;
    private InsiderUser user;

    @Before
    public void setUp() {
        room = new Room();
        room.setName("Test Room");
        room.setDescription("This is a test room");
        testEntityManager.persist(room);

        user = new InsiderUser();
        user.setUsername("admin001");
        user.setLastName("admin");
        user.setFirstName("001");
        user.setAdmin(true);
        testEntityManager.persist(user);

        post = new Post();
        post.setDate(new Date());
        post.setTitle("This is a test title");
        post.setContent("This is a test content");
        post.setRoom(room);
        post.setUser(user);
        post.setTags(new ArrayList<>());
        testEntityManager.persist(post);
    }

    @Test
    public void testSave() {
        assertTrue(postRepository.findById(post.getUuid()).isPresent());
    }

    @Test
    public void testByUser() {
        assertFalse(postRepository.findPostsByUser(user.getUuid()).isEmpty());
    }

    @Test
    public void testRoom() {
        assertFalse(postRepository.getPostsByRoom(room.getUuid()).isEmpty());
    }
}
