package de.ativelox.feo.client.view.element.specific;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class IntegerField extends AInputField<Integer> {

    /**
     * @param percentX
     * @param percentY
     * @param percentWidth
     * @param fontSize
     */
    public IntegerField(int percentX, int percentY, int percentWidth, int fontSize, int limit) {
        super(percentX, percentY, percentWidth, fontSize, limit);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected boolean validInput(char input) {
        try {
            Integer.parseInt(Character.toString(input));

        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public Integer getContent() {
        if (mContent.isEmpty()) {
            return 1;
        }

        return Integer.parseInt(mContent);
    }

}
