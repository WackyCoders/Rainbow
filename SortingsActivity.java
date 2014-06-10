package com.guisorting.app;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.view.*;
import android.widget.*;

import example.guisorting.app.R;

/**
 * Created by walter on 09.06.14.
 */
public class SortingsActivity extends Activity {

    private String[] names = {"QuickSort", "BubbleSort", "HeapSort", "MergeSort"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sortings);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, names);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        final ListView spinner = (ListView) findViewById(R.id.sortings_checker);
        spinner.setAdapter(adapter);

        final Toast toast = Toast.makeText(this, "Sorting type selected", Toast.LENGTH_SHORT);

        final Intent intent = new Intent(this, MainActivity.class);
        final Intent bridge = getIntent();

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toast.show();
                intent.putExtra("SortType", names[position]);
                intent.putExtra("SizeOfArray", bridge.getStringExtra("SizeOfArray"));
                intent.putExtra("Timeout", bridge.getStringExtra("Timeout"));
                intent.putExtra("ColorMode", bridge.getStringExtra("ColorMode"));
                startActivity(intent);
            }
        });
    }
}
