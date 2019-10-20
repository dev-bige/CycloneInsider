package edu.cs309.cycloneinsider;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.cs309.cycloneinsider.activities.LoginActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Test
    public void useAppContext() {
        ActivityScenario<LoginActivity> launch = ActivityScenario.launch(LoginActivity.class);

        onView(withId(R.id.sign_in)).perform(click());
        onView(withId(R.id.hidden_text)).check(ViewAssertions.matches(withText(R.string.login_no_id_password)));
        onView(withId(R.id.net_id)).perform(typeText("admin001"));
        onView(withId(R.id.sign_in)).perform(click());
        onView(withId(R.id.hidden_text)).check(ViewAssertions.matches(withText(R.string.login_no_password)));

        launch.close();
    }
}
