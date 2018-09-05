package com.example.joaoleitao.batalhanaval.BatalhaNaval;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.Ship;

import java.util.ArrayList;
import java.util.List;

public class ShipsBoard extends Board {


    public ShipsBoard(BoardType boardType, Game parent, Coord size) {
        super(boardType, parent, size);
    }

    public ShipsBoard(BoardType boardType, Game parent, int size) {
        super(boardType, parent, size);
    }

    void addShip(Ship ship) {
        ship.setBoard(this);
        boardElementsList.add(ship);
    }

}
