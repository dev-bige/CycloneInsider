package edu.cs309.cycloneinsider;

import android.content.Intent;
import android.os.Build;

import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.di.ApplicationComponent;
import edu.cs309.cycloneinsider.di.DaggerApplicationComponent;
import okhttp3.OkHttpClient;

public class CycloneInsiderApp extends DaggerApplication {
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    OkHttpClient okHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, NotificationService.class));
    }

    public CycloneInsiderService getApiService() {
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
