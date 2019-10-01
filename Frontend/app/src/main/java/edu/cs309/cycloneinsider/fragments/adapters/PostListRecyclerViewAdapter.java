package edu.cs309.cycloneinsider.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.models.PostModel;
import io.reactivex.subjects.PublishSubject;

public class PostListRecyclerViewAdapter extends RecyclerView.Adapter<PostListRecyclerViewAdapter.ViewHolder> {
    private final PublishSubject<PostModel> postModelPublishSubject = PublishSubject.create();
    private List<PostModel> posts = new ArrayList<>();

    @NonNull
    @Override
    public PostListRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostListRecyclerViewAdapter.ViewHolder holder, int position) {
        final PostModel post = posts.get(position);
        holder.title.setText(post.getTitle());
        holder.content.setText(post.getContent());

        holder.itemView.setOnClickListener(view -> postModelPublishSubject.onNext(post));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void updateList(List<PostModel> posts) {
        this.posts = posts;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.list_item_post_title);
            content = itemView.findViewById(R.id.list_item_post_content);
        }
    }
}
