package de.ativelox.feo.client.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.ativelox.feo.client.controller.input.EAction;
import de.ativelox.feo.client.controller.input.InputManager;
import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.tile.ETileSet;
import de.ativelox.feo.client.model.gfx.tile.ETileType;
import de.ativelox.feo.client.model.gfx.tile.ITile;
import de.ativelox.feo.client.model.gfx.tile.editor.EditorTile;
import de.ativelox.feo.client.model.gfx.tile.editor.EditorTileSet;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.property.callback.IActionListener;
import de.ativelox.feo.client.model.property.callback.IRelativeMouseMoveListener;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.screen.EScreen;
import de.ativelox.feo.client.view.screen.IScreenManager;
import de.ativelox.feo.client.view.screen.editor.MapEditorScreen;
import de.ativelox.feo.client.view.screen.editor.MapEditorUIScreen;
import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class MapEditorController implements IRelativeMouseMoveListener, IActionListener, KeyListener, MouseListener {

    private int mThemeSetIndex;

    private int mTileSetIndex;

    private ETileType[] mTileTypes;

    private final List<EditorTileSet> mTileSets;

    private EditorTile mCurrentlyBound;

    private final MapEditorScreen mScreen;

    private final MapEditorUIScreen mUiScreen;

    private ITile[][] mGrid;

    private static final int BOUND_LAYER = 20;

    private final IScreenManager mManager;

    private final InputManager mInputManager;

    private final Display mDisplay;

    public MapEditorController(IScreenManager manager, InputManager im, Display d, MapEditorScreen screen,
            MapEditorUIScreen uiScreen) {
        mTileTypes = ETileType.values();
        mTileSetIndex = 0;
        mInputManager = im;
        mDisplay = d;

        mTileSets = new ArrayList<>();
        mScreen = screen;
        mUiScreen = uiScreen;

        mManager = manager;

        screen.setController(this);
        uiScreen.setController(this);

        d.addKeyListener(this);
        d.addMouseListener(this);
        im.register((IRelativeMouseMoveListener) this);
        im.register((IActionListener) this);

        for (final ETileSet set : ETileSet.values()) {
            mTileSets.add(new EditorTileSet(set));
        }
        screen.setTileSet(mTileTypes[mTileSetIndex].toString(),
                mTileSets.get(mThemeSetIndex).get(mTileTypes[mTileSetIndex]));
    }

    @Override
    public void onMouseMoved(int relX, int relY) {
        mScreen.onMouseMoved(relX, relY);

    }

    public void onBorderStartRequest(ISpatial spatial) {
        mUiScreen.startRenderingBorder(spatial);
    }

    public void onBorderStopRequest(ISpatial spatial) {
        mUiScreen.stopRenderingBorder(spatial);
    }

    public void onStatDisplayRequest(final ITile tile) {
        mUiScreen.startDisplayingStats(tile);

    }

    public void onStatRemovalRequest(final ITile tile) {
        mUiScreen.stopDisplayingStats(tile);
    }

    public void onGridSizeChanged(final int width, final int height) {
        mGrid = new ITile[width][height];
        mScreen.updateGrid(width, height);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        mScreen.keyTyped(e);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        mScreen.keyPressed(e);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        mScreen.keyReleased(e);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mScreen.mouseClicked(e);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mScreen.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mScreen.mouseReleased(e);

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mScreen.mouseEntered(e);

    }

    @Override
    public void mouseExited(MouseEvent e) {
        mScreen.mouseExited(e);

    }

    public void bind(EditorTile toBind) {
        if (mCurrentlyBound != null) {
            Logger.get().log(ELogType.INFO, "Already something bound.");
            return;
        }
        int size = mScreen.getSnapTileSize();
        EditorTile copy = (EditorTile) toBind.copy();

        copy.setLayer(BOUND_LAYER);
        copy.setWidth(size);
        copy.setHeight(size);
        mScreen.newBind(copy);

        mCurrentlyBound = copy;

    }

    public void nextTileSet() {
        if (mTileSetIndex >= mTileTypes.length - 1) {
            return;
        }

        mTileSetIndex++;
        while (mTileSetIndex < mTileTypes.length) {
            List<EditorTile> tiles = mTileSets.get(mThemeSetIndex).get(mTileTypes[mTileSetIndex]);

            if (tiles != null) {
                mScreen.setTileSet(mTileTypes[mTileSetIndex].toString(), tiles);
                break;
            }
            mTileSetIndex++;
        }

    }

    public void previousTileSet() {
        if (mTileSetIndex <= 0) {
            return;
        }

        mTileSetIndex--;
        while (mTileSetIndex >= 0) {
            List<EditorTile> tiles = mTileSets.get(mThemeSetIndex).get(mTileTypes[mTileSetIndex]);

            if (tiles != null) {
                mScreen.setTileSet(mTileTypes[mTileSetIndex].toString(), tiles);
                break;
            }
            mTileSetIndex--;
        }
    }

    public void unbind() {
        if (mCurrentlyBound == null) {
            return;
        }
        mCurrentlyBound = null;
        mScreen.newBind(null);
    }

    public void setData(EditorTile data, int colIndex, int rowIndex) {
        mGrid[colIndex][rowIndex] = data;

        mScreen.updateGridValue(data, colIndex, rowIndex);

    }

    public void exportMap() {
        Assets.saveMap(mGrid, mTileSets.get(mThemeSetIndex).getTileSet());
    }

    public void importMap() {
        EditorTile[][] map = Assets.loadMap();

        mScreen.clearMap();

        this.onGridSizeChanged(map[0].length, map.length);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == null) {
                    continue;
                }
                this.setData(map[i][j], j, i);

            }
        }
    }

    @Override
    public void onPress(Set<EAction> action) {
        // not needed

    }

    @Override
    public void onRelease(Set<EAction> action) {
        if (action.contains(EAction.CANCEL)) {
            mInputManager.remove((IRelativeMouseMoveListener) this);
            mInputManager.remove((IActionListener) this);
            mDisplay.removeKeyListener(this);
            mDisplay.removeMouseListener(this);

            mManager.switchTo(EScreen.MAIN_MENU_SCREEN);

        }
    }
}
