package jeu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jeu.model.Profil;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/** classe pour lancer le jeu
 * Elle crée simplement une instance de MenuJeu
 * 
 * @author helene
 *
 */
public class LancementJeu{

    public static void main(String args[]){
         new jeu.MenuJeu("PolyCards");
    }

}
