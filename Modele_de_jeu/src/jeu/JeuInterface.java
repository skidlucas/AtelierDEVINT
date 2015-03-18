package jeu;

import t2s.SIVOXDevint;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

/**
 * This class manages the game.
 */
public class JeuInterface extends JFrame implements KeyListener, ActionListener {

    /** The UID. */
    private static final long serialVersionUID = 2656171540305934138L;

    /** The button used to ask questions. */
    JButton question;

    /** The instance of <code>SIVOXDevint</code> used to manage vocal signs. */
    private SIVOXDevint voix;

    /** The layout. */
    private GridBagLayout placement;

    /** The associated constraints. */
    private GridBagConstraints regles;

    /** The frame width. */
    private int largeur = Toolkit.getDefaultToolkit().getScreenSize().width;

    /** The frame height. */
    private int hauteur = Toolkit.getDefaultToolkit().getScreenSize().height;

    /**
     * Constructor.
     * 
     * @param title
     *            The application title.
     */
    public JeuInterface(String title) {
        super(title);
        this.setVisible(true);
        this.requestFocus();
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addKeyListener(this);
        init();
        voix = new SIVOXDevint(2);
        voix.playWav("../ressources/accueilJeu.wav");
    }

    /**
     * Creates the content of the frame.
     */
    private void init() {
        // The layout.
        placement = new GridBagLayout();
        regles = new GridBagConstraints();
        regles.fill = GridBagConstraints.BOTH;
        setLayout(placement);
        regles.insets = new Insets(10, 50, 10, 50);

        // First label
        String text = "Salut, Popol. Il est important d'annoncer ce qu'il faut faire pour démarrer ";
        text += "le jeu.\n L'interface de votre jeu est totalement libre mais vous devez suivre ";
        text += "les conventions d'utilisation des touches.";
        JTextArea lb1 = new JTextArea(text);
        lb1.setLineWrap(true);
        lb1.setEditable(false);
        lb1.setFont(new Font("Georgia", 1, 30));
        regles.gridx = 1;
        regles.gridy = 1;
        placement.setConstraints(lb1, regles);
        this.add(lb1);

        // Second label
        text = "Quand il est indispensable de fixer la taille des composants,";
        text += " faites-le de façon proportionnelle à la fenêtre. \n";
        text += "Ce champ de texte occupe la moitié de la largeur de l'écran";
        text += " et le tiers de la hauteur.";
        JTextArea lb2 = new JTextArea(text);
        lb2.setLineWrap(true);
        lb2.setEditable(false);
        lb2.setFont(new Font("Georgia", 1, 30));
        lb2.setPreferredSize(new Dimension(largeur / 2, hauteur / 3));

        regles.gridx = 1;
        regles.gridy = 2;
        placement.setConstraints(lb2, regles);
        this.add(lb2);

        question = new JButton();
        question.setText("écouter la question");
        question.setBackground(new Color(50, 50, 255));
        question.setBorder(new LineBorder(Color.BLACK, 5));
        question.addActionListener(this);
        question.setFont(new Font("Georgia", 1, 40));
        regles.gridx = 1;
        regles.gridy = 3;
        placement.setConstraints(question, regles);
        this.add(question);
    }

    /**
     * Inherited method.
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae) {
        voix.stop();
        Object source = ae.getSource();
        if (source == question) {
            String text = "les questions sont longues et ont un contenu variable. Il ne faut pas générer un fichier wave mais lire directement les textes";
            voix.playText(text);
        }
        this.requestFocus();
    }

    /**
     * Inherited method.
     * 
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent e) {
        // NOTHNG TO DO.
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
     * Inherited method.
     * 
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent e) {
        voix.stop();
        // Escape to exit.
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            voix = new SIVOXDevint(0);// donc voix 7 (défaut)
            voix.playText("Fini");
            dispose();
        } else if (e.getKeyCode() == KeyEvent.VK_F1) {
            voix.playWav("../ressources/aideF1.wav");
        } else if (e.getKeyCode() == KeyEvent.VK_F2) {
            voix.playWav("../ressources/aideF2.wav");
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            voix.playWav("../ressources/callas.wav");
        }
    }
}
