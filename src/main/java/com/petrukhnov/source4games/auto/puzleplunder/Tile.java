package com.petrukhnov.source4games.auto.puzleplunder;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sikuli.script.Pattern;

@Data
@AllArgsConstructor
public class Tile {

    private Pattern pattern;
    private int type;
}
