package edu.cyclone.insider;

import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.repos.RoomRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoomRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private RoomRepository roomRepository;
    private Room room;

    @Before
    public void setUp() {
        room = new Room();
        room.setName("Test Room");
        room.setDescription("This is a test room");
        testEntityManager.persist(room);
    }

    @Test
    public void testSave() {
        Optional<Room> instance = roomRepository.findById(room.getUuid());
        assertTrue(instance.isPresent());
    }
}
