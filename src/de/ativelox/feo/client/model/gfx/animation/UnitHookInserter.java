package de.ativelox.feo.client.model.gfx.animation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ativelox.feo.client.model.gfx.animation.hook.IHook;
import de.ativelox.feo.client.model.gfx.animation.hook.NonBlockingSoundHook;
import de.ativelox.feo.client.model.property.EBattleAnimType;
import de.ativelox.feo.client.model.property.EClass;
import de.ativelox.feo.client.model.sound.ESoundEffect;
import de.ativelox.feo.logging.Logger;
import de.ativelox.feo.util.Pair;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class UnitHookInserter {

    private static final Path ANIMATION_HOOK_PATH = Paths.get("res", "fe6", "animation_hook");

    private UnitHookInserter() {

    }

    private static Map<EClass, Map<EBattleAnimType, List<Pair<Integer, ESoundEffect>>>> SOUND_HOOK_MAPPING;
    private static Map<EClass, Map<EBattleAnimType, Integer>> HIT_MAPPING;

    public static void insert(IAnimation source, EClass unitClass, EBattleAnimType type) {
        ensureAvailability(unitClass);

        List<Pair<Integer, ESoundEffect>> hooks = SOUND_HOOK_MAPPING.get(unitClass).get(type);

        for (final Pair<Integer, ESoundEffect> hook : hooks) {
            source.addHook(hook.getFirst(), new NonBlockingSoundHook(hook.getSecond()));

        }
    }

    public static void insert(IAnimation source, EClass unitClass, EBattleAnimType type, IHook... insertOnHit) {
        ensureAvailability(unitClass);

        for (final IHook hook : insertOnHit) {
            if (!HIT_MAPPING.get(unitClass).containsKey(type)) {
                break;
            }
            source.addHook(HIT_MAPPING.get(unitClass).get(type), hook);

        }
    }

    private static void ensureAvailability(EClass unitClass) {
        if (SOUND_HOOK_MAPPING == null) {
            SOUND_HOOK_MAPPING = new HashMap<>();

        }
        if (HIT_MAPPING == null) {
            HIT_MAPPING = new HashMap<>();
        }

        if (!SOUND_HOOK_MAPPING.containsKey(unitClass)) {
            SOUND_HOOK_MAPPING.put(unitClass, new HashMap<>());
            HIT_MAPPING.put(unitClass, new HashMap<>());
            load(unitClass);
        }
    }

    private static void load(EClass unitClass) {
        Path path = ANIMATION_HOOK_PATH.resolve(unitClass.toString().toLowerCase() + ".ah");

        try {
            List<String> lines = Files.readAllLines(path);

            for (final String line : lines) {
                String[] data = line.split("\t");
                EBattleAnimType type = EBattleAnimType.valueOf(data[0]);

                SOUND_HOOK_MAPPING.get(unitClass).put(type, new ArrayList<>());

                for (int i = 1; i < data.length; i++) {
                    String[] pair = data[i].split("\\s+");
                    if (pair.length == 1) {
                        HIT_MAPPING.get(unitClass).put(type, Integer.parseInt(pair[0]));
                        break;
                    }

                    SOUND_HOOK_MAPPING.get(unitClass).get(type)
                            .add(Pair.of(Integer.parseInt(pair[0]), ESoundEffect.valueOf(pair[1])));

                }

            }

        } catch (IOException e) {
            Logger.get().logError(e);

        }
    }
}
