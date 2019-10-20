import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.repos.PostRepository;
import edu.cyclone.insider.repos.RoomRepository;
import edu.cyclone.insider.repos.UsersRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PostTest {
    @TestConfiguration
    static class PostTesting {

        @Bean
        public Post post() {
            return new Post();
        }
        @Bean
        public Room room() {
            return new Room();
        }
    }

    @Autowired
    private Post post;
    private Room room;
    private InsiderUser Jim;


    @MockBean
    private PostRepository postRepository;
    private RoomRepository roomRepository;
    private UsersRepository usersRepository;

    @Before
    public void setUp() {
        InsiderUser Jim = new InsiderUser();
        Room room1 = new Room();
        room1.setDescription("This is a test room");
        room1.setName("TestRoom");
        Post post1 = new Post();
        post1.setRoom(room1);
        post1.setTitle("TestTitle");



    }

    @Test
    public void test_room_setup() {
        Room room1 = new Room();

        InsiderUser Jim = new InsiderUser();
        Jim.setUsername("JimmyBoy");

        room1.setDescription("This is a test room");
        room1.setName("TestRoom");
        UUID uuid = room1.getUuid();
        Post post1 = new Post();
        post1.setUser(Jim);
        post1.setRoom(room1);
        post1.setTitle("TestTitle");
        String title = "TestTitle";

        UUID postuuid = post1.getUuid();
        
        assertThat(post1.getTitle())
                .isEqualTo(title);

        assertThat(post1.getRoom())
                .isEqualTo(room1);

        assertThat(room1.getUuid())
                .isEqualTo(uuid);
        assertThat(post1.getUuid())
                .isEqualTo(postuuid);
        Assert.assertEquals("TestRoom", post1.getRoom().getName());
        System.out.println(post1.getRoom().getName());
        Assert.assertEquals("JimmyBoy", post1.getUser().getUsername());
    }

}
