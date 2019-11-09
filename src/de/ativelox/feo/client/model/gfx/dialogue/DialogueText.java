package de.ativelox.feo.client.model.gfx.dialogue;

import java.util.Iterator;

import de.ativelox.feo.client.model.util.ArrayIterator;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class DialogueText implements Iterable<String> {

    private final String[] mFormattedText;

    public DialogueText(final String text) {
        mFormattedText = text.split("\n\n");

    }

    @Override
    public Iterator<String> iterator() {
        return new ArrayIterator<>(mFormattedText);
    }
}
