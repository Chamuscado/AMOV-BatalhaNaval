package com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships;

import android.graphics.Canvas;
import android.support.graphics.drawable.VectorDrawableCompat;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Coord;
import com.example.joaoleitao.batalhanaval.R;

import java.util.List;

public class Target extends BoardElement {
    public Target(Coord pos) {
        super(pos);
    }

    @Override
    protected Coord isValidPos(Coord newPos) {
        Coord size = board.getSize();
        if (newPos.getX() < 1)
            newPos.setX(1);
        if (newPos.getY() < 1)
            newPos.setY(1);
        if (newPos.getX() > size.getX())
            newPos.setX(size.getX());

        if (newPos.getY() > size.getY())
            newPos.setY(size.getY());
        return newPos;
    }

    @Override
    public boolean isInConflict() {
        if (board.getSquare(pos).getNumberElements() == 0)
            return true;

        List<BoardElement> elements = board.getSquare(pos).getElementsList();
        for (BoardElement element : elements) {
            if (element instanceof Target)
                return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas, int cellsize) {
        VectorDrawableCompat vector = VectorDrawableCompat.create(board.getResources(), R.drawable.target, null);
        vector.setBounds(Math.round(pos.getX() * cellsize), Math.round(pos.getY() * cellsize), Math.round((pos.getX() + 1) * cellsize), Math.round((pos.getY() + 1) * cellsize));
        vector.setTint(board.getColorTarget());
        vector.draw(canvas);
    }
}
