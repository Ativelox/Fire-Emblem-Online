package de.ativelox.feo.client.model.gfx;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.ativelox.feo.client.model.gfx.animation.DefaultLoopingAnimation;
import de.ativelox.feo.client.model.gfx.animation.EAnimationDirection;
import de.ativelox.feo.client.model.gfx.animation.OffsetLoopingAnimation;
import de.ativelox.feo.client.model.gfx.animation.UnitMovementAnimation;
import de.ativelox.feo.client.model.gfx.tile.ATileSet;
import de.ativelox.feo.client.model.gfx.tile.ETileSet;
import de.ativelox.feo.client.model.gfx.tile.ETileType;
import de.ativelox.feo.client.model.gfx.tile.ITile;
import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.gfx.tile.TileSet;
import de.ativelox.feo.client.model.gfx.tile.editor.EditorTile;
import de.ativelox.feo.client.model.gfx.tile.editor.EditorTileSet;
import de.ativelox.feo.client.model.property.EBattleAnimType;
import de.ativelox.feo.client.model.property.EClass;
import de.ativelox.feo.client.model.property.EGender;
import de.ativelox.feo.client.model.property.EIndicatorDirection;
import de.ativelox.feo.client.model.property.EPlatformDistance;
import de.ativelox.feo.client.model.property.ESide;
import de.ativelox.feo.client.model.property.IRequireResource;
import de.ativelox.feo.client.model.util.SplitBuilder;
import de.ativelox.feo.client.model.util.SpriteSheet;
import de.ativelox.feo.client.model.util.TightWidthFit;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Assets {

    private static boolean isInit = false;

    private static final Path MAP_PATH = Paths.get("res", "fe6", "map");
    private static final Path HOVER_MAP_PATH = MAP_PATH.resolve("hover");
    private static final Path MOVE_MAP_PATH = MAP_PATH.resolve("move");
    private static final Path TILES_SET_PATH = MAP_PATH.resolve("tileset");

    private static final Path SYSTEM_PATH = Paths.get("res", "fe6", "system");
    private static final Path FONT_PATH = Paths.get("res", "font");

    private static final Path PALETTE_PATH = Paths.get("res", "palette");

    private static final Path PORTRAIT_PATH = Paths.get("res", "fe6", "portrait");

    private static final Path BATTLE_PATH = Paths.get("res", "fe6", "battle");

    private static final Path DODGE_PATH = Paths.get("res", "fe6", "dodge");

    private static final Path MELEE_ATTACK_PATH = Paths.get("res", "fe6", "melee_attack");
    private static final Path MELEE_CRIT_PATH = Paths.get("res", "fe6", "melee_crit");

    private static final Path RANGED_ATTACK_PATH = Paths.get("res", "fe6", "ranged_attack");
    private static final Path RANGED_CRIT_PATH = Paths.get("res", "fe6", "ranged_crit");

    private static final String FIELDS_TILE_SET_NAME = "fields.png";
    private static final String DIALOGUE_FONT_NAME = "fe-dialogue.ttf";
    private static final String REGULAR_FONT_NAME = "regular.png";
    private static final String SELECTOR_LEFT_NAME = "selector_left.png";
    private static final String SELECTOR_RIGHT_NAME = "selector_right.png";
    private static final String MAP_SELECTOR_NAME = "map_selection.png";
    private static final String MAP_INDICATOR_NAME = "arrows.png";
    private static final String ACTION_WINDOW_TOP_NAME = "action_window_top.png";
    private static final String ACTION_WINDOW_BOTTOM_NAME = "action_window_bot.png";
    private static final String ACTION_WINDOW_MIDDLE_NAME = "action_window_middle.png";
    private static final String ACTION_SELECTOR_NAME = "action_selector.png";
    private static final String BURST_WINDOW_NAME = "burst_window.png";
    private static final String TILE_STATUS_NAME = "tile_status.png";
    private static final String TARGET_SELECTOR_NAME = "target_selection.png";
    private static final String BATTLE_PREVIEW_NAME = "battle_preview.png";
    private static final String BATTLE_BACKGROUND_NAME = "battle_screen.png";

    private static Font DIALOGUE_FONT;

    private static Map<String, Image> REGULAR_FONT_CACHE;

    private static Map<String, Integer> INDEX_MAPPING;

    private Assets() {

    }

    public static void init() {
        if (isInit) {
            return;
        }
        REGULAR_FONT_CACHE = new HashMap<>();
        INDEX_MAPPING = new HashMap<>();

        INDEX_MAPPING.put("A", 0);
        INDEX_MAPPING.put("B", 1);
        INDEX_MAPPING.put("C", 2);
        INDEX_MAPPING.put("D", 3);
        INDEX_MAPPING.put("E", 4);
        INDEX_MAPPING.put("F", 5);
        INDEX_MAPPING.put("G", 6);
        INDEX_MAPPING.put("H", 7);
        INDEX_MAPPING.put("I", 8);
        INDEX_MAPPING.put("J", 9);
        INDEX_MAPPING.put("K", 10);
        INDEX_MAPPING.put("L", 11);
        INDEX_MAPPING.put("M", 12);
        INDEX_MAPPING.put("N", 13);
        INDEX_MAPPING.put("O", 14);

        INDEX_MAPPING.put("P", 15);
        INDEX_MAPPING.put("Q", 16);
        INDEX_MAPPING.put("R", 17);
        INDEX_MAPPING.put("S", 18);
        INDEX_MAPPING.put("T", 19);
        INDEX_MAPPING.put("U", 20);
        INDEX_MAPPING.put("V", 21);
        INDEX_MAPPING.put("W", 22);
        INDEX_MAPPING.put("X", 23);
        INDEX_MAPPING.put("Y", 24);
        INDEX_MAPPING.put("Z", 25);
        INDEX_MAPPING.put("!", 26);
        INDEX_MAPPING.put("?", 27);
        INDEX_MAPPING.put(",", 28);
        INDEX_MAPPING.put(".", 29);

        INDEX_MAPPING.put("a", 30);
        INDEX_MAPPING.put("b", 31);
        INDEX_MAPPING.put("c", 32);
        INDEX_MAPPING.put("d", 33);
        INDEX_MAPPING.put("e", 34);
        INDEX_MAPPING.put("f", 35);
        INDEX_MAPPING.put("g", 36);
        INDEX_MAPPING.put("h", 37);
        INDEX_MAPPING.put("i", 38);
        INDEX_MAPPING.put("j", 39);
        INDEX_MAPPING.put("k", 40);
        INDEX_MAPPING.put("l", 41);
        INDEX_MAPPING.put("m", 42);
        INDEX_MAPPING.put("n", 43);
        INDEX_MAPPING.put("o", 44);

        INDEX_MAPPING.put("p", 45);
        INDEX_MAPPING.put("q", 46);
        INDEX_MAPPING.put("r", 47);
        INDEX_MAPPING.put("s", 48);
        INDEX_MAPPING.put("t", 49);
        INDEX_MAPPING.put("u", 50);
        INDEX_MAPPING.put("v", 51);
        INDEX_MAPPING.put("w", 52);
        INDEX_MAPPING.put("x", 53);
        INDEX_MAPPING.put("y", 54);
        INDEX_MAPPING.put("z", 55);
        INDEX_MAPPING.put(":", 56);
        INDEX_MAPPING.put("/", 57);
        INDEX_MAPPING.put("&", 58);
        INDEX_MAPPING.put("-", 59);

        INDEX_MAPPING.put("1", 60);
        INDEX_MAPPING.put("2", 61);
        INDEX_MAPPING.put("3", 62);
        INDEX_MAPPING.put("4", 63);
        INDEX_MAPPING.put("5", 64);
        INDEX_MAPPING.put("6", 65);
        INDEX_MAPPING.put("7", 66);
        INDEX_MAPPING.put("8", 67);
        INDEX_MAPPING.put("9", 68);
        INDEX_MAPPING.put("0", 69);
        INDEX_MAPPING.put(" ", 70);

        try {
            DIALOGUE_FONT = Font.createFont(Font.TRUETYPE_FONT,
                    Files.newInputStream(FONT_PATH.resolve(DIALOGUE_FONT_NAME)));

        } catch (FontFormatException | IOException e) {
            Logger.get().logError(e);
        }

//        Optional<BufferedImage> swordmasterFMeleeRegular = SpriteSheet
//                .load(Paths.get("res", "fe6", "melee_attack", "swordmaster_f.png"));
//
//        SWORDMASTER_F_MELEE_ATTACK = new DefaultNonLoopingAnimation(
//                SpriteSheet.split(swordmasterFMeleeRegular.get(), 240, 160, 39, 0, 7, 7, 7, 7, 7, 4),
//                EAnimationDirection.FORWARD, 3000);
//
//        Optional<BufferedImage> swordmasterFMeleeCrit = SpriteSheet
//                .load(Paths.get("res", "fe6", "melee_crit", "swordmaster_f.png"));
//
//        SWORDMASTER_F_MELEE_CRIT = new DefaultNonLoopingAnimation(
//                SpriteSheet.split(swordmasterFMeleeCrit.get(), 240, 160, 82, 0, 10, 10, 10, 10, 10, 10, 10, 10, 2),
//                EAnimationDirection.FORWARD, 6000);
//
//        Optional<BufferedImage> swordmasterFRangedRegular = SpriteSheet
//                .load(Paths.get("res", "fe6", "ranged_attack", "swordmaster_f.png"));
//
//        SWORDMASTER_F_RANGED_ATTACK = new DefaultNonLoopingAnimation(
//                SpriteSheet.split(swordmasterFRangedRegular.get(), 240, 160, 5, 0, 3, 2), EAnimationDirection.FORWARD,
//                1000);
//
//        Optional<BufferedImage> swordmasterFRangedCrit = SpriteSheet
//                .load(Paths.get("res", "fe6", "ranged_crit", "swordmaster_f.png"));
//
//        SWORDMASTER_F_RANGED_CRIT = new DefaultNonLoopingAnimation(
//                SpriteSheet.split(swordmasterFRangedCrit.get(), 240, 160, 12, 0, 4, 4, 4), EAnimationDirection.FORWARD,
//                2000);
//
//        Optional<BufferedImage> swordmasterFDodge = SpriteSheet
//                .load(Paths.get("res", "fe6", "dodge", "swordmaster_f.png"));
//
//        SWORDMASTER_F_DODGE = new DefaultNonLoopingAnimation(
//                SpriteSheet.split(swordmasterFDodge.get(), 240, 160, 3, 0, 2, 1), EAnimationDirection.FORWARD, 700);
//
//        Optional<BufferedImage> tileset = SpriteSheet.load(Paths.get("res", "fe6", "map", "tileset", "fields.png"));
//
//        TILES = SpriteSheet.split(tileset.get(), 16, 16, 1024, 0);

        isInit = true;
    }

    public static <T> T getFor(IRequireResource<T> requester, String... additionalInfo) {
        return getFor(requester.getResourceTypes(), additionalInfo);

    }

    @SuppressWarnings("unchecked")
    public static <T> T getFor(EResource resource, String... additionalInfo) {
        T result = null;

        // TODO: in general better handling of passing possibly empty optionals. maybee
        // pass placeholder textures instead if empty.

        switch (resource) {
        case TILESET_FIELDS:
            Optional<BufferedImage> opt = SpriteSheet.load(TILES_SET_PATH.resolve(FIELDS_TILE_SET_NAME));
            result = (T) SpriteSheet.split(opt.get(), 16, 16, 1024, 0);
            break;

        case MAP:
            try {
                List<String> lines = Files.readAllLines(MAP_PATH.resolve(additionalInfo[0]));
                String[] header = lines.remove(0).split("\t");
                int columns = Integer.parseInt(header[0]);
                int rows = Integer.parseInt(header[1]);
                ETileSet set = ETileSet.valueOf(header[2]);
                TileSet tileSet = new TileSet(set);
                Tile[][] map = new Tile[rows][columns];
                Assets.loadMap(tileSet, lines, map);
                result = (T) map;

            } catch (IOException e) {
                Logger.get().logError(e);

            }
            break;

        case MENU_BACKGROUND:
            result = (T) SpriteSheet.load(SYSTEM_PATH.resolve("background.png")).get();
            break;
        case MENU_BUTTON:
            result = (T) SpriteSheet.load(SYSTEM_PATH.resolve("selection.png")).get();
            break;
        case DIALOGUE_FONT:
            result = (T) DIALOGUE_FONT;
            break;

        case REGULAR_FONT:
            if (REGULAR_FONT_CACHE.containsKey(additionalInfo[0])) {
                result = (T) REGULAR_FONT_CACHE.get(additionalInfo[0]);
                break;
            }

            BufferedImage sheet = SpriteSheet.load(FONT_PATH.resolve(REGULAR_FONT_NAME)).get();
            BufferedImage[] loaded = new SplitBuilder().setTileDimensions(8, 13).setAmount(71)
                    .setDirectProcessing(new TightWidthFit(11063456)).setDimensions(15, 15, 15, 15, 11).apply(sheet);
            List<Image> relevant = new ArrayList<>();

            for (final char c : additionalInfo[0].toCharArray()) {
                String asString = Character.toString(c);
                if (INDEX_MAPPING.containsKey(asString)) {
                    relevant.add(loaded[INDEX_MAPPING.get(asString)]);

                }

            }

            result = (T) SpriteSheet.stitchHorizontally(relevant);
            REGULAR_FONT_CACHE.put(additionalInfo[0], (Image) result);
            break;

        case SYSTEM_SELECTOR_LEFT:
            result = (T) SpriteSheet.load(SYSTEM_PATH.resolve(SELECTOR_LEFT_NAME)).get();
            break;

        case SYSTEM_SELECTOR_RIGHT:
            result = (T) SpriteSheet.load(SYSTEM_PATH.resolve(SELECTOR_RIGHT_NAME)).get();
            break;

        case MAP_HOVER:
            BufferedImage hoverSheet = SpriteSheet.load(HOVER_MAP_PATH.resolve(additionalInfo[0])).get();
            BufferedImage[] animationSequence = SpriteSheet.split(hoverSheet, 16, 16, 3, 0, 1, 1, 1);
            result = (T) new DefaultLoopingAnimation(animationSequence, EAnimationDirection.FORWARD_BACKWARD, 1000,
                    16 * Display.INTERNAL_RES_FACTOR, 16 * Display.INTERNAL_RES_FACTOR);
            break;

        case MAP_MOVE_RIGHT:
            BufferedImage moveRightSheet = SpriteSheet.load(MOVE_MAP_PATH.resolve(additionalInfo[0])).get();
            BufferedImage[] rightSequence = SpriteSheet.split(moveRightSheet, 32, 32, 4, 0, 1, 1, 1, 1);
            result = (T) new UnitMovementAnimation(rightSequence, EAnimationDirection.FORWARD, 1000,
                    32 * Display.INTERNAL_RES_FACTOR, 32 * Display.INTERNAL_RES_FACTOR);
            break;

        case MAP_MOVE_LEFT:
            BufferedImage moveLeftSheet = SpriteSheet.load(MOVE_MAP_PATH.resolve(additionalInfo[0])).get();
            BufferedImage[] sequenceLeft = SpriteSheet.split(moveLeftSheet, 32, 32, 4, 0, 0, 0, 0, 0, 1, 1, 1, 1);
            result = (T) new UnitMovementAnimation(sequenceLeft, EAnimationDirection.FORWARD, 1000,
                    32 * Display.INTERNAL_RES_FACTOR, 32 * Display.INTERNAL_RES_FACTOR);
            break;

        case MAP_MOVE_DOWN:
            BufferedImage moveDownSheet = SpriteSheet.load(MOVE_MAP_PATH.resolve(additionalInfo[0])).get();
            BufferedImage[] downSequence = SpriteSheet.split(moveDownSheet, 32, 32, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1,
                    1, 1);
            result = (T) new UnitMovementAnimation(downSequence, EAnimationDirection.FORWARD, 1000,
                    32 * Display.INTERNAL_RES_FACTOR, 32 * Display.INTERNAL_RES_FACTOR);
            break;

        case MAP_MOVE_UP:
            BufferedImage moveUpShet = SpriteSheet.load(MOVE_MAP_PATH.resolve(additionalInfo[0])).get();
            BufferedImage[] upSequence = SpriteSheet.split(moveUpShet, 32, 32, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    1, 1, 1, 1);
            result = (T) new UnitMovementAnimation(upSequence, EAnimationDirection.FORWARD, 1000,
                    32 * Display.INTERNAL_RES_FACTOR, 32 * Display.INTERNAL_RES_FACTOR);
            break;

        case MAP_SELECTION:
            BufferedImage selectionSheet = SpriteSheet.load(MOVE_MAP_PATH.resolve(additionalInfo[0])).get();
            BufferedImage[] selectionSequence = SpriteSheet.split(selectionSheet, 32, 32, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1);
            result = (T) new UnitMovementAnimation(selectionSequence, EAnimationDirection.FORWARD_BACKWARD, 700,
                    32 * Display.INTERNAL_RES_FACTOR, 32 * Display.INTERNAL_RES_FACTOR);
            break;

        case MAP_SELECTOR:
            BufferedImage mapSelectionSheet = SpriteSheet.load(SYSTEM_PATH.resolve(MAP_SELECTOR_NAME)).get();
            BufferedImage[] mapSelectionSequence = SpriteSheet.split(mapSelectionSheet, 20, 20, 5, 0, 5);
            result = (T) new OffsetLoopingAnimation(mapSelectionSequence, EAnimationDirection.FORWARD_BACKWARD, 500,
                    20 * Display.INTERNAL_RES_FACTOR, 20 * Display.INTERNAL_RES_FACTOR,
                    -2 * Display.INTERNAL_RES_FACTOR, -2 * Display.INTERNAL_RES_FACTOR);
            break;

        case TARGET_SELECTOR:
            BufferedImage targetSelectionSheet = SpriteSheet.load(SYSTEM_PATH.resolve(TARGET_SELECTOR_NAME)).get();
            BufferedImage[] targetSelectionSequence = SpriteSheet.split(targetSelectionSheet, 20, 20, 5, 0, 5);
            result = (T) new OffsetLoopingAnimation(targetSelectionSequence, EAnimationDirection.FORWARD_BACKWARD, 500,
                    20 * Display.INTERNAL_RES_FACTOR, 20 * Display.INTERNAL_RES_FACTOR,
                    -2 * Display.INTERNAL_RES_FACTOR, -2 * Display.INTERNAL_RES_FACTOR);
            break;

        case MAP_INDICATOR:
            BufferedImage mapIndicatorSheet = SpriteSheet.load(SYSTEM_PATH.resolve(MAP_INDICATOR_NAME)).get();
            BufferedImage[] mapIndicatorSequence = SpriteSheet.split(mapIndicatorSheet, 16, 16, 16, 0, 16);
            EIndicatorDirection direction = EIndicatorDirection.valueOf(additionalInfo[0]);

            Image tempResult = null;

            switch (direction) {
            case ARC_DOWN_RIGHT:
                tempResult = mapIndicatorSequence[4];
                break;
            case ARC_LEFT_DOWN:
                tempResult = mapIndicatorSequence[5];
                break;
            case ARC_LEFT_UP:
                tempResult = mapIndicatorSequence[7];
                break;
            case ARC_UP_RIGHT:
                tempResult = mapIndicatorSequence[6];
                break;
            case BEGINNING_DOWN_UP:
                tempResult = mapIndicatorSequence[3];
                break;
            case BEGINNING_LEFT_RIGHT:
                tempResult = mapIndicatorSequence[0];
                break;
            case BEGINNING_RIGHT_LEFT:
                tempResult = mapIndicatorSequence[2];
                break;
            case BEGINNING_UP_DOWN:
                tempResult = mapIndicatorSequence[1];
                break;
            case END_DOWN_UP:
                tempResult = mapIndicatorSequence[11];
                break;
            case END_LEFT_RIGHT:
                tempResult = mapIndicatorSequence[8];
                break;
            case END_RIGHT_LEFT:
                tempResult = mapIndicatorSequence[10];
                break;
            case END_UP_DOWN:
                tempResult = mapIndicatorSequence[9];
                break;
            case HORIZONTAL:
                tempResult = mapIndicatorSequence[14];
                break;
            case VERTICAL:
                tempResult = mapIndicatorSequence[12];
                break;
            default:
                Logger.get().log(ELogType.ERROR, "Couldn't parse the following indicator direction: " + direction);
                break;

            }
            result = (T) tempResult;
            break;

        case ACTION_WINDOW_TOP:
            result = (T) SpriteSheet.load(SYSTEM_PATH.resolve(ACTION_WINDOW_TOP_NAME)).get();
            break;

        case ACTION_WINDOW_MIDDLE:
            result = (T) SpriteSheet.load(SYSTEM_PATH.resolve(ACTION_WINDOW_MIDDLE_NAME)).get();
            break;

        case ACTION_WINDOW_BOTTOM:
            result = (T) SpriteSheet.load(SYSTEM_PATH.resolve(ACTION_WINDOW_BOTTOM_NAME)).get();
            break;

        case ACTION_SELECTOR:
            result = (T) SpriteSheet.load(SYSTEM_PATH.resolve(ACTION_SELECTOR_NAME)).get();
            break;

        case PORTRAIT:
            BufferedImage portraitSheet = SpriteSheet.load(PORTRAIT_PATH.resolve(additionalInfo[0])).get();
            result = (T) SpriteSheet.split(portraitSheet, 96, 80, 1, 0, 1)[0];
            break;

        case BURST_WINDOW:
            BufferedImage bwImage = SpriteSheet.load(SYSTEM_PATH.resolve(BURST_WINDOW_NAME)).get();
            boolean[] ignore = new boolean[bwImage.getWidth() * bwImage.getHeight()];
            ignore[0] = true;
            ignore[1] = true;

            ignore[84] = true;
            ignore[85] = true;

            ignore[86 * 1 + 0] = true;
            ignore[86 * 1 + 85] = true;

            ignore[86 * 36 + 0] = true;
            ignore[86 * 36 + 85] = true;

            ignore[86 * 37 + 0] = true;
            ignore[86 * 37 + 1] = true;

            ignore[86 * 37 + 84] = true;
            ignore[86 * 37 + 85] = true;
            result = (T) SpriteSheet.applyTransparency(bwImage, 200, ignore);
            break;

        case TILE_STATUS:
            BufferedImage tsImage = SpriteSheet.load(SYSTEM_PATH.resolve(TILE_STATUS_NAME)).get();
            result = (T) SpriteSheet.applyTransparency(tsImage, 200,
                    new boolean[tsImage.getWidth() * tsImage.getHeight()]);
            break;

        case PALETTE:
            result = (T) SpriteSheet.load(PALETTE_PATH.resolve(additionalInfo[0])).get();
            break;

        case BATTLE_PREVIEW:
            result = (T) SpriteSheet.load(SYSTEM_PATH.resolve(BATTLE_PREVIEW_NAME)).get();
            break;

        case BATTLE_PLATFORM_WIDE:
            BufferedImage widePlatformTexture = getBattlePlatformTexture(ETileType.valueOf(additionalInfo[0])).get();

            switch (ESide.valueOf(additionalInfo[1])) {
            case LEFT:
                result = (T) SpriteSheet.generatePlatform(widePlatformTexture, EPlatformDistance.WIDE_LEFT);
                break;
            case RIGHT:
                result = (T) SpriteSheet.generatePlatform(widePlatformTexture, EPlatformDistance.WIDE_RIGHT);
                break;
            default:
                break;

            }

            break;

        case BATTLE_PLATFORM_CLOSE:
            BufferedImage closePlatformTexture = getBattlePlatformTexture(ETileType.valueOf(additionalInfo[0])).get();
            result = (T) SpriteSheet.generatePlatform(closePlatformTexture, EPlatformDistance.CLOSE);
            break;

        case BATTLE_BACKGROUND:
            result = (T) SpriteSheet.load(SYSTEM_PATH.resolve(BATTLE_BACKGROUND_NAME)).get();
            break;

        case BATTLE_ANIMATION:
            // 0: class
            // 1. gender
            // 2: animation type (dodge, crit, normal, ranged, ...)
            // 3: side (left, right)
            EClass unitClass = EClass.valueOf(additionalInfo[0]);
            EGender gender = EGender.valueOf(additionalInfo[1]);
            EBattleAnimType animType = EBattleAnimType.valueOf(additionalInfo[2]);
            ESide side = ESide.valueOf(additionalInfo[3]);

            Path path = null;

            switch (animType) {
            case DODGE:
                path = DODGE_PATH;
                break;
            case MELEE_ATTACK:
                path = MELEE_ATTACK_PATH;
                break;
            case MELEE_CRIT:
                path = MELEE_CRIT_PATH;
                break;
            case RANGED_ATTACK:
                path = RANGED_ATTACK_PATH;
                break;
            case RANGED_CRIT:
                path = RANGED_CRIT_PATH;
                break;
            case STALE:
                break;
            default:
                break;

            }

        default:
            Logger.get().log(ELogType.ERROR,
                    "Couldn't parse: " + resource + " with " + Arrays.toString(additionalInfo));
            break;

        }
        return result;
    }

    private static Optional<BufferedImage> getBattlePlatformTexture(ETileType type) {
        Path path = BATTLE_PATH;

        switch (type) {
        case ARCH:
            break;
        case ARENA:
            break;
        case ARMORY:
            break;
        case BRIDGE:
            break;
        case BROKEN:
            break;
        case CHEST:
            break;
        case CLIFF:
            break;
        case CLOSED:
            break;
        case CURCH:
            break;
        case DESERT:
            break;
        case DOOR:
            break;
        case FLOOR:
            break;
        case FOREST:
            break;
        case FORT:
            break;
        case GATE:
            break;
        case GLACIER:
            break;
        case HOUSE:
            break;
        case KILLER:
            break;
        case LAKE:
            break;
        case LAND:
            break;
        case LONG:
            break;
        case MOUNTAIN:
            break;
        case PEAK:
            break;
        case PILLAR:
            break;
        case PLACEHOLDER:
            break;
        case PLAINS:
            path = path.resolve("plains.png");
            break;
        case RIVER:
            break;
        case ROAD:
            break;
        case ROOF:
            break;
        case RUINS:
            break;
        case SAND:
            break;
        case SEA:
            break;
        case STAIRS:
            break;
        case STORAGE:
            break;
        case THRONE:
            break;
        case VALLEY:
            break;
        case VENDOR:
            break;
        case VILLAGE:
            break;
        case WALL:
            break;
        case WOODS:
            break;
        default:
            break;

        }

        return SpriteSheet.load(path).flatMap(p -> Optional.of(p));

    }

    @SuppressWarnings("unchecked")
    public static <T extends ITile> void loadMap(ATileSet<T> loadedSet, List<String> content, T[][] toPopulate) {
        for (int i = 0; i < toPopulate.length; i++) {
            String[] splitLine = content.get(i).split("\t");

            for (int j = 0; j < toPopulate[0].length; j++) {
                ITile tile = loadedSet.get(Integer.parseInt(splitLine[j]));
                if (tile != null) {
                    toPopulate[i][j] = (T) tile.copy();

                }
            }
        }
    }

    public static EditorTile[][] loadMap() {
        FileFilter filter = new FileNameExtensionFilter("Map File", "map");
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Export to...");
        chooser.setCurrentDirectory(new File(MAP_PATH.toAbsolutePath().toString()));
        int userSelection = chooser.showOpenDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return new EditorTile[0][0];

        }
        File toLoad = chooser.getSelectedFile();
        Path loadPath = Paths.get(toLoad.getAbsolutePath());

        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(loadPath);

        } catch (IOException e) {
            Logger.get().logError(e);

        }
        String[] header = lines.remove(0).split("\t");
        int columns = Integer.parseInt(header[0]);
        int rows = Integer.parseInt(header[1]);
        ETileSet set = ETileSet.valueOf(header[2]);

        EditorTileSet s = new EditorTileSet(set);
        EditorTile[][] map = new EditorTile[rows][columns];

        Assets.loadMap(s, lines, map);
        return map;

    }

    public static void saveMap(ITile[][] map, ETileSet set) {
        StringJoiner rowJoiner = new StringJoiner("\n");
        StringJoiner columnJoiner = new StringJoiner("\t");

        columnJoiner.add(map.length + "");
        columnJoiner.add(map[0].length + "");
        columnJoiner.add(set.toString());

        rowJoiner.add(columnJoiner.toString());

        columnJoiner = new StringJoiner("\t");

        for (int i = 0; i < map[0].length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[j][i] == null) {
                    columnJoiner.add("-1");
                    continue;
                }
                columnJoiner.add(map[j][i].getId() + "");
            }
            rowJoiner.add(columnJoiner.toString());
            columnJoiner = new StringJoiner("\t");
        }

        FileFilter filter = new FileNameExtensionFilter("Map File", "map");
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Export to...");
        chooser.setCurrentDirectory(new File(MAP_PATH.toAbsolutePath().toString()));
        int userSelection = chooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File toSave = chooser.getSelectedFile();
            String suffix = "";

            if (!toSave.getAbsolutePath().endsWith(".map")) {
                suffix = ".map";
            }
            Path savePath = Paths.get(toSave.getAbsolutePath().concat(suffix));

            try {
                Files.write(savePath, rowJoiner.toString().getBytes("UTF-8"));

            } catch (IOException e) {
                Logger.get().logError("Couldn't properly write the file: " + savePath, e);

            }
        }
    }
}
