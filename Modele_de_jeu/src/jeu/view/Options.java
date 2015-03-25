package jeu.view;

import devintAPI.FenetreAbstraite;
import devintAPI.Preferences;

import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 18/03/2015.
 */
public class Options extends FenetreAbstraite{


    private JPanel profil;
    private JPanel options;
    private JPanel buttons;

    private JLabel nom;

    public Options(String title) {
        super(title);
    }

    @Override
    protected void init() {
        setLayout(new BorderLayout());
        // on récupère les couleurs de base dans la classe Preferences
        Preferences pref = Preferences.getData();
        Color foregroundColor = pref.getCurrentForegroundColor();
        Color backgroundColor = pref.getCurrentBackgroundColor();


        profil = new JPanel();
        JButton gauche = new JButton();
        JButton droite = new JButton();
        nom = new JLabel("Nom");
        profil.setLayout(new FlowLayout());
        profil.add(gauche);
        profil.add(nom);
        profil.add(droite);
        this.add(profil, BorderLayout.NORTH);
        nom.setFont(pref.getCurrentFont());
        profil.setBackground(backgroundColor);
        profil.setForeground(foregroundColor);

        options = new JPanel();
        options.setLayout(new BorderLayout());
        JTextField fieldNom = new JTextField();
        JComboBox couleur = new JComboBox();
        JComboBox taille = new JComboBox();
        options.add(fieldNom);
        options.add(couleur);
        options.add(taille);
        this.add(options);
        options.setBackground(backgroundColor);
        options.setForeground(foregroundColor);

        buttons = new JPanel();
        JButton save = new JButton("Save");
        JButton quit = new JButton("Quit");
        buttons.setLayout(new FlowLayout());
        buttons.add(save);
        buttons.add(quit);
        this.add(buttons, BorderLayout.SOUTH);
        buttons.setBackground(backgroundColor);
        buttons.setForeground(foregroundColor);
    }

    public static void main(String[] args) {
        Options opt = new Options("salut");
    }

    // renvoie le fichier wave contenant le message d'accueil
    protected  String wavAccueil() {
        return "../../ressources/sons/accueilFichier.wav";
    }

    // renvoie le fichier wave contenant la règle du jeu
    protected  String wavRegleJeu() {
        return "../../ressources/sons/accueilFichier.wav";
    }

    // renvoie le fichier wave contenant la règle du jeu
    protected  String wavAide() {
        return "../../ressources/sons/aide.wav";
    }
    @Override
    public void changeColor() {
        Preferences pref = Preferences.getData();
        profil.setBackground(pref.getCurrentBackgroundColor());
        profil.setForeground(pref.getCurrentForegroundColor());
        options.setBackground(pref.getCurrentBackgroundColor());
        options.setForeground(pref.getCurrentForegroundColor());
        buttons.setBackground(pref.getCurrentBackgroundColor());
        buttons.setForeground(pref.getCurrentForegroundColor());
    }

    @Override
    public void changeSize() {
        Font f = Preferences.getData().getCurrentFont();
        nom.setFont(f);
    }
}
