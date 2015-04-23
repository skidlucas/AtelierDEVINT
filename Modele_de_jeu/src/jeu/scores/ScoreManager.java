package jeu.scores;

import jeu.Utils;

import java.util.Collections;
import java.util.List;

/**
 * Created by user on 23/04/2015.
 */
public class ScoreManager {
    private static final String filename = "../ressources/scores.json";
    private List<Score> scores;

    public ScoreManager() {
        scores = Utils.chargeJsonScores();
        trier(scores);
    }

    public void add(Score score) {
        scores.add(score);
        trier(scores);
        Utils.writeJson(scores);
    }

    public void trier(List<Score> listScores) {
        Collections.sort(listScores);
    }
}
