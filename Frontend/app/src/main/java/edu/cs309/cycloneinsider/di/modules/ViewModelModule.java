package edu.cs309.cycloneinsider.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.di.ViewModelKey;
import edu.cs309.cycloneinsider.viewmodels.LoginViewModel;
import edu.cs309.cycloneinsider.viewmodels.PostDetailViewModel;
import edu.cs309.cycloneinsider.viewmodels.PostListViewModel;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PostListViewModel.class)
    abstract ViewModel bindPostListViewModel(PostListViewModel postListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailViewModel.class)
    abstract ViewModel bindPostDetailViewModel(PostDetailViewModel postListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
