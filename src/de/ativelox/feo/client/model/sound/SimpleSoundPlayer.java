package de.ativelox.feo.client.model.sound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.logging.Logger;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class SimpleSoundPlayer implements ISoundPlayer {

    private final List<Clip> mMusic;

    private final List<Clip> mSoundEffects;

    public SimpleSoundPlayer() {
	mMusic = new ArrayList<>();
	mSoundEffects = new ArrayList<>();
    }

    @Override
    public int getPriority() {
	return 0;
    }

    @Override
    public void update(TimeSnapshot ts) {
	mSoundEffects.removeIf(c -> !c.isRunning());
    }

    @Override
    public void play(EMusic music) {
	mMusic.forEach(m -> m.stop());

	try {
	    Clip clip = AudioSystem.getClip();
	    clip.open(AudioSystem.getAudioInputStream(SoundMapping.get(music)));
	    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	    gainControl.setValue(-15.0f);
	    clip.start();

	    mMusic.add(clip);

	} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
	    Logger.get().logError(e);

	}
    }

    @Override
    public void play(ESoundEffect effect) {
	try {
	    Clip clip = AudioSystem.getClip();
	    clip.open(AudioSystem.getAudioInputStream(SoundMapping.get(effect)));
	    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	    gainControl.setValue(-10.0f);
	    clip.start();

	    mSoundEffects.add(clip);

	} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
	    Logger.get().logError(e);

	}
    }
}
