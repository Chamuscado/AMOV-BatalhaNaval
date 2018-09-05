package com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships;

import android.graphics.Canvas;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Board;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Coord;

import java.io.Serializable;

public abstract class BoardElement implements Serializable {
     Coord pos;
     Board board;

    public BoardElement(Coord pos) {
        this.pos = pos;
    }

    public void setPos(Coord pos) {
        if (!board.isReady())
            this.pos = isValidPos(pos);
    }

    public Coord getPos() {
        return pos;
    }

    protected abstract Coord isValidPos(Coord newPos);

    public abstract boolean isInConflict();

    public abstract void draw(Canvas canvas, int cellsize);

    public void setBoard(Board board) {
        this.board = board;
        setPos(pos);
    }
}
