package edu.cs309.cycloneinsider.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.fragments.adapters.UserListRecyclerViewAdapter;
import edu.cs309.cycloneinsider.viewmodels.AdminProfessorValidateViewModel;
import io.reactivex.disposables.Disposable;

public class AdminProfessorValidateFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;
    AdminProfessorValidateViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    UserListRecyclerViewAdapter professorListRecyclerViewAdapter;
    private LinearLayoutManager layoutManager;
    private Disposable onClickSubscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onDestroy() {
        if (onClickSubscription!= null && !onClickSubscription.isDisposed()) {
            onClickSubscription.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(AdminProfessorValidateViewModel.class);
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(viewModel::refresh);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        professorListRecyclerViewAdapter = new UserListRecyclerViewAdapter();
        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(professorListRecyclerViewAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        viewModel.getProfessorListResponse().observe(this, listResponse -> {
            if (listResponse.isSuccessful()) {
                List<InsiderUserModel> insiderUserModelList = listResponse.body();
                professorListRecyclerViewAdapter.updateList(insiderUserModelList);
            }
            swipeRefreshLayout.setRefreshing(false);
        });


        onClickSubscription = professorListRecyclerViewAdapter.getItemClicks().subscribe(insiderUserModel -> {
            new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Validate " + insiderUserModel.username + "?")

                    .setPositiveButton("Accept", (dialogInterface, i) -> {
                        viewModel.setProfessor();
                    })
                    .setNegativeButton("Deny", ((dialogInterface, i) -> {
                        // deny professor by API request
                    }))
                    .setNeutralButton("Cancel", (dialogInterface, i) -> {
                        // just cancel
                    })
                    .create()
                    .show();
        });
        viewModel.refresh();
    }
}
