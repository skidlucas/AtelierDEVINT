/*  Classe de menu de lancement de l'exemple de jeu.
 *  Cette classe hérite de la classe abstraite MenuAbstrait en définissant les méthodes :
 *     - nomOptions qui renvoie la liste des options possibles pour le menu 
 *     - lancerOption qui associe une action à chaque option du menu
 *     - wavAccueil() qui renvoie le nom du fichier wav lu lors de l'accueil dans le menu
 *     - wavAide() qui renvoie le nom du fichier wav lu lors de l'activation de la touche F1
 */

package jeu; 

import devintAPI.MenuAbstrait;

import javax.swing.*;

public class MenuJeu extends MenuAbstrait {

	/** constructeur
	 * @param title : le nom du jeu 
	 */
	public MenuJeu(String title) {
		super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	/** renvoie le nom des options du menu
     * vous pouvez définir autant d'options que vous voulez
     **/
	protected String[] nomOptions() {
		String[] noms = {"Jeu Solo","Jeu en Multi","Options","Scores", "Quitter"};
		return noms;
	}

	/** lance l'action associée au bouton
	 * la numérotation est celle du tableau renvoyé par nomOption
	 */
	protected void lancerOption(int i) {
        switch (i){
            case 0 : new PresJeuSolo(nomJeu);break;
            case 1 : new PresJeuMulti(nomJeu);break;
            case 2 : new Options("Options");break;
            case 3 : new FichierScore("Meilleurs Scores");break;
            case 4 : System.exit(0);
		default: System.err.println("action non définie");
		}
	} 

	// renvoie le fichier wave contenant le message d'accueil
	// ces fichiers doivent être placés dans ressources/sons/
	protected  String wavAccueil() {
		return "../ressources/sons/accueil.wav";
	}

	// renvoie le fichier wave contenant la règle du jeu
	protected  String wavRegleJeu() {
		return "../ressources/sons/accueil.wav";
	}
	
}
