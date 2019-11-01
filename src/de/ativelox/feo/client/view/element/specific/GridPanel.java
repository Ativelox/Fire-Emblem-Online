package de.ativelox.feo.client.view.element.specific;

import java.awt.Rectangle;
import java.util.Optional;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.util.Pair;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class GridPanel<T extends IRenderable & ISpatial & IUpdateable> extends APanel<Container<T>>
        implements IUpdateable {

    private int mRows;
    private int mColumns;

    private Container<T>[][] mMapping;

    private int mSpacing;

    /**
     * @param percentX
     * @param percentY
     * @param percentWidth
     * @param percentHeight
     */
    @SuppressWarnings("unchecked")
    public GridPanel(int percentX, int percentY, int percentWidth, int percentHeight, int spacing) {
        super(percentX, percentY, percentWidth, percentHeight);

        mMapping = new Container[0][0];
        mSpacing = spacing;

        mRows = 1;
        mColumns = 1;
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        for (int i = 0; i < mMapping.length; i++) {
            for (int j = 0; j < mMapping[i].length; j++) {
                mMapping[i][j].render(g);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void align() {
        mMapping = new Container[mColumns][mRows];

        float sampleWidth = Math.min(getWidth() / mColumns, getHeight() / mRows);

        if (sampleWidth <= 1) {
            return;
        }

        int column = 0;
        int row = 0;

        for (int i = 0; i < mColumns * mRows; i++) {

            if (column > 0 && column % mColumns == 0) {
                column = 0;
                row++;
            }
            final Container<T> cont = new Container<T>((int) (getX() + (column * (sampleWidth + mSpacing))),
                    (int) (getY() + (row * (sampleWidth + mSpacing))), (int) sampleWidth, (int) sampleWidth);

            mMapping[column][row] = cont;

            column++;
        }

    }

    public void updateGrid(int width, int height) {
        mColumns = width;
        mRows = height;
        this.align();

    }

    public int getElementSize() {
        if (mMapping.length <= 0) {
            return 0;
        }
        return mMapping[0][0].getWidth();
    }

    public Optional<Rectangle> get(int x, int y) {
        return getContainer(x, y).flatMap(opt -> Optional.of(opt.getBounds()));

    }

    public Optional<Pair<Integer, Integer>> getIndex(int x, int y) {
        if (!this.contains(x, y) || mMapping.length <= 0) {
            return Optional.empty();
        }

        int size = mMapping[0][0].getWidth();

        int xRelToThis = x - this.getX();
        int yRelToThis = y - this.getY();

        if (xRelToThis > size * mColumns || yRelToThis > size * mRows) {
            return Optional.empty();
        }

        int columnIndex = 0;
        int rowIndex = 0;

        for (int i = 1; i < mColumns; i++) {
            if (i * size > xRelToThis) {
                break;
            }
            columnIndex = i;

        }
        for (int i = 1; i < mRows; i++) {
            if (i * size > yRelToThis) {
                break;
            }
            rowIndex = i;

        }
        return Optional.of(Pair.of(columnIndex, rowIndex));

    }

    private Optional<Container<T>> getContainer(int x, int y) {
        return getIndex(x, y).flatMap(pair -> Optional.of(mMapping[pair.getFirst()][pair.getSecond()]));
    }

    public void replace(T data, int colIndex, int rowIndex) {
        mMapping[colIndex][rowIndex].set(data);
    }

    @Override
    public void update(TimeSnapshot ts) {
        for (int i = 0; i < mMapping.length; i++) {
            for (int j = 0; j < mMapping[i].length; j++) {
                mMapping[i][j].update(ts);
            }
        }

    }

    @Override
    public void clear() {
        for (int i = 0; i < mMapping.length; i++) {
            for (int j = 0; j < mMapping[i].length; j++) {
                mMapping[i][j].set(null);
            }
        }
    }
}
