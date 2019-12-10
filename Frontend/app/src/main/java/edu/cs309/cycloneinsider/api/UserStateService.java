package edu.cs309.cycloneinsider.api;


import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.cs309.cycloneinsider.api.models.FavoritePostModel;
import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import edu.cs309.cycloneinsider.api.models.MembershipModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.subjects.BehaviorSubject;
import retrofit2.Response;

@Singleton
public class UserStateService {
    private CycloneInsiderService cycloneInsiderService;
    private BehaviorSubject<InsiderUserModel> currentUser = BehaviorSubject.create();
    private BehaviorSubject<Map<String, MembershipModel>> memberships = BehaviorSubject.create();
    private BehaviorSubject<Map<String, FavoritePostModel>> favoritesMap = BehaviorSubject.create();

    @Inject
    public UserStateService(CycloneInsiderService cycloneInsiderService) {
        this.cycloneInsiderService = cycloneInsiderService;
        invalidateUser();
    }

    public Observable<InsiderUserModel> getUserAsync() {
        return currentUser.hide();
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
        this.refreshMemberships(null);
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

    @SuppressLint("CheckResult")
    public void refreshMemberships(@Nullable Action onComplete) {
        this.cycloneInsiderService.getMemberships().filter(Response::isSuccessful)
                .map(Response::body)
                .map(membershipModels -> {
                    Map<String, MembershipModel> memberships = new HashMap<>();
                    for (MembershipModel membershipModel : membershipModels) {
                        memberships.put(membershipModel.room.uuid, membershipModel);
                    }
                    return memberships;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(membershipModels -> memberships.onNext(membershipModels), e -> {}, onComplete == null ? () -> {} : onComplete);
    }

    public Observable<MembershipModel> getMembershipAsync(String room_uuid) {
        return this.memberships
                .map(stringMembershipModelMap -> stringMembershipModelMap.get(room_uuid));
    }

    public MembershipModel getMembership(String room_uuid) {
        return this.memberships.getValue().get(room_uuid);
    }

    public Observable<Collection<MembershipModel>> getMembershipsAsync() {
        return memberships.map(stringMembershipModelMap -> stringMembershipModelMap.values());
    }

    public Collection<MembershipModel> getMemberships() {
        return memberships.getValue().values();
    }

    public Observable<Boolean> isFavoritePost(String uuid) {
        return this.favoritesMap
                .map(stringFavoritePostModelMap -> stringFavoritePostModelMap.containsKey(uuid));
    }
}
