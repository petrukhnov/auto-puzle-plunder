package com.petrukhnov.source4games.auto.puzleplunder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SwapAction {

    public enum SWAP_ACTION {NONE, UP, DOWN, LEFT, RIGHT}

    private int x;
    private int y;
    private SWAP_ACTION action;

}
