package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    int levelId = 0;

    public List<Level> getCustomLevels() {
        return customLevels;
    }

    public List<Level> getOfficialLevels() {
        return officialLevels;
    }

    ArrayList<Level> officialLevels;
    ArrayList<Level> customLevels;

    public static class Level {
        File levelFile;
        String levelName;

        public Level(File file) {
            levelFile = file;
            levelName = file.getName().replaceAll(".save", "");
            levelName = levelName.replaceAll(".*_", "");
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public File getLevelFile() {
            return levelFile;
        }

        public void setLevelFile(File levelFile) {
            this.levelFile = levelFile;
        }
    }

    public LevelManager() {
        loadLevels();
    }

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

    public void nextLevel() {
        levelId++;
    }

    public Level getLevel(){
        return officialLevels.get(levelId);
    }

    public int getLevelId(){
        return levelId;
    }

    public boolean noMoreLevels() {
        return levelId == officialLevels.size();
    }
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
