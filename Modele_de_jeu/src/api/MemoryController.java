package api;

/**
 * The MVC controller type.
 */
public interface MemoryController {

    /**
     * Method invoked when a <code>Card</code> is selected by the user.
     * 
     * @param card
     *            The selected <code>Card</code>.
     */
    public abstract void selectCard(Card card);

    /**
     * Used to compute the scores.
     */
    public abstract void computeScores();

}