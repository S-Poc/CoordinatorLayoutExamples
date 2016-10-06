package com.example.sawai.coordinatorlayoutexamples.Example5;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ex_5, container, false);
    }
}
