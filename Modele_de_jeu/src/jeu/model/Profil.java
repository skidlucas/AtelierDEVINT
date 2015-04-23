package jeu.model;

import java.io.Serializable;

/**
 * Created by Lucas SOUMILLE on 01/04/2015.
 */
public class Profil implements Serializable{

    String name;

    String couleur;

    String taille;

    int score;

    public Profil(String name, String couleur, String taille, int score) {
        this.name = name;
        this.couleur = couleur;
        this.taille = taille;
        this.score = score;
    }

    public Profil() {
        this.name = "Unknown";
        this.couleur = "Orange";
        this.taille = "Grande";
    }

    public String getName() {
        return name;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getTaille() {
        return taille;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    /*List<Profil> allProfil = new ArrayList<>();
        Profil lucas = new Profil("Lucas","Bleu", "High", 90);
        Profil pascal = new Profil("Pascal","Orange", "Small", 180);
        allProfil.add(lucas);
        allProfil.add(pascal);
        Gson parser = new Gson();
        String res = parser.toJson(allProfil);
        try {
            PrintWriter out = new PrintWriter (new BufferedWriter(new FileWriter("../ressources/json/profils.json")));
            System.out.println(res);
            out.println(res);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        res = new String();
        String ligne = null;
        try {
            BufferedReader buf = new BufferedReader(new FileReader("../ressources/json/profils.json"));
            while((ligne = buf.readLine()) != null){
                res += ligne;
            }
            buf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Profil> allProfils = new ArrayList<>();
        System.out.println(res);
        System.out.println(new TypeToken<ArrayList<Profil>>(){});
        //Type type = new TypeToken<ArrayList<Profil>>(){};
       // System.out.println(type);
        Type token = (new TypeToken<ArrayList<Profil>>(){}).getType();
        System.out.println(token);
        allProfils = parser.fromJson(res, token);
        //System.out.println(allProfils.add(new Profil("l","l","l",0)));
        Profil tmp = allProfils.get(0);
        System.out.println(allProfils.get(0).getName());*/
}
