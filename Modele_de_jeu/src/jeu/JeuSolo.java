package jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import devintAPI.FenetreAbstraite;
import devintAPI.Preferences;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/** Cette classe est un exemple d'interface de jeu.
 *  Elle étend DevintFrame pour avoir un Frame et réagir aux évênements claviers
 * Implémente ActionListener pour réagir au clic souris sur le bouton.
 * On surchage la méthode "keyPressed" pour associer une action à la touche F3
 * 
 * @author helene
 *
 */

public class JeuSolo extends FenetreAbstraite implements ActionListener{

	private JComboBox themesList, difficultesList;
    private JLabel theme, difficulte, text;
    private JButton bouton;
    private JPanel jp1, jp2, jp3;
	private JButton picButton1, picButton2, picButton3;
    BufferedImage backCard = null, giraffe = null;

	// appel au constructeur de la classe mère
    public JeuSolo(String title) {
    	super(title);
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
        jp1.setLayout(new GridLayout(2, 2));
        jp1.setBackground(backgroundColor);

        jp2 = new JPanel();
        jp2.setLayout(new BorderLayout());
        jp2.setBackground(backgroundColor);

        jp3 = new JPanel();
        jp3.setLayout(new GridLayout(0,3));
        jp3.setBackground(backgroundColor);


        String[] themes = {"Animaux", "Fruits", "Caractères chinois" };
        themesList = new JComboBox(themes);
        themesList.setSelectedIndex(0);
    	themesList.setEditable(false);
    	themesList.setFont(new Font("Georgia",1,30));
		themesList.setBackground(backgroundColor);
		themesList.setForeground(foregroundColor);
        themesList.addActionListener(this);

        String[] difficultes = {"Facile", "Moyen", "Difficile"};
        difficultesList = new JComboBox(difficultes);
        difficultesList.setSelectedIndex(0);
        difficultesList.setEditable(false);
        difficultesList.setFont(new Font("Georgia",1,30));
        difficultesList.setBackground(backgroundColor);
        difficultesList.setForeground(foregroundColor);
        difficultesList.addActionListener(this);

        theme = new JLabel("Thème");
        theme.setFont(new Font("Georgia", Font.BOLD, 30));
        theme.setForeground(foregroundColor);

        difficulte = new JLabel("Difficulté");
        difficulte.setFont(new Font("Georgia", Font.BOLD, 30));
        difficulte.setForeground(foregroundColor);

        bouton.setBackground(foregroundColor);
        bouton.setBorder(new LineBorder(Color.BLACK, 5));
        bouton.setFont(new Font("Georgia", 1, 40));
        bouton.setForeground(backgroundColor);
        bouton.addActionListener(this);

        text = new JLabel("Trouvez la paire de cartes");
        text.setFont(new Font("Georgia", Font.BOLD, 30));
        text.setForeground(foregroundColor);
        text.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        jp1.add(theme);
        jp1.add(themesList);
        jp1.add(difficulte);
        jp1.add(difficultesList);
        this.add(jp1);
        try {
            backCard = ImageIO.read(new File("../ressources/images/dosCarte.JPG"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        picButton1 = new JButton(new ImageIcon(backCard));
        picButton1.setBackground(backgroundColor);
        picButton1.setBorder(null);

        picButton2 = new JButton(new ImageIcon(backCard));
        picButton3 = new JButton(new ImageIcon(backCard));
        picButton2.setBackground(backgroundColor);
        picButton2.setBorder(null);
        picButton3.setBackground(backgroundColor);
        picButton3.setBorder(null);


        jp3.add(picButton1);
        jp3.add(picButton2);
        jp3.add(picButton3);
        this.add(jp3);
        jp2.add(text, BorderLayout.NORTH);
        jp2.add(bouton, BorderLayout.SOUTH);
        this.add(jp2);

        setPairCards();
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
    		String text = "Vous avez appuyé sur le bouton";
    		voix.playText(text);
    	}
    	// on redonne le focus au JFrame principal
    	// (après un clic, le focus est sur le bouton)
    	this.requestFocus();
    }

    public void setPairCards () {
        Random rand = new Random();
        int firstCard = rand.nextInt(3);
        int secondCard = firstCard;
        while (secondCard == firstCard) {
            secondCard = rand.nextInt(3);
        }
        try {
            giraffe = ImageIO.read(new File("../ressources/images/girafe.jpg"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        if (firstCard == 0 || secondCard == 0) {
            picButton1.addActionListener(new GiraffeListener());
        }
        if (firstCard == 1 || secondCard == 1) {
            picButton2.addActionListener(new GiraffeListener());
        }
        if (firstCard == 2 || secondCard == 2) {
            picButton3.addActionListener(new GiraffeListener());
        }
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
        difficultesList.setBackground(pref.getCurrentBackgroundColor());
        difficultesList.setForeground(pref.getCurrentForegroundColor());
        jp1.setBackground(pref.getCurrentBackgroundColor());
        jp1.setForeground(pref.getCurrentForegroundColor());
        jp2.setBackground(pref.getCurrentBackgroundColor());
        jp2.setForeground(pref.getCurrentForegroundColor());
        jp3.setBackground(pref.getCurrentBackgroundColor());
        jp3.setForeground(pref.getCurrentForegroundColor());
        theme.setForeground(pref.getCurrentForegroundColor());
        difficulte.setForeground(pref.getCurrentForegroundColor());
        picButton1.setBackground(pref.getCurrentBackgroundColor());
        picButton2.setBackground(pref.getCurrentBackgroundColor());
        picButton3.setBackground(pref.getCurrentBackgroundColor());
        bouton.setBackground(pref.getCurrentForegroundColor());
        bouton.setForeground(pref.getCurrentBackgroundColor());
        text.setForeground(pref.getCurrentForegroundColor());

    }
	
	
	/**
	 * Pour modifier la police des textes à chaque fois que l'on presse F4 
	 */
	public void changeSize(){
		Font f = Preferences.getData().getCurrentFont();
		themesList.setFont(f);
        difficultesList.setFont(f);
        theme.setFont(f);
        difficulte.setFont(f);
        text.setFont(f);
	}

    public class GiraffeListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            try {
                giraffe = ImageIO.read(new File("../ressources/images/girafe.jpg"));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            JButton source = (JButton) e.getSource();
            source.setIcon(new ImageIcon(giraffe));
            requestFocus();
        }
    }
}
