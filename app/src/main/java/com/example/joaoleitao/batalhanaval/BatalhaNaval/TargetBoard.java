package com.example.joaoleitao.batalhanaval.BatalhaNaval;

import android.graphics.Canvas;
import android.support.graphics.drawable.VectorDrawableCompat;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.BoardElement;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.Target;
import com.example.joaoleitao.batalhanaval.R;

import java.util.ArrayList;
import java.util.List;

public class TargetBoard extends Board {


    public TargetBoard(BoardType boardType, Game parent, Coord size) {
        super(boardType, parent, size);
    }

    public TargetBoard(BoardType boardType, Game parent, int size) {
        super(boardType, parent, size);
    }


    @Override
    public void draw(Canvas canvas, float cellsize, int MAX) {
        super.draw(canvas, cellsize, MAX);
        drawTargets(canvas, cellsize);
    }

    private void drawTargets(Canvas canvas, float cellsize) {

        for (BoardElement element : boardElementsList) {
            element.draw(canvas, Math.round(cellsize));
        }
    }

    public void addTarget(Target target) {
        target.setBoard(this);
        boardElementsList.add(target);
    }
}
