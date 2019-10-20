package edu.cs309.cycloneinsider.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.models.PostModel;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class PostListRecyclerViewAdapter extends RecyclerView.Adapter<PostListRecyclerViewAdapter.ViewHolder> {
    private final PublishSubject<PostModel> onClickSubject = PublishSubject.create();
    Date now = new Date();
    SimpleDateFormat dayMonthFormat = new SimpleDateFormat("MMM d", Locale.US);
    SimpleDateFormat timeDateFormat = new SimpleDateFormat("h:mm a", Locale.US);
    private List<PostModel> posts = new ArrayList<>();

    public Observable<PostModel> getItemClicks() {
        return onClickSubject.hide();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PostListRecyclerViewAdapter.ViewHolder holder, int position) {
        final PostModel post = posts.get(position);
        holder.title.setText(post.getTitle());
        String date = "";
        if (now.getTime() - post.getDate().getTime() > TimeUnit.DAYS.toMillis(1)) {
            date = dayMonthFormat.format(post.getDate());
        } else {
            date = timeDateFormat.format(post.getDate());
        }
        holder.username.setText(String.format("%s - %s", post.getUser().getUsername(), date));

        holder.itemView.setOnClickListener(view -> onClickSubject.onNext(post));
    }

    @NonNull
    @Override
    public PostListRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_post, parent, false);
        return new ViewHolder(view);
    }

    public void updateList(List<PostModel> posts) {
        this.posts = posts;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView username;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.list_item_post_title);
            username = itemView.findViewById(R.id.list_item_post_username);
        }
    }
}
