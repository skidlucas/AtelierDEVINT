package jeu.scores;

import jeu.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by user on 23/04/2015.
 */
public class ScoreManager {
    private final int NB_MAX_SCORES = 10;
    private List<Score> scores;

    public ScoreManager() {
        scores = Utils.chargeJsonScores();
        trier(scores);
    }

    public void add(Score score) {
        scores.add(score);
        trier(scores);
        Utils.writeJson(scores, Utils.scoresFilename);
    }

    public void trier(List<Score> listScores) {
        Collections.sort(listScores);
        limitListScores();
    }

    private void limitListScores() {
        if (scores.size() > NB_MAX_SCORES) {
            List<Score> newScores = new ArrayList<>();
            for (int i = 0; i < NB_MAX_SCORES; i++) {
                newScores.add(scores.get(i));
            }

            scores = newScores;
        }
    }
}
