package edu.cs309.cycloneinsider;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.LoginRequestModel;
import io.reactivex.Observable;
import retrofit2.Response;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void successLogin() {
        CycloneInsiderService service = mock(CycloneInsiderService.class);

        Response<Void> response = Response.success(null);
        LoginRequestModel loginRequestModel = new LoginRequestModel("admin001", "admin001");
        when(service.login(loginRequestModel)).thenReturn(Observable.just(response));

        assertTrue(service.login(loginRequestModel).blockingSingle().isSuccessful());
    }
}
