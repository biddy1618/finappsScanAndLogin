package com.example.dauren.myapplication;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dauren.myapplication.LogsFragment.LogsFragment;
import com.example.dauren.myapplication.data.LogsDbHelper;
import com.example.dauren.myapplication.retrofit.CustomService;
import com.example.dauren.myapplication.retrofit.ExClass;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {


    public static final String ARRAY_KEY    = "LIST";
    public static final String FRAGMENT_TAG = "FIRST_FRAGMENT";

    public ArrayAdapter      adapter  ;
    public ArrayList<String> arrayList;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container    ,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View   v = inflater.inflate(R.layout.fragment_first, container, false);

        Button scanButton  = (Button) v.findViewById(R.id.scanButton    );
        Button logsButton  = (Button) v.findViewById(R.id.scanLogsButton);
        Button cacheButton = (Button) v.findViewById(R.id.clearCashe    );

        scanButton .setOnClickListener(onScanButtonClick );
        logsButton .setOnClickListener(onLogsButtonClick );
        cacheButton.setOnClickListener(onCacheButtonClick);


        if (arrayList == null) arrayList = new ArrayList<>();

        adapter = new ArrayAdapter(
                getActivity(),
                R.layout.fragment_first_list_item,
                arrayList
        );

        ListView listView = (ListView) v.findViewById(R.id.fragment_first_list_view);
        listView.setAdapter(adapter);

        View progress = v.findViewById(R.id.loading_panel);

        SharedPreferences preferences = getActivity().getPreferences(Activity.MODE_PRIVATE);
        String newData = preferences.getString(ScanFragment.NEW_DATA, null);
        if (newData != null) {
            updateListView(newData, progress);
            preferences.edit().remove(ScanFragment.NEW_DATA).commit();
        }

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) arrayList = savedInstanceState.getStringArrayList(ARRAY_KEY);
    }

    public void updateListView(final String item, final View progressBar) {
        Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();

        progressBar.setVisibility(View.VISIBLE);

        final LogsDbHelper mDb = LogsDbHelper.getInstance(getContext());

        CustomService service = CustomService.retrofit.create(CustomService.class);
        Call<ExClass> call    = service.getBoolean(item, "test");
        call.enqueue(new Callback<ExClass>() {
            @Override
            public void onResponse(Call<ExClass> call, Response<ExClass> response) {
//                Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                Log.i(MainActivity.TAG, "Got response: " + response.body().toString());

                if (arrayList != null) arrayList.add(item + ": " + response.body().toString());
                if (adapter   != null) adapter  .notifyDataSetChanged(                       );

                mDb.insert(item, response.body().toString());
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ExClass> call, Throwable t) {
                Log.e(MainActivity.TAG, "Error: " + t.getMessage());

                if (arrayList != null) arrayList.add(item + ": " + t.getMessage());
                if (adapter   != null) adapter  .notifyDataSetChanged(           );


                mDb.insert(item, t.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }



    View.OnClickListener onScanButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ScanFragment scanFragment = new ScanFragment();
            getFragmentManager()
                    .beginTransaction(                                )
                    .replace         (R.id.frame_layout, scanFragment)
                    .addToBackStack  (null                            )
                    .commit          (                                );
        }
    };

    View.OnClickListener onLogsButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LogsFragment logsFragment  = new LogsFragment();
            getFragmentManager()
                    .beginTransaction(                               )
                    .replace         (R.id.frame_layout, logsFragment)
                    .addToBackStack  (null                           )
                    .commit          (                               );
        }
    };


    View.OnClickListener onCacheButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
    };
}
