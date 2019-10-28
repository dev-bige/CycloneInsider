package edu.cs309.cycloneinsider;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.cs309.cycloneinsider.activities.LoginActivity;
import edu.cs309.cycloneinsider.activities.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Before
    public void setUp() {
        Intents.init();
    }
    @Test
    public void testSignIn() {
        ActivityScenario<LoginActivity> launch = ActivityScenario.launch(LoginActivity.class);

        onView(withId(R.id.sign_in)).perform(click());
        onView(withId(R.id.hidden_text)).check(ViewAssertions.matches(withText(R.string.login_no_id_password)));
        onView(withId(R.id.net_id)).perform(typeText("admin001"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.sign_in)).perform(click());
        onView(withId(R.id.hidden_text)).check(ViewAssertions.matches(withText(R.string.login_no_password)));

        onView(withId(R.id.password)).perform(typeText("x"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.sign_in)).perform(click());

        intended(hasComponent(MainActivity.class.getName()));

        launch.close();
    }

    @After
    public void finish() {
        Intents.release();
    }
}
