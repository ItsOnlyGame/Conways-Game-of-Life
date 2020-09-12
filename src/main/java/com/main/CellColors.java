package com.main;

import java.awt.*;

public class CellColors implements Runnable {

    private int state = 0;
    private int a = 255;
    private int r = 255;
    private int g = 0;
    private  int b = 0;

    @Override
    public void run() {
        while (Main.running) {
            if (state == 0){
                g += 3;
                if(g == 255)
                    state = 1;
            }
            if (state == 1){
                r -= 3;
                if(r == 0)
                    state = 2;
            }
            if (state == 2){
                b += 3;
                if(b == 255)
                    state = 3;
            }
            if (state == 3){
                g -= 3;
                if(g == 0)
                    state = 4;
            }
            if (state == 4){
                r += 3;
                if(r == 255)
                    state = 5;
            }
            if (state == 5){
                b -= 3;
                if(b == 0)
                    state = 0;
            }
            int hex = (a << 24) + (r << 16) + (g << 8) + (b);
            Settings.CellColor = Color.decode(String.format("#%06X", (0xFFFFFF & hex)));

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
