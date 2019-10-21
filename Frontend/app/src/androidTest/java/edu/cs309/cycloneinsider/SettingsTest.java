package edu.cs309.cycloneinsider;

import android.app.Instrumentation;
import android.view.KeyEvent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.KeyEventAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.cs309.cycloneinsider.activities.FeedbackActivity;
import edu.cs309.cycloneinsider.activities.LoginActivity;
import edu.cs309.cycloneinsider.activities.SettingsActivity;

import static android.view.KeyEvent.KEYCODE_BACK;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;


@RunWith(AndroidJUnit4.class)
public class SettingsTest {
    @Test
    public void EmptyFeedback() {
        ActivityScenario<SettingsActivity> launch = ActivityScenario.launch(SettingsActivity.class);

        onView(withId(R.id.feedback_button)).perform(click());
        onView(withId(R.id.email_button)).perform(click());
        onView(withId(R.id.hidden_feedback_text)).check(ViewAssertions.matches(withText("Cannot leave Feedback box empty")));

        launch.close();

    }

    @Test
    public void SpacesFeedback(){

        ActivityScenario<FeedbackActivity> launch = ActivityScenario.launch(FeedbackActivity.class);

        onView(withId(R.id.feedback_message)).perform(typeText("       "));
        onView(withId(R.id.email_button)).perform(click());
        onView(withId(R.id.hidden_feedback_text)).check(ViewAssertions.matches(withText("Cannot just have spaces in Feedback box")));

        launch.close();

    }

    @Test
    public void EmptyPassword(){

        ActivityScenario<SettingsActivity> launch = ActivityScenario.launch(SettingsActivity.class);

        onView(withId(R.id.new_password_button)).perform(click());
        onView(withId(R.id.change_password)).perform(click());
        onView(withId(R.id.hidden_text_password)).check(ViewAssertions.matches(withText("You must enter characters in all text boxes")));

        launch.close();

    }

    @Test
    public void OnlyOldPassword(){

        ActivityScenario<SettingsActivity> launch = ActivityScenario.launch(SettingsActivity.class);

        onView(withId(R.id.new_password_button)).perform(click());
        onView(withId(R.id.old_password)).perform(typeText("hunter2"));
        onView(withId(R.id.change_password)).perform(click());
        onView(withId(R.id.hidden_text_password)).check(ViewAssertions.matches(withText("You must enter characters in all text boxes")));

        launch.close();

    }


    @Test
    public void OnlyNewPassword(){

        ActivityScenario<SettingsActivity> launch = ActivityScenario.launch(SettingsActivity.class);

        onView(withId(R.id.new_password_button)).perform(click());
        onView(withId(R.id.new_password)).perform(typeText("hunter2"));
        onView(withId(R.id.new_password_again)).perform(typeText("hunter2"));
        onView(withId(R.id.change_password)).perform(click());
        onView(withId(R.id.hidden_text_password)).check(ViewAssertions.matches(withText("You must enter characters in all text boxes")));

        launch.close();

    }

    @Test
    public void OnlyOldAndFirstNewPassword(){

        ActivityScenario<SettingsActivity> launch = ActivityScenario.launch(SettingsActivity.class);

        onView(withId(R.id.new_password_button)).perform(click());
        onView(withId(R.id.old_password)).perform(typeText("hunter1"));
        onView(withId(R.id.new_password)).perform(typeText("hunter2"));
        onView(withId(R.id.change_password)).perform(click());
        onView(withId(R.id.hidden_text_password)).check(ViewAssertions.matches(withText("You must enter characters in all text boxes")));

        launch.close();

    }

    @Test
    public void NewPasswordNotMatch(){

        ActivityScenario<SettingsActivity> launch = ActivityScenario.launch(SettingsActivity.class);

        onView(withId(R.id.new_password_button)).perform(click());
        onView(withId(R.id.old_password)).perform(typeText("hunter1"));
        onView(withId(R.id.new_password)).perform(typeText("hunter2"));
        onView(withId(R.id.new_password_again)).perform(typeText("hunter3"));
        onView(withId(R.id.change_password)).perform(click());
        onView(withId(R.id.hidden_text_password)).check(ViewAssertions.matches(withText("New password must be the same in both text boxes")));

        launch.close();

    }

}
