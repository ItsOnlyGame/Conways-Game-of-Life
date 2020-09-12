package com.main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Window {

    public static JFrame f = new JFrame();

    private final int width, height;

    private Window(int width, int height, String title, Main main) {
        this.width = width;
        this.height = height;

        f.setMinimumSize(new Dimension(width, height));
        f.setPreferredSize(new Dimension(width, height));
        f.setMaximumSize(new Dimension(width, height));

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setTitle(title);
        f.setResizable(false);
        f.setLocationRelativeTo(null);

        f.add(main);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private static Window instance;
    public static Window getInstance(Main main) {
        if (Window.instance == null) {
            Window.instance = new Window(1280, 720, "Conway's Game of Life", main);
        }
        return Window.instance;
    }

}