package com.guisorting.app;

import android.app.*;
import android.content.Intent;
import android.os.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;

import example.guisorting.app.R;

/**
 * Created by walter on 09.06.14.
 */
public class ConstantsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_constants);

        final Intent bridge = new Intent(this, SortingsActivity.class);
        final Intent intent = getIntent();

        final EditText sizeofarray = (EditText) findViewById(R.id.size_of_array);
        sizeofarray.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                bridge.putExtra("SizeOfArray", sizeofarray.getText().toString());
            }
        });

        final EditText timeout = (EditText) findViewById(R.id.timeout);
        timeout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                bridge.putExtra("Timeout", timeout.getText().toString());
            }
        });

        Button resume = (Button) findViewById(R.id.resume);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bridge.putExtra("ColorMode", intent.getStringExtra("ColorMode"));
                startActivity(bridge);
            }
        });
    }
}
