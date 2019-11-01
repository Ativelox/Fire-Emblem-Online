package de.ativelox.feo.client.view;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Display {

    private BufferStrategy mBufferStrategy;

    private final Frame mFrame;

    private final Canvas mCanvas;

    public static final int INTERNAL_RES_FACTOR = 6;

    public static final int WIDTH = 240 * INTERNAL_RES_FACTOR;

    public static final int HEIGHT = 160 * INTERNAL_RES_FACTOR;

    public Display(final String title, int width, int height) {
        Dimension size = new Dimension(width, height);

        mFrame = new Frame(title);
        mFrame.setSize(size);
        mFrame.setLocationRelativeTo(null);
        mFrame.setVisible(true);

        mCanvas = new Canvas();
        mCanvas.setPreferredSize(size);
        mCanvas.setFocusable(false);
        mCanvas.setBounds(0, 0, WIDTH, HEIGHT);

        mFrame.add(mCanvas);

        mCanvas.createBufferStrategy(3);
        mBufferStrategy = mCanvas.getBufferStrategy();

    }

    public void addMouseListener(final MouseListener listener) {
        mCanvas.addMouseListener(listener);
    }

    public void removeMouseListener(final MouseListener listener) {
        mCanvas.removeMouseListener(listener);
    }

    public int getCurrentWidth() {
        return mFrame.getWidth();
    }

    public int getCurrentHeight() {
        return mFrame.getHeight();
    }

    public void close() {
        mFrame.dispose();
    }

    public void addComponentListener(final ComponentListener listener) {
        mFrame.addComponentListener(listener);
    }

    public void addWindowListener(final WindowListener listener) {
        mFrame.addWindowListener(listener);
    }

    public void addKeyListener(final KeyListener listener) {
        mFrame.addKeyListener(listener);

    }

    public void removeKeyListener(final KeyListener listener) {
        mFrame.removeKeyListener(listener);

    }

    public void addMouseMotionListener(final MouseMotionListener listener) {
        mCanvas.addMouseMotionListener(listener);
    }

    public BufferStrategy getBufferStrategy() {
        return mBufferStrategy;
    }

}
