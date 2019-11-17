package edu.cs309.cycloneinsider.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import io.reactivex.Observable;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import retrofit2.Response;


public class AdminProfessorValidateViewModel extends ViewModel {
    private final MutableLiveData<Response<List<InsiderUserModel>>> professorResponse = new MutableLiveData<>();
    private final MutableLiveData<Response<InsiderUserModel>> userToProfessor = new MutableLiveData<>();
    private CycloneInsiderService cycloneInsiderService;

    @Inject
    public AdminProfessorValidateViewModel(CycloneInsiderService cycloneInsiderService) {
        this.cycloneInsiderService = cycloneInsiderService;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void refresh() {
        Observable<Response<List<InsiderUserModel>>> observable = null;

        observable = cycloneInsiderService.getPendingProfessorMemberships();

        observable.subscribe(professorResponse::postValue);
    }

    public void setProfessor() {
        Observable<Response<InsiderUserModel>> observable = null;
        observable = cycloneInsiderService.setUserToProfessor();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(userToProfessor::postValue);
    }

    public LiveData<Response<List<InsiderUserModel>>> getProfessorListResponse() {
        return professorResponse;
    }
}
