package com.wordpress.smdaudhilbe.mohammed_2284.materializemyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wordpress.smdaudhilbe.adapter.MyRecyclerViewAdapter;
import com.wordpress.smdaudhilbe.scrollmanager.ScrollManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    public static final String EXTRA_IMAGE = "com.antonioleiva.materializeyourapp.extraImage";
    public static final String EXTRA_TITLE = "com.antonioleiva.materializeyourapp.extraTitle";

    private Toolbar toolBar;
    RecyclerView myRecyclerView;
    private View myFab;

    private static List<MyRecyclerViewAdapter.ViewModel> items = new ArrayList<>();

    static {
        for (int i = 1; i <= 10; i++) {
            items.add(new MyRecyclerViewAdapter.ViewModel("Item " + i, "http://lorempixel.com/500/500/animals/" + i));
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setActionBarIcon(R.drawable.ic_ab_drawer);
        setMainActivityActionBar(getString(R.string.mainActivity));

        initViews();
    }

    public void initViews() {
        myRecyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        myRecyclerView.setLayoutManager(gridLayoutManager);
        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(items);
        myRecyclerView.setAdapter(myRecyclerViewAdapter);
        myRecyclerViewAdapter.setOnItemClickListener(itemClickListenerInterface);

        myFab = findViewById(R.id.myFab);

        toolBar = (Toolbar) findViewById(R.id.myToolbar);

        // Toolbar height needs to be known before establishing the initial offset
        toolBar.post(new Runnable() {
            @Override
            public void run() {
                ScrollManager scrollManager = new ScrollManager();
                scrollManager.attach(myRecyclerView);
                scrollManager.addView(toolBar, ScrollManager.Direction.UP);
                scrollManager.addView(myFab, ScrollManager.Direction.DOWN);
                scrollManager.setInitialOffset(toolBar.getHeight());
            }
        });
    }

    //  recyclerView Item click listener
    MyRecyclerViewAdapter.OnItemClickListenerInterface itemClickListenerInterface = new MyRecyclerViewAdapter.OnItemClickListenerInterface() {

        @Override
        public void onItemClickInterface(View view, MyRecyclerViewAdapter.ViewModel viewModel) {
            Intent iTent = new Intent(getApplicationContext(), SecondActivity.class);
            iTent.putExtra(EXTRA_IMAGE, viewModel.getImageString());
            iTent.putExtra(EXTRA_TITLE, viewModel.getTextString());

            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, view, EXTRA_IMAGE);
            ActivityCompat.startActivity(MainActivity.this, iTent, activityOptions.toBundle());
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_item1:
                Toast.makeText(getApplicationContext(), "Jack and jill", Toast.LENGTH_LONG).show();
                break;

            case R.id.action_item2:
                Toast.makeText(getApplicationContext(), "went up the hill", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @fromXML
    public void fabClicked(View view) {
        Toast.makeText(getApplicationContext(), "You are tapping FAB!", Toast.LENGTH_LONG).show();
    }
}