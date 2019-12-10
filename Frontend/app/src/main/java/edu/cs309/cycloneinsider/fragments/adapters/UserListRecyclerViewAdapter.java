package edu.cs309.cycloneinsider.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.List;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.UserStateService;
import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class UserListRecyclerViewAdapter extends RecyclerView.Adapter<UserListRecyclerViewAdapter.ViewHolder> {
    private final PublishSubject<InsiderUserModel> onClickSubject = PublishSubject.create();
    UserStateService userStateService;
    Function<InsiderUserModel, Boolean> canDelete = user -> this.userStateService.isAdmin();
    private List<InsiderUserModel> userList = new ArrayList<>();

    public UserListRecyclerViewAdapter(UserStateService userStateService) {
        this.userStateService = userStateService;
    }

    public Observable<InsiderUserModel> getItemClicks() {
        return onClickSubject.hide();
    }

    @NonNull
    @Override
    public UserListRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListRecyclerViewAdapter.ViewHolder holder, int position) {
        final InsiderUserModel user = userList.get(position);
        holder.title.setText(String.format("%s %s", user.firstName, user.lastName));
        holder.username.setText(user.username);
        holder.delete.setVisibility(canDelete.apply(user) ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(view -> onClickSubject.onNext(user));
    }


    public void setCanDelete(Function<InsiderUserModel, Boolean> canDelete) {
        this.canDelete = canDelete;
    }

    public void updateList(List<InsiderUserModel> userList) {
        this.userList = userList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton delete;
        TextView title;
        TextView username;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.list_item_user_title);
            username = itemView.findViewById(R.id.list_item_user_username);
            delete = itemView.findViewById(R.id.list_item_user_delete);
        }
    }
}
