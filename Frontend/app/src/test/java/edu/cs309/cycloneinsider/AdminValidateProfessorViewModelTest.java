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
import edu.cs309.cycloneinsider.api.UserStateService;
import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import edu.cs309.cycloneinsider.viewmodels.AdminProfessorValidateViewModel;
import io.reactivex.Observable;
import retrofit2.Response;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminValidateProfessorViewModelTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testSetUserToProfessor() {
        CycloneInsiderService cycloneInsiderService = mock(CycloneInsiderService.class);
        AdminProfessorValidateViewModel viewModel = new AdminProfessorValidateViewModel(cycloneInsiderService);

        ArrayList<InsiderUserModel> insiderUserModelArrayList = new ArrayList<>();
        Observable<Response<List<InsiderUserModel>>> observable = Observable.just(Response.success(insiderUserModelArrayList));

        when(cycloneInsiderService.getAllPendingProfs()).thenReturn(observable);

        viewModel.refresh();

        assertTrue(viewModel.getProfessorListResponse().getValue().isSuccessful());
        assertEquals(insiderUserModelArrayList, viewModel.getProfessorListResponse().getValue().body());

    }

    @Test
    public void userToProfessor() {
        CycloneInsiderService cycloneInsiderService = mock(CycloneInsiderService.class);
        UserStateService userStateService = mock(UserStateService.class);
        AdminProfessorValidateViewModel viewModel = new AdminProfessorValidateViewModel(cycloneInsiderService);

        assertFalse(userStateService.isAdmin());

    }
}
