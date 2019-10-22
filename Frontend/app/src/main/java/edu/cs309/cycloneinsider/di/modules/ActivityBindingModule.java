package edu.cs309.cycloneinsider.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import edu.cs309.cycloneinsider.activities.LoginActivity;
import edu.cs309.cycloneinsider.activities.MainActivity;
import edu.cs309.cycloneinsider.activities.StartupActivity;

@Module(includes = ViewModelModule.class)
public abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = {MainActivityBindingModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract StartupActivity bindStartupActivity();

    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();
}
