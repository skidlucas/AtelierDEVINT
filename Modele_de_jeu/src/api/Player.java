package api;

/**
 * This is the representation of a player in memory game.
 */
public class Player {

    /** Found pairs number. */
    private int matchingPairs = 0;

    /** Returned pairs number. */
    private int seenPairs = 0;

    /** Name of the Player. */
    private String name;

    private int score = 0;
    
    /**
     * Setter.
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Getter.
     * @return the score
     */
    public int getScore() {
        return score;
    }

    public Player(String name) {
        this.name = name;
    }

    /**
     * Invoked when a play has been performed.
     * 
     * @param matching
     *            true if a pair has been discovered.
     */
    public void play(boolean matching) {
        seenPairs++;
        if (matching) {
            matchingPairs++;
        }
    }

    /**
     * Getter.
     * 
     * @return the matchingPairs.
     */
    public int getMatchingPairs() {
        return matchingPairs;
    }

    /**
     * Getter.
     * 
     * @return the seenPairs.
     */
    public int getSeenPairs() {
        return seenPairs;
    }

    /**
     * Getter.
     * 
     * @return the name.
     */
    public String getName() {
        return name;
    }
}
