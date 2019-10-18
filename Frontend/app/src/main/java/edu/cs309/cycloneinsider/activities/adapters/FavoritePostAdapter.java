package edu.cs309.cycloneinsider.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.models.FavPostModel;

public class FavoritePostAdapter extends RecyclerView.Adapter<FavoritePostAdapter.ViewHolder> implements Serializable{
    private List<FavPostModel> favPostModelList;
    private OnPostListener mOnPostListener;

    public FavoritePostAdapter(List<FavPostModel> favPostModelList, OnPostListener onPostListener) {
        this.favPostModelList = favPostModelList;
        mOnPostListener = onPostListener;
    }

    @NonNull
    @Override
    public FavoritePostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_post, parent, false);
        return new FavoritePostAdapter.ViewHolder(view, mOnPostListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritePostAdapter.ViewHolder holder, int position) {
        FavPostModel favPost = favPostModelList.get(position);
        holder.post.setText(favPost.post.getTitle());
        holder.room.setText(favPost.post.getRoom().name);

    }

    @Override
    public int getItemCount() { return favPostModelList.size(); }

    public void updateFavList(List<FavPostModel> newFavList) {
        favPostModelList = newFavList;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView post, room;
        OnPostListener onPostListener;

        public ViewHolder(@NonNull View itemView, OnPostListener onPostListener) {
            super (itemView);
            post = itemView.findViewById(R.id.list_item_post_title);
            room = itemView.findViewById(R.id.list_item_room_title);
            this.onPostListener = onPostListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onPostListener.OnPostListener(getAdapterPosition());
        }
    }

    public interface OnPostListener {
        void OnPostListener(int position);
    }
}
