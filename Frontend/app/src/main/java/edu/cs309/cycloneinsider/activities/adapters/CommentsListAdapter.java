package edu.cs309.cycloneinsider.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.models.CommentModel;


public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.ViewHolder> {
    private List<CommentModel> comments = new ArrayList<>();

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsListAdapter.ViewHolder holder, int position) {
        CommentModel comment = comments.get(position);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.list_item_comment_username);
            content = itemView.findViewById(R.id.list_item_comment_content);
        }
    }
}
