package jeu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jeu.model.Profil;
import jeu.scores.Score;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 05/04/2015.
 */
public class Utils {
    public static final String profilFilename = "../ressources/profils.json";
    public static final String scoresFilename = "../ressources/scores.json";
    public static Gson parser = new Gson();

    public static List<Profil> chargeJsonProfil() {
        String res = new String();
        String ligne;
        List<Profil> tmp;
        try {
            BufferedReader buf = new BufferedReader(new FileReader(profilFilename));
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

    public static List<Score> chargeJsonScores() {
        String res = new String();
        String ligne;
        List<Score> tmp;
        try {
            BufferedReader buf = new BufferedReader(new FileReader(scoresFilename));
            while ((ligne = buf.readLine()) != null) {
                res += ligne;
            }
            buf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.lang.reflect.Type type = (new TypeToken<ArrayList<Score>>() {
        }).getType();
        tmp = parser.fromJson(res, type);
        return tmp;
    }

    public static void writeJson(List<?> list, String filename){
        String res = parser.toJson(list);
        try {
            PrintWriter out = new PrintWriter (new BufferedWriter(new FileWriter(filename)));
            out.println(res);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
