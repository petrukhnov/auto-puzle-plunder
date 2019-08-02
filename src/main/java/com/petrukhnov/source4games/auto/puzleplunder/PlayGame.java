package com.petrukhnov.source4games.auto.puzleplunder;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.*;

@Slf4j
public class PlayGame {

    private static App app = null;

    public static void main(String[] args) {

        //Debug.setDebugLevel(3);

        app = new App("Puzzle Plunder");
        app.focus();


       Game game = new Game(app);


        while (true) {

            //check for continue
            try {
                ImageHelper.clickOnImage("continue.png", app.window(0));
            } catch (FindFailed e) {
                //do nothing
            }
            //wait for board loaded
            App.pause(2);

            //do all possible swaps
            game.swapNext();

        }


    }


}
