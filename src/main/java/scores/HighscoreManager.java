package scores;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class HighscoreManager {
    public static class Highscore {
        private String playerName;
        private int score;
        private int lvlReached;

        public Highscore(String playerName, int score, int lvlReached) {
            this.playerName = playerName;
            this.score = score;
            this.lvlReached = lvlReached;
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getLvlReached() {
            return lvlReached;
        }

        public void setLvlReached(int lvlReached) {
            this.lvlReached = lvlReached;
        }
    }

    private ArrayList<Highscore> highscores;

    public HighscoreManager() {
        // no stuff needed
    }

    public void loadHighscores() {
        highscores = new ArrayList<>();
        Scanner sc;
        try {
            sc = new Scanner(new File("src/highscores.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (sc.hasNextLine()) {
            String[] tokens = sc.nextLine().split(" ");
            Highscore hs = new Highscore(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
            highscores.add(hs);
        }
        sc.close();
        highscores.sort(Comparator.comparingInt(h -> h.score));
        Collections.reverse(highscores);
    }
    public void saveHighscores() throws IOException {
        FileWriter fw = new FileWriter("src/highscores.txt");
        highscores.sort(Comparator.comparingInt(h -> h.score));
        Collections.reverse(highscores);
        for (Highscore hs : highscores) {
            String line = hs.getPlayerName() + " " + hs.getScore() + " " + hs.getLvlReached() + "\n";
            fw.write(line);
        }
        fw.close();
    }
    public void addHighscore(String playerName, int score, int lvlReached) {
        Highscore hs = new Highscore(playerName, score, lvlReached + 1);
        highscores.add(hs);
    }

    public List<Highscore> getHighscores() {
        return highscores;
    }
}
