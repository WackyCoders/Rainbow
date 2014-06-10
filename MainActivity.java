package com.guisorting.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.*;
import android.preference.PreferenceManager;
import android.support.v7.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import example.guisorting.app.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActionBar().hide();
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Intent bridge
        Intent intent = getIntent();
        final String sortType = intent.getStringExtra("SortType");
        //final String colorMode = intent.getStringExtra("ColorMode");
        //String sizeofarray = intent.getStringExtra("SizeOfArray");
        //String stimeout = intent.getStringExtra("Timeout");
        int size_of_array = 0;
        int timeout = 0;

        //Shared Prefences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String colorMode = preferences.getString("ColorMode", null);
        String sizeofarray = preferences.getString("SizeOfArray", null);
        String stimeout = preferences.getString("Timeout", null);

        if(sizeofarray != null) {
            size_of_array = Integer.parseInt(sizeofarray);
        }
        if(stimeout != null) {
            timeout = Integer.parseInt(stimeout);
        }

        //components modifying
        final Button startButton = (Button) findViewById(R.id.start_button);
        Display thisDisplay = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        thisDisplay.getSize(size);
        ViewGroup.LayoutParams params = startButton.getLayoutParams();
        params.height = size.y / 10;
        startButton.setLayoutParams(params);
        final Button stopbutton = (Button) findViewById(R.id.stop_button);
        params = stopbutton.getLayoutParams();
        params.height = size.y / 10;
        stopbutton.setLayoutParams(params);
        stopbutton.setEnabled(false);


        //Layouts magic
        FrameLayout currentView = (FrameLayout) findViewById(R.id.action_frame);
        final GUIVisualizer gui = new GUIVisualizer(this);
        gui.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT));

        if(sortType != null) {
            gui.setSortType(sortType(sortType));
        }

        if(size_of_array != 0) {
            gui.setARRAY_SIZE(size_of_array);
        } else {
            size_of_array = 40;
        }

        if(timeout != 0){
            gui.setMillis(timeout);
        }

        if(colorMode != null) {
            gui.setRainbowType(rainbowType(colorMode));
        }

        int color = gui.getRainbowType();
        if(color == 0) {
            gui.init();
        } else if(color == 1){
            gui.rainbowFill(size_of_array/6);
            //System.out.println("WARNING!!! INSIDE COLOR!!!");
        }
        currentView.addView(gui);

        //Actions performed
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gui.start();
                //System.out.println("WARNING!!!!" + colorMode);
                startButton.setEnabled(false);
                stopbutton.setEnabled(true);
            }
        });

        stopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gui.stop();
                startButton.setEnabled(true);
                stopbutton.setEnabled(false);
            }
        });


    }

    private int rainbowType(String rainbow){
        if(rainbow.equals("MonoRainbow")){
            return 0;
        } else if(rainbow.equals("ColoredRainbow")){
            return 1;
        }
        return -1;
    }

    private int sortType(String sort){
        if(sort.equals("QuickSort")){
            return 0;
        } else if(sort.equals("BubbleSort")){
            return 1;
        } else if(sort.equals("HeapSort")){
            return 2;
        } else if(sort.equals("MergeSort")){
            return 3;
        }
        return -1;
    }
}
