package com.example.sawai.coordinatorlayoutexamples.Example2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sawai.coordinatorlayoutexamples.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sawai on 30/09/16.
 */
public class ExFragment extends Fragment {
    RecyclerView mRecyclerView;
    Adapter mListAdapter;
    List<String> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ex_2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mList.addAll(Arrays.asList("1", "2", "3", "5", "6", "7", "8", "9", "0", "1", "2", "3", "5", "6", "7", "8", "9", "0"));

        mListAdapter = new Adapter(getActivity(), mList);
        mRecyclerView.setAdapter(mListAdapter);

    }
}
