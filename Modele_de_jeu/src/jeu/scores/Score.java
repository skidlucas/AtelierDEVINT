package jeu.scores;

/**
 * Created by user on 23/04/2015.
 */
public class Score implements Comparable {
    private String name;
    private Integer nbPoint;
    private String time;

    public Score(String name, int nbPoint, String time) {
        this.name = name;
        this.nbPoint = nbPoint;
        this.time = time;
    }

    @Override
    public int compareTo(Object o) {
        Score s = (Score)o;
        if (nbPoint == s.getNbPoint()) {
            return time.compareTo(s.getTime());
        }

        return nbPoint.compareTo(s.getNbPoint());
    }

    public String getName() {
        return name;
    }

    public int getNbPoint() {
        return nbPoint;
    }

    public String getTime() {
        return time;
    }
}
