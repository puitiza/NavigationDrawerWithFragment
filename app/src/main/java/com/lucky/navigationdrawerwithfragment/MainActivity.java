package com.lucky.navigationdrawerwithfragment;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.lucky.navigationdrawerwithfragment.fragments.ChatFragment;
import com.lucky.navigationdrawerwithfragment.fragments.MessageFragment;
import com.lucky.navigationdrawerwithfragment.fragments.ProfileFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// -------------------------Se setea el toolbar------------------------------------------------------------
        showToolbar(getResources().getString(R.string.Drawer));
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
// -------------------------------------------------------------------------------------------------------
// -----------Esto es para implementar los metodos cuando se selecciona un item----------------------------
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
// -------------------------------------------------------------------------------------------------------
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MessageFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_message);
        }
    }

    public void showToolbar(String tittle) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        TextView subtitle_toolbar = toolbar.findViewById(R.id.subtitle_toolbar);
        subtitle_toolbar.setText(tittle);
    }

    /**
     * Esta funcion es para que se cierre correctamente el drawer
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /**
     * Segun lo que selecciona en el navigation drawer comienza a hacer
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_message:
                MessageFragment messageFragment = new MessageFragment();
                 getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, messageFragment)
                                            //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) //no es ni tan necesario
                                            .commit();
                break;
            case R.id.nav_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChatFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment())
                        .commit();
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
        }

// Luego de seleccionar un item tiene que cerrarse el drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
