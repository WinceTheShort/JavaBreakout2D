package scores;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Manages the highscores by loading from and saving to a file.
 */
public class HighscoreManager {

    /**
     * Represents a highscore entry with player name, score, and level reached.
     */
    public static class Highscore {
        private String playerName;
        private int score;
        private int lvlReached;

    /**
     * Creates a new highscore entry.
     *
     * @param playerName the name of the player
     * @param score the score achieved by the player
     * @param lvlReached the level reached by the player
     */
    public Highscore(String playerName, int score, int lvlReached) {
            this.playerName = playerName;
            this.score = score;
            this.lvlReached = lvlReached;
        }

    /**
     * Gets the name of the player.
     *
     * @return the name of the player
     */
    public String getPlayerName() {
            return playerName;
        }

    /**
     * Sets the name of the player.
     *
     * @param playerName the name to set
     */
    public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

    /**
     * Gets the score of the player.
     *
     * @return the player's score
     */
    public int getScore() {
            return score;
        }

    /**
     * Sets the score of the player.
     *
     * @param score the score to set
     */
    public void setScore(int score) {
            this.score = score;
        }

    /**
     * Gets the level reached by the player.
     *
     * @return the level reached
     */
    public int getLvlReached() {
            return lvlReached;
        }

    /**
     * Sets the level reached by the player.
     *
     * @param lvlReached the level to set
     */
    public void setLvlReached(int lvlReached) {
            this.lvlReached = lvlReached;
        }
    }

    private ArrayList<Highscore> highscores;

    /**
     * Initializes a new instance of the HighscoreManager.
     */
    public HighscoreManager() {
        loadHighscores();
    }

    /**
     * Loads highscores from a file.
     */
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
    /**
     * Saves highscores to a file.
     *
     * @throws IOException if an I/O error occurs
     */
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
    /**
     * Adds a new highscore.
     *
     * @param playerName the name of the player
     * @param score the score of the player
     * @param lvlReached the last level reached by the player
     */
    public void addHighscore(String playerName, int score, int lvlReached) {
        Highscore hs = new Highscore(playerName, score, lvlReached + 1);
        highscores.add(hs);
    }

    /**
     * Gets the list of highscores.
     *
     * @return the list of highscores
     */
    public List<Highscore> getHighscores() {
        return highscores;
    }
}
