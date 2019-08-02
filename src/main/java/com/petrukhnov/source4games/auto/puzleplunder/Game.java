package com.petrukhnov.source4games.auto.puzleplunder;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.*;

@Slf4j
public class Game {

    private App app;

    private final static int TILE_W = 50;
    private final static int TILE_H = 48;

    public final static int TILES_TOTAL = 6;

    //tile coordinates
    private static Region[][] boardCellCoordinates = null;
    private static Region boardRegion;

    private static Tile[] tileList = null;

    private final static int BOARD_W = 8;
    private final static int BOARD_H = 8;

    //tile values.
    private static int[][] boardTiles = null;

    private static Matcher2 matcher = new Matcher2();

    public Game(App app) {

        this.app = app;

        //init tiles
        tileList = new Tile[TILES_TOTAL+1];
        tileList[0] = new Tile(null, 0); //undefined
        for (int i = 1; i<=TILES_TOTAL; i++){
            tileList[i] = new Tile((new Pattern(ImageHelper.getImagePath("tile"+i+".png"))).similar(0.70f), i);
        }

        //init region locations
        Match topLeft = ImageHelper.getMatchForImageUnsafe("board-top-left.png", app.window(0));
        Match bottomRight = ImageHelper.getMatchForImageUnsafe("board-bottom-right.png", app.window(0));

        //with adjustments to game
        boardRegion = new Region(
                topLeft.x+20,
                topLeft.y+25,
                bottomRight.x-topLeft.x-21,
                bottomRight.y-topLeft.y-18
        );

        boardTiles = new int[BOARD_W][BOARD_H];


        boardRegion.highlight(1);

        boardCellCoordinates = new Region[BOARD_W][BOARD_H];
        for (int w = 0; w<BOARD_W; w++) {
            for (int h = 0; h<BOARD_H; h++) {
                boardCellCoordinates[w][h] = new Region(boardRegion.x+(TILE_W*w), boardRegion.y+boardRegion.h-(TILE_H*(h+1)), TILE_W, TILE_H);
                //debug highlight
                //boardCellCoordinates[w][h].highlight(1);
            }
        }
    }

    /**
     * Find and swap next possible combination.
     * return if no match
     */
    public void swapNext() {


        clearBoard();

        log.info("START");
        //log.info("S {}", boardTiles.length);

        //iterate over each cell
        for (int h = 0; h<BOARD_H;h++) {
            for (int w = 0; w<BOARD_W; w++) {

                //log.info("w {}", boardTiles.length);

                //debug
                //boardCellCoordinates[w][h].highlight(1);

                //detect tile
                for (int t = 1; t <= TILES_TOTAL; t++) {


                    try {
                        Match match = ImageHelper.getMatchForImage(tileList[t].getPattern(), boardCellCoordinates[w][h]);
                        //matched
                        //store it
                        boardTiles[w][h] = tileList[t].getType();
                        //log.info("type {}", boardTiles.length);
                        break;
                    } catch (FindFailed e) {
                        //continue
                    }
                } //for each tile type

                if(boardTiles[w][h] == 0) {
                    //failed
                    log.error("failed to detect at [" + w + "," + h + "]");
                }
            } //for row
        } //for column

        SwapAction action = matcher.getSwapDirection(boardTiles);

        if (action.getAction().equals(SwapAction.SWAP_ACTION.NONE)) {
            log.error("no swap");
        } else {

            Region swapTileRegion = boardCellCoordinates[action.getX()][action.getY()];
            swapTileRegion.click();

            //do swap
            switch (action.getAction()) {
                case UP: {
                    swapTileRegion.above(TILE_H).click();
                    break;
                }
                case DOWN: {
                    swapTileRegion.below(TILE_H).click();
                    break;
                }
                case LEFT: {
                    swapTileRegion.left(TILE_W).click();
                    break;
                }
                case RIGHT: {
                    swapTileRegion.right(TILE_W).click();
                    break;
                }
            }


            //if match, set w/h to zeroes
            clearBoard();
            //wait few seconds
            boardRegion.right().click();
            App.pause(4);

        }
        log.info("END");

    }


    private void clearBoard() {
        //bigger board for simpler match calculations
        boardTiles = new int[BOARD_W][BOARD_H];
    }


}
