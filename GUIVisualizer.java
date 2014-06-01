package example.guisorting.app;

import android.content.Context;
import android.graphics.*;
import android.util.*;
import android.view.*;
import android.widget.LinearLayout;

import java.util.*;

/**
 * Created by Walter on 5/31/2014.
 */
public class GUIVisualizer extends LinearLayout{
    private int ARRAY_SIZE;
    private int[] hue;
    private int[] palette;
    private Runner runner;
    private Display display;
    private volatile boolean running;
    private final int millis = 30;

    //Menu settings
    private int sortType = 1;
    private int rainbowType = 1;

    public GUIVisualizer(Context context){
        super(context);
        display = new Display(context);
        display.setBackgroundColor(Color.WHITE);
        display.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                                                              LinearLayout.LayoutParams.FILL_PARENT));
        this.addView(display);
        ARRAY_SIZE = 100;
        switch (rainbowType){
            case 1:
                rainbowFill(100);
                break;
            case 2:
                init();
                break;
        }
        //init();
        //rainbowFill(100);
    }

    private void init(){
        hue = new int[ARRAY_SIZE];
        palette = new int[ARRAY_SIZE];
        Random random = new Random();
        for(int i = 0; i < ARRAY_SIZE; ++i){
            palette[i] = Color.rgb(i + 10 , i + 30, i + 70);
            hue[i] = i;
        }
    }

    private void rainbowFill(int size){
        List<Integer> colors = new ArrayList<Integer>();
        int i = 0;
        for (int r = 0; r < size; r++){
            colors.add(Color.rgb(r * 255/size, 255, 0));
        }
        for (int g = size; g > 0; g--){
            colors.add(Color.rgb(255, g * 255/size, 0));
        }
        for (int b = 0; b < size; b++){
            colors.add(Color.rgb(255, 0, b * 255/size));
        }
        for (int r = size; r > 0; r--){
            colors.add(Color.rgb(r * 255/size, 0, 255));
        }
        for (int g = 0; g < size; g++){
            colors.add(Color.rgb(0, g * 255/size, 255));
        }
        for (int b = size; b > 0; b--){
            colors.add(Color.rgb(0, 255, b * 255/size));
        }
        colors.add(Color.rgb(0, 255, 0));

        palette = new int[colors.size()];
        hue = new int[colors.size()];
        for(int j = 0; j < palette.length; ++j){
            palette[j] = colors.get(j);
            hue[j] = j;
        }
    }

    public void start(){
        runner = null;
        runner = new Runner();
        running = true;
        runner.start();
    }

    public void stop(){
        running = false;
        runner = null;
        System.exit(0);
    }

    private void delay(int millis){
        display.postInvalidate();
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int quickSortStep(int lo, int hi){
        int pivot = hue[lo];
        hue[lo] = -1;
        delay(millis);
        while(true){
            while(hi > lo && hue[hi] > pivot){
                --hi;
            }
            if(hi == lo){
                break;
            }
            hue[lo] = hue[hi];
            hue[hi] = -1;
            delay(millis);
            while(lo < hi && hue[lo] < pivot){
                lo++;
            }
            if(hi == lo){
                break;
            }
            hue[hi] = hue[lo];
            hue[lo] = -1;
            delay(millis);
        }
        hue[lo] = pivot;
        delay(millis);
        return lo;
    }

    private void quickSort(int lo, int hi){
        if(hi <= lo){
            return;
        }
        int mid = quickSortStep(lo, hi);
        quickSort(lo, mid - 1);
        quickSort(mid + 1, hi);
    }

    private void bubbleSort(){
        while(true){
            for(int i = 0; i < hue.length - 1; ++i){
                if(hue[i] > hue[i + 1]){
                    int k = hue[i + 1];
                    hue[i + 1] = hue[i];
                    delay(10);
                    hue[i] = -1;
                    delay(2);
                    hue[i] = k;
                    delay(2);
                }
            }
        }
    }

    public void setARRAY_SIZE(int ARRAY_SIZE) {
        this.ARRAY_SIZE = ARRAY_SIZE;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public int getRainbowType() {
        return rainbowType;
    }

    public void setRainbowType(int rainbowType) {
        this.rainbowType = rainbowType;
    }

    private class Display extends View{
        private Paint paint;

        public Display(Context context){
            super(context);
            paint = new Paint();
        }

        @Override
        public void onDraw(Canvas canvas){
            super.onDraw(canvas);
            double barWidth = (double)(getWidth())/hue.length;
            int h = getHeight();
            for(int i = 0; i < hue.length; ++i){
                int x1 = (int)(i * barWidth);
                int x2 = (int)((i + 1) * barWidth);
                int w = x2 - x1;
                if(hue[i] == -1){
                    paint.setColor(Color.BLACK);
                } else{
                    paint.setColor(palette[hue[i]]);
                }
                canvas.drawRect(x1, 0, (x1 + w), h, paint);
                //System.out.println(barWidth + " " + x1 + " " + w + " " + h);
            }
        }
    }

    private class Runner extends Thread{

        @Override
        public void run() {
            for (int i = hue.length - 1; i > 0; i--) {
                int r = (int) (Math.random() * (i + 1));
                int temp = hue[r];
                hue[r] = hue[i];
                hue[i] = temp;
            }
            delay(millis * 4);
            switch (sortType){
                case 1:
                    quickSort(0, hue.length - 1);
                    break;
                case 2:
                    bubbleSort();
                    break;
            }
            //quickSort(0, hue.length - 1);
            //bubbleSort();
        }
    }
}
