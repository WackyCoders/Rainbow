package com.guisorting.app;

import android.app.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.preference.PreferenceManager;
import android.view.*;
import android.widget.*;

import example.guisorting.app.R;

/**
 * Created by walter on 09.06.14.
 */
public class ColorModeActivity extends Activity {

    private String[] names = {"ColoredRainbow", "MonoRainbow"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_color_mode);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, names);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        ListView listView = (ListView) findViewById(R.id.colored_rainbow);
        listView.setAdapter(adapter);

        //final Intent bridge = new Intent(this, ConstantsActivity.class);

        final Toast toast = Toast.makeText(this, "Color mode selected", Toast.LENGTH_SHORT);

        //Now on I will try to avoid using intents to have an apportunity of making friendly user interface
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //bridge.putExtra("ColorMode", names[position]);
                //startActivity(bridge);

                //shared preferences
                toast.show();
                editor.putString("ColorMode", names[position]);
                editor.commit();
            }
        });
    }
}
