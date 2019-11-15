package edu.cs309.cycloneinsider.api;


import android.annotation.SuppressLint;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.cs309.cycloneinsider.api.models.FavoritePostModel;
import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import retrofit2.Response;

@Singleton
public class UserStateService {
    private CycloneInsiderService cycloneInsiderService;
    private BehaviorSubject<InsiderUserModel> currentUser = BehaviorSubject.create();
    private BehaviorSubject<Map<String, FavoritePostModel>> favoritesMap = BehaviorSubject.create();

    @Inject
    public UserStateService(CycloneInsiderService cycloneInsiderService) {
        this.cycloneInsiderService = cycloneInsiderService;
        invalidateUser();
    }

    public Observable<InsiderUserModel> getUserAsync() {
        return currentUser;
    }

    public InsiderUserModel getUser() {
        return currentUser.getValue();
    }

    public Boolean isAdmin() {
        return getUser().getAdmin();
    }

    public Boolean isProfessor() {
        return getUser().getProfessor();
    }

    @SuppressLint("CheckResult")
    public void invalidateUser() {
        this.cycloneInsiderService.currentUser()
                .filter(Response::isSuccessful)
                .map(Response::body)
                .subscribe(insiderUserModel -> currentUser.onNext(insiderUserModel));
        this.refreshFavorites();
    }

    @SuppressLint("CheckResult")
    public void refreshFavorites() {
        this.cycloneInsiderService.getFavoritePost().filter(Response::isSuccessful)
                .map(Response::body)
                .map(favoritePostModels -> {
                    HashMap<String, FavoritePostModel> stringFavoritePostModelHashMap = new HashMap<>();
                    for (int i = 0; i < favoritePostModels.size(); i++) {
                        stringFavoritePostModelHashMap.put(favoritePostModels.get(i).getPost().getUuid(), favoritePostModels.get(i));
                    }
                    return stringFavoritePostModelHashMap;
                })
                .subscribe(favoritesMap -> this.favoritesMap.onNext(favoritesMap));
    }

    public Observable<Boolean> isFavoritePost(String uuid) {
        return this.favoritesMap
                .map(stringFavoritePostModelMap -> stringFavoritePostModelMap.containsKey(uuid));
    }
}
