package de.ativelox.feo.client.view.element.game;

import java.util.Iterator;
import java.util.Set;

import de.ativelox.feo.client.controller.input.EAction;
import de.ativelox.feo.client.controller.input.InputManager;
import de.ativelox.feo.client.controller.input.InputReceiver;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.dialogue.Dialogue;
import de.ativelox.feo.client.model.property.ICanDisplayDialogue;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.property.callback.IActionListener;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.generic.AScreenElement;
import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class DialogueWindow extends AScreenElement implements IActionListener, IUpdateable {

    private final Iterator<Dialogue> mDialogueSequence;

    private final InputReceiver mReceiver;

    private Dialogue mCurrentDialogue;

    private final ICanDisplayDialogue mParent;

    private boolean mIsDone;

    public DialogueWindow(Iterator<Dialogue> dialogueSequence, ICanDisplayDialogue parent) {
        super(0, 0, Display.WIDTH, 32 * Display.INTERNAL_RES_FACTOR, false);

        if (!dialogueSequence.hasNext()) {
            Logger.get().log(ELogType.ERROR, "Expected more than 0 dialogues.");

        }
        mDialogueSequence = dialogueSequence;
        mCurrentDialogue = dialogueSequence.next();
        mParent = parent;

        mReceiver = new InputReceiver();

    }

    public boolean isFinished() {
        return mIsDone;
    }

    @Override
    public void update(TimeSnapshot ts) {
        if (mIsDone) {
            return;
        }

        mCurrentDialogue.update(ts);

        if (mReceiver.isActiveInitially(EAction.CONFIRMATION)) {
            if (!mCurrentDialogue.advance()) {
                // current dialogue didn't advance, we need to switch to the next dialogue in
                // line.
                if (mDialogueSequence.hasNext()) {
                    mCurrentDialogue = mDialogueSequence.next();

                } else {
                    mParent.onDialogueFinished();
                    mIsDone = true;

                }
            }
        }

        mReceiver.cycleFinished();
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        if (mIsDone) {
            return;
        }

        mCurrentDialogue.render(g);

    }

    public void registerTo(final InputManager im) {
        im.register(this);
    }

    public void unregisterFrom(final InputManager im) {
        im.remove(this);
    }

    @Override
    public void onPress(Set<EAction> action) {
        mReceiver.onPress(action);

    }

    @Override
    public void onRelease(Set<EAction> action) {
        mReceiver.onRelease(action);

    }

}
