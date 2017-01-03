package com.example.yamc.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yamc on 12/25/16.
 */

public class DayFragment extends Fragment {
    private static final String KEY_DAY_NAME = "day_name";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.day_fragment, container, false);
//        String dayName = getArguments().getString(KEY_DAY_NAME);
//        TextView textView = rootView.findViewById(R.id.)
        return rootView;
    }

    protected static DayFragment newInstance(String dayName) {
        DayFragment f = new DayFragment();
        Bundle args = new Bundle();
        args.putString(KEY_DAY_NAME, dayName);
        f.setArguments(args);
        return f;
    }
}
