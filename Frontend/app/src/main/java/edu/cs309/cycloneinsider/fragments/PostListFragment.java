package edu.cs309.cycloneinsider.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.activities.DefaultForum;

public class PostListFragment extends Fragment {
    public static final String ROOM_UUID = "ROOM_UUID";
    private String roomUUID;

    public static PostListFragment newInstance(String roomUuid) {
        PostListFragment postListFragment = new PostListFragment();
        Bundle args = new Bundle();
        args.putString(ROOM_UUID, roomUuid);
        postListFragment.setArguments(args);
        return postListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomUUID = getArguments().getString(ROOM_UUID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView().findViewById(R.id.new_post_button).setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), DefaultForum.class));
        });
        TextView roomUUIdTextView = getView().findViewById(R.id.room_uuid);
        roomUUIdTextView.setText(roomUUID);
    }
}
