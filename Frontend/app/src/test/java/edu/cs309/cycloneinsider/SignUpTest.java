package edu.cs309.cycloneinsider;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import edu.cs309.cycloneinsider.activities.SignUpActivity;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.SignUpRequestModel;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.ByteString;
import retrofit2.Response;

import io.reactivex.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SignUpTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void successfulSignUp() {
        SignUpActivity signUpActivity = new SignUpActivity();
        CycloneInsiderService service = mock(CycloneInsiderService.class);

        Response<Void> signUpRequestModelResponse = Response.success(null);
        SignUpRequestModel signUpRequestModel = new SignUpRequestModel();
        when(service.signUp(signUpRequestModel)).thenReturn(Observable.just(signUpRequestModelResponse));

        Response<Void> activityResponse = signUpActivity.signUp(service, signUpRequestModel).blockingFirst();

        assertEquals(signUpRequestModelResponse, activityResponse);
    }

    @Test
    public void unsuccessfulSignUp() {
        SignUpActivity signUpActivity = new SignUpActivity();
        CycloneInsiderService service = mock(CycloneInsiderService.class);

        Response<Void> signUpRequestModelResponse = Response.error(403, ResponseBody.create(MediaType.get("application/json"), ByteString.EMPTY));
        SignUpRequestModel signUpRequestModel = new SignUpRequestModel();
        when(service.signUp(signUpRequestModel)).thenReturn(Observable.just(signUpRequestModelResponse));

        Response<Void> activityResponse = signUpActivity.signUp(service, signUpRequestModel).blockingFirst();

        assertEquals(signUpRequestModelResponse, activityResponse);
    }
}
