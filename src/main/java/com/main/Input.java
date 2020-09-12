package com.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Input implements KeyListener, MouseListener {

    private final Main main;
    public Input(Main main) {
        this.main = main;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            Settings.WindowWrap = !Settings.WindowWrap;
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            Settings.ShowGrid = !Settings.ShowGrid;
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            main.newInstance();
        }

        if (e.getKeyCode() == KeyEvent.VK_C) {
            main.clear();
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Settings.CellUpdating = !Settings.CellUpdating;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = Math.round(1.0f * e.getX() / Settings.CellSize);
        int y =  Math.round(1.0f * e.getY() / Settings.CellSize);
        main.cell[x][y] = (main.cell[x][y] == Constant.ALIVE ? 0 : 1);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
