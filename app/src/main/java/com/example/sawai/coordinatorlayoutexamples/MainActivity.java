package com.example.sawai.coordinatorlayoutexamples;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.example.sawai.coordinatorlayoutexamples.Example1.ExFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListAdapter.AdapterCallback, FragmentManager.OnBackStackChangedListener{
    RecyclerView mRecyclerView;
    ListAdapter mListAdapter;
    FrameLayout mFrameLayout;
    List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFrameLayout = (FrameLayout) findViewById(R.id.main_container);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mList.addAll(Arrays.asList("Example 1", "Example 2", "Example 3", "Example 4", "Example 5"));

        mListAdapter = new ListAdapter(this, mList);
        mRecyclerView.setAdapter(mListAdapter);

        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(int position) {
        if (position == 0) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, new ExFragment())
                    .addToBackStack(null)
                    .commit();

        } else if (position == 1) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, new com.example.sawai.coordinatorlayoutexamples.Example2.ExFragment())
                    .addToBackStack(null)
                    .commit();

        } else if (position == 2) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, new com.example.sawai.coordinatorlayoutexamples.Example3.ExFragment())
                    .addToBackStack(null)
                    .commit();

        } else if (position == 3) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, new com.example.sawai.coordinatorlayoutexamples.Example4.ExFragment())
                    .addToBackStack(null)
                    .commit();

        } else if (position == 4) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, new com.example.sawai.coordinatorlayoutexamples.Example5.ExFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onBackStackChanged() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count > 0) {
            mRecyclerView.setVisibility(View.GONE);
            mFrameLayout.setVisibility(View.VISIBLE);

        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mFrameLayout.setVisibility(View.GONE);
        }
    }
}

