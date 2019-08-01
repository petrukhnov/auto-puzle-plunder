package com.petrukhnov.source4games.auto.puzleplunder;

/**
 * There are 8*3 + 4*2 possible cases that allow match 3.
 *
 * E.g.
 *
 * X__
 * _XX
 *
 *
 *  x_xx
 */
public class Matcher {

    public enum SWAP_DIRECTION {NONE,UP, DOWN, LEFT, RIGHT}


    /**
     *
     * fixme: currently assume square board (w==h)
     *
     * @param board
     * @param newValue
     * @return direction of swap.
     */
    public SWAP_DIRECTION getSwapDirection(int[][] board, int newValue, int posX, int posY) {

        // __-
        if (posX-2>=0 && posY-1 >= 0 && board[posX-2][posY-1] == newValue && board[posX-1][posY-1] == newValue) {
            return SWAP_DIRECTION.DOWN;
        }

        // -__
        if (posX+2<board.length && posY-1 >= 0 && board[posX+1][posY-1] == newValue && board[posX+2][posY-1] == newValue) {
            return SWAP_DIRECTION.DOWN;
        }

        // --_
        if (posX-2>=0 && posY+1<board.length && board[posX-2][posY+1] == newValue && board[posX-1][posY+1] == newValue) {
            return SWAP_DIRECTION.UP;
        }

        // _--
        if (posX+2<board.length && posY+1<board.length && board[posX+1][posY+1] == newValue && board[posX+2][posY+1] == newValue) {
            return SWAP_DIRECTION.UP;
        }

        // ':
        if (posX+1<board.length && posY-2 >= 0 && board[posX+1][posY-1] == newValue && board[posX+1][posY-2] == newValue) {
            return SWAP_DIRECTION.RIGHT;
        }

        // :'
        if (posX-1>=0 && posY-2 >= 0 && board[posX-1][posY-1] == newValue && board[posX-1][posY-2] == newValue) {
            return SWAP_DIRECTION.LEFT;
        }


        // ,l
        if (posX+1<board.length && posY+2<board.length && board[posX+1][posY+1] == newValue && board[posX+1][posY+2] == newValue) {
            return SWAP_DIRECTION.RIGHT;
        }

        // l,
        if (posX-1>=0 && posY+2<board.length && board[posX-1][posY+1] == newValue && board[posX-1][posY+2] == newValue) {
            return SWAP_DIRECTION.LEFT;
        }

        return SWAP_DIRECTION.NONE;
    }

}
