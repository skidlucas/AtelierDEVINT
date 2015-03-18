package api;

import java.util.List;

import javax.swing.JOptionPane;

/**
 * This class manages the cards in a standard memory game.
 */
public class MemoryManager implements MemoryController {

    /** The cards list of the <code>Memory</code> game. */
    private List<Card> cards;

    /** Used to know if the <code>Memory</code> game has a timer or not. */
    private boolean hasTimer;

    /**
     * Used to know if the returned <code>Card</code> is the first in a turn or
     * not.
     */
    private boolean isFirstCard = true;

    /** The first <code>Card</code> returned in the turn. */
    private Card firstCard;

    /** The associated view to the <code>Memory</code> game. */
    private MemoryView view;

    /** The players list of the game. */
    private List<Player> players;

    /** The <code>Player</code> currently playing. */
    private Player currentPlayer;

    /** Used to know if there is one <code>Player</code> or more. */
    private boolean singlePlayer;

    /** The current player index. */
    private int indexPlayer = 0;

    private boolean theEnd = false;

    /**
     * Constructor.
     * 
     * @param players
     *            The <code>Player</code> list.
     * 
     * @param view
     *            The view to set.
     */
    public MemoryManager(List<Player> players, MemoryView view) {
        this.players = players;
        currentPlayer = players.get(0);
        singlePlayer = (players.size() == 1);
        this.view = view;
    }

    /**
     * Inherited method.
     * 
     * @see api.MemoryController#selectCard(api.Card)
     */
    public void selectCard(Card card) {
        if (!theEnd) {
            if (isFirstCard) {
                firstCard = card;

                // Notifies the view of the first "move".
                view.turnFirstCard(card);
            } else {
                boolean matching = false;
                if (card.getTwin() == firstCard) {
                    matching = true;
                }

                // Notifies the view of the second "move".
                view.managePair(card, matching);
                currentPlayer.play(matching);

                // This parts changes the current player.
                if (!singlePlayer) {
                    indexPlayer++;
                    indexPlayer = indexPlayer % players.size();
                    currentPlayer = players.get(indexPlayer);
                }
            }

            isFirstCard = !isFirstCard;

            theEnd = view.isTheEnd();

            if (theEnd) {
                computeScores();
            }
        }
    }

    /**
     * Inherited method.
     * 
     * @see api.MemoryController#computeScores()
     */
    public void computeScores() {
        for (Player player : players) {
            computePlayerScore(player);
        }
        view.updatePlayerScore(players);
    }

    /**
     * Updates score of a player.
     * 
     * @param player
     *            The <code>Player</code> which score is to update.
     */
    private void computePlayerScore(Player player) {
        int matching = player.getMatchingPairs();
        int seen = player.getSeenPairs();

        int score;
        if (singlePlayer) {
            score = matching * 10 - 2 * seen;
        } else {
            score = matching;
        }
        player.setScore(score);
    }
}
