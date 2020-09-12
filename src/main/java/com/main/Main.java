package com.main;


import com.main.Thread.ThreadPool;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Main extends Canvas implements Runnable {

    public static boolean running = true;

    public int[][] cell;
    private int maxSizeX;
    private int maxSizeY;

    private Window window;

    private void init() {
        Input input = new Input(this);

        this.addKeyListener(input);
        this.addMouseListener(input);
        window = Window.getInstance(this);
        maxSizeX = window.getWidth() / Settings.CellSize;
        maxSizeY = window.getHeight() / Settings.CellSize;

        newInstance();
    }

    public void newInstance() {
        cell = new int[maxSizeX][maxSizeY];
        Random r = new Random();

        for (int x = 0; x < maxSizeX; x++) {
            for (int y = 0; y < maxSizeY; y++) {
                cell[x][y] = ((r.nextInt(50) + 1) >= 45) ? 1 : 0;
            }
        }
    }

    private void update() {
        if (!Settings.CellUpdating) return;
        int[][] currentCells = new int[maxSizeX][maxSizeY];
        for (int x = 0; x < maxSizeX; x++) {
            for (int y = 0; y < maxSizeY; y++) {
                int cellState = cell[x][y];
                int alive = aliveNeighbours(x, y);

                if (cellState == Constant.ALIVE) {
                    if (alive != 2 && alive != 3) {
                        currentCells[x][y] = Constant.DEAD;
                        continue;
                    }
                } else if (cellState == Constant.DEAD) {
                    if (alive == 3) {
                        currentCells[x][y] = Constant.ALIVE;
                        continue;
                    }
                }
                currentCells[x][y] = cell[x][y];
            }
        }
        cell = currentCells;
    }


    public int aliveNeighbours(int dx, int dy) {
        int alive = 0;
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (x == 0 && y == 0) continue;

                int tempX = dx+x;
                int tempY = dy+y;

                if (tempX > cell.length - 1 || tempX < 0) continue;
                if (tempY > cell[tempX].length - 1 || tempY < 0) continue;

                alive += cell[tempX][tempY];
            }
        }
        return alive;
    }


    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Settings.BackgroundColor);
        g2d.fillRect(0, 0, window.getWidth(), window.getHeight());

        if (Settings.ShowGrid) {
            g2d.setColor(Settings.GridColor);
            for (int x = 1; x < maxSizeX+1; x++) {
                g2d.drawLine(x * Settings.CellSize, 0, x * Settings.CellSize, maxSizeY * Settings.CellSize);
            }
            for (int y = 0; y < maxSizeY+1; y++) {
                g2d.drawLine(0, y * Settings.CellSize, maxSizeX * Settings.CellSize, y * Settings.CellSize);
            }
        }

        g2d.setColor(Settings.CellColor);
        for (int y = 0; y < maxSizeY; y++) {
            for (int x = 0; x < maxSizeX; x++) {
                if (cell[x][y] == Constant.ALIVE) {
                    g2d.fillRect(x * Settings.CellSize, y * Settings.CellSize, Settings.CellSize, Settings.CellSize);
                }
            }
        }

        g2d.setColor(Color.WHITE);
        g2d.drawString("Keyboard functions" ,7, 20);
        g2d.drawString("Clear: C", 7, 20 + (15));
        g2d.drawString("Randomize: R", 7, 20 + (15 * 2));
        g2d.drawString("Show grid: G", 7, 20 + (15 * 3));
        g2d.drawString("Start / Stop: Enter", 7, 20 + (15 * 4));
        g2d.drawString("Click with mouse to add / remove points", 7, 20 + (15 * 5));

        bs.show();
        g.dispose();
    }

    public static void main(String[] args) {
        ThreadPool thread = new ThreadPool(5);
        thread.runTask(new Main());
        thread.runTask(new CellColors());
    }

    @Override
    public void run() {
        running = true;
        this.init();
        this.requestFocus();

        while(running) {
            render();
            update();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stop();
    }

    public synchronized void stop() {
        if (!running) return;
        running = false;
    }

    public void clear() {
        cell = new int[maxSizeX][maxSizeY];
    }
}
