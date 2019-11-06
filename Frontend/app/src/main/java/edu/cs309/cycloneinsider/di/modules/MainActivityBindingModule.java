package edu.cs309.cycloneinsider.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import edu.cs309.cycloneinsider.fragments.FavoritePostFragment;
import edu.cs309.cycloneinsider.fragments.JoinRoomFragment;
import edu.cs309.cycloneinsider.fragments.MyPostListFragment;
import edu.cs309.cycloneinsider.fragments.PostListFragment;
<<<<<<< Frontend/app/src/main/java/edu/cs309/cycloneinsider/di/modules/MainActivityBindingModule.java
import edu.cs309.cycloneinsider.fragments.RoomInvitationFragment;
=======
import edu.cs309.cycloneinsider.viewmodels.JoinRoomViewModel;
>>>>>>> Frontend/app/src/main/java/edu/cs309/cycloneinsider/di/modules/MainActivityBindingModule.java

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

    @ContributesAndroidInjector
    abstract JoinRoomFragment provideJoinRoomFragment();
}
