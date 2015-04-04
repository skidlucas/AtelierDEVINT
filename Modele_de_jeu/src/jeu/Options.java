package jeu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import devintAPI.FenetreAbstraite;
import devintAPI.Preferences;
import jeu.model.Profil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by user on 18/03/2015.
 */
public class Options extends FenetreAbstraite{

    private JPanel profil;
    private JPanel options;
    private JPanel buttons;

    private JLabel nom;
    private JLabel lNom;
    private JTextField fieldNom;
    private JLabel lTaille;
    private JLabel lCouleur;

    private GridBagLayout placement; // le layout
    private GridBagConstraints regles; // les regles de placement

    Preferences pref;
    Color foregroundColor;
    Color backgroundColor;

    private JButton save;
    private JButton quit;
    private JButton gauche;
    private JButton droite;

    java.util.List<Profil> allProfils;
    Profil currentProf;
    int indProf;

    Gson parser;

    public Options(String title) {
        super(title);
        parser = new Gson();
        chargeJson();
        init();
    }

    private void chargeJson(){
        String res = new String();
        String ligne = new String();
        allProfils = new ArrayList<Profil>();
        try {
            BufferedReader buf = new BufferedReader(new FileReader("../ressources/json/profils.json"));
            while((ligne = buf.readLine()) != null){
                res += ligne;
            }
            buf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.lang.reflect.Type type = (new TypeToken<ArrayList<Profil>>(){}).getType();
        allProfils = parser.fromJson(res, type);
        indProf = 0;
        currentProf = allProfils.get(indProf);
    }

    @Override
    protected void init() {
       // setLayout(new BorderLayout());
        // on récupère les couleurs de base dans la classe Preferences
        pref = Preferences.getData();
        Color foregroundColor = pref.getCurrentForegroundColor();
        Color backgroundColor = pref.getCurrentBackgroundColor();

        createLayout();
        createTete();
        createForm();
        createButtons();
        this.setVisible(true);
    }

    private void createLayout() {
        placement = new GridBagLayout();
        regles = new GridBagConstraints();
        setLayout(placement);
        // par défaut on étire les composants horizontalement et verticalement
        regles.fill = GridBagConstraints.HORIZONTAL;
        // par défaut, tous les composants ont un poids de 1
        // on les répartit donc équitablement sur la grille
        regles.weightx = 1;
        regles.weighty = 1;
        // espaces au bord des composants
        regles.insets = new Insets(10, 50, 10, 50);
        // pour placer en haut des zones
        regles.anchor = GridBagConstraints.NORTH;
    }

    private void createTete(){
        profil = new JPanel();

        ActionListener changeList = new changeListener();
        gauche = new JButton(new ImageIcon("../ressources/images/flechegauche.png"));
        gauche.addActionListener(changeList);
        droite = new JButton(new ImageIcon("../ressources/images/flechedroite.png"));
        droite.addActionListener(changeList);
        nom = new JLabel(currentProf.getName());
        profil.setLayout(new FlowLayout());
        profil.add(gauche);
        profil.add(nom);
        profil.add(droite);
        nom.setFont(pref.getCurrentFont());
        profil.setBackground(backgroundColor);
        profil.setForeground(foregroundColor);
        // placement de l'entete en 1ère ligne, 1ère colonne
        regles.gridx = 1;
        regles.gridy = 1;
        placement.setConstraints(profil, regles);
        add(profil);
    }

    private void createForm(){
        options = new JPanel();

        fieldNom = new JTextField();
        fieldNom.setText(currentProf.getName());
        JComboBox couleur = new JComboBox();
        JComboBox taille = new JComboBox();
        lNom = new JLabel("Nom :");
        lNom.setFont(pref.getCurrentFont());
        lCouleur = new JLabel("Couleur :");
        lCouleur.setFont(pref.getCurrentFont());
        lTaille = new JLabel("Taille :");
        lTaille.setFont(pref.getCurrentFont());
        options.setLayout(new GridLayout(3,2));
        options.add(lNom);
        options.add(fieldNom);
        options.add(lCouleur);
        options.add(couleur);
        options.add(lTaille);
        options.add(taille);
        //regles.weighty = 2;
        // placement des boutons
        regles.gridx = 1;
        regles.gridy = 2;
        placement.setConstraints(options, regles);
        add(options);
    }

    private void createButtons(){
        buttons = new JPanel();
        save = new JButton("Sauver");
        save.setFont(pref.getCurrentFont());
        quit = new JButton("Quitter");
        quit.setFont(pref.getCurrentFont());
        buttons.setLayout(new FlowLayout());
        buttons.add(save);
        buttons.add(quit);

        regles.gridx = 1;
        regles.gridy = 3;
        placement.setConstraints(buttons, regles);
        add(buttons);
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
        this.getContentPane().setBackground(pref.getCurrentBackgroundColor());
        this.getContentPane().setForeground(pref.getCurrentForegroundColor());
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
        lCouleur.setFont(f);
        lTaille.setFont(f);
        lNom.setFont(f);
        save.setFont(f);
        quit.setFont(f);
    }

    private void refreshChamp(){
        fieldNom.setText(currentProf.getName());
        nom.setText(currentProf.getName());
    }

    public class changeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if((e.getSource() == droite) && indProf + 1 < allProfils.size()){
                indProf++;
            } else if(e.getSource() == gauche && indProf - 1 >= 0){
                indProf--;
            }
            currentProf = allProfils.get(indProf);
            refreshChamp();
        }
    }
}
