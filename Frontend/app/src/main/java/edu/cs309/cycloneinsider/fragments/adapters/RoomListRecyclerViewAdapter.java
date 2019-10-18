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
import edu.cs309.cycloneinsider.api.models.RoomModel;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RoomListRecyclerViewAdapter extends RecyclerView.Adapter<RoomListRecyclerViewAdapter.ViewHolder> {
    private final PublishSubject<RoomModel> onClickSubject = PublishSubject.create();
    private List<RoomModel> rooms = new ArrayList();

    public Observable<RoomModel> getItemClicks() {
        return onClickSubject.hide();
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RoomModel room = rooms.get(position);
        holder.title.setText(room.name);

        holder.itemView.setOnClickListener(view -> onClickSubject.onNext(room));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_room, parent, false);
        return new ViewHolder(view);
    }

    public void updateList(List<RoomModel> rooms) {
        this.rooms = rooms;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.list_item_room_title);
        }
    }
}
