package edu.cs309.cycloneinsider.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import edu.cs309.cycloneinsider.fragments.FavoritePostFragment;
import edu.cs309.cycloneinsider.fragments.MyPostListFragment;
import edu.cs309.cycloneinsider.fragments.PostListFragment;
import edu.cs309.cycloneinsider.fragments.RoomInvitationFragment;

@Module
public abstract class MainActivityBindingModule {
    @ContributesAndroidInjector
    abstract PostListFragment providePostListFragment();

    @ContributesAndroidInjector
    abstract FavoritePostFragment provideFavoritePostFragment();

    @ContributesAndroidInjector
    abstract MyPostListFragment provideMyPostFragment();

    @ContributesAndroidInjector
    abstract RoomInvitationFragment roomInviteFragement();
}
