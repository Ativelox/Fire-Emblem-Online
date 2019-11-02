package de.ativelox.feo.client.model.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.ativelox.feo.client.model.property.EGraphicsOperation;
import de.ativelox.feo.client.model.util.DepthBufferComparator;
import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;
import de.ativelox.feo.util.Pair;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class DepthBufferedGraphics {

    private final List<Pair<EGraphicsOperation, Object[]>> mToRender;

    private final Comparator<Pair<EGraphicsOperation, Object[]>> mComparator;

    public DepthBufferedGraphics() {
        mToRender = new ArrayList<>();
        mComparator = new DepthBufferComparator();

    }

    public void fillRect(Color color, int x, int y, int width, int height, int depth) {
        mToRender.add(Pair.of(EGraphicsOperation.FILL_RECT, new Object[] { color, x, y, width, height, depth }));

    }

    public void fillRect(int x, int y, int width, int height, int depth) {
        this.fillRect(null, x, y, width, height, depth);

    }

    public void fillRect(int x, int y, int width, int height) {
        this.fillRect(x, y, width, height, 0);

    }

    public void fillRect(Color color, int x, int y, int width, int height) {
        this.fillRect(color, x, y, width, height, 0);

    }

    public void drawRect(Color color, int x, int y, int width, int height, int depth) {
        mToRender.add(Pair.of(EGraphicsOperation.DRAW_RECT, new Object[] { color, x, y, width, height, depth }));

    }

    public void drawRect(int x, int y, int width, int height, int depth) {
        this.drawRect(null, x, y, width, height, depth);

    }

    public void drawRect(Color color, int x, int y, int width, int height) {
        this.drawRect(color, x, y, width, height, 0);

    }

    public void drawRect(int x, int y, int width, int height) {
        this.drawRect(x, y, width, height, 0);

    }

    public void drawImage(Image image, int x, int y, int width, int height, int depth) {
        mToRender.add(Pair.of(EGraphicsOperation.DRAW_IMAGE, new Object[] { image, x, y, width, height, depth }));

    }

    public void drawImage(Image image, int x, int y, int width, int height) {
        this.drawImage(image, x, y, width, height, 0);

    }

    public void drawImage(Image image, int x, int y, int depth) {
        mToRender.add(Pair.of(EGraphicsOperation.DRAW_IMAGE_X_Y, new Object[] { image, x, y, depth }));

    }

    public void drawImage(Image image, int x, int y) {
        this.drawImage(image, x, y, 0);

    }

    public void drawString(String content, int x, int y, int depth) {
        this.drawString(null, null, content, x, y, depth);
    }

    public void drawString(String content, int x, int y) {
        this.drawString(content, x, y, 0);
    }

    public void drawString(Color color, String content, int x, int y, int depth) {
        this.drawString(null, color, content, x, y, depth);
    }

    public void drawString(Color color, String content, int x, int y) {
        this.drawString(color, content, x, y, 0);
    }

    public void drawString(Font font, String content, int x, int y, int depth) {
        this.drawString(font, null, content, x, y, depth);

    }

    public void drawString(Font font, String content, int x, int y) {
        this.drawString(font, content, x, y, 0);
    }

    public void drawString(Font font, Color color, String content, int x, int y, int depth) {
        mToRender.add(Pair.of(EGraphicsOperation.DRAW_STRING, new Object[] { font, color, content, x, y, depth }));
    }

    public void drawString(Font font, Color color, String content, int x, int y) {
        this.drawString(font, color, content, x, y, 0);
    }

    public void drawLine(Color color, int x1, int y1, int x2, int y2, int depth) {
        mToRender.add(Pair.of(EGraphicsOperation.DRAW_LINE, new Object[] { color, x1, y1, x2, y2, depth }));
    }

    public void drawLine(int x1, int y1, int x2, int y2, int depth) {
        this.drawLine(null, x1, y1, x2, y2, depth);
    }

    public void drawLine(Color color, int x1, int y1, int x2, int y2) {
        this.drawLine(color, x1, y1, x2, y1, 0);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        this.drawLine(null, x1, y1, x2, y2, 0);
    }

    public void draw(Graphics2D g, AffineTransform transform) {
        mToRender.sort(mComparator);

        g.setTransform(transform);

        for (final Pair<EGraphicsOperation, Object[]> pair : mToRender) {
            Object[] args = pair.getSecond();

            switch (pair.getFirst()) {
            case DRAW_IMAGE:
                g.drawImage((Image) args[0], (int) args[1], (int) args[2], (int) args[3], (int) args[4], null);
                break;

            case DRAW_RECT:
                if (args[0] != null) {
                    g.setColor((Color) args[0]);
                }
                g.drawRect((int) args[1], (int) args[2], (int) args[3], (int) args[4]);
                break;

            case FILL_RECT:
                if (args[0] != null) {
                    g.setColor((Color) args[0]);
                }
                g.fillRect((int) args[1], (int) args[2], (int) args[3], (int) args[4]);
                break;

            case DRAW_IMAGE_X_Y:
                g.drawImage((Image) args[0], (int) args[1], (int) args[2], null);
                break;

            case DRAW_STRING:
                if (args[0] != null) {
                    g.setFont((Font) args[0]);
                }
                if (args[1] != null) {
                    g.setColor((Color) args[1]);
                }
                g.drawString((String) args[2], (int) args[3], (int) args[4]);
                break;

            case DRAW_LINE:
                if (args[0] != null) {
                    g.setColor((Color) args[0]);
                }
                g.drawLine((int) args[1], (int) args[2], (int) args[3], (int) args[4]);
                break;

            default:
                Logger.get().log(ELogType.ERROR, "Unknown graphics operation: " + pair.getFirst());
                break;

            }
        }
        mToRender.clear();
    }
}
