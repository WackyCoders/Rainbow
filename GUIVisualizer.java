package com.guisorting.app;

import android.content.*;
import android.graphics.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import java.util.*;

/**
 * Created by Walter on 5/31/2014.
 */
public class GUIVisualizer extends LinearLayout{
    private int ARRAY_SIZE = 100;
    private int[] hue;
    private int[] palette;
    private Runner runner;
    private Display display;
    private volatile boolean running = true;
    private int millis = 30;

    //Menu settings
    private int sortType = 0;
    private int rainbowType = 0;

    public GUIVisualizer(Context context){
        super(context);
        display = new Display(context);
        display.setBackgroundColor(Color.WHITE);
        display.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                                                              LayoutParams.FILL_PARENT));
        this.addView(display);
    }

    protected void init(){
        hue = new int[ARRAY_SIZE];
        palette = new int[ARRAY_SIZE];
        Random random = new Random();
        for(int i = 0; i < ARRAY_SIZE; ++i){
            palette[i] = Color.rgb((i + ARRAY_SIZE)/ARRAY_SIZE + i , (i + ARRAY_SIZE)/ARRAY_SIZE + i, (i + ARRAY_SIZE)/ARRAY_SIZE + i);
            hue[i] = i;
        }
    }

    protected void rainbowFill(int size){
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
        runner = new Runner();
        running = true;
        runner.start();
    }

    public void stop(){
        running = false;
        synchronized (runner){
            try {
                runner.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        runner = null;
        switch (rainbowType) {
            case 0 :init(); break;
            case 1 :rainbowFill(ARRAY_SIZE/6); break;
        }
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
        if(!running){
            Thread.currentThread().interrupt();
            return 0;
        }
        int pivot = hue[lo];
        hue[lo] = -1;
        delay(millis);
        while(true){
            if(!running){
                Thread.currentThread().interrupt();
                break;
            }
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
        if(!running){
            Thread.currentThread().interrupt();
            return;
        }
        int mid = quickSortStep(lo, hi);
        quickSort(lo, mid - 1);
        quickSort(mid + 1, hi);
    }

    private void bubbleSort(){
        while(true){
            if(!running){
                Thread.currentThread().interrupt();
                break;
            }
            for(int i = 0; i < hue.length - 1; ++i){
                if(!running){
                    Thread.currentThread().interrupt();
                    break;
                }
                if(hue[i] > hue[i + 1]){
                    int k = hue[i + 1];
                    hue[i + 1] = hue[i];
                    delay(millis/3);
                    hue[i] = -1;
                    delay(millis/15);
                    hue[i] = k;
                    delay(millis/15);
                }
            }
        }
    }

    private void siftDown(int i, int j){
        boolean done = false;
        int maxChild, temp;

        while((i * 2 + 1 < j) && (!done)){
            if(!running){
                Thread.currentThread().interrupt();
                break;
            }
            if(i * 2  + 1 == j - 1){
                maxChild = i * 2 + 1;
            } else if(hue[i * 2 + 1] > hue[i * 2 + 2]){
                maxChild = i * 2 + 1;
            } else {
                maxChild = i * 2 + 2;
            }

            if(hue[i] < hue[maxChild]){
                temp = hue[i];
                delay(millis);
                hue[i] = hue[maxChild];
                delay(millis);
                hue[maxChild] = temp;
                delay(millis);
                i = maxChild;
            } else {
                done = true;
            }
        }
    }

    private void heapSort(){
        int i, temp;
        for(i = hue.length/2 - 1; i >= 0; --i){
            if(!running){
                Thread.currentThread().interrupt();
                break;
            }
            siftDown(i, hue.length);
        }

        for(i = hue.length - 1; i >= 1; --i){
            if(!running){
                Thread.currentThread().interrupt();
                break;
            }
            temp = hue[0];
            delay(millis);
            hue[0] = hue[i];
            delay(millis);
            hue[i] = temp;
            delay(millis);
            siftDown(0, i);
        }
    }

    private void makeMerge(int l, int mid,int r) {
        int[] tmp = new int[hue.length];
        int i = l;
        int j, coun = 0;
        j = mid + 1;
        for(int step = 0; step < r - l + 1; step++){
            if(!running){
                Thread.currentThread().interrupt();
                break;
            }
            if((j > r) || ((i <= mid) && (hue[i] < hue[j]))) {
                coun += j - (mid + 1);
                tmp[step] = hue[i];
                i++;
            }
            else{
                tmp[step] = hue[j];
                j++;
            }
        }
        for(int step = 0; step<r - l + 1; ++step){
            if(!running){
                Thread.currentThread().interrupt();
                break;
            }
            delay(millis);
            hue[l + step] = tmp[step];
            delay(millis);
        }
    }

    private void mergeSort(int l,int r){
        if(l == r){
            return;
        }
        if(!running){
            Thread.currentThread().interrupt();
            return;
        }
        int mid = (l + r)/2;
        mergeSort(l, mid);
        mergeSort(mid + 1, r);
        makeMerge(l, mid, r);
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

    public int getMillis() {
        return millis;
    }

    public void setMillis(int millis) {
        this.millis = millis;
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

        public void stopForced(){
            running = false;
        }

        @Override
        public void run() {
            for (int i = hue.length - 1; i > 0; i--) {
                int r = (int) (Math.random() * (i + 1));
                int temp = hue[r];
                hue[r] = hue[i];
                hue[i] = temp;
            }
            delay(millis * 4);
            switch (sortType) {
                case 0:
                    quickSort(0, hue.length - 1);
                    break;
                case 1:
                    bubbleSort();
                    break;
                case 2:
                    heapSort();
                    break;
                case 3:
                    mergeSort(0, hue.length - 1);
                    break;
            }
        }
    }
}
