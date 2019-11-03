package edu.cs309.cycloneinsider.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import edu.cs309.cycloneinsider.NotificationService;

@Module
public abstract class ServiceBindingModule {
    @ContributesAndroidInjector
    abstract NotificationService bindNotificationService();
}
