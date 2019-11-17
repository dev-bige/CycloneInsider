package edu.cs309.cycloneinsider.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import edu.cs309.cycloneinsider.activities.CreatePostActivity;
import edu.cs309.cycloneinsider.activities.CreateRoomActivity;
import edu.cs309.cycloneinsider.activities.InviteActivity;
import edu.cs309.cycloneinsider.activities.LoginActivity;
import edu.cs309.cycloneinsider.activities.MainActivity;
import edu.cs309.cycloneinsider.activities.PostDetailActivity;
import edu.cs309.cycloneinsider.activities.SettingsActivity;
import edu.cs309.cycloneinsider.activities.SignUpActivity;
import edu.cs309.cycloneinsider.activities.StartupActivity;

//import edu.cs309.cycloneinsider.activities.NewPasswordActivity;

@Module(includes = ViewModelModule.class)
public abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = {MainActivityBindingModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract StartupActivity bindStartupActivity();

    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector
    abstract PostDetailActivity bindPostDetailActivity();

    @ContributesAndroidInjector
    abstract CreateRoomActivity bindCreateRoomActivity();

//    @ContributesAndroidInjector
//    abstract NewPasswordActivity bindNewPasswordActivity();

    @ContributesAndroidInjector
    abstract SettingsActivity bindSettingsActivity();

    @ContributesAndroidInjector
    abstract SignUpActivity bindSignUpActivity();

    @ContributesAndroidInjector
    abstract InviteActivity bindInviteActivity();

    @ContributesAndroidInjector
    abstract CreatePostActivity bindCreatePostActivity();


}
