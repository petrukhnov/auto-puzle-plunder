package com.petrukhnov.source4games.auto.puzleplunder;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

@Slf4j
public class ImageHelper {


    public static String getImagePath(String filename) {
        return PlayGame.class.getClassLoader().getResource(filename).getPath();
    }

    public static void clickOnImage(String path, Region region) throws FindFailed {
        getMatchForImage(path, region).click();
    }

    /*private static void clickOnImageUnsafe(String path, Region region) {
        getMatchForImageUnsafe(path, region).click();
    }*/

    /*public static Match getMatchForImage(String path) throws FindFailed {
        return getMatchForImage(path,app.window(0));
    }*/

    public static Match getMatchForImage(String path, Region region) throws FindFailed {
        String filename = getImagePath(path);
        return region.find(filename);
    }

    public static Match getMatchForImage(Pattern pattern, Region region) throws FindFailed {
        return region.find(pattern);
    }

    /*public static Match getMatchForImageUnsafe(String path) {
        return getMatchForImageUnsafe(path, app.window(0));
    }*/


    public static Match getMatchForImageUnsafe(String path, Region region) {
        try {
            return getMatchForImage(path, region);
        } catch (FindFailed e) {
            log.error("failed to find image", e);
            return null;
        }
    }
}
