package de.ativelox.feo.client.model.sound;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.EResource;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class SoundMapping {
    private static Map<EMusic, File> mMusicMapping;
    private static Map<ESoundEffect, File> mEffectMapping;

    private SoundMapping() {

    }

    public static void init() {
        mMusicMapping = new HashMap<>();
        mEffectMapping = new HashMap<>();

    }

    public static File get(EMusic music) {
        if (mMusicMapping.containsKey(music)) {
            return mMusicMapping.get(music);
        }

        String fileName = "";

        switch (music) {
        case AFTER_THE_WAR:
            fileName = "After the War";
            break;
        case AN_UNEXPECTED_CALLER:
            fileName = "An Unexpected Caller";
            break;
        case ARENA_BATTLE:
            fileName = "Arena Battle";
            break;
        case ARENA_ENTRANCE:
            fileName = "Arena Entrance";
            break;
        case ATTACK:
            fileName = "Attack";
            break;
        case AT_THE_FINAL_DRAGON:
            fileName = "At the Final Dragon";
            break;
        case BATTLE_FOR_TOMORROW:
            fileName = "Battle for Tomorrow";
            break;
        case BATTLE_FOR_WHOM:
            fileName = "Battle for Whom";
            break;
        case BATTLE_RESULTS:
            fileName = "Battle Results";
            break;
        case BEHOLDING_NATURES_BEAUTY:
            fileName = "Beholding Nature's Beauty";
            break;
        case BENEATH_A_NEW_LIGHT:
            fileName = "Beneath a New Light";
            break;
        case BEYOND_THE_SKY:
            fileName = "Beyond the Sky";
            break;
        case BGM:
            fileName = "BGM";
            break;
        case BINDING_TIES:
            fileName = "Binding Ties";
            break;
        case CAMPAIGN:
            fileName = "Campaign";
            break;
        case CAPTIVATING_DANCE:
            fileName = "Captivating Dance";
            break;
        case COGS_OF_FATE:
            fileName = "Cogs of Fate";
            break;
        case CURING:
            fileName = "Curing";
            break;
        case DEER_OF_THE_PLAINS:
            fileName = "Deer of the Plains";
            break;
        case DIGNIFIED:
            fileName = "Dignified";
            break;
        case DISTANT_LANDS:
            fileName = "Distant Lands";
            break;
        case DISTANT_UTOPIDA:
            fileName = "Distant Utopia";
            break;
        case DRAGON_SANCTUARY:
            fileName = "Dragon Sanctuary";
            break;
        case EPILOGUE:
            fileName = "Epilogue";
            break;
        case ETERNAL_WIND:
            fileName = "Eternal Wind";
            break;
        case FE_GAIDEN_ENEMY_PHASE:
            fileName = "FE Gaiden Enemy Phase";
            break;
        case FE_GAIDEN_PLAYER_PHASE:
            fileName = "FE Gaiden Player Phase";
            break;
        case FINAL_CHAPTER:
            fileName = "Final Chapter";
            break;
        case FINAL_FAREWELL:
            fileName = "Final Farewell";
            break;
        case FIRE_EMBLEM_THEME:
            fileName = "Fire Emblem Theme";
            break;
        case GAME_OVER:
            fileName = "Game Over";
            break;
        case GOING_MY_WAY:
            fileName = "Going My Way";
            break;
        case HAPPINESS_ABOUNDS:
            fileName = "Happiness Abounds";
            break;
        case HEALING:
            fileName = "Healing";
            break;
        case INTO_THE_SHADOW_OF_TRIUMPH:
            fileName = "Into the Shadow of Triumph";
            break;
        case INVISIBLE_ENEMY:
            fileName = "Invisible Enemy";
            break;
        case IN_THE_NAME_OF_BERN:
            fileName = "In the Name of Bern";
            break;
        case JOIN_US:
            fileName = "Join Us";
            break;
        case LEGENDARY_INHERITANCE:
            fileName = "Legendary Inheritance";
            break;
        case MAIN_THEME_ARRANGEMENT:
            fileName = "Main Theme Arrangement";
            break;
        case MELANCHOLIC:
            fileName = "Melancholic";
            break;
        case MESSENGER_FROM_THE_DARKNESS:
            fileName = "Messenger from the Darkness";
            break;
        case ONE_WILL:
            fileName = "One Will";
            break;
        case POEM_OF_THE_SOFT_WIND:
            fileName = "Poem of the Soft Wind";
            break;
        case PRELUDE_TELLING_A_LEGEND:
            fileName = "Prelude - Telling a Legend";
            break;
        case PREPARE_TO_CHARGE:
            fileName = "Prepare to Charge";
            break;
        case PRIESTESS_IN_THE_DARK:
            fileName = "Priestess in the Dark";
            break;
        case PRINCESS_OF_DESTINY:
            fileName = "Princess of Destiny";
            break;
        case RECAPTURING_THE_ROYAL_CAPITAL:
            fileName = "Recapturing the Royal Capital";
            break;
        case REVENGE:
            fileName = "Revenge";
            break;
        case SCARS_OF_THE_SCOURING:
            fileName = "Scars of the Scouring";
            break;
        case SHADOW_APPROACHES:
            fileName = "Shadow Approaches";
            break;
        case SHOCKING_TRUTH_I:
            fileName = "Shocking Truth I";
            break;
        case SHOCKING_TRUTH_II:
            fileName = "Shocking Truth II";
            break;
        case SHOP:
            fileName = "Shop";
            break;
        case SUSPICIOUS:
            fileName = "Suspicious";
            break;
        case TALKING:
            fileName = "Talking";
            break;
        case THE_DARK_PRIESTESS:
            fileName = "The Dark Priestess";
            break;
        case THE_KINGDOM_OF_BERN:
            fileName = "The Kingdom of Bern";
            break;
        case THE_KING_OF_BERN:
            fileName = "The King of Bern";
            break;
        case THE_PATH_TO_GREATNESS:
            fileName = "The Path to Greatness";
            break;
        case TO_THE_HEIGHTS:
            fileName = "To the Heights";
            break;
        case TRIUMPH:
            fileName = "Triumph";
            break;
        case WHAT_CAME_BEFORE:
            fileName = "What Came Before";
            break;
        case WHEN_THE_RUSH_COMES:
            fileName = "When the Rush Comes";
            break;
        case WILD_FRONTIER:
            fileName = "Wild Frontier";
            break;
        case WINNING_ROAD:
            fileName = "Winning Road";
            break;
        default:
            break;

        }
        Path path = Assets.getFor(EResource.MUSIC, fileName);
        File result = path.toFile();
        mMusicMapping.put(music, result);

        return result;

    }

    public static File get(ESoundEffect sfx) {
        if (mEffectMapping.containsKey(sfx)) {
            return mEffectMapping.get(sfx);
        }

        String fileName = "";

        switch (sfx) {
        case ACQUIRED:
            fileName = "Acquired";
            break;
        case BROKE:
            fileName = "Broke";
            break;
        case CURSOR_MOVE:
            fileName = "CursorMove";
            break;
        case LEVEL_UP:
            fileName = "LevelUp";
            break;
        case MAIN_MENU_CONFIRMATION:
            fileName = "MainMenuConfirmation";
            break;
        case WINDOW_ACCEPT:
            fileName = "WindowAccept";
            break;
        case WINDOW_CANCELED:
            fileName = "WindowCanceled";
            break;
        case WINDOW_SELECTION_MOVED:
            fileName = "WindowSelectionMoved";
            break;
        case MOVEMENT_RANGE:
            fileName = "MovementRange";
            break;
        case WINDOW_POPUP:
            fileName = "WindowPopup";
            break;
        case HEAVY_STEPPING:
            fileName = "song330";
            break;
        case NORMAL_HIT:
            fileName = "song210";
            break;
        case SWORD_SLASHING_AIR:
            fileName = "song202";
            break;
        default:
            break;

        }

        Path path = Assets.getFor(EResource.SFX, fileName);
        File file = path.toFile();
        mEffectMapping.put(sfx, file);

        return file;

    }
}
