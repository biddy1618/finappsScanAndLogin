package com.example.dauren.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public  static final String TAG                               = "MYAPPLICATION6";
    private static final int    MY_PERMISSIONS_REQUEST_USE_CAMERA = 1               ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState    );
        setContentView(R.layout.activity_main);

        switch(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)) {
            case PackageManager.PERMISSION_GRANTED:
                break;
            case PackageManager.PERMISSION_DENIED:
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{ Manifest.permission.CAMERA  },
                        MY_PERMISSIONS_REQUEST_USE_CAMERA         );
                break;
            default:
        }

        if (findViewById(R.id.frame_layout) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            FirstFragment fragment = new FirstFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
//            Bundle args= new Bundle();
//            fragment.setArguments(args);

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager()
                    .beginTransaction()
                    .add             (R.id.frame_layout, fragment)
                    .commit          ();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_USE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                return;
            default:
                return;
        }
    }
}
