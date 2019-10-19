package edu.cs309.cycloneinsider;

import android.view.View;

import org.mockito.Mock;

import edu.cs309.cycloneinsider.activities.NewPasswordActivity;
import edu.cs309.cycloneinsider.activities.SettingsActivity;

import static org.mockito.Mockito.*;

public class SettingsPageTest {

    NewPasswordActivity thisActivity = new NewPasswordActivity();

    public void setThisActivity(NewPasswordActivity thisActivity) {
        this.thisActivity = thisActivity;
        thisActivity.changePassword(null);

    }
}
