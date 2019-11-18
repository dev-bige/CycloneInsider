package edu.cs309.cycloneinsider;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import edu.cs309.cycloneinsider.activities.SignUpActivity;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.SignUpRequestModel;
import edu.cs309.cycloneinsider.viewmodels.SignUpViewModel;
import static org.junit.Assert.assertFalse;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.ByteString;
import retrofit2.Response;

import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.reactivex.Observable;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SignUpTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void successfulSignUp() {
        CycloneInsiderService service = mock(CycloneInsiderService.class);
        Response<Void> success = Response.success(null);
        SignUpRequestModel signUpRequestModel = new SignUpRequestModel("Ethan", "Evans", "edevans", "Password1", false);
        when(service.signUp(signUpRequestModel)).thenReturn(Observable.just(success));
        SignUpViewModel signUpViewModel = new SignUpViewModel(service);
        signUpViewModel.signUp(signUpRequestModel);
        assertFalse(signUpViewModel.getSignUpResponse().getValue().isError());
        assertEquals(signUpViewModel.getSignUpResponse().getValue().getRawResponse(), success);
    }

    @Test
    public void incorrectFirstName() {
        CycloneInsiderService service = mock(CycloneInsiderService.class);
        SignUpViewModel signUpViewModel = new SignUpViewModel(service);
        SignUpRequestModel signUpRequestModel = new SignUpRequestModel("Ethan ", "Evans", "edevans", "Password1", false);
        signUpViewModel.signUp(signUpRequestModel);
        assertTrue(signUpViewModel.getSignUpResponse().getValue().isError());
        assertEquals(signUpViewModel.getSignUpResponse().getValue().getStringError(), R.string.error_sign_up_only_one_name);
    }

    @Test
    public void errorFirstNameLength() {
        CycloneInsiderService service = mock(CycloneInsiderService.class);
        SignUpViewModel signUpViewModel = new SignUpViewModel(service);
        SignUpRequestModel signUpRequestModel = new SignUpRequestModel("", "Evans", "edevans", "Password1", false);
        signUpViewModel.signUp(signUpRequestModel);
        assertTrue(signUpViewModel.getSignUpResponse().getValue().isError());
        assertEquals(signUpViewModel.getSignUpResponse().getValue().getStringError(), R.string.error_name_length);
    }

    @Test
    public void incorrectLastName() {
        CycloneInsiderService service = mock(CycloneInsiderService.class);
        SignUpViewModel signUpViewModel = new SignUpViewModel(service);
        SignUpRequestModel signUpRequestModel = new SignUpRequestModel("Ethan", "Evans ", "edevans", "Password1", false);
        signUpViewModel.signUp(signUpRequestModel);
        assertTrue(signUpViewModel.getSignUpResponse().getValue().isError());
        assertEquals(signUpViewModel.getSignUpResponse().getValue().getStringError(), R.string.error_sign_up_only_one_name);
    }

    @Test
    public void errorLastNameLength() {
        CycloneInsiderService service = mock(CycloneInsiderService.class);
        SignUpViewModel signUpViewModel = new SignUpViewModel(service);
        SignUpRequestModel signUpRequestModel = new SignUpRequestModel("Ethan", "", "edevans", "Password1", false);
        signUpViewModel.signUp(signUpRequestModel);
        assertTrue(signUpViewModel.getSignUpResponse().getValue().isError());
        assertEquals(signUpViewModel.getSignUpResponse().getValue().getStringError(), R.string.error_name_length);
    }

    @Test
    public void errorUserNameLength() {
        CycloneInsiderService service = mock(CycloneInsiderService.class);
        SignUpViewModel signUpViewModel = new SignUpViewModel(service);
        SignUpRequestModel signUpRequestModel = new SignUpRequestModel("Ethan", "Evans", "", "Password1", false);
        signUpViewModel.signUp(signUpRequestModel);
        assertTrue(signUpViewModel.getSignUpResponse().getValue().isError());
        assertEquals(signUpViewModel.getSignUpResponse().getValue().getStringError(), R.string.error_username_length);
    }

}
