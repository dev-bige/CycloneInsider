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

import javax.inject.Inject;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.Session;
import edu.cs309.cycloneinsider.api.UserStateService;
import edu.cs309.cycloneinsider.api.models.MembershipModel;
import edu.cs309.cycloneinsider.fragments.AdminProfessorValidateFragment;
import edu.cs309.cycloneinsider.fragments.FavoritePostFragment;
import edu.cs309.cycloneinsider.fragments.JoinRoomFragment;
import edu.cs309.cycloneinsider.fragments.MyPostListFragment;
import edu.cs309.cycloneinsider.fragments.PostListFragment;
import edu.cs309.cycloneinsider.fragments.RoomInvitationFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;

/**
 * Activity that is launched every time a user logs in or when already logged in user launches the application
 */
public class MainActivity extends InsiderActivity {
    private static final String TAG = "MainActivity";
    @Inject
    CycloneInsiderService cycloneInsiderService;
    @Inject
    Session session;
    @Inject
    UserStateService userStateService;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private SubMenu classrooms;
    private boolean first = true;
    private List<MembershipModel> memberships;

    public MembershipModel getSelectedMembership(MenuItem menuItem) {
        int size = classrooms.size();
        for (int i = 0; i < size; i++) {
            if (classrooms.getItem(i) == menuItem) {
                return memberships.get(i);
            }
        }
        return null;
    }

    @SuppressLint("CheckResult")
    public void loadRooms(Action complete) {

        cycloneInsiderService
                .getMemberships()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (classrooms == null) {
                        MenuItem item = navigationView.getMenu().findItem(R.id.classrooms);
                        classrooms = item.getSubMenu();
                        classrooms.clear();
                    } else {
                        classrooms.clear();
                    }
                    if (response.code() == 200) {
                        Log.d(TAG, "onCreate: " + response);

                        memberships = response.body();
                        for (MembershipModel membership : memberships) {
                            classrooms.add(membership.room.name);
                        }
                        navigationView.invalidate();
                    }
                    if (first) {
                        navigationView.setCheckedItem(R.id.nav_front_page);
                        selectDrawerItem(navigationView.getCheckedItem());
                        first = false;
                    }
                }, error -> {
                    Log.d(TAG, error.toString());
                }, complete);
    }

    public void loadRooms() {
        loadRooms(() -> {
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        drawerToggle.syncState();
        super.onConfigurationChanged(newConfig);
    }

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

        userStateService.getUserAsync().subscribe(userResponse -> {
            ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_username)).setText(userResponse.getUsername());
            if (!userResponse.getAdmin() && !userResponse.getProfessor()) {
                navigationView.getMenu().findItem(R.id.nav_create_room).setVisible(false);
            } else if (!userResponse.getAdmin()) {
                navigationView.getMenu().findItem(R.id.nav_create_room).setVisible(false);
            }

            if (!userResponse.getAdmin()) {
                navigationView.getMenu().findItem(R.id.nav_admin_tools).setVisible(false);
            }
        });

        navigationView.getHeaderView(0).findViewById(R.id.sign_out).setOnClickListener(view -> {
            session.invalidate();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        this.loadRooms();
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

    public void openDefaultThread(View view) {
        Intent intent = new Intent(this, CreatePostActivity.class);
        startActivity(intent);
        return;
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
                startActivity(new Intent(this, SettingsActivity.class));
                mDrawer.closeDrawers();
                return;
            case R.id.nav_create_room:
                startActivity(new Intent(this, CreateRoomActivity.class));
                mDrawer.closeDrawers();
                return;
            case R.id.nav_fav_post:
                fragment = new FavoritePostFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                break;
            case R.id.nav_join_room:
                fragment = new JoinRoomFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                break;
            case R.id.nav_my_post:
                fragment = new MyPostListFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                break;
            case R.id.nav_room_invitation:
                fragment = new RoomInvitationFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                break;
            case R.id.nav_admin_tools:
                fragment = new AdminProfessorValidateFragment();
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

}