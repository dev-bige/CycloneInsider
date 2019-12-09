import edu.cyclone.insider.controllers.comments.models.CommentCreateRequestModel;
import edu.cyclone.insider.controllers.post.models.PostCreateRequestModel;
import edu.cyclone.insider.controllers.room.RoomController;
import edu.cyclone.insider.controllers.room.models.CreateRoomRequestModel;
import edu.cyclone.insider.models.Comment;
import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.repos.PostRepository;
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
public class PostTest {

    private CommentCreateRequestModel commentCreateRequestModel;
    private PostCreateRequestModel postCreateRequestModel;

    @TestConfiguration
    static class InsiderUserServiceImplTestContextConfiguration {

        @Bean
        public InsiderUser insiderUser() {
            return new InsiderUser();
        }
    }

    @Mock
    RoomService roomService = mock(RoomService.class);
    PostsService postsService = mock(PostsService.class);
    @MockBean
    private UsersRepository usersRepository;
    private PostRepository postRepository;

    /*test for making sure room name post title and comment get set correctly*/
    @Test
    public void commentTesting() {

        CreateRoomRequestModel room1 = new CreateRoomRequestModel();
        PostCreateRequestModel post1 = new PostCreateRequestModel();
        post1.content = "hello this is a test";
        Room room = new Room();
        Post post = new Post();
        post.setContent("meeeeppp");
        post1.title = "testTitle";
        post.setContent("edit me");
        when(postsService.createPost(post1, room.getUuid())).thenReturn(post);
        when(postsService.editPost(post.getUuid(), post1)).thenReturn(post);
        assertEquals(post,postsService.editPost(post.getUuid(),post1));

    }


}


