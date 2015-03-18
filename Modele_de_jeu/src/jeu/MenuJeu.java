package jeu;

import devintAPI.MenuAbstrait;

/**
 * This class provides an implementation of the abstract
 * <code>MenuAbstrait</code> displaying a memory game.
 */
public class MenuJeu extends MenuAbstrait {

    /** Exit option. */
    private static final String EXIT = "Quitter";

    /** The help .wav path. */
    private static final String HELP_PATH = "../ressources/aideF1.wav";

    /** The help .wav path. */
    private static final String HELP_PATH_2 = "../ressources/aideF2.wav";
    
    /** Tutorial option. */
    private static final String TUTORIAL = "Tutoriel";

    /** Start option. */
    private static final String PLAY = "Jouer";

    /** Scores option. */
    private static final String SCORES = "Scores";

    /** The UID. */
    private static final long serialVersionUID = 7045671832542814865L;

    /** The application title. */
    private static final String TITLE = "Memory Card";

    /** Warning message. */
    private static final String UNDEFINED_ACTION = "Action non définie.";

    /** Relative path for the help .wav file. */
    private static final String WELCOMING_PATH = "../ressources/accueil.wav";

    /**
     * Constructor.
     */
    public MenuJeu() {
        super(TITLE);
    }

    /**
     * Inherited method.
     * 
     * @see devintAPI.MenuAbstrait#lancerOption(int)
     */
    protected void lancerOption(int i) {
        switch (i) {
        case 0:
            new JeuInterfaceBis(nomJeu);
            break;
        case 1:
            new OptionInterface(nomJeu);
            break;
        case 2:
            System.exit(0);
        default:
            System.err.println(UNDEFINED_ACTION);
        }
    }

    /**
     * Inherited method.
     * 
     * @see devintAPI.MenuAbstrait#nomOptions()
     */
    protected String[] nomOptions() {
        String[] noms = { PLAY, TUTORIAL, EXIT };
        return noms;
    }

    /**
     * Inherited method.
     * 
     * @see devintAPI.MenuAbstrait#wavAccueil()
     */
    protected String wavAccueil() {
        return WELCOMING_PATH;
    }

    /**
     * Inherited method.
     * 
     * @see devintAPI.MenuAbstrait#wavAide()
     */
    protected String wavAide() {
        return HELP_PATH;
    }

    /**
     * Inherited method.
     * 
     * @see devintAPI.MenuAbstrait#wavAide()
     */
    protected String wavAide2() {
        return HELP_PATH_2;
    }
}
