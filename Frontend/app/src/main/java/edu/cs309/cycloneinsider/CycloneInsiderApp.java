package edu.cs309.cycloneinsider;

import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.Session;
import edu.cs309.cycloneinsider.di.ApplicationComponent;
import edu.cs309.cycloneinsider.di.DaggerApplicationComponent;
import retrofit2.Retrofit;

public class CycloneInsiderApp extends DaggerApplication {
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public CycloneInsiderService getApiService() {
        return null;
    }

    public Session getSession() {
        return null;
    }

    private Retrofit provideRetrofit(String url) {
        return null;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent component = DaggerApplicationComponent
                .builder()
                .application(this)
                .build();
        component.inject(this);
        return component;
    }
}
