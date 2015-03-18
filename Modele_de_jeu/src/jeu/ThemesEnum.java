package jeu;

/**
 * This enum type is used to display the theme of cards.
 */
public enum ThemesEnum {
    /** A specific type. */
    ANIMAUX("animaux");

    /** The name associated to this enum. */
    private String name;

    /**
     * Constructor.
     * 
     * @param name
     *            The name to set.
     */
    ThemesEnum(String name) {
        this.name = name;
    }

    /**
     * Inherited method.
     * 
     * @see java.lang.Enum#toString()
     */
    public String toString() {
        return name;
    }
}
