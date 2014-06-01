package example.guisorting.app;

import android.content.pm.ActivityInfo;
import android.graphics.*;
import android.support.v7.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;


public class MainActivity extends ActionBarActivity {
    //Menu constants
    private static final int EXIT = 0;
    private static final int EDIT = 1;
    private static final int EXIT_MENU_ITEM = Menu.FIRST;
    private static final int UNDO_MENU_ITEM = EXIT_MENU_ITEM + 1;
    private static final int SORT_MENU_ITEM = UNDO_MENU_ITEM + 1;
    private static final int QUICKSORT_MENU_ITEM = SORT_MENU_ITEM + 1;
    private static final int BUBLESORT_MENU_ITEM = QUICKSORT_MENU_ITEM + 1;
    private static final int COLORED_MENU_ITEM = BUBLESORT_MENU_ITEM + 1;
    private static final int MONO_MENU_ITEM = COLORED_MENU_ITEM + 1;

    //The heart
    GUIVisualizer tmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

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


        //Layouts magic
        FrameLayout currentView = (FrameLayout) findViewById(R.id.action_frame);
        final GUIVisualizer gui = new GUIVisualizer(this);
        gui.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                                                        FrameLayout.LayoutParams.FILL_PARENT));
        currentView.addView(gui);

        //Actions performed
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gui.start();
                startButton.setActivated(false);
                stopbutton.setActivated(true);
            }
        });

        stopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gui.stop();
                startButton.setActivated(true);
                stopbutton.setActivated(false);
            }
        });

        tmp = gui;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu fileMenu = menu.addSubMenu("Exit");
        SubMenu editMenu = menu.addSubMenu("Edit");
        fileMenu.add(EXIT, EXIT_MENU_ITEM, 0, "Exit");
        editMenu.add(EDIT, UNDO_MENU_ITEM, 0, "Set");
        editMenu.add(EDIT, SORT_MENU_ITEM, 1, "Sort");
        //getMenuInflater().inflate(R.menu.main, menu);

        SubMenu quicksort = editMenu.addSubMenu("QuickSort");
        SubMenu bublesort = editMenu.addSubMenu("BubleSort");
        quicksort.add(EDIT, QUICKSORT_MENU_ITEM, 2, "QuickSort");
        bublesort.add(EDIT, BUBLESORT_MENU_ITEM, 2, "BubbleSort");

        SubMenu coloredRainbow = editMenu.addSubMenu("Colored");
        SubMenu monoRainbow = editMenu.addSubMenu("Mono");
        coloredRainbow.add(EDIT, COLORED_MENU_ITEM, 3, "Colored Rainbow");
        monoRainbow.add(EDIT, MONO_MENU_ITEM, 3, "Mono Rainbow");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(getApplicationContext(), "You Clicked on Settings", Toast.LENGTH_LONG).show();
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case EXIT_MENU_ITEM:
                Toast.makeText(getApplicationContext(), "You Clicked on Exit", Toast.LENGTH_LONG).show();
                System.exit(0);
                break;
            case UNDO_MENU_ITEM:
                Toast.makeText(getApplicationContext(), "You Clicked on Set", Toast.LENGTH_LONG).show();
                break;
            case SORT_MENU_ITEM:
                Toast.makeText(getApplicationContext(), "You Clicked on Sort", Toast.LENGTH_LONG).show();
                break;
            case QUICKSORT_MENU_ITEM:
                Toast.makeText(getApplicationContext(), "You Selected the Quicksort", Toast.LENGTH_LONG).show();
                tmp.setSortType(1);
                break;
            case BUBLESORT_MENU_ITEM:
                Toast.makeText(getApplicationContext(), "You Selected the BubleSort", Toast.LENGTH_LONG).show();
                tmp.setSortType(2);
                break;
            case COLORED_MENU_ITEM:
                Toast.makeText(getApplicationContext(), "You Selected the Colored Rainbow", Toast.LENGTH_LONG).show();
                tmp.setRainbowType(1);
                break;
            case MONO_MENU_ITEM:
                Toast.makeText(getApplicationContext(), "You Selected the Mono Rainbow", Toast.LENGTH_LONG).show();
                tmp.setRainbowType(2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
