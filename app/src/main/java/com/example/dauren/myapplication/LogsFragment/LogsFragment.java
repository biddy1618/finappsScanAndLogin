package com.example.dauren.myapplication.LogsFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dauren.myapplication.R;
import com.example.dauren.myapplication.data.LogsDbHelper;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogsFragment extends Fragment {

    ListView listView;

    public LogsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_logs, container, false);

        ArrayList<LogsListItem> data = LogsDbHelper.getInstance(getContext()).getData();

        CustomArrayHolder holder = new CustomArrayHolder(
                getContext(),
                R.layout.fragment_logs_list_item,
                data
        );

        listView = (ListView) v.findViewById(R.id.fragment_logs_list_view);
        listView.setAdapter(holder);

        return v;
    }

}
