package edu.cs309.cycloneinsider.fragments.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.UserStateService;
import edu.cs309.cycloneinsider.api.models.PostModel;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class PostListRecyclerViewAdapter extends RecyclerView.Adapter<PostListRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "PostListRecyclerViewAda";
    private final PublishSubject<PostModel> onClickSubject = PublishSubject.create();
    private final PublishSubject<PostModel> onFavoriteClickSubject = PublishSubject.create();
    SimpleDateFormat dayMonthFormat = new SimpleDateFormat("MMM d", Locale.US);
    SimpleDateFormat timeDateFormat = new SimpleDateFormat("h:mm a", Locale.US);
    private List<PostModel> posts = new ArrayList<>();
    private UserStateService userStateService;
    private CycloneInsiderService cycloneInsiderService;

    public PostListRecyclerViewAdapter(UserStateService userStateService, CycloneInsiderService cycloneInsiderService) {
        this.userStateService = userStateService;
        this.cycloneInsiderService = cycloneInsiderService;
    }

    public Observable<PostModel> getItemClicks() {
        return onClickSubject.hide();
    }

    public Observable<PostModel> getFavoriteClicks() {
        return onFavoriteClickSubject.hide();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull PostListRecyclerViewAdapter.ViewHolder holder, int position) {
        final PostModel post = posts.get(position);
        holder.title.setText(post.getTitle());
        Date rounded = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        String date = "";
        if (post.getDate().getTime() < rounded.getTime()) {
            date = dayMonthFormat.format(post.getDate());
        } else {
            date = timeDateFormat.format(post.getDate());
        }
        holder.username.setText(String.format("%s - %s", post.getUser().getUsername(), date));

        holder.itemView.setOnClickListener(view -> onClickSubject.onNext(post));
        holder.favorite.setOnClickListener(view -> {
            onFavoriteClickSubject.onNext(post);
            if (holder.isFavorite) {
                this.cycloneInsiderService.removeFavoritePost(post.getUuid()).subscribe(favoritePostModelResponse -> {
                }, error -> {
                }, () -> this.userStateService.refreshFavorites());
            } else {
                this.cycloneInsiderService.favoritePost(post.getUuid()).subscribe(favoritePostModelResponse -> {
                    Log.d(TAG, favoritePostModelResponse.toString());
                }, error -> {
                    Log.e(TAG, "onBindViewHolder: ", error);
                }, () -> this.userStateService.refreshFavorites());
            }

        });
        holder.favoriteSub(userStateService, post.getUuid());
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
        this.userStateService.refreshFavorites();
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton favorite;
        TextView title;
        TextView username;
        Disposable favoriteSub;
        private boolean isFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.list_item_post_title);
            username = itemView.findViewById(R.id.list_item_post_username);
            favorite = itemView.findViewById(R.id.list_item_post_favorite);
        }

        public void favoriteSub(UserStateService userStateService, String uuid) {
            if (favoriteSub != null) {
                favoriteSub.dispose();
                favoriteSub = null;
            }

            userStateService.isFavoritePost(uuid).subscribe(this::onFavoriteChange);
        }

        public void onFavoriteChange(boolean isFavorite) {
            this.isFavorite = isFavorite;
            Log.d(TAG, "onFavoriteChange() called with: isFavorite = [" + isFavorite + "]");
            if (isFavorite) {
                this.favorite.setImageResource(R.drawable.ic_star_black_24dp);
            } else {
                this.favorite.setImageResource(R.drawable.ic_star_border_black_24dp);
            }
        }
    }
}
