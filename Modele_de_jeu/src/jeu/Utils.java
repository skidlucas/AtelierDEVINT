package jeu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import devintAPI.Preferences;
import jeu.model.Profil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 05/04/2015.
 */
public class Utils {

    public static Gson parser = new Gson();

    public static List<Profil> chargeJson() {
        String res = new String();
        String ligne = new String();
        List<Profil> tmp = new ArrayList<Profil>();
        try {
            BufferedReader buf = new BufferedReader(new FileReader("../ressources/profils.json"));
            while ((ligne = buf.readLine()) != null) {
                res += ligne;
            }
            buf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.lang.reflect.Type type = (new TypeToken<ArrayList<Profil>>() {
        }).getType();
        tmp = parser.fromJson(res, type);
        return tmp;
    }

    public static void writeJson(List<Profil> allProfils){
        String res = parser.toJson(allProfils);
        try {
            PrintWriter out = new PrintWriter (new BufferedWriter(new FileWriter("../ressources/profils.json")));
            out.println(res);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Profil getProfilFromName(String name) {
        List<Profil> allProfiles = Utils.chargeJson();
        for (Profil profil : allProfiles) {
            if (profil.getName().equals(name))
                return profil;
        }
        return null;
    }

    public static void changeParam(Profil profil) {
        String couleur = profil.getCouleur();
        String taille = profil.getTaille();
        Preferences pref = Preferences.getData();

        switch (couleur){
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
        pref.changeColor();
        switch (taille){
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
        pref.changeSize();
    }
}
