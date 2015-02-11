package jeu;


import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import devintAPI.*;

/** Cette classe est un exemple d'interface pour les options
 * 
 */
public class FenetreSimple extends FenetreAbstraite {

    public FenetreSimple(String title) {
    	super(title);
    }
    
    // un label
	// est une variable d'instance car il doit être accessible 
	// dans la méthode changeColor, qui gère les préférences
	private JTextArea lb1;
    
	// renvoie le fichier wave contenant le message d'accueil
	protected  String wavAccueil() {
		return "../ressources/sons/accueilSimple.wav";
	}
	
	// renvoie le fichier wave contenant la règle du jeu
	protected  String wavRegleJeu() {
		return "../ressources/sons/aideF1.wav";
	}
	
	// renvoie le fichier wave contenant la règle du jeu
	protected  String wavAide() {
		return "../ressources/sons/aide.wav";
	}

    public void init() {
    	String text = "FENETRE SIMPLE\n\n";
    	text+= "En héritant de FenetreAbstraite vous bénéficiez de la gestion des touches, ";
    	text+="ESC pour sortir, F1 pour la règle du jeu, F2 l'aide, F3 pour la gestion des couleurs,";
    	text+="et F4 pour la gestion des polices de caractères\n\n";
    	text += "Voir le fichier FenetreSimple et la fonction changeColor()";
    	lb1 = new JTextArea (text);
    	lb1.setLineWrap(true);
    	lb1.setEditable(false);
    	// met la police par défaut de DeViNT
    	lb1.setFont(Preferences.getData().getCurrentFont());
    	this.add(lb1);
    }
    
	/**
	 * Pour modifier les couleurs de fond et de premier plan
	 * Cette fonction est appelée par la fonction "changeColor" de la classe "devintAPI.Preferences"
	 * à chaque fois que l'on presse F3 
	 * 
	 * ici on décide que le changement des couleurs s'applique sur le label lbl1
	 **/
	public  void changeColor() {
    	// on récupère les couleurs de base dans la classe Preferences 
		Preferences pref = Preferences.getData();
		lb1.setBackground(pref.getCurrentBackgroundColor());
		lb1.setForeground(pref.getCurrentForegroundColor());
	}
	
	/**
	 * Pour modifier la police des textes à chaque fois que l'on presse F4 
	 */
	public void changeSize(){
		Font f = Preferences.getData().getCurrentFont();
		lb1.setFont(f);
	}

}
