package edu.cs309.cycloneinsider.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.UserStateService;
import edu.cs309.cycloneinsider.api.models.CommentModel;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.ViewHolder> {
    private List<CommentModel> comments = new ArrayList<>();
    private UserStateService userStateService;

    private Subject<CommentModel> onEditCommentClicked = PublishSubject.create();
    private Subject<CommentModel> onDeleteCommentClicked = PublishSubject.create();

    public CommentsListAdapter(UserStateService userStateService) {
        this.userStateService = userStateService;
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsListAdapter.ViewHolder holder, int position) {
        CommentModel comment = comments.get(position);
        if (comment.getUser().getUuid().equals(userStateService.getUser().getUuid())) {
            holder.edit.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
            holder.edit.setOnClickListener(view -> onEditCommentClicked.onNext(comment));
            holder.delete.setOnClickListener(view -> onDeleteCommentClicked.onNext(comment));
        } else {
            holder.edit.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
        }
        holder.content.setText(comment.getComment());
        holder.username.setText(comment.getUser().getUsername());
    }

    @NonNull
    @Override
    public CommentsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_comment, parent, false);
        return new CommentsListAdapter.ViewHolder(view);
    }

    public void updateList(List<CommentModel> comments) {
        this.comments = comments;
        this.notifyDataSetChanged();
    }

    public Observable<CommentModel> getOnEditCommentClicked() {
        return onEditCommentClicked.hide();
    }

    public Observable<CommentModel> getOnDeleteCommentClicked() {
        return onDeleteCommentClicked.hide();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton edit, delete;
        TextView username, content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.list_item_comment_username);
            content = itemView.findViewById(R.id.list_item_comment_content);
            edit = itemView.findViewById(R.id.list_item_comment_edit);
            delete = itemView.findViewById(R.id.list_item_comment_delete);
        }
    }
}
