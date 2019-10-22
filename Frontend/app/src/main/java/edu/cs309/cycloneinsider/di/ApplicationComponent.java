package edu.cs309.cycloneinsider.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;
import edu.cs309.cycloneinsider.CycloneInsiderApp;
import edu.cs309.cycloneinsider.di.modules.ActivityBindingModule;
import edu.cs309.cycloneinsider.di.modules.ApplicationModule;
import edu.cs309.cycloneinsider.di.modules.ContextModule;

@Singleton
@Component(modules = {
        ContextModule.class,
        AndroidSupportInjectionModule.class,
        ActivityBindingModule.class,
        ApplicationModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {
    void inject(CycloneInsiderApp application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}
