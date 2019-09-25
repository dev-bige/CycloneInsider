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

public class RoomListRecyclerViewAdapter extends RecyclerView.Adapter<RoomListRecyclerViewAdapter.ViewHolder> {
    private List<RoomModel> rooms = new ArrayList();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_room, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RoomModel room = rooms.get(position);
        holder.title.setText(room.name);
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.list_item_room_title);
        }
    }

    public void updateList(List<RoomModel> rooms) {
        this.rooms = rooms;
        this.notifyDataSetChanged();
    }
}
