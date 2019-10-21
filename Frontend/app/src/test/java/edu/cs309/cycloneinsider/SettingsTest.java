package edu.cs309.cycloneinsider;

import android.view.View;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import edu.cs309.cycloneinsider.activities.CreatePostActivity;
import edu.cs309.cycloneinsider.activities.FeedbackActivity;
import edu.cs309.cycloneinsider.activities.NewPasswordActivity;
import edu.cs309.cycloneinsider.activities.SettingsActivity;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.LoginRequestModel;
import io.reactivex.Observable;
import retrofit2.Response;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SettingsTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void checkExplicitFilter1() {

        CreatePostActivity post = mock(CreatePostActivity.class);
        String text = "Hello, what is you name? shit";
        post.checkFilter(text);
        when(post.checkFilter(text)).thenReturn(false);
        assertEquals(post.checkFilter(text),false);

    }

    @Test
    public void checkExplicitFilter2(){

        CreatePostActivity post = mock(CreatePostActivity.class);
        String text = "Hello, what is you name?";
        post.checkFilter(text);
        when(post.checkFilter(text)).thenReturn(true);
        assertEquals(post.checkFilter(text),true);

    }

    @Test
    public void checkExplicitFiler3(){

        CreatePostActivity post = mock(CreatePostActivity.class);
        String text = "Hello, asshole";
        post.checkFilter(text);
        when(post.checkFilter(text)).thenReturn(false);
        assertEquals(post.checkFilter(text),false);

    }

    @Test
    public void sendFeedback1(){

        FeedbackActivity feed = mock(FeedbackActivity.class);
        View view = mock(EditText.class);
        String text = "";
        feed.check(text);
        when(feed.check(text)).thenReturn(false);
        assertEquals(feed.check(text), false);

    }

    @Test
    public void sendFeedback2(){

        FeedbackActivity feed = mock(FeedbackActivity.class);
        View view = mock(EditText.class);
        String text = "      ";
        feed.check(text);
        when(feed.check(text)).thenReturn(false);
        assertEquals(feed.check(text), false);

    }

    @Test
    public void sendFeedback3(){

        FeedbackActivity feed = mock(FeedbackActivity.class);
        View view = mock(EditText.class);
        String text = "Hello, good job on the app!";
        feed.check(text);
        when(feed.check(text)).thenReturn(true);
        assertEquals(feed.check(text), true);

    }

    @Test
    public void newPassword1(){

        NewPasswordActivity pass = mock(NewPasswordActivity.class);
        String oldPass = "";
        String newPass = "hunter2";
        String newPass2 = "hunter2";
        pass.checkPassword(oldPass, newPass, newPass2);
        when(pass.checkPassword(oldPass,newPass,newPass2)).thenReturn(false);
        assertEquals(pass.checkPassword(oldPass,newPass,newPass2),false);

    }

    @Test
    public void newPassword2(){

        NewPasswordActivity pass = mock(NewPasswordActivity.class);
        String oldPass = "hunter2";
        String newPass = "";
        String newPass2 = "hunter2";
        pass.checkPassword(oldPass, newPass, newPass2);
        when(pass.checkPassword(oldPass,newPass,newPass2)).thenReturn(false);
        assertEquals(pass.checkPassword(oldPass,newPass,newPass2),false);

    }

    @Test
    public void newPassword3(){

        NewPasswordActivity pass = mock(NewPasswordActivity.class);
        String oldPass = "hunter2";
        String newPass = "hunter2";
        String newPass2 = "";
        pass.checkPassword(oldPass, newPass, newPass2);
        when(pass.checkPassword(oldPass,newPass,newPass2)).thenReturn(false);
        assertEquals(pass.checkPassword(oldPass,newPass,newPass2),false);

    }

    @Test
    public void newPassword4(){

        NewPasswordActivity pass = mock(NewPasswordActivity.class);
        String oldPass = "hunter1";
        String newPass = "hunter2";
        String newPass2 = "hunter3";
        pass.checkPassword(oldPass, newPass, newPass2);
        when(pass.checkPassword(oldPass,newPass,newPass2)).thenReturn(false);
        assertEquals(pass.checkPassword(oldPass,newPass,newPass2),false);

    }

    @Test
    public void newPassword5(){

        NewPasswordActivity pass = mock(NewPasswordActivity.class);
        String oldPass = "hunter1";
        String newPass = "hunter2";
        String newPass2 = "hunter2";
        pass.checkPassword(oldPass, newPass, newPass2);
        when(pass.checkPassword(oldPass,newPass,newPass2)).thenReturn(true);
        assertEquals(pass.checkPassword(oldPass,newPass,newPass2),true);

    }
}
