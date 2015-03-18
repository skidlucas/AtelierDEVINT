package jeu;

import t2s.SIVOXDevint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class displays the players scores.
 */
public class ScoreInterface extends JFrame implements KeyListener {

    /** The UID. */
    private static final long serialVersionUID = 3025609084618106294L;
    
    /** The instance of <code>SIVOXDevint</code> used to manage vocal signs. */
    SIVOXDevint voix;

    /**
     * Constructor.
     * @param title The application title.
     */
    public ScoreInterface(String title) {
        super(title);
        this.setVisible(true);
        this.requestFocus();
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        addKeyListener(this);
        init();
        voix = new SIVOXDevint();
        voix.playWav("../ressources/accueilScore.wav");
    }

    /**
     * Creates the content of the frame.
     */
    private void init() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        String text = "Si vous voulez vous pouvez gérer des scores ";
        JTextArea lb1 = new JTextArea(text);
        lb1.setLineWrap(true);
        lb1.setEditable(false);
        lb1.setFont(new Font("Georgia", 1, 96));
        this.add(lb1);
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
     * Inherited method.
     * 
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent e) {
        voix.stop();
        // Escape to exit.
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dispose();
        } else if (e.getKeyCode() == KeyEvent.VK_F1) {
            // F1 for help.
            voix.playWav("../ressources/aideF1.wav");
        } else if (e.getKeyCode() == KeyEvent.VK_F2) {
            // F2 for contextual help.
            voix.playWav("../ressources/aideF2.wav");
        }
    }
}
