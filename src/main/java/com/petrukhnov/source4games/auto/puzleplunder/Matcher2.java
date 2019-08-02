package com.petrukhnov.source4games.auto.puzleplunder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Matcher2 {

    private static final int SIZE_INCREASE = 3;

    /**
     *
     * fixme: currently assume square board (w==h)
     *
     * @param originalBoard
     * @return direction of swap.
     */
    public SwapAction getSwapDirection(int[][] originalBoard) {

        //copy data to bigger array to make code more readable (or less)
        //this will eliminate need to check for array bounds.
        // append 3*8 cells to each side
        int [][] board = new int[originalBoard.length+SIZE_INCREASE+SIZE_INCREASE][originalBoard.length+SIZE_INCREASE+SIZE_INCREASE];

        //copy values
        for(int i=0; i<originalBoard.length; i++) {
            for (int j = 0; j < originalBoard[i].length; j++) {
                board[i+SIZE_INCREASE][j+SIZE_INCREASE] = originalBoard[i][j];
            }
        }


        //iterate over each cell, to find match
        for (int h = SIZE_INCREASE; h<board.length-SIZE_INCREASE;h++) {
            for (int w = SIZE_INCREASE; w<board.length-SIZE_INCREASE; w++) {

                //if value undetected - skip
                if(board[w][h] == 0) {
                    continue;
                }

                SwapAction action = checkMatch(board, w, h);
                //convert result
                if (!action.getAction().equals(SwapAction.SWAP_ACTION.NONE)) {
                    //adjust row and column
                    action.setX(action.getX()-SIZE_INCREASE);
                    action.setY(action.getY()-SIZE_INCREASE);
                    //log.info("do swap");
                    return action;
                } else {
                    // else continue
                    //log.info("no swap");
                }

            }
        }

        //if nothing
        return new SwapAction(-1, -1, SwapAction.SWAP_ACTION.NONE);
    }


    /**
     *
     *
     * @param board
     * @param posX
     * @param posY
     * @return
     */
    private SwapAction checkMatch(int[][] board, int posX, int posY) {

        int newValue = board[posX][posY];

        log.info("-");
        log.info("val: {}", newValue);

        /*
         * xx X
         */
        if (board[posX-3][posY] == newValue && board[posX-2][posY] == newValue) {
            log.info("xx X");
            log.info("posX, posY");
            return new SwapAction(posX, posY, SwapAction.SWAP_ACTION.LEFT);
        }

        /*
         * x xX
         */
        if (board[posX-3][posY] == newValue && board[posX-1][posY] == newValue) {
            log.info("x xX");
            log.info("posX-3, posY");
            return new SwapAction(posX-3, posY, SwapAction.SWAP_ACTION.RIGHT);
        }

        /*
         * X
         *
         * x
         * x
         */
        if (board[posX][posY-2] == newValue && board[posX][posY-3] == newValue) {
            log.info("X");
            log.info("");
            log.info("x");
            log.info("x");
            log.info("posX, posY");
            return new SwapAction(posX, posY, SwapAction.SWAP_ACTION.DOWN);
        }

        /*
         * X
         * x
         *
         * x
         */
        if (board[posX][posY-1] == newValue && board[posX][posY-3] == newValue) {
            log.info("X");
            log.info("x");
            log.info("");
            log.info("x");
            log.info("posX, posY-3");
            return new SwapAction(posX, posY-3, SwapAction.SWAP_ACTION.UP);
        }


        /*
         * x X
         *  x
         */
        if (board[posX-2][posY] == newValue && board[posX-1][posY-1] == newValue) {
            log.info("x X");
            log.info(" x");
            log.info("posX-1, posY-1");
            return new SwapAction(posX-1, posY-1, SwapAction.SWAP_ACTION.UP);
        }


        /*
         *  X
         * x x
         */
        if (board[posX-1][posY-1] == newValue && board[posX+1][posY-1] == newValue) {
            log.info(" X");
            log.info("x x");
            log.info("posX, posY");
            return new SwapAction(posX, posY, SwapAction.SWAP_ACTION.DOWN);
        }


        /*
         * X
         *  x
         * x
         */
        if (board[posX+1][posY-1] == newValue && board[posX][posY-2] == newValue) {
            log.info("X");
            log.info(" x");
            log.info("x");
            log.info("posX+1, posY-1");
            return new SwapAction(posX+1, posY-1, SwapAction.SWAP_ACTION.LEFT);
        }

        /*
         *  X
         * x
         *  x
         */
        if (board[posX-1][posY-1] == newValue && board[posX][posY-2] == newValue) {
            log.info(" X");
            log.info("x");
            log.info(" x");
            log.info("posX-1, posY-1");
            return new SwapAction(posX-1, posY-1, SwapAction.SWAP_ACTION.RIGHT);
        }




        /*
         *   X
         * xx
         */
        if (board[posX-2][posY-1] == newValue && board[posX-1][posY-1] == newValue) {
            log.info("  X");
            log.info("xx");
            log.info("posX, posY");
            return new SwapAction(posX, posY, SwapAction.SWAP_ACTION.DOWN);
        }


        /*
         * xX
         *   x
         */
        if (board[posX-1][posY] == newValue && board[posX+1][posY-1] == newValue) {
            log.info("xX");
            log.info("  x");
            log.info("posX+1, posY-1");
            return new SwapAction(posX+1, posY-1, SwapAction.SWAP_ACTION.UP);
        }


        /*
         * X
         *  xx
         */
        if (board[posX+1][posY-1] == newValue && board[posX+2][posY-1] == newValue) {
            log.info("X");
            log.info(" xx");
            log.info("posX, posY");
            return new SwapAction(posX, posY, SwapAction.SWAP_ACTION.DOWN);
        }


        /*
         *  xX
         * x
         */
        if (board[posX-2][posY-1] == newValue && board[posX-1][posY] == newValue) {
            log.info(" xX");
            log.info("x");
            log.info("posX-2, posY-1");
            return new SwapAction(posX-2, posY-1, SwapAction.SWAP_ACTION.UP);
        }


        /*
         *  X
         * x
         * x
         */
        if (board[posX-1][posY-1] == newValue && board[posX-1][posY-2] == newValue) {
            log.info(" X");
            log.info("x");
            log.info("x");
            log.info("posX, posY");
            return new SwapAction(posX, posY, SwapAction.SWAP_ACTION.LEFT);
        }


        /*
         * X
         *  x
         *  x
         */
        if (board[posX+1][posY-1] == newValue && board[posX+1][posY-2] == newValue) {
            log.info("X");
            log.info(" x");
            log.info(" x");
            log.info("posX, posY");
            return new SwapAction(posX, posY, SwapAction.SWAP_ACTION.RIGHT);
        }


        /*
         * X
         * x
         *  x
         */
        if (board[posX][posY-1] == newValue && board[posX+1][posY-2] == newValue) {
            log.info("X");
            log.info("x");
            log.info(" x");
            log.info("posX+1, posY-2");
            return new SwapAction(posX+1, posY-2, SwapAction.SWAP_ACTION.LEFT);
        }

        /*
         *  X
         *  x
         * x
         */
        if (board[posX][posY-1] == newValue && board[posX-1][posY-2] == newValue) {
            log.info(" X");
            log.info(" x");
            log.info("x");
            log.info("posX-1, posY-2");
            return new SwapAction(posX-1, posY-2, SwapAction.SWAP_ACTION.RIGHT);
        }


        return new SwapAction(posX, posY, SwapAction.SWAP_ACTION.NONE);
    }
}
