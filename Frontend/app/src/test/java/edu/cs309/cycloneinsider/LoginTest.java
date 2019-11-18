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
import edu.cs309.cycloneinsider.api.models.LoginRequestModel;
import edu.cs309.cycloneinsider.viewmodels.LoginViewModel;
import io.reactivex.Observable;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    //Bypass main thread check for Live Data
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void noPassword() {
        CycloneInsiderService service = mock(CycloneInsiderService.class);
        UserStateService userStateService = mock(UserStateService.class);
        LoginViewModel loginViewModel = new LoginViewModel(service, userStateService);
        loginViewModel.login(new LoginRequestModel("admin001", ""));
        assertTrue(loginViewModel.getLoginResponse().getValue().isError());
        assertEquals(loginViewModel.getLoginResponse().getValue().getStringError(), R.string.login_no_password);
    }

    @Test
    public void noNetId() {
        CycloneInsiderService service = mock(CycloneInsiderService.class);
        UserStateService userStateService = mock(UserStateService.class);
        LoginViewModel loginViewModel = new LoginViewModel(service, userStateService);
        loginViewModel.login(new LoginRequestModel("", "x"));
        assertTrue(loginViewModel.getLoginResponse().getValue().isError());
        assertEquals(loginViewModel.getLoginResponse().getValue().getStringError(), R.string.login_no_id);
    }

    @Test
    public void noBoth() {
        CycloneInsiderService service = mock(CycloneInsiderService.class);
        UserStateService userStateService = mock(UserStateService.class);
        LoginViewModel loginViewModel = new LoginViewModel(service, userStateService);
        loginViewModel.login(new LoginRequestModel("", ""));
        assertTrue(loginViewModel.getLoginResponse().getValue().isError());
        assertEquals(loginViewModel.getLoginResponse().getValue().getStringError(), R.string.login_no_id_password);
    }

    @Test
    public void successLogin() {
        CycloneInsiderService service = mock(CycloneInsiderService.class);
        UserStateService userStateService = mock(UserStateService.class);
        Response<Void> success = Response.success(null);
        LoginRequestModel loginRequestModel = new LoginRequestModel("admin001", "admin001");
        when(service.login(loginRequestModel)).thenReturn(Observable.just(success));
        LoginViewModel loginViewModel = new LoginViewModel(service, userStateService);
        loginViewModel.login(loginRequestModel);
        assertFalse(loginViewModel.getLoginResponse().getValue().isError());
        assertEquals(loginViewModel.getLoginResponse().getValue().getRawResponse(), success);
    }
}
