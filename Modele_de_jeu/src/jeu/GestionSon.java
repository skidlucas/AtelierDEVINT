package jeu;

import javax.swing.*;
import javax.swing.border.LineBorder;

import devintAPI.FenetreAbstraite;
import devintAPI.Preferences;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/** Cette classe montre comment utiliser la synthèse vocale
 * @author helene
 *
 */

public class GestionSon extends FenetreAbstraite implements ActionListener{

	// le bouton 1
	// est une variable d'instance car il doit être accessible 
	// dans la méthode actionPerformed 
	// on lit un long texte avec playText quand on l'active
	private JButton question1;
	
	// le bouton 2
	// on lit un wav avec playWav quand on l'active
	private JButton question2;

	// le bouton 3
	// on lit un calcul avec playText quand on l'active
	private JButton question3;

	
	// un label
	// est une variable d'instance car il doit être accessible 
	// dans la méthode changeColor, qui gère les préférences
	private JTextArea lb1;
	
	// appel au constructeur de la classe mère
    public GestionSon(String title) {
    	super(title);
     }
    
	// renvoie le fichier wave contenant le message d'accueil
	protected  String wavAccueil() {
		return "../ressources/sons/accueilSon.wav";
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
    protected void init() {
    	setLayout(new BorderLayout());
 
    	// premier label
    	// ce label est géré par les préférences (cf méthode changeColor)
    	String text = "Exemple pour utiliser le lecteur de texte.\n\n";
    	text += "En héritant de \"FenetreAbstraite\", la variable voix permet de lire des textes.\n";
    	text += "Quand le texte est court et fixe il vaut mieux enregistrer le texte et lire le wave avec voix.playWav.\n"; 
    	text += "Quand le texte est long et variable comme par exemple le texte d'une question,";
    	text += " vous pouvez lire le texte directement avec voix.playText.\n\n";
    	text += "Le deuxième paramètre à TRUE indique qu'on ne peut pas interrompre la lecture.\n\n";
    	text+="Le code source est dans jeu.GestionSon.java";
     	lb1 = new JTextArea (text); 
    	lb1.setLineWrap(true);
    	lb1.setEditable(false);
    	lb1.setFont(new Font("Georgia",1,30));
    	// on récupère les couleurs de base dans la classe Preferences 
		Preferences pref = Preferences.getData();
		Color foregroundColor = pref.getCurrentForegroundColor();
		Color backgroundColor = pref.getCurrentBackgroundColor();
		lb1.setBackground(backgroundColor);
		lb1.setForeground(foregroundColor);
    	this.add(lb1,BorderLayout.CENTER);


    	// bouton pour poser une question
    	question1 = new JButton();
    	question1.setText("Cliquez pour lire la question fixe courte\n(non interruptible");
    	// c'est l'objet Jeu lui-même qui réagit au clic souris
       	question1.addActionListener(this);
    	// on met le bouton à droite
     	this.add(question1,BorderLayout.EAST);
     	
       	// bouton pour poser une question
    	question2 = new JButton();
    	question2.setText("Cliquez pour lire la question avec texte long (interruptible)");
     	// c'est l'objet Jeu lui-même qui réagit au clic souris
       	question2.addActionListener(this);
    	// on met le bouton à droite
     	this.add(question2,BorderLayout.WEST);
     	
       	// bouton pour une opération arithmétique aléatoire
    	question3 = new JButton();
    	question3.setText("Cliquez pour lire la question de calcul");
     	// c'est l'objet Jeu lui-même qui réagit au clic souris
       	question3.addActionListener(this);
    	// on met le bouton à droite
     	this.add(question3,BorderLayout.SOUTH);
   }

    // lire la question si clic sur le bouton 
    public void actionPerformed(ActionEvent ae){
       	// toujours stopper la voix avant de parler
    	voix.stop();
    	// on récupère la source de l'évènement
     	Object source = ae.getSource();
     	// on lit la question
    	if (source.equals(question2)) {
    		String text = "Si la question est longue et que son contenu est variable";
    		text += "par exemple si on lit la question dans un fichier texte";
    		text += "il faut lire directement le texte avec playText";
    		// texte long, interruptible car un seul paramètre
    		voix.playText(text);
    	}
    	else if (source.equals(question1)) {
    		// on lit le wav, non interruptible car (true)
    		voix.playWav("../ressources/sons/questionCourte.wav",true);
    	}
        else if (source.equals(question3)) {
        	// on tire deux nombre aléatoires et on lit l'opération
        	Random r = new Random();
        	int x = r.nextInt(100000);
        	int y = r.nextInt(30);
        	String text = x + " + " + y + " = ";
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
		lb1.setBackground(pref.getCurrentBackgroundColor());
		lb1.setForeground(pref.getCurrentForegroundColor());
	}

	@Override
	public void changeSize() {
		// TODO Auto-generated method stub
	}

}
