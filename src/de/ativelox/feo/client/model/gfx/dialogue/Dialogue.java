package de.ativelox.feo.client.model.gfx.dialogue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.Iterator;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.animation.IAnimation;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Dialogue implements IRequireResources, IRenderable, IUpdateable {

    private static final Color FONT_COLOR = new Color(40, 40, 40);

    private Image mDialogueMain;
    private IAnimation mDialogueContinue;

    private Image mPortrait;
    private Iterator<String> mText;

    private Font mFont;

    private String[] mCurrentText;

    private String mLineOne;

    private String mLineTwo;

    public Dialogue(Image portrait, DialogueText text) {
        mPortrait = portrait;
        mText = text.iterator();

        this.load();
        this.advance();

        mDialogueContinue.start();

    }

    @Override
    public void load() {
        mDialogueMain = Assets.getFor(EResource.DIALOGUE_MAIN);
        mDialogueContinue = Assets.getFor(EResource.DIALOGUE_CONTINUE);

        mFont = Assets.getFor(EResource.DIALOGUE_FONT);
        mFont = mFont.deriveFont(((float) (mDialogueMain.getHeight(null) * Display.INTERNAL_RES_FACTOR) / 3.5f));

    }

    @Override
    public void update(TimeSnapshot ts) {
        mDialogueContinue.update(ts);

    }

    @Override
    public void render(DepthBufferedGraphics g) {
        g.drawImage(mDialogueMain,
                mPortrait.getWidth(null) * Display.INTERNAL_RES_FACTOR - 25 * Display.INTERNAL_RES_FACTOR,
                Display.HEIGHT - mDialogueMain.getHeight(null) * Display.INTERNAL_RES_FACTOR - 30
                        - 4 * Display.INTERNAL_RES_FACTOR,
                mDialogueMain.getWidth(null) * Display.INTERNAL_RES_FACTOR,
                mDialogueMain.getHeight(null) * Display.INTERNAL_RES_FACTOR, 1);
        mDialogueContinue.render(g);

        g.drawImage(mPortrait, -15 * Display.INTERNAL_RES_FACTOR,
                Display.HEIGHT - mPortrait.getHeight(null) * Display.INTERNAL_RES_FACTOR - 30,
                mPortrait.getWidth(null) * Display.INTERNAL_RES_FACTOR,
                mPortrait.getHeight(null) * Display.INTERNAL_RES_FACTOR, 1);

        g.drawString(mFont, FONT_COLOR, mLineOne,
                mPortrait.getWidth(null) * Display.INTERNAL_RES_FACTOR - 25 * Display.INTERNAL_RES_FACTOR
                        + 5 * Display.INTERNAL_RES_FACTOR,
                (int) (Display.HEIGHT - mDialogueMain.getHeight(null) * Display.INTERNAL_RES_FACTOR - 30
                        - 4 * Display.INTERNAL_RES_FACTOR
                        + (mDialogueMain.getHeight(null) * Display.INTERNAL_RES_FACTOR) / 3.5f
                        + 5 * Display.INTERNAL_RES_FACTOR),
                20);

        g.drawString(mFont, FONT_COLOR, mLineTwo,
                mPortrait.getWidth(null) * Display.INTERNAL_RES_FACTOR - 25 * Display.INTERNAL_RES_FACTOR
                        + 5 * Display.INTERNAL_RES_FACTOR,
                (int) (Display.HEIGHT - mDialogueMain.getHeight(null) * Display.INTERNAL_RES_FACTOR - 30
                        - 4 * Display.INTERNAL_RES_FACTOR
                        + 2 * (mDialogueMain.getHeight(null) * Display.INTERNAL_RES_FACTOR) / 3.5f
                        + 10 * Display.INTERNAL_RES_FACTOR),
                20);

    }

    public boolean advance() {
        if (!mText.hasNext()) {
            return false;
        }
        mCurrentText = mText.next().split("\n");
        mLineOne = mCurrentText[0];

        if (mCurrentText.length > 1) {
            mLineTwo = mCurrentText[1];
        }else {
            mLineTwo = "";
        }
        mDialogueContinue.setX(mPortrait.getWidth(null) * Display.INTERNAL_RES_FACTOR - 25 * Display.INTERNAL_RES_FACTOR
                + (mDialogueMain.getWidth(null) - 15) * Display.INTERNAL_RES_FACTOR);
        mDialogueContinue.setY(Display.HEIGHT - 25 * Display.INTERNAL_RES_FACTOR);

        return true;

    }
}
