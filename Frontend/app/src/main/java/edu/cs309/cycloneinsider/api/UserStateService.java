package edu.cs309.cycloneinsider.api;


import javax.inject.Inject;
import javax.inject.Singleton;

import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import retrofit2.Response;

@Singleton
public class UserStateService {
    private CycloneInsiderService cycloneInsiderService;
    private BehaviorSubject<InsiderUserModel> currentUser = BehaviorSubject.create();

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

    public void invalidateUser() {
        this.cycloneInsiderService.currentUser()
                .filter(Response::isSuccessful)
                .map(Response::body)
                .subscribe(insiderUserModel -> currentUser.onNext(insiderUserModel));
    }
}
