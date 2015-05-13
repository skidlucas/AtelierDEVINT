package jeu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import devintAPI.FenetreAbstraite;
import devintAPI.Preferences;
import javafx.scene.control.ComboBox;
import jeu.model.Profil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by user on 18/03/2015.
 */
public class Options extends FenetreAbstraite{

    private JPanel profil;
    private JPanel options;
    private JPanel buttons;

    private String[] colors = {"Orange","Bleu", "Noir"};
    private String[] sizes = {"Petite","Moyenne","Grande"};

    private JLabel nom;
    private JLabel lNom;
    private JTextField fieldNom;
    private JLabel lTaille;
    private JLabel lCouleur;
    private JComboBox couleur;
    private JComboBox taille;

    private GridBagLayout placement; // le layout
    private GridBagConstraints regles; // les regles de placement

    Preferences pref;
    Color foregroundColor;
    Color backgroundColor;

    private JButton save,quit, gauche, droite, addButton, delete;

    java.util.List<Profil> allProfils;
    Profil currentProf;
    int indProf;

    Gson parser;

    public Options(String title) {
        super(title);
        parser = new Gson();
        initJson();
        init();
        refreshChamp();
    }

    private void initJson(){
        allProfils = Utils.chargeJsonProfil();
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
        voix.playText("Choisissez ici le profil actif. Vous pouvez naviguer entre les profils à l'aide des flèches du clavier ou bien en cliquant sur les flèches en haut de l'écran. Si votre profil n'existe pas, vous pouvez en créer un nouveau avec un nom qui n'est pas encore utilisé, ou bien en modifier un déjà existant. Vous pouvez également supprimer les profils inutilisés.");
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

        ActionListener changeList = new ChangeListener();
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

        ActionListener comboListener = new ComboListener();

        fieldNom = new JTextField();
        fieldNom.setText(currentProf.getName());
        couleur = new JComboBox(colors);
        couleur.addActionListener(comboListener);
        taille = new JComboBox(sizes);
        taille.addActionListener(comboListener);
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
        save.addActionListener(new SubmitListener());
        addButton = new JButton("Ajouter");
        addButton.setFont(pref.getCurrentFont());
        addButton.addActionListener(new AddListener());
        quit = new JButton("Quitter");
        quit.addActionListener(new SubmitListener());
        quit.setFont(pref.getCurrentFont());
        delete = new JButton("Supprimer");
        delete.setFont(pref.getCurrentFont());
        delete.addActionListener(new DeleteListener());
        buttons.setLayout(new FlowLayout());
        buttons.add(addButton);
        buttons.add(save);
        buttons.add(delete);
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
        pref = Preferences.getData();
        this.getContentPane().setBackground(pref.getCurrentBackgroundColor());
        this.getContentPane().setForeground(pref.getCurrentForegroundColor());
        profil.setBackground(pref.getCurrentBackgroundColor());
        profil.setForeground(pref.getCurrentForegroundColor());
        options.setBackground(pref.getCurrentBackgroundColor());
        options.setForeground(pref.getCurrentForegroundColor());
        buttons.setBackground(pref.getCurrentBackgroundColor());
        buttons.setForeground(pref.getCurrentForegroundColor());
        lCouleur.setForeground(pref.getCurrentForegroundColor());
        lTaille.setForeground(pref.getCurrentForegroundColor());
        lNom.setForeground(pref.getCurrentForegroundColor());
        nom.setForeground(pref.getCurrentForegroundColor());
    }

    @Override
    public void changeSize() {
        Font f = Preferences.getData().getCurrentFont();
        nom.setFont(f);
        lCouleur.setFont(f);
        lTaille.setFont(f);
        lNom.setFont(f);
        addButton.setFont(f);
        delete.setFont(f);
        save.setFont(f);
        quit.setFont(f);
        couleur.setFont(f);
        taille.setFont(f);
        fieldNom.setFont(f);
    }

    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            changeToLeftProfil();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            changeToRightProfil();
        }
    }

    private void refreshChamp(){
        fieldNom.setText(currentProf.getName());
        nom.setText(currentProf.getName());
        couleur.setSelectedItem(currentProf.getCouleur());
        taille.setSelectedItem(currentProf.getTaille());
    }

    private void changeToLeftProfil() {
        indProf--;
        if(indProf < 0){
            indProf = allProfils.size() - 1;
        }
        currentProf = allProfils.get(indProf);
        refreshChamp();
        requestFocus();
    }

    private void changeToRightProfil() {
        indProf++;
        if(indProf >= allProfils.size()){
            indProf = 0;
        }
        currentProf = allProfils.get(indProf);
        refreshChamp();
        requestFocus();
    }


    /**
     * check if the profil already exists
     * @param name
     * @return
     */
    private boolean nameIsInProfils(String name){
        for(Profil prof : allProfils){
            if(prof.getName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    public class ChangeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == gauche) {
                changeToLeftProfil();
            } else if (e.getSource() == droite) {
                changeToRightProfil();
            }
        }
    }

    public class ComboListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox cbTmp = (JComboBox)e.getSource();
            String value = cbTmp.getSelectedItem().toString();
            if(e.getSource() == couleur){
                switch (value){
                    case "Orange":{
                        pref.setCurrentSetOfColor(1);
                        break;
                    }
                    case "Bleu":{
                        pref.setCurrentSetOfColor(2);
                        break;
                    }
                    case "Noir":{
                        pref.setCurrentSetOfColor(0);
                        break;
                    }
                }
                pref.getData().changeColor();
            } else {
                switch (value){
                    case "Petite":{
                        pref.setCurrentSize(100);
                        break;
                    }
                    case "Moyenne":{
                        pref.setCurrentSize(30);
                        break;
                    }
                    case "Grande":{
                        pref.setCurrentSize(70);
                        break;
                    }
                }
                pref.getData().changeSize();
            }
            requestFocus();
        }
    }

    public class SubmitListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == save && !fieldNom.getText().isEmpty()){
                Profil tmp = new Profil(fieldNom.getText(),
                        couleur.getSelectedItem().toString(),
                        taille.getSelectedItem().toString());
                if(nameIsInProfils(tmp.getName())){
                    voix.playText("Un profil existe déjà avec ce nom, veuillez saisir un autre nom");
                } else {
                    allProfils.remove(indProf);
                    allProfils.add(tmp);
                    currentProf = tmp;
                    indProf = allProfils.size() - 1;
                    Utils.writeJson(allProfils, Utils.profilFilename);
                }
                refreshChamp();
            } else {
                dispose();
            }
            requestFocus();
        }
    }

    private class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == addButton && !fieldNom.getText().isEmpty()){
                Profil tmp = new Profil(fieldNom.getText(),
                        couleur.getSelectedItem().toString(),
                        taille.getSelectedItem().toString());
                if(nameIsInProfils(tmp.getName())){
                    voix.playText("Un profil existe déjà avec ce nom, veuillez saisir un autre nom");
                } else {
                    allProfils.add(tmp);
                    currentProf = tmp;
                    indProf = allProfils.size() - 1;
                    Utils.writeJson(allProfils, Utils.profilFilename);
                }
                refreshChamp();
            }
            requestFocus();
        }
    }

    private class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == delete && allProfils.size() > 1) {
                allProfils.remove(indProf);
                currentProf = allProfils.get(0);
                indProf = 0;
                Utils.writeJson(allProfils, Utils.profilFilename);
                refreshChamp();
            }
            requestFocus();
        }
    }
}
