package jeu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
            BufferedReader buf = new BufferedReader(new FileReader("../ressources/json/profils.json"));
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
            PrintWriter out = new PrintWriter (new BufferedWriter(new FileWriter("../ressources/json/profils.json")));
            out.println(res);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
