package devintAPI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import t2s.SIVOXDevint;

/**
 * This abstract class provides a generic menu.
 */
public abstract class MenuAbstrait extends JFrame implements KeyListener,
        ActionListener {

    /** The default font. */
    private static final String DEFAULT_FONT = "Tahoma";

    /** Header font. */
    private static final String HEADER_FONT = "Georgia";

    /** The UID. */
    private static final long serialVersionUID = 1871733154864975885L;

    /** Option buttons. */
    private JButton[] boutonOption;

    /** Color used for the buttons. */
    protected Color couleurBouton;

    /** Selection color used for the buttons. */
    protected Color couleurBoutonSelectionne;

    /** Text color used for the buttons. */
    protected Color couleurTexte;

    /** Text color used for selected buttons. */
    protected Color couleurTexteSelectionne;

    /** Font used for the buttons. */
    protected Font fonteBouton;

    /** Options number. */
    private int nbOption;

    /** Name of the game. */
    protected final String nomJeu;

    /** Names for options. */
    private String[] nomOptions;

    /** The currently selected option. */
    private int optionCourante;

    /** The layout. */
    private GridBagLayout placement;

    /** The associated constraints. */
    private GridBagConstraints regles;

    /** The instance of <code>SIVOXDevint</code> used to manage vocal signs. */
    private SIVOXDevint voix;

    /**
     * Constructor.
     * 
     * @param title
     *            The displayed title for the application.
     */
    public MenuAbstrait(String title) {
        super(title);
        nomJeu = title;
        optionCourante = -1;
        nomOptions = nomOptions();
        nbOption = nomOptions.length;
        creerAttributs();
        creerLayout();
        creerEntete();
        creerOption(nomOptions);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setVisible(true);
        requestFocus();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addKeyListener(this);
        voix = new SIVOXDevint();
        voix.playWav(wavAccueil());
    }

    /**
     * Inherited method.
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae) {

        // To activate the option when clicking on a button.
        voix.stop();
        Object source = ae.getSource();
        for (int i = 0; i < nbOption; i++) {
            if (source == boutonOption[i]) {
                if (optionCourante != -1)
                    unFocusedButton(optionCourante);
                optionCourante = i;
                setFocusedButton(optionCourante);
                lancerOption(i);
            }
        }
    }

    /**
     * Creates attributes such as font or color.
     */
    protected void creerAttributs() {

        // Text colors.
        couleurTexte = Color.WHITE;
        couleurTexteSelectionne = new Color(10, 0, 150);

        // Buttons attributes update.
        fonteBouton = new Font(DEFAULT_FONT, 1, 56);
        couleurBouton = couleurTexteSelectionne;
        couleurBoutonSelectionne = couleurTexte;
    }

    /**
     * Useful method to create a button associated with a text.
     * 
     * @param i
     *            The index.
     * @param texte
     *            The displayed text.
     */
    private void creerBouton(int i, String texte) {
        boutonOption[i] = new JButton();
        boutonOption[i].setText(texte);
        setPropertiesButton(boutonOption[i]);
    }

    /**
     * Creates header displaying the name of the game.
     */
    public void creerEntete() {

        // Header panel for the frame.
        JPanel entete = new JPanel();
        FlowLayout enteteLayout = new FlowLayout();
        enteteLayout.setAlignment(FlowLayout.CENTER);
        entete.setLayout(enteteLayout);
        entete.setBorder(new LineBorder(Color.GRAY, 8));

        // Title label.
        JLabel lb1 = new JLabel(nomJeu);
        lb1.setFont(new Font(HEADER_FONT, 1, 96));
        lb1.setForeground(couleurTexteSelectionne);
        lb1.setBackground(couleurBoutonSelectionne);
        entete.add(lb1);

        // Sets the header.
        regles.gridx = 1;
        regles.gridy = 1;
        placement.setConstraints(entete, regles);
        add(entete);
    }

    /**
     * Creates the layout.
     */
    private void creerLayout() {

        placement = new GridBagLayout();
        regles = new GridBagConstraints();
        setLayout(placement);
        regles.fill = GridBagConstraints.BOTH;
        regles.weightx = 1;
        regles.weighty = 1;
        regles.insets = new Insets(10, 50, 10, 50);
        regles.anchor = GridBagConstraints.NORTH;
    }

    /**
     * Creates the buttons associated to the options.
     * 
     * @param noms
     *            The options name.
     */
    private void creerOption(String[] noms) {

        // Buttons panel creation.
        JPanel boutons = new JPanel();
        boutons.setLayout(new GridLayout(nbOption, 1));
        boutonOption = new JButton[nbOption];
        for (int i = 0; i < nbOption; i++) {
            creerBouton(i, noms[i]);
            boutons.add(boutonOption[i]);
        }
        regles.weighty = 4;
        regles.gridx = 1;
        regles.gridy = 2;
        placement.setConstraints(boutons, regles);
        add(boutons);
    }

    /**
     * Inherited method.
     * 
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent e) {

        voix.stop();

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            // Escape to exit.
            System.exit(0);
        } else if (e.getKeyCode() == KeyEvent.VK_F1) {

            // F1 for help.
            voix.playWav(wavAide());
        } else if (e.getKeyCode() == KeyEvent.VK_F2) {

            // F2 for help.
            voix.playWav(wavAide2());
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            // Enter to select the option.
            lancerOption(optionCourante);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

            // DownArrow to navigate to the bottom.
            if (optionCourante == -1) {
                optionCourante = 0;
                setFocusedButton(optionCourante);
            } else {
                unFocusedButton(optionCourante);
                optionCourante = (optionCourante + 1) % nbOption;
                setFocusedButton(optionCourante);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {

            // UpArrow to navigate to the top.
            if (optionCourante == -1) {
                optionCourante = 0;
                setFocusedButton(optionCourante);
            } else {
                unFocusedButton(optionCourante);
                optionCourante = optionCourante - 1;
                if (optionCourante == -1)
                    optionCourante = nbOption - 1;
                setFocusedButton(optionCourante);
            }
        }
    }

    /**
     * Inherited method.
     * 
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent e) {
        // NOTHING TO DO.
    }

    /**
     * Inherited method.
     * 
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    public void keyTyped(KeyEvent e) {
        // NOTHING TO DO.
    }

    /**
     * This method has to be overridden in order to trigger the action
     * corresponding to the given option.
     * 
     * @param i
     *            The index of the option.
     */
    protected abstract void lancerOption(int i);

    /**
     * Used to retrieve the array containing the options name.
     * 
     * @return The names.
     */
    protected abstract String[] nomOptions();

    /**
     * Used to activate the focus on a button.
     * 
     * @param i
     *            The button index.
     */
    private void setFocusedButton(int i) {
        voix.playShortText(boutonOption[i].getText());
        boutonOption[i].setBackground(couleurBoutonSelectionne);
        boutonOption[i].setForeground(couleurTexteSelectionne);
    }

    /**
     * Used to update the properties of a button.
     * 
     * @param b
     *            The button.
     */
    protected void setPropertiesButton(JButton b) {
        b.setFocusable(false);
        b.setBackground(couleurBouton);
        b.setForeground(couleurTexte);
        b.setFont(fonteBouton);
        b.setBorder(new LineBorder(Color.BLACK, 5));
        b.addActionListener(this);
    }

    /**
     * Used to inhibit the focus of a button.
     * 
     * @param i
     *            The button index.
     */
    private void unFocusedButton(int i) {
        boutonOption[i].setBackground(couleurBouton);
        boutonOption[i].setForeground(couleurTexte);
    }

    /**
     * Should be overridden to get the path of the .wav file for welcoming the
     * user.
     * 
     * @return The corresponding path.
     */
    protected abstract String wavAccueil();

    /**
     * Should be overridden to get the path of the .wav file for help.
     * 
     * @return The corresponding path.
     */
    protected abstract String wavAide();
    
    /**
     * Should be overridden to get the path of the .wav file for more detailed help.
     * 
     * @return The corresponding path.
     */
    protected abstract String wavAide2();
}
