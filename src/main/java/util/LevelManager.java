
package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * LevelManager class provides functionalities to manage and retrieve levels.
 */
public class LevelManager {

/**
 * Represents the current level index.
 */
int levelId = 0;

/**
 * Retrieves the list of custom levels.
 *
 * @return a list of custom levels
 */
public List<Level> getCustomLevels() {
        return customLevels;
    }

/**
 * Retrieves the list of official levels.
 *
 * @return a list of official levels
 */
public List<Level> getOfficialLevels() {
        return officialLevels;
    }

/**
 * Stores the list of official levels.
 */
ArrayList<Level> officialLevels;

/**
 * Stores the list of custom levels.
 */
ArrayList<Level> customLevels;

/**
 * Represents a game level, which consists of a name and a file.
 */
public static class Level {

/**
 * The file associated with the level.
 */
File levelFile;

/**
 * The name of the level.
 */
String levelName;

/**
 * Constructs a new level using the provided file.
 *
 * @param file the file associated with the level
 */
public Level(File file) {
            levelFile = file;
            levelName = file.getName().replaceAll(".save", "");
            levelName = levelName.replaceAll(".*_", "");
        }

/**
 * Gets the name of the level.
 *
 * @return the level name
 */
public String getLevelName() {
            return levelName;
        }

/**
 * Sets the name of the level.
 *
 * @param levelName the new name for the level
 */
public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

/**
 * Gets the file associated with the level.
 *
 * @return the level file
 */
public File getLevelFile() {
            return levelFile;
        }

/**
 * Sets the file associated with the level.
 *
 * @param levelFile the new file for the level
 */
public void setLevelFile(File levelFile) {
            this.levelFile = levelFile;
        }
    }

/**
 * Constructs a LevelManager instance and loads the levels.
 */
public LevelManager() {
        loadLevels();
    }

/**
 * Loads official and custom levels from the file system.
 */
public void loadLevels() {
        officialLevels = new ArrayList<>();
        customLevels = new ArrayList<>();

        File[] official = new File("src/levels/official").listFiles();
        assert official != null;
        for (File f : official) {
            Level level = new Level(f);
            officialLevels.add(level);
        }

        File[] custom = new File("src/levels/custom").listFiles();
        assert custom != null;
        for (File f : custom) {
            Level level = new Level(f);
            customLevels.add(level);
        }
    }

/**
 * Advances to the next level.
 */
public void nextLevel() {
        levelId++;
    }

/**
 * Retrieves the current level based on the levelId.
 *
 * @return the current level
 */
public Level getLevel(){
        return officialLevels.get(levelId);
    }

/**
 * Gets the current level id.
 *
 * @return the id of the current level
 */
public int getLevelId(){
        return levelId;
    }

/**
 * Checks if there are no more levels to play.
 *
 * @return {@code true} if all levels have been played, {@code false} otherwise
 */
public boolean noMoreLevels() {
        return levelId == officialLevels.size();
    }
/**
 * Finds a level by its name.
 *
 * @param levelName the name of the level to search for
 * @return the found level, or {@code null} if no level with the specified name exists
 */
public Level getLevelByName(String levelName) {
        for (Level level : officialLevels) {
            if (level.getLevelName().equals(levelName)) {
                return level;
            }
        }
        for (Level level : customLevels) {
            if (level.getLevelName().equals(levelName)) {
                return level;
            }
        }
        return null;
    }

}
