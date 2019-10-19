package edu.cs309.cycloneinsider.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.models.MembershipModel;
import edu.cs309.cycloneinsider.fragments.JoinRoomFragment;
import edu.cs309.cycloneinsider.fragments.PostListFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;

public class MainActivity extends InsiderActivity {
    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private SubMenu classrooms;
    private List<MembershipModel> memberships;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_main_page);
        mDrawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nvView);

        setSupportActionBar(toolbar);

        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();


        navigationView.setNavigationItemSelectedListener(menuItem -> {
            selectDrawerItem(menuItem);
            return true;
        });

        getInsiderApplication().getApiService().currentUser().observeOn(AndroidSchedulers.mainThread()).subscribe(userResponse -> {
            ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_username)).setText(userResponse.body().username);
        });

        navigationView.getHeaderView(0).findViewById(R.id.sign_out).setOnClickListener(view -> {
            getInsiderApplication().getSession().invalidate();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        this.loadRooms();
    }

    public void selectRoom(String uuid) {
        int i;
        for (i = 0; i < memberships.size(); i++) {
            if (memberships.get(i).room.uuid.equals(uuid)) {
                break;
            }
        }
        selectDrawerItem(classrooms.getItem(i));
    }



    public void unselectAllItems() {
        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
        size = classrooms.size();
        for (int i = 0; i < size; i++) {
            classrooms.getItem(i).setChecked(false);
        }
    }

    public MembershipModel getSelectedMembership(MenuItem menuItem) {
        int size = classrooms.size();
        for (int i = 0; i < size; i++) {
            if (classrooms.getItem(i) == menuItem) {
                return memberships.get(i);
            }
        }
        return null;
    }

    public void loadRooms(Action complete) {
        if (classrooms == null) {
            classrooms = navigationView.getMenu().addSubMenu("Classrooms");
        } else {
            classrooms.clear();
        }
        getInsiderApplication().getApiService()
                .getMemberships()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() == 200) {
                        Log.d(TAG, "onCreate: " + response);

                        memberships = response.body();
                        for (MembershipModel membership : memberships) {
                            classrooms.add(membership.room.name);
                        }
                        navigationView.invalidate();
                        navigationView.setCheckedItem(R.id.nav_front_page);
                        selectDrawerItem(navigationView.getCheckedItem());
                    }
                }, error -> {
                    Log.d(TAG, error.toString());
                }, complete);
    }

    public void loadRooms() {
        loadRooms(() -> {
        });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        unselectAllItems();
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.nav_front_page:
                fragment = PostListFragment.newInstance(null);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                break;
            case R.id.settings:
                startActivity(new Intent(this,SettingsActivity.class));
                return;
            case R.id.nav_fav_post:
                startActivity(new Intent(this, FavoritePostActivity.class));
                return;
            case R.id.nav_join_room:
                fragment = new JoinRoomFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                break;
            default: {
                MembershipModel selectedMembership = getSelectedMembership(menuItem);
                if (selectedMembership == null) {
                    return;
                }
                fragment = PostListFragment.newInstance(selectedMembership.room.uuid);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        }

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    public void openDefaultThread(View view) {
        Intent intent = new Intent(this, CreatePostActivity.class);
        startActivity(intent);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        drawerToggle.syncState();
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        drawerToggle.syncState();
        super.onConfigurationChanged(newConfig);
    }

}