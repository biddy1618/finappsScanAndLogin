package com.example.dauren.myapplication;



import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScanFragment extends Fragment implements ZXingScannerView.ResultHandler {

    public static final String NEW_DATA = "NEW_DATA";

    private ZXingScannerView mScannerView;

    public ScanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mScannerView = new ZXingScannerView(getContext());

        mScannerView.setResultHandler(this);
        mScannerView.startCamera     (    );

        return mScannerView;
    }

    @Override
    public void handleResult(final Result result) {

        SharedPreferences preferences = getActivity().getPreferences(Activity.MODE_PRIVATE);
        preferences.edit().putString(NEW_DATA, result.getText()).commit();

        getFragmentManager().popBackStack();

        mScannerView.stopCamera();
    }
}
