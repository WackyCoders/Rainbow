package com.guisorting.app;

import android.app.*;
import android.content.*;
import android.graphics.Point;
import android.os.*;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;

import example.guisorting.app.R;

/**
 * Created by walter on 09.06.14.
 */
public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getActionBar().hide();
        setContentView(R.layout.activity_menu);

        Button startButton = (Button) findViewById(R.id.start_game);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        Button startSettings = (Button) findViewById(R.id.settings);
        startSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSettings();
            }
        });

        Display localDisplay = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        localDisplay.getSize(size);
        ViewGroup.LayoutParams params = startButton.getLayoutParams();
        params.height = size.y/10;
        startButton.setLayoutParams(params);

        params = startSettings.getLayoutParams();
        params.height = size.y/10;
        startSettings.setLayoutParams(params);
    }

    private void startGame(){
        Intent launchGame = new Intent(this, MainActivity.class);
        startActivity(launchGame);
    }

    private void startSettings(){
        Intent launchSettings = new Intent(this, SettingsActivity.class);
        startActivity(launchSettings);
    }
}
