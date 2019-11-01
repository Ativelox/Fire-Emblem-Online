package de.ativelox.feo.client.controller;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import de.ativelox.feo.client.model.property.IPriorityUpdateable;
import de.ativelox.feo.client.model.property.callback.IResizeListener;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.model.util.UpdatePriority;
import de.ativelox.feo.client.view.Display;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class DisplayController implements WindowListener, ComponentListener, IPriorityUpdateable {

    private final List<IResizeListener> mResizeListener;

    private final int mOriginalWidth;
    private final int mOriginalHeight;

    private double mXFactor;
    private double mYFactor;

    private boolean mDidResize;

    private final MainController mMainController;

    public DisplayController(final Display display, final MainController mainController) {
        mResizeListener = new ArrayList<>();

        mMainController = mainController;

        mOriginalHeight = Display.HEIGHT;
        mOriginalWidth = Display.WIDTH;

        mXFactor = 1;
        mYFactor = 1;

        mDidResize = false;

        display.addWindowListener(this);
        display.addComponentListener(this);

    }

    public void register(IResizeListener listener) {
        mResizeListener.add(listener);

    }

    @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowClosing(WindowEvent e) {
        mMainController.stop();

    }

    @Override
    public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void componentResized(ComponentEvent e) {
        mDidResize = true;

        Dimension newSize = e.getComponent().getSize();
        mXFactor = newSize.getWidth() / mOriginalWidth;
        mYFactor = newSize.getHeight() / mOriginalHeight;

    }

    @Override
    public void componentMoved(ComponentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void componentShown(ComponentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void componentHidden(ComponentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(TimeSnapshot ts) {
        if (!mDidResize) {
            return;
        }

        for (final IResizeListener listener : mResizeListener) {
            listener.onResize(mXFactor, mYFactor);
        }

        mDidResize = false;
    }

    @Override
    public int getPriority() {
        return UpdatePriority.WINDOW_CONTROLLER;

    }
}
