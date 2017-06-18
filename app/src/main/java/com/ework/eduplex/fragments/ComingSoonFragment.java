package com.ework.eduplex.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ework.eduplex.R;

import butterknife.ButterKnife;

/**
 * Class for Kreditplus Tunai page in Home page on the third tab.
 */
public class ComingSoonFragment extends BaseFragment {


    public ComingSoonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_coming_soon, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

}














