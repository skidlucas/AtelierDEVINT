package api;

import java.util.List;

/**
 * The view in MVC design.
 */
public interface MemoryView {

    /**
     * Used to check a <code>Card</code>.
     * 
     * @param card
     *            The <code>Card</code> to check.
     */
    public void turnFirstCard(Card card);

    /**
     * Used to manage a pair valid or not.
     * 
     * @param secondCard
     *            The second Card that has been selected.
     * @param valid
     *            true if the pair is matching.
     */
    public void managePair(Card secondCard, boolean valid);

    /**
     * Used to notify that the <code>Player</code> score has changed.
     * 
     * @param players
     *            The <code>Player</code> list
     */
    public void updatePlayerScore(List<Player> players);

    /**
     * Used to know if the game is ended or not.
     * 
     * @return true if the game is ended false if not.
     */
    public boolean isTheEnd();
}
