package jeu;

import devintAPI.FenetreAbstraite;
import devintAPI.Preferences;
import jeu.model.Card;
import jeu.model.Profil;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

/**
 * Created by user on 17/04/2015.
 */
public class PresJeuMulti extends FenetreAbstraite implements ActionListener {
    private JComboBox themesList, firstProfil, secondProfil;
    private JLabel theme, profil1, profil2;
    private JButton bouton;
    private JPanel jp1, jp2, jp3;

    public PresJeuMulti(String title) {
        super(title);
        init();
        voix.stop();
        voix.playText("Mode multijoueur. Choisissez vos profils et le thème avant de lancer la partie.");
    }

    // renvoie le fichier wave contenant le message d'accueil
    protected  String wavAccueil() {
        return "../ressources/sons/accueil.wav";
    }

    // renvoie le fichier wave contenant la règle du jeu
    protected  String wavRegleJeu() {
        return "../ressources/sons/aideF1.wav";
    }

    // renvoie le fichier wave contenant la règle du jeu
    protected  String wavAide() {
        return "../ressources/sons/aide.wav";
    }

    // définition de la méthode abstraite "init()"
    // initialise le frame
    protected void init() {// on récupère les couleurs de base dans la classe Preferences
        bouton = new JButton("Commencer");
        Preferences pref = Preferences.getData();
        Color foregroundColor = pref.getCurrentForegroundColor();
        Color backgroundColor = pref.getCurrentBackgroundColor();
        setLayout(new GridLayout(3, 0));
        jp1 = new JPanel();
        jp1.setLayout(new GridLayout(0, 2));
        jp1.setBackground(backgroundColor);

        jp2 = new JPanel();
        jp2.setBackground(backgroundColor);

        List<Profil> allProfiles = Utils.chargeJson();
        String[] allNames = new String[allProfiles.size()];
        for (int i = 0; i < allProfiles.size(); i++) {
            allNames[i] = allProfiles.get(i).getName();
        }
        firstProfil = new JComboBox(allNames);
        firstProfil.setSelectedIndex(0);
        firstProfil.setEditable(false);
        firstProfil.setFont(new Font("Georgia", 1, 30));
        firstProfil.setBackground(backgroundColor);
        firstProfil.setForeground(foregroundColor);
        firstProfil.addActionListener(this);

        profil1 = new JLabel("Choisir le profil du joueur 1");
        profil1.setHorizontalAlignment(SwingConstants.CENTER);
        profil1.setFont(new Font("Georgia", Font.BOLD, 30));
        profil1.setForeground(foregroundColor);

        jp2.add(profil1);
        jp2.add(firstProfil);

        jp3 = new JPanel();
        jp3.setLayout(new BorderLayout());
        jp3.setBackground(backgroundColor);


        String[] themes = {"Animaux", "Fruits", "Caractères chinois" };
        themesList = new JComboBox(themes);
        themesList.setSelectedIndex(0);
        themesList.setEditable(false);
        themesList.setFont(new Font("Georgia", 1, 30));
        themesList.setBackground(backgroundColor);
        themesList.setForeground(foregroundColor);
        themesList.addActionListener(this);

        theme = new JLabel("Choisir un thème parmi les suivants");
        theme.setHorizontalAlignment(SwingConstants.CENTER);
        theme.setFont(new Font("Georgia", Font.BOLD, 30));
        theme.setForeground(foregroundColor);

        bouton.setBackground(foregroundColor);
        bouton.setBorder(new LineBorder(Color.BLACK, 5));
        bouton.setFont(new Font("Georgia", 1, 40));
        bouton.setForeground(backgroundColor);
        bouton.addActionListener(this);

        jp1.add(theme);
        jp1.add(themesList);
        this.add(jp1);
        this.add(jp2);
        jp3.add(bouton, BorderLayout.SOUTH);
        this.add(jp3);
    }

    // lire la question si clic sur le bouton
    public void actionPerformed(ActionEvent ae){
        // toujours stopper la voix avant de parler
        voix.stop();
        // on récupère la source de l'évènement
        Object source = ae.getSource();
        // si c'est le bouton "question" on lit la question
        // le contenu des questions est variable donc on les lit avec SI_VOX
        if (source.equals(bouton)) {
            String text = "Vous avez appuyé sur le bouton commencer";
            voix.playText(text);
        }
        // on redonne le focus au JFrame principal
        // (après un clic, le focus est sur le bouton)
        this.requestFocus();
    }


    // évènements clavier
    public void keyPressed(KeyEvent e) {
        // appel à la méthode mère qui gère les évènements ESC, F1, F3, F4
        super.keyPressed(e);
        // cas particulier pour ce jeu : la touche F5
        if (e.getKeyCode()==KeyEvent.VK_F5){
            voix.playText("Vous venez d'appuyer sur EFFE 5");
        }
    }

    /**
     * Pour modifier les couleurs de fond et de premier plan de la fenêtre
     * Cette fonction est appelée par la fonction "changeColor" de la classe "Preferences"
     * à chaque fois que l'on presse F3
     *
     * on change la couleur du texte principal
     **/
    public  void changeColor() {
        // on récupère les couleurs de base dans la classe Preferences
        Preferences pref = Preferences.getData();
        themesList.setBackground(pref.getCurrentBackgroundColor());
        themesList.setForeground(pref.getCurrentForegroundColor());
        jp1.setBackground(pref.getCurrentBackgroundColor());
        jp1.setForeground(pref.getCurrentForegroundColor());
        jp2.setBackground(pref.getCurrentBackgroundColor());
        jp2.setForeground(pref.getCurrentForegroundColor());
        jp3.setBackground(pref.getCurrentBackgroundColor());
        jp3.setForeground(pref.getCurrentForegroundColor());
        theme.setForeground(pref.getCurrentForegroundColor());
        bouton.setBackground(pref.getCurrentForegroundColor());
        bouton.setForeground(pref.getCurrentBackgroundColor());
    }


    /**
     * Pour modifier la police des textes à chaque fois que l'on presse F4
     */
    public void changeSize(){
        Font f = Preferences.getData().getCurrentFont();
        themesList.setFont(f);
        theme.setFont(f);
    }

    private class startGameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JeuMulti frameSoloGame = new JeuMulti("Jeu Multi");
            dispose();
        }
    }
}
