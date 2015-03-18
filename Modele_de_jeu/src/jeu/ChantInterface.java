/** 
 * Cette classe est un exemple pour faire chanter la synthèse vocale
 */

package jeu;

import t2s.SIVOXDevint;
import t2s.ChantDevint;
import t2s.exception.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class allows to sing the chosen song.
 */
public class ChantInterface extends JFrame implements KeyListener {

    /** The UID. */
    private static final long serialVersionUID = -2042037438937030639L;
    
    /** The instance of <code>SIVOXDevint</code> used to manage vocal signs. */
    SIVOXDevint voix;

    /**
     * Constructor.
     * 
     * @param title The application title.
     */
    public ChantInterface(String title) {
        super(title);
        this.setVisible(true);
        this.requestFocus();
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        addKeyListener(this);
        init();
        voix = new SIVOXDevint();
        voix.playWav("../ressources/accueilChant.wav");
    }

    /**
     * Creates the content of the frame.
     */
    private void init() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        String text = "Ici on a codé un exemple pour faire chanter ";
        text += "la synthèse en activant F5";
        text += "\n\n le fichier de champ est dans ressources/clairLune.svc";
        JTextArea lb1 = new JTextArea(text);
        lb1.setLineWrap(true);
        lb1.setEditable(false);
        lb1.setFont(new Font("Georgia", 1, 80));
        this.add(lb1);
    }

    /**
     * Inherited method.
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent e) {
        //NOTHING TO DO.
    }

    /**
     * Inherited method.
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    public void keyTyped(KeyEvent e) {
        //NOTHING TO DO.
    }

    /**
     * Inherited method.
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
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            // F5 for the song "au clair de la lune"
            chanter();
        }
    }

    /**
     * Used to sing a song.
     */
    private void chanter() {
        ChantDevint c = new ChantDevint();
        try {
            c.sing("../ressources/clairLune.svc");
        } catch (SIVOXException e) {
            e.printStackTrace();
        }
    }

}
