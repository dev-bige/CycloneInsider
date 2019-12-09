import edu.cyclone.insider.controllers.comments.models.CommentCreateRequestModel;
import edu.cyclone.insider.controllers.post.models.PostCreateRequestModel;
import edu.cyclone.insider.controllers.room.RoomController;
import edu.cyclone.insider.controllers.room.models.CreateRoomRequestModel;
import edu.cyclone.insider.models.Comment;
import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.repos.UsersRepository;
import edu.cyclone.insider.services.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class RoomTesting {

    private CommentCreateRequestModel commentCreateRequestModel;

    @TestConfiguration
    static class InsiderUserServiceImplTestContextConfiguration {

        @Bean
        public InsiderUser insiderUser() {
            return new InsiderUser();
        }
    }

    @Mock
    RoomService roomService = mock(RoomService.class);

    @MockBean
    private UsersRepository usersRepository;

    /*test for making sure room name post title and comment get set correctly*/
    @Test
    public void commentTesting() {

        CreateRoomRequestModel room1 = new CreateRoomRequestModel();

        room1.name = "TestRoom";
        room1.description = "room for testing";
        room1.privateRoom = true;
        roomService.createRoom(room1);
        Room room = new Room();
        when(roomService.createRoom(room1)).thenReturn(room);


    }


}


