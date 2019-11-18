package edu.cs309.cycloneinsider;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.UserStateService;
import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import edu.cs309.cycloneinsider.api.models.RoomMembershipModel;
import edu.cs309.cycloneinsider.viewmodels.InviteViewModel;
import io.reactivex.Observable;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InviteViewModelTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testFindByUsername() {
        CycloneInsiderService cycloneInsiderService = mock(CycloneInsiderService.class);
        UserStateService stateService = mock(UserStateService.class);
        InsiderUserModel insiderUserModel = new InsiderUserModel();
        Observable<Response<InsiderUserModel>> response = Observable.just(Response.success(insiderUserModel));
        when(cycloneInsiderService.findUser("admin")).thenReturn(response);
        InviteViewModel viewModel = new InviteViewModel(cycloneInsiderService, null);

        assertNull(viewModel.findUser(null));
        assertNull(viewModel.findUser("admin"));

        assertTrue(viewModel.getFindUserResponse().getValue().isSuccessful());
        assertEquals(insiderUserModel, viewModel.getFindUserResponse().getValue().body());
    }

    @Test
    public void inviteTest() {
        CycloneInsiderService cycloneInsiderService = mock(CycloneInsiderService.class);

        RoomMembershipModel roomMembershipModel = new RoomMembershipModel();
        Observable<Response<RoomMembershipModel>> response = Observable.just(Response.success(roomMembershipModel));

        when(cycloneInsiderService.invite("abcd", "efgh")).thenReturn(response);

        InviteViewModel viewModel = new InviteViewModel(cycloneInsiderService, null);
        viewModel.invite("abcd", "efgh");

        assertTrue(viewModel.getInviteResponse().getValue().isSuccessful());
        assertEquals(roomMembershipModel, viewModel.getInviteResponse().getValue().body());
    }
}
