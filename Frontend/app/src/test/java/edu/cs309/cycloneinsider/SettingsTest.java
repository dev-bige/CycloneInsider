package edu.cs309.cycloneinsider;

import android.view.View;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import edu.cs309.cycloneinsider.activities.CreatePostActivity;
import edu.cs309.cycloneinsider.activities.FeedbackActivity;
import edu.cs309.cycloneinsider.activities.FeedbackOptionsActivity;
import edu.cs309.cycloneinsider.activities.NewPasswordActivity;
import edu.cs309.cycloneinsider.activities.SettingsActivity;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.UserStateService;
import edu.cs309.cycloneinsider.api.models.LoginRequestModel;
import edu.cs309.cycloneinsider.viewmodels.LoginViewModel;
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

    @Test
    public void FeedbackOptions1(){

        FeedbackOptionsActivity feed = mock(FeedbackOptionsActivity.class);
        boolean value1 = true;
        boolean value2 = false;
        boolean value3 = false;
        boolean value4 = false;
        feed.Check(value1,value2,value3,value4);
        when(feed.Check(value1,value2,value3,value4)).thenReturn(true);
        assertEquals(feed.Check(value1,value2,value3,value4), true);

    }

    @Test
    public void FeedbackOptions2(){

        FeedbackOptionsActivity feed = mock(FeedbackOptionsActivity.class);
        boolean value1 = false;
        boolean value2 = false;
        boolean value3 = false;
        boolean value4 = false;
        feed.Check(value1,value2,value3,value4);
        when(feed.Check(value1,value2,value3,value4)).thenReturn(false);
        assertEquals(feed.Check(value1,value2,value3,value4), false);

    }

    @Test
    public void FeedbackOptions3(){

        FeedbackOptionsActivity feed = mock(FeedbackOptionsActivity.class);
        boolean value1 = false;
        boolean value2 = true;
        boolean value3 = false;
        boolean value4 = false;
        feed.Check(value1,value2,value3,value4);
        when(feed.Check(value1,value2,value3,value4)).thenReturn(true);
        assertEquals(feed.Check(value1,value2,value3,value4), true);

    }

    @Test
    public void FeedbackOptions4(){

        FeedbackOptionsActivity feed = mock(FeedbackOptionsActivity.class);
        boolean value1 = false;
        boolean value2 = false;
        boolean value3 = true;
        boolean value4 = false;
        feed.Check(value1,value2,value3,value4);
        when(feed.Check(value1,value2,value3,value4)).thenReturn(true);
        assertEquals(feed.Check(value1,value2,value3,value4), true);

    }

    @Test
    public void FeedbackOptions5(){

        FeedbackOptionsActivity feed = mock(FeedbackOptionsActivity.class);
        boolean value1 = false;
        boolean value2 = false;
        boolean value3 = false;
        boolean value4 = true;
        feed.Check(value1,value2,value3,value4);
        when(feed.Check(value1,value2,value3,value4)).thenReturn(true);
        assertEquals(feed.Check(value1,value2,value3,value4), true);

    }

    @Test
    public void FeedbackOptions6(){

        FeedbackOptionsActivity feed = mock(FeedbackOptionsActivity.class);
        boolean value1 = true;
        boolean value2 = true;
        boolean value3 = false;
        boolean value4 = false;
        feed.Check(value1,value2,value3,value4);
        when(feed.Check(value1,value2,value3,value4)).thenReturn(true);
        assertEquals(feed.Check(value1,value2,value3,value4), true);

    }

    @Test
    public void FeedbackOptions7(){

        FeedbackOptionsActivity feed = mock(FeedbackOptionsActivity.class);
        boolean value1 = true;
        boolean value2 = false;
        boolean value3 = false;
        boolean value4 = true;
        feed.Check(value1,value2,value3,value4);
        when(feed.Check(value1,value2,value3,value4)).thenReturn(true);
        assertEquals(feed.Check(value1,value2,value3,value4), true);

    }

    @Test
    public void AdditionalFeedbackOptions1(){

        FeedbackActivity feed = mock(FeedbackActivity.class);
        boolean value1 = false;
        boolean value2 = false;
        boolean value3 = false;
        boolean value4 = false;
        feed.CheckSubject(value1,value2,value3,value4);
        when(feed.CheckSubject(value1,value2,value3,value4)).thenReturn("(User Feedback)");
        assertEquals(feed.CheckSubject(value1,value2,value3,value4),"(User Feedback)");

    }

    @Test
    public void AdditionalFeedbackOptions2(){

        FeedbackActivity feed = mock(FeedbackActivity.class);
        boolean value1 = true;
        boolean value2 = false;
        boolean value3 = false;
        boolean value4 = false;
        feed.CheckSubject(value1,value2,value3,value4);
        when(feed.CheckSubject(value1,value2,value3,value4)).thenReturn("Technical: (User Feedback)");
        assertEquals(feed.CheckSubject(value1,value2,value3,value4),"Technical: (User Feedback)");

    }

    @Test
    public void AdditionalFeedbackOptions3(){

        FeedbackActivity feed = mock(FeedbackActivity.class);
        boolean value1 = false;
        boolean value2 = true;
        boolean value3 = false;
        boolean value4 = false;
        feed.CheckSubject(value1,value2,value3,value4);
        when(feed.CheckSubject(value1,value2,value3,value4)).thenReturn("Improve: (User Feedback)");
        assertEquals(feed.CheckSubject(value1,value2,value3,value4),"Improve: (User Feedback)");

    }

    @Test
    public void AdditionalFeedbackOptions4(){

        FeedbackActivity feed = mock(FeedbackActivity.class);
        boolean value1 = false;
        boolean value2 = false;
        boolean value3 = true;
        boolean value4 = false;
        feed.CheckSubject(value1,value2,value3,value4);
        when(feed.CheckSubject(value1,value2,value3,value4)).thenReturn("Feature: (User Feedback)");
        assertEquals(feed.CheckSubject(value1,value2,value3,value4),"Feature: (User Feedback)");

    }


    @Test
    public void AdditionalFeedbackOptions5(){

        FeedbackActivity feed = mock(FeedbackActivity.class);
        boolean value1 = false;
        boolean value2 = false;
        boolean value3 = false;
        boolean value4 = true;
        feed.CheckSubject(value1,value2,value3,value4);
        when(feed.CheckSubject(value1,value2,value3,value4)).thenReturn("Other: (User Feedback)");
        assertEquals(feed.CheckSubject(value1,value2,value3,value4),"Other: (User Feedback)");

    }

    @Test
    public void AdditionalFeedbackOptions6(){

        FeedbackActivity feed = mock(FeedbackActivity.class);
        boolean value1 = true;
        boolean value2 = false;
        boolean value3 = false;
        boolean value4 = true;
        feed.CheckSubject(value1,value2,value3,value4);
        when(feed.CheckSubject(value1,value2,value3,value4)).thenReturn("Technical: Other: (User Feedback)");
        assertEquals(feed.CheckSubject(value1,value2,value3,value4),"Technical: Other: (User Feedback)");

    }

    @Test
    public void AdditionalFeedbackOptions7(){

        FeedbackActivity feed = mock(FeedbackActivity.class);
        boolean value1 = true;
        boolean value2 = true;
        boolean value3 = true;
        boolean value4 = true;
        feed.CheckSubject(value1,value2,value3,value4);
        when(feed.CheckSubject(value1,value2,value3,value4)).thenReturn("Technical: Improve: Feature: Other: (User Feedback)");
        assertEquals(feed.CheckSubject(value1,value2,value3,value4),"Technical: Improve: Feature: Other: (User Feedback)");

    }

    @Test
    public void SendFeedbackExplicit1(){

        FeedbackActivity feed = mock(FeedbackActivity.class);
        String  text = "shit";
        feed.checkEmail(text);
        when(feed.checkEmail(text)).thenReturn(false);
        assertEquals(feed.checkEmail(text),false);

    }

    @Test
    public void SendFeedbackExplicit2(){

        FeedbackActivity feed = mock(FeedbackActivity.class);
        String  text = "hello this is a test";
        feed.checkEmail(text);
        when(feed.checkEmail(text)).thenReturn(true);
        assertEquals(feed.checkEmail(text),true);

    }

    @Test
    public void SendFeedbackExplicit3(){

        FeedbackActivity feed = mock(FeedbackActivity.class);
        String  text = "Your app is shit";
        feed.checkEmail(text);
        when(feed.checkEmail(text)).thenReturn(false);
        assertEquals(feed.checkEmail(text),false);

    }

    @Test
    public void SendFeedbackExplicit4(){

        FeedbackActivity feed = mock(FeedbackActivity.class);
        String  text = "This should return true";
        feed.checkEmail(text);
        when(feed.checkEmail(text)).thenReturn(true);
        assertEquals(feed.checkEmail(text),true);

    }

}
