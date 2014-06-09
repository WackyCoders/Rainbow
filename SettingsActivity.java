package com.guisorting.app;

import android.app.*;
import android.content.Intent;
import android.graphics.Point;
import android.os.*;
import android.view.*;
import android.widget.*;

import example.guisorting.app.R;

/**
 * Created by walter on 09.06.14.
 */
public class SettingsActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        Button sortingButton = (Button) findViewById(R.id.sort_type);
        sortingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSorting();
            }
        });

        Button colors = (Button) findViewById(R.id.color_modes);
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startColorModes();
            }
        });

        Button constantsButton = (Button) findViewById(R.id.local_constants);
        constantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConstants();
            }
        });

        Display localDisplay = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        localDisplay.getSize(size);
        ViewGroup.LayoutParams params = sortingButton.getLayoutParams();
        params.height = size.y/10;
        sortingButton.setLayoutParams(params);

        params = colors.getLayoutParams();
        params.height = size.y/10;
        colors.setLayoutParams(params);

        params = constantsButton.getLayoutParams();
        params.height = size.y/10;
        constantsButton.setLayoutParams(params);
    }
    
    private void startSorting(){
        Intent launchSorting = new Intent(this, SortingsActivity.class);
        startActivity(launchSorting);
    }

    private void startConstants(){
        Intent launchConstants = new Intent(this, ConstantsActivity.class);
        startActivity(launchConstants);
    }

    private void startColorModes(){
        Intent launchColorModes = new Intent(this, ColorModeActivity.class);
        startActivity(launchColorModes);
    }
}
