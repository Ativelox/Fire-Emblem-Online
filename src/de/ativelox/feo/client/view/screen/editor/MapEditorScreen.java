package de.ativelox.feo.client.view.screen.editor;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import de.ativelox.feo.client.controller.MapEditorController;
import de.ativelox.feo.client.model.camera.ECameraApplication;
import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.tile.ITile;
import de.ativelox.feo.client.model.gfx.tile.editor.EditorTile;
import de.ativelox.feo.client.model.property.EClickType;
import de.ativelox.feo.client.model.property.IClickable;
import de.ativelox.feo.client.model.property.IHoverable;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.util.MouseButtonMapper;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.generic.ImageElement;
import de.ativelox.feo.client.view.element.specific.AInputField;
import de.ativelox.feo.client.view.element.specific.Button;
import de.ativelox.feo.client.view.element.specific.GridPanel;
import de.ativelox.feo.client.view.element.specific.IntegerField;
import de.ativelox.feo.client.view.element.specific.UpdatingHorizontallyAlignedPanel;
import de.ativelox.feo.client.view.screen.EScreen;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class MapEditorScreen implements IMapEditorScreen, IRequireResources {

    // panel left for tile selection, panel right for map preview buttons to
    // navigate.
    private MapEditorController mController;

    private Collection<EditorTile> mTileSet;

    private UpdatingHorizontallyAlignedPanel<EditorTile> mLeftContainer;

    private GridPanel<EditorTile> mRightContainer;

    private List<AInputField<Integer>> mInputFields;

    private Button mApplyButton;
    private Button mNextTileSet;
    private Button mLastTileSet;
    private Button mExportButton;
    private Button mImportButton;

    private List<Button> mButton;

    private static final String APPLY_BUTTON_TEXT = "Apply";
    private static final String NEXT_TILESET_TEXT = "Next";
    private static final String LAST_TILESET_TEXT = "Last";
    private static final String EXPORT_BUTTON_TEXT = "Export";
    private static final String IMPORT_BUTTON_TEXT = "Import";

    private static final int BACKGROUND_LAYER = -20;

    private EditorTile mBound;

    private String mTileSetName;

    private boolean mIsSnapped;

    private int mMouseX;

    private int mMouseY;

    private Image mFormattedText;

    private final ImageElement mBackground;

    private int mTextX;
    private int mTextY;
    private int mTextWidth;
    private int mTextHeight;

    public MapEditorScreen() {
        mTileSet = new ArrayList<>();
        mButton = new ArrayList<>();
        mLeftContainer = new UpdatingHorizontallyAlignedPanel<>(10, 10, 35, 80, Display.INTERNAL_RES_FACTOR / 2);
        mRightContainer = new GridPanel<>(50, 10, 45, 80, 0);

        mInputFields = new ArrayList<>();

        mInputFields.add(new IntegerField(70, 3, 2, 10, 3));
        mInputFields.add(new IntegerField(73, 3, 2, 10, 3));

        mApplyButton = new Button(APPLY_BUTTON_TEXT, 76, 3, 4, 2);
        mApplyButton.addClickListener(this);

        mNextTileSet = new Button(NEXT_TILESET_TEXT, 33, 3, 4, 2);
        mNextTileSet.addClickListener(this);

        mLastTileSet = new Button(LAST_TILESET_TEXT, 16, 3, 4, 2);
        mLastTileSet.addClickListener(this);

        mExportButton = new Button(EXPORT_BUTTON_TEXT, 85, 91, 5, 2);
        mExportButton.addClickListener(this);

        mImportButton = new Button(IMPORT_BUTTON_TEXT, 92, 91, 5, 2);
        mImportButton.addClickListener(this);

        mBackground = new ImageElement(0, 0, 100, 100, true, EResource.MENU_BACKGROUND);

        mButton.add(mApplyButton);
        mButton.add(mNextTileSet);
        mButton.add(mLastTileSet);
        mButton.add(mExportButton);
        mButton.add(mImportButton);

        mTileSetName = "";

    }

    @Override
    public void updateGrid(int width, int height) {
        mRightContainer.updateGrid(width, height);

    }

    @Override
    public void setTileSet(String tileSetName, Collection<EditorTile> tileSet) {
        mTileSetName = tileSetName;
        load();

        mTileSet.forEach(a -> a.removeHoverListener(this));
        mTileSet.forEach(a -> a.removeClickListener(this));

        mTileSet.clear();
        mLeftContainer.clear();

        mTileSet.addAll(tileSet);

        tileSet.forEach(a -> a.addHoverListener(this));
        tileSet.forEach(a -> a.addClickListener(this));

        mLeftContainer.addAll(tileSet);

    }

    @Override
    public void setController(MapEditorController controller) {
        mController = controller;
        if (controller == null) {
            return;
        }
        controller.onGridSizeChanged(27, 10);

    }

    @Override
    public void render(DepthBufferedGraphics g) {
        if (mController == null) {
            return;
        }
        mBackground.render(g);

        g.fillRect(new Color(190, 190, 190), 0, 0, Display.WIDTH, Display.HEIGHT, BACKGROUND_LAYER);

        mLeftContainer.render(g);
        mRightContainer.render(g);
        mInputFields.forEach(f -> f.render(g));
        mButton.forEach(b -> b.render(g));

        g.drawImage(mFormattedText, mTextX, mTextY, mTextWidth, mTextHeight);

        if (mBound != null) {
            mBound.render(g);
        }

    }

    @Override
    public void update(TimeSnapshot ts) {
        if (mController == null) {
            return;
        }
        mLeftContainer.update(ts);
        mRightContainer.update(ts);
        mInputFields.forEach(f -> f.update(ts));
        mButton.forEach(b -> b.update(ts));

        if (mBound != null) {
            mBound.update(ts);
            mBound.setX(mMouseX - mBound.getWidth() / 2);
            mBound.setY(mMouseY - mBound.getHeight() / 2);

            Optional<Rectangle> snapBounds = mRightContainer.get(mMouseX, mMouseY);
            if (snapBounds.isPresent()) {
                mBound.setX((int) snapBounds.get().getX());
                mBound.setY((int) snapBounds.get().getY());
                mIsSnapped = true;

            } else {
                mIsSnapped = false;

            }
        }
        mTextX = mLastTileSet.getX() + mLastTileSet.getWidth() + 10;
        mTextWidth = mNextTileSet.getX() - mTextX - 10;
        mTextY = mLastTileSet.getY();
        mTextHeight = mLastTileSet.getHeight();

    }

    @Override
    public EScreen getIdentifier() {
        return EScreen.MAP_EDITOR_SCREEN;

    }

    @Override
    public boolean renderLower() {
        return false;

    }

    @Override
    public boolean updateLower() {
        return false;
    }

    @Override
    public ECameraApplication cameraApplied() {
        return ECameraApplication.WINDOW_RESIZE_ONLY;

    }

    @Override
    public void onMouseMoved(int relX, int relY) {
        mMouseX = relX;
        mMouseY = relY;

        mTileSet.forEach(a -> a.onMouseMoved(relX, relY));

        mInputFields.forEach(f -> f.onMouseMoved(relX, relY));

        mButton.forEach(b -> b.onMouseMoved(relX, relY));

    }

    @Override
    public void onHoverStart(IHoverable hoverable) {
        mController.onBorderStartRequest(hoverable);

        if (hoverable instanceof ITile) {
            mController.onStatDisplayRequest((ITile) hoverable);
        }
    }

    @Override
    public void onHoverLoss(IHoverable hoverable) {
        mController.onBorderStopRequest(hoverable);

        if (hoverable instanceof ITile) {
            mController.onStatRemovalRequest((ITile) hoverable);
        }
    }

    @Override
    public void onHover(IHoverable hoverable) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {
        mInputFields.forEach(t -> t.keyTyped(e));

    }

    @Override
    public void keyPressed(KeyEvent e) {
        mInputFields.forEach(t -> t.keyPressed(e));

    }

    @Override
    public void keyReleased(KeyEvent e) {
        mInputFields.forEach(t -> t.keyReleased(e));

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mInputFields.forEach(t -> t.pressed(MouseButtonMapper.getType(e), e.getX(), e.getY()));
        mButton.forEach(b -> b.pressed(MouseButtonMapper.getType(e), e.getX(), e.getY()));
        mTileSet.forEach(a -> a.pressed(MouseButtonMapper.getType(e), e.getX(), e.getY()));

        if (MouseButtonMapper.getType(e) == EClickType.LEFT && mIsSnapped && mBound != null) {
            mRightContainer.getIndex(mMouseX, mMouseY).ifPresent(pair -> {
                mController.setData(mBound, pair.getFirst(), pair.getSecond());
                mController.unbind();
            });
        }

        if (MouseButtonMapper.getType(e) == EClickType.RIGHT && mBound != null) {
            mController.unbind();

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mInputFields.forEach(t -> t.released(MouseButtonMapper.getType(e), e.getX(), e.getY()));
        mButton.forEach(b -> b.released(MouseButtonMapper.getType(e), e.getX(), e.getY()));
        mTileSet.forEach(a -> a.released(MouseButtonMapper.getType(e), e.getX(), e.getY()));
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void newBind(EditorTile tile) {
        mBound = tile;
    }

    @Override
    public void onClick(IClickable clickable, EClickType type) {
        if (mInputFields.size() == 2) {

            if (clickable instanceof Button && ((Button) clickable).getText().equals(APPLY_BUTTON_TEXT)) {
                mController.onGridSizeChanged(mInputFields.get(0).getContent(), mInputFields.get(1).getContent());
            }
        }
        if (clickable instanceof Button) {
            if (((Button) clickable).getText().equals(NEXT_TILESET_TEXT)) {
                mController.nextTileSet();

            } else if (((Button) clickable).getText().equals(LAST_TILESET_TEXT)) {
                mController.previousTileSet();

            } else if (((Button) clickable).getText().equals(EXPORT_BUTTON_TEXT)) {
                System.out.println("now");
                mController.exportMap();

            } else if (((Button) clickable).getText().equals(IMPORT_BUTTON_TEXT)) {
                mController.importMap();
            }
        }

        if (clickable instanceof EditorTile && type == EClickType.LEFT) {
            mController.bind((EditorTile) clickable);
        }

    }

    @Override
    public int getSnapTileSize() {
        return mRightContainer.getElementSize();
    }

    @Override
    public Rectangle getSnapBounds() {
        return mRightContainer.getBounds();
    }

    @Override
    public void updateGridValue(EditorTile data, int colIndex, int rowIndex) {
        mRightContainer.replace(data, colIndex, rowIndex);
    }

    @Override
    public void clearMap() {
        mRightContainer.clear();
    }

    @Override
    public void load() {
        mFormattedText = Assets.getFor(EResource.REGULAR_FONT, mTileSetName);

    }
}
