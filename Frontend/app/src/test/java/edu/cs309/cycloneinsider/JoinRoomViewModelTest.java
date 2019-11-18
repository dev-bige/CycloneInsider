package edu.cs309.cycloneinsider;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.RoomModel;
import edu.cs309.cycloneinsider.viewmodels.JoinRoomViewModel;
import io.reactivex.Observable;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JoinRoomViewModelTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testViewModel() {
        CycloneInsiderService cycloneInsiderService = mock(CycloneInsiderService.class);
        JoinRoomViewModel joinRoomViewModel = new JoinRoomViewModel(cycloneInsiderService);
        ArrayList<RoomModel> list = new ArrayList<>();
        Observable<Response<List<RoomModel>>> observable = Observable.just(Response.success(list));

        when(cycloneInsiderService.getAllRooms()).thenReturn(observable);

        joinRoomViewModel.refresh();

        assertTrue(joinRoomViewModel.getRoomModelResponse().getValue().isSuccessful());
        assertEquals(list, joinRoomViewModel.getRoomModelResponse().getValue().body());

    }
}
