package edu.cs309.cycloneinsider.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import edu.cs309.cycloneinsider.fragments.FavoritePostFragment;
import edu.cs309.cycloneinsider.fragments.JoinRoomFragment;
import edu.cs309.cycloneinsider.fragments.MyPostListFragment;
import edu.cs309.cycloneinsider.fragments.PostListFragment;

@Module
public abstract class MainActivityBindingModule {
    @ContributesAndroidInjector
    abstract PostListFragment providePostListFragment();

    @ContributesAndroidInjector
    abstract FavoritePostFragment provideFavoritePostFragment();

    @ContributesAndroidInjector
    abstract MyPostListFragment provideMyPostFragment();

    @ContributesAndroidInjector
    abstract JoinRoomFragment provideJoinRoomFragment();
}
