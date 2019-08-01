package com.petrukhnov.source4games.auto.puzleplunder;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.basics.Debug;
import org.sikuli.script.*;

@Slf4j
public class PlayGame {

    private static App app = null;

    private final static int TILE_W = 50;
    private final static int TILE_H = 48;

    private final static int BOARD_W = 8;
    private final static int BOARD_H = 8;


    //tile values.
    private static int[][] boardTiles = null;

    private final static int TILES_TOTAL = 6;

    //tile coordinates
    private static Region[][] boardCellCoordinates = null;
    private static Region boardRegion;

    private static Tile[] tileList = null;

    private static Matcher matcher = new Matcher();


    public static void main(String[] args) {

        //Debug.setDebugLevel(3);

        app = new App("Puzzle Plunder");
        app.focus();


        //init tiles
        tileList = new Tile[TILES_TOTAL+1];
        tileList[0] = new Tile(null, 0); //undefined
        for (int i = 1; i<=TILES_TOTAL; i++){
            tileList[i] = new Tile((new Pattern(getImagePath("tile"+i+".png"))).similar(0.7f), i);
        }

        //init region locations
        Match topLeft = getMatchForImageUnsafe("board-top-left.png");
        Match bottomRight = getMatchForImageUnsafe("board-bottom-right.png");

        //with adjustments to game
        boardRegion = new Region(
                topLeft.x+20,
                topLeft.y+25,
                bottomRight.x-topLeft.x-21,
                bottomRight.y-topLeft.y-18
        );


        boardRegion.highlight(1);

        boardCellCoordinates = new Region[BOARD_W][BOARD_H];
        for (int w = 0; w<BOARD_W; w++) {
            for (int h = 0; h<BOARD_H; h++) {
                boardCellCoordinates[w][h] = new Region(boardRegion.x+(TILE_W*w), boardRegion.y+boardRegion.h-(TILE_H*(h+1)), TILE_W, TILE_H);
                //debug highlight
                //boardCellCoordinates[w][h].highlight(1);
            }
        }


        //fixme
        /*try {
            clickOnImage("tile1.png");
        } catch (FindFailed e) {
            log.error("failed find image", e);
        }*/

        while (true) {

            //check for continue
            try {
                clickOnImage("continue.png");
            } catch (FindFailed e) {
                //do nothing
            }
            //wait for board loaded
            App.pause(5);

            //do all possible swaps
            swapNext();

        }


    }

    private static String getImagePath(String filename) {
        return PlayGame.class.getClassLoader().getResource(filename).getPath();
    }

    private static void clickOnImage(String path) throws FindFailed {
        getMatchForImage(path).click();
    }

    /*private static void clickOnImageUnsafe(String path, Region region) {
        getMatchForImageUnsafe(path, region).click();
    }*/

    private static Match getMatchForImage(String path) throws FindFailed {
        return getMatchForImage(path,app.window(0));
    }

    private static Match getMatchForImage(String path, Region region) throws FindFailed {
        String filename = getImagePath(path);
        return region.find(filename);
    }

    private static Match getMatchForImage(Pattern pattern, Region region) throws FindFailed {
        return region.find(pattern);
    }

    private static Match getMatchForImageUnsafe(String path) {
        return getMatchForImageUnsafe(path, app.window(0));
    }


    private static Match getMatchForImageUnsafe(String path, Region region) {
        try {
            return getMatchForImage(path, region);
        } catch (FindFailed e) {
            log.error("failed to find image", e);
            return null;
        }
    }

    /**
     * Find and swap next possible combination.
     * return if no match
     */
    private static void swapNext() {


        clearBoard();

        log.info("START");

        //iterate over each cell
        for (int h = 0; h<BOARD_H;h++) {
            for (int w = 0; w<BOARD_W; w++) {

                //debug
                //boardCellCoordinates[w][h].highlight(1);

                //detect tile
                for (int t = 1; t<=TILES_TOTAL; t++) {



                    try {
                        Match match = getMatchForImage(tileList[t].getPattern(), boardCellCoordinates[w][h]);
                        //matched
                        //store it
                        boardTiles[w][h] = tileList[t].getType();
                        break;
                    } catch (FindFailed e){
                        //failed
                        //continue
                    }
                }

                //check if no match
                if (boardTiles[w][h] == 0) {
                    log.error("failed to detect at [" + w +","+ h + "]");
                    continue;
                }


                //check for matches with it
                Matcher.SWAP_DIRECTION swap = matcher.getSwapDirection(boardTiles, boardTiles[w][h], w, h);

                if (swap == Matcher.SWAP_DIRECTION.NONE) {
                    continue;
                }

                //boardCellCoordinates[w][h].highlight(1);
                boardCellCoordinates[w][h].click();


                switch (swap) {
                    case UP: {
                        boardCellCoordinates[w][h].above(TILE_H).click();
                        break;
                    }
                    case DOWN: {
                        boardCellCoordinates[w][h].below(TILE_H).click();
                        break;
                    }
                    case LEFT: {
                        boardCellCoordinates[w][h].left(TILE_W).click();
                        break;
                    }
                    case RIGHT: {
                        boardCellCoordinates[w][h].right(TILE_W).click();
                        break;
                    }
                }

                //if match, set w/h to zeroes
                clearBoard();
                h = 0;
                w = 0;
                //wait few seconds
                boardRegion.above().click();
                App.pause(5);




            } //for
        } //for

        log.info("end");

        //no match detected, so exit

    }



    private static void clearBoard() {
        boardTiles = new int[8][8];
    }
}
