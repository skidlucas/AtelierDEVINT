/*
* Copyright 2007-2011, HÃ©lÃ¨ne Collavizza, Jean-Paul Stromboni
* 
* This file is part of project 'Modele_de_Jeu'
* 
* 'Modele_de_Jeu' is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* 'Modele_de_Jeu'is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.
* 
* You should have received a copy of the GNU Lesser General Public License
* along with 'Modele_de_Jeu'. If not, see <http://www.gnu.org/licenses/>.
*/
package jeu;

import javax.swing.*;
import javax.swing.border.LineBorder;

import devintAPI.FenetreAbstraite;
import devintAPI.Preferences;
import jeu.scores.Score;
import jeu.scores.ScoreManager;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Cette classe est un exemple d'utilisation d'un fichier
 * 
 * @author helene
 * @author Jean-Paul, mars 2011
 */

public class FichierScore extends FenetreAbstraite{

	private JButton quitter; // Le bouton pour quitter
    private JPanel lb1;
    private JLabel presNom, presScore, presTime; // Titre des colonnes
    private List<JLabel> labelsScores;

	// appel au constructeur de la classe mère
    public FichierScore(String title) {
    	super(title);
        init();
     }

    // définition de la méthode abstraite "init()"
    // initialise le frame 
    protected void init() {
    	// BorderLayout, voir http://java.sun.com/docs/books/tutorial/uiswing/layout/border.html
    	setLayout(new BorderLayout());

        labelsScores = new ArrayList<>();
     	lb1 = new JPanel();
    	lb1.setFont(new Font("Georgia", 1, 30));
        lb1.setLayout(new GridLayout(11, 3));
        presNom = new JLabel("Nom");
        presNom.setFont(new Font("Georgia", 1, 30));
        presNom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        presScore = new JLabel("Score");
        presScore.setFont(new Font("Georgia", 1, 30));
        presScore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        presTime = new JLabel("Temps");
        presTime.setFont(new Font("Georgia", 1, 30));
        presTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        List<Score> allScores = Utils.chargeJsonScores();
        lb1.add(presNom);
        lb1.add(presScore);
        lb1.add(presTime);

    	// on place le premier composant en bas
    	this.add(lb1,BorderLayout.CENTER);

     	Preferences pref = Preferences.getData();
        lb1.setBackground(pref.getCurrentBackgroundColor());
        lb1.setForeground(pref.getCurrentForegroundColor());
        presNom.setForeground(pref.getCurrentForegroundColor());
        presScore.setForeground(pref.getCurrentForegroundColor());
        presTime.setForeground(pref.getCurrentForegroundColor());
        presNom.setBorder(new LineBorder(pref.getCurrentForegroundColor(), 5));
        presTime.setBorder(new LineBorder(pref.getCurrentForegroundColor(), 5));
        presScore.setBorder(new LineBorder(pref.getCurrentForegroundColor(), 5));


        int maxLength = allScores.size();
        if (maxLength > ScoreManager.NB_MAX_SCORES) maxLength = ScoreManager.NB_MAX_SCORES;
        for (int i = 0; i < maxLength; i++) {
            JLabel currentLabelName = new JLabel(allScores.get(i).getName());
            JLabel currentLabelScore = new JLabel(Integer.toString(allScores.get(i).getNbPoint()));
            JLabel currentLabelTime = new JLabel(allScores.get(i).getTime());
            currentLabelName.setFont(new Font("Georgia", 1, 30));
            currentLabelTime.setFont(new Font("Georgia", 1, 30));
            currentLabelScore.setFont(new Font("Georgia", 1, 30));

            currentLabelName.setForeground(pref.getCurrentForegroundColor());
            currentLabelTime.setForeground(pref.getCurrentForegroundColor());
            currentLabelScore.setForeground(pref.getCurrentForegroundColor());

            currentLabelTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            currentLabelScore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            currentLabelName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

            labelsScores.addAll(Arrays.asList(currentLabelName, currentLabelScore, currentLabelTime));
            lb1.add(currentLabelName);
            lb1.add(currentLabelScore);
            lb1.add(currentLabelTime);
        }

        if (maxLength < ScoreManager.NB_MAX_SCORES) {
            for (int i = maxLength; i < ScoreManager.NB_MAX_SCORES; i++) {
                JLabel currentLabelName = new JLabel();
                JLabel currentLabelScore = new JLabel();
                JLabel currentLabelTime = new JLabel();
                currentLabelTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                currentLabelScore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                currentLabelName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

                labelsScores.addAll(Arrays.asList(currentLabelName, currentLabelScore, currentLabelTime));
                lb1.add(currentLabelName);
                lb1.add(currentLabelScore);
                lb1.add(currentLabelTime);
            }
        }

      	// bouton pour lancer la lecture dans le fichier
    	quitter = new JButton();
    	quitter.setText("Quitter la page des scores");
    	quitter.setBackground(pref.getCurrentForegroundColor());
        quitter.setForeground(pref.getCurrentBackgroundColor());
    	quitter.setFont(new Font("Georgia",1,40));
     	// c'est l'objet Jeu lui-même qui réagit au clic souris
    	quitter.addActionListener(new CloseListener());
    	// on met le bouton en haut
     	this.add(quitter,BorderLayout.SOUTH);
     	
   }

	@Override
	public void changeColor() {
        Preferences currentPref = Preferences.getData();
        quitter.setBackground(currentPref.getCurrentForegroundColor());
        quitter.setForeground(currentPref.getCurrentBackgroundColor());
        lb1.setBackground(currentPref.getCurrentBackgroundColor());
        lb1.setForeground(currentPref.getCurrentForegroundColor());
        presNom.setForeground(currentPref.getCurrentForegroundColor());
        presScore.setForeground(currentPref.getCurrentForegroundColor());
        presTime.setForeground(currentPref.getCurrentForegroundColor());
        for (JLabel label : labelsScores) {
            label.setForeground(currentPref.getCurrentForegroundColor());
        }
        presNom.setBorder(new LineBorder(currentPref.getCurrentForegroundColor(), 5));
        presTime.setBorder(new LineBorder(currentPref.getCurrentForegroundColor(), 5));
        presScore.setBorder(new LineBorder(currentPref.getCurrentForegroundColor(), 5));
	}

    private class CloseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
	
	@Override
	public void changeSize() {
        Font f = Preferences.getData().getCurrentFont();
        lb1.setFont(f);
        quitter.setFont(f);
        presNom.setFont(f);
        presScore.setFont(f);
        presTime.setFont(f);
	}

	// renvoie le fichier wave contenant le message d'accueil
	protected  String wavAccueil() {
		return "../ressources/sons/accueilFichier.wav";
	}
	
	// renvoie le fichier wave contenant la règle du jeu
	protected  String wavRegleJeu() {
		return "../ressources/sons/accueilFichier.wav";
	}
	
	// renvoie le fichier wave contenant la règle du jeu
	protected  String wavAide() {
		return "../ressources/sons/aide.wav";
	}

}
