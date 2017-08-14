package com.example.francisco.w2project;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickChild, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = "MainActivity";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    String names[] = Constant.name;
    int nameIcons[] = Constant.nameIcons;

    String subNames[][] = Constant.subName;
    int subIcons[][] = Constant.subIcons;

    Hashtable<String, String> classSubName = Constant.classSubName;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.media_player_container)
    LinearLayout media_player_container;

    static MediaPlayer mp3;
    ImageButton btnPlay, btnPause, btnStop;

    public static String PACKAGE_NAME = "com.example.francisco.w2project";

    DialogFragmentHome dialogFragment;

    boolean showed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
//        final ActionBar actionar = getSupportActionBar();
//        actionar.setDisplayHomeAsUpEnabled(true);
//        actionar.setHomeAsUpIndicator(R.drawable.ic_menu);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        List<TitleMenu> list = getList();
        RecyclerAdapter adapter = new RecyclerAdapter(this, list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);



        if(mp3!=null && !mp3.isPlaying()) {
            mp3 = MediaPlayer.create(this, R.raw.got_jazz);
        }

        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnPause = (ImageButton) findViewById(R.id.btnPause);
        btnStop = (ImageButton) findViewById(R.id.btnStop);

        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        Fragment fragment = new Home();
        if(fragment != null && savedInstanceState == null)
            Functions.setFragment(fragment,R.id.content_main,getSupportFragmentManager(),this);


        if(!showed) {
            //Show fragment manager
            FragmentManager fm = getSupportFragmentManager();
            dialogFragment = new DialogFragmentHome();
            dialogFragment.show(fm, "Welcome");


            Thread dismissFragment = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                        dialogFragment.dismissmethod();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            dismissFragment.start();
        }


        final SendSMS sendSms = new SendSMS();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSms.show(fragmentManager,"sendSms");
            }
        });
    }

    private List<TitleMenu> getList() {
        List<TitleMenu> list = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            List<SubTitle> subTitles = new ArrayList<>();
            for (int j = 0; j < subNames[i].length; j++) {
                SubTitle subTitle = new SubTitle(subNames[i][j],subIcons[i][j]);
                subTitles.add(subTitle);
            }
            TitleMenu model = new TitleMenu(names[i], nameIcons[i], subTitles);
            list.add(model);
        }
        return list;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            //noinspection SimplifiableIfStatement
            case R.id.action_media:
                if(item.isCheckable()) {
                    item.setCheckable(false);
                    media_player_container.setVisibility(media_player_container.getRootView().GONE);
                }else{
                    item.setCheckable(true);
                    media_player_container.setVisibility(media_player_container.getRootView().VISIBLE);
                }
                return true;
            // Respond to the action bar's back button
            case R.id.action_back:
                //Trigger BackKey
                this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
                return true;
            case R.id.action_backstack:
                String s = "SingleTask: ";
                FragmentManager manager = getSupportFragmentManager();
                for(int i=0;i<manager.getBackStackEntryCount();i++)
                    s += manager.getBackStackEntryAt(i).getName()+",";
                s = s.substring(0,s.length()-1);
                Toast.makeText(this, s, Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChildClick(String who) {
        //String name = subNames[position];
        try {
            Log.d(TAG, "onChildClick: " + who + " " + PACKAGE_NAME + "." + classSubName.get(who));
            Fragment fragment = Functions.string_to_fragment(PACKAGE_NAME + "." + classSubName.get(who));
            Functions.setFragment(fragment,R.id.content_main,getSupportFragmentManager(),this);
        }catch(Exception ex){
            Toast.makeText(this, "This element doesnt exists", Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Log.d(TAG, "onNavigationItemSelected: jeje"+id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnPlay:
                if(mp3 == null) {
                    Log.d(TAG, "stopped" +mp3);
                    mp3 = MediaPlayer.create(getApplicationContext(),R.raw.got_jazz);
                    mp3.start();
                }
                if(!mp3.isPlaying()){
                    mp3.start();
                }
                break;
            case R.id.btnPause:
                if(mp3 != null)
                    if(mp3.isPlaying())
                        mp3.pause();
                break;
            case R.id.btnStop:
                if(mp3 != null) {
                    mp3.stop();
                    mp3.release();
                    mp3 = null;
                    Log.d(TAG, "onClick: " + mp3);
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("dialog",true);
        dialogFragment.dismissmethod();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        showed = savedInstanceState.getBoolean("dialog");
        if(showed)
            dialogFragment.dismissmethod();
    }
}
