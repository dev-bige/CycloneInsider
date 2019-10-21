package edu.cs309.cycloneinsider;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import edu.cs309.cycloneinsider.activities.LoginActivity;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.LoginRequestModel;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.ByteString;
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

    @Test
    public void successLogin() {
        LoginActivity loginActivity = new LoginActivity();
        CycloneInsiderService service = mock(CycloneInsiderService.class);

        Response<Void> response = Response.success(null);
        LoginRequestModel loginRequestModel = new LoginRequestModel("admin001", "admin001");
        when(service.login(loginRequestModel)).thenReturn(Observable.just(response));

        Response<Void> activityResponse = loginActivity.login(service, loginRequestModel).blockingSingle();

        assertEquals(response, activityResponse);
    }

    @Test
    public void failed() {
        CycloneInsiderService service = mock(CycloneInsiderService.class);
        LoginActivity loginActivity = new LoginActivity();

        Response<Void> response = Response.error(403, ResponseBody.create(MediaType.get("application/json"), ByteString.EMPTY));
        LoginRequestModel loginRequestModel = new LoginRequestModel("admin001", "admin001");
        when(service.login(loginRequestModel)).thenReturn(Observable.just(response));

        Response<Void> activityResponse = loginActivity.login(service, loginRequestModel).blockingSingle();

        assertEquals(response, activityResponse);
    }
}
