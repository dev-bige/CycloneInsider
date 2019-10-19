package edu.cs309.cycloneinsider.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.models.CreateRoomRequestModel;
import io.reactivex.disposables.Disposable;

public class CreateRoomActivity extends InsiderActivity implements View.OnClickListener {
    RadioGroup group;
    EditText description, title;

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.create_room_button) {
            //TODO add when private rooms are merged
            boolean isPrivate = group.getCheckedRadioButtonId() == R.id.private_room;
            CreateRoomRequestModel createRoomRequestModel = new CreateRoomRequestModel();
            createRoomRequestModel.setName(title.getText().toString());
            createRoomRequestModel.setDescription(description.getText().toString());
            Disposable subscribe = getInsiderApplication().getApiService().createRoom(createRoomRequestModel).subscribe(createRoomResponseModel -> {
                if (createRoomResponseModel.isSuccessful()) {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        group = findViewById(R.id.private_room_selection);
        description = findViewById(R.id.room_description);
        title = findViewById(R.id.room_name);
        findViewById(R.id.create_room_button).setOnClickListener(this);
    }
}
