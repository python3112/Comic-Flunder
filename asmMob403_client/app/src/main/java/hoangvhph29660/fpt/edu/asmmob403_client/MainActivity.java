package hoangvhph29660.fpt.edu.asmmob403_client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import de.hdodenhof.circleimageview.CircleImageView;
import hoangvhph29660.fpt.edu.asmmob403_client.Adapter.comic_manager;
import hoangvhph29660.fpt.edu.asmmob403_client.CallApi.APIServices;
import hoangvhph29660.fpt.edu.asmmob403_client.fragments.catetory_manger;
import hoangvhph29660.fpt.edu.asmmob403_client.fragments.fragment_home;
import hoangvhph29660.fpt.edu.asmmob403_client.fragments.list_cate_Fragment;
import hoangvhph29660.fpt.edu.asmmob403_client.fragments.search_fragment;
import hoangvhph29660.fpt.edu.asmmob403_client.fragments.setting_profile_frag;
import hoangvhph29660.fpt.edu.asmmob403_client.fragments.user_manager;
import hoangvhph29660.fpt.edu.asmmob403_client.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    CircleImageView avataUser;
    ProgressBar loading_nav;
    TextView nav_fulluser, nav_email;
    public final static String urlAvata = "http://192.168.1.89:3000/uploads/avata/";
    public final static String urlContent = "http://192.168.1.89:3000/uploads/contentImage/";
    public final static String urlCover = "http://192.168.1.89:3000/uploads/coverImage/";
    Toolbar toolbar;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        View view = navigationView.getHeaderView(0);
        avataUser = view.findViewById(R.id.avata_user);
        loading_nav = view.findViewById(R.id.loading_nav);
        nav_email = view.findViewById(R.id.nav_email);
        nav_fulluser = view.findViewById(R.id.nav_fulluser);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu_dra);
        navigationView.setItemIconTintList(null);
        fragment_home home = new fragment_home();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flContent, home)
                .commit();


        ///////// chuyền dữ liệu header nav ///////////
        getUser();

        ////////////////
        if (Build.VERSION.SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                Intent getpermission = new Intent();
                getpermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(getpermission);
            }
        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.setting_pro) {
                    fragment = new setting_profile_frag();

                } else if (item.getItemId() == R.id.home) {
                    fragment = new fragment_home();
                } else if (item.getItemId() == R.id.cate_manager) {
                    fragment = new catetory_manger();
                } else if (item.getItemId() == R.id.user_manager) {
                    fragment = new user_manager();
                } else if (item.getItemId() == R.id.comic_manager) {
                        fragment = new comic_manager();
                } else if (item.getItemId() == R.id.nav_logout) {
                    openDialog_out();
                    return true;
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                toolbar.setTitle(item.getTitle());
                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);


    }

    public void getUser() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String id_user = sharedPreferences.getString("id_user", "");
        id = id_user;
        Log.d("zzzz", "onCreate: " + id_user);

        APIServices.apiServices.getOneUser(id_user).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.code() == 200) {

                    loading_nav.setVisibility(View.INVISIBLE);
                    Glide.with(MainActivity.this).load(urlAvata + response.body().getAvata()).into(avataUser);
                    nav_email.setText("" + response.body().getEmail());
                    nav_fulluser.setText("" + response.body().getFullname());
                    Log.d("zzz", "onResponse: " + response.body().getRole());
                    if ("admin".equals(response.body().getRole())) {
                        navigationView.getMenu().setGroupVisible(R.id.group_user, true);
                        navigationView.getMenu().setGroupVisible(R.id.group_admin, true);
                    } else {
                        navigationView.getMenu().setGroupVisible(R.id.group_user, true);
                        navigationView.getMenu().setGroupVisible(R.id.group_admin, false);
                    }

                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("errGetInfo", "onFailure: " + t);
            }
        });


    }


    public String getId() {
        return id;
    }

    public CircleImageView getAvataUser() {
        return avataUser;
    }

    public TextView getNav_fulluser() {
        return nav_fulluser;
    }

    public TextView getNav_email() {
        return nav_email;
    }

    public void setAvataUser(CircleImageView avataUser) {
        this.avataUser = avataUser;
    }

    public void setNav_fulluser(TextView nav_fulluser) {
        this.nav_fulluser = nav_fulluser;
    }

    public void setNav_email(TextView nav_email) {
        this.nav_email = nav_email;
    }

    public void openDialog_out() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_logout, null);
        builder.setView(view);

        TextView txt_warning = view.findViewById(R.id.txt_warning);
        Button btn_no_dialog = view.findViewById(R.id.btn_no_dialog);
        Button btn_yes_dialog = view.findViewById(R.id.btn_yes_dialog);
        AlertDialog dialog = builder.create();


        btn_no_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_yes_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }


        dialog.show();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

}