package edu.cs309.cycloneinsider.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.di.ViewModelKey;
import edu.cs309.cycloneinsider.viewmodels.PostListViewModel;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PostListViewModel.class)
    abstract ViewModel bindPostListViewModel(PostListViewModel postListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
