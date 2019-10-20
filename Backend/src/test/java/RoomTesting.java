import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.repos.RoomRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RoomTesting {
    @TestConfiguration
    static class RoomConfiguration {

        @Bean
        public Room room() {
            return new Room();
        }
    }

    @Autowired
    private Room room;


    @MockBean
    private RoomRepository roomRepository;

    @Before
    public void setUp() {
        Room room1 = new Room();
        room1.setDescription("This is a test room");
        room1.setName("TestRoom");



    }

    @Test
    public void test_room_setup() {
        Room room1 = new Room();
        String name = "TestRoom";
        String description = "This is a test room";
        room1.setDescription("This is a test room");
        room1.setName("TestRoom");
        UUID uuid = room1.getUuid();


        assertThat(room1.getName())
                .isEqualTo(name);

        assertThat(room1.getDescription())
                .isEqualTo(description);


        assertThat(room1.getUuid())
                .isEqualTo(uuid);
    }
}
