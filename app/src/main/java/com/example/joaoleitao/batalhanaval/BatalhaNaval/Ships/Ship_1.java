package com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Board;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Coord;

import java.util.ArrayList;
import java.util.List;

public class Ship_1 extends Ship {

    public Ship_1(Coord pos) {
        super(pos, 1);
    }

    public Ship_1(int x, int y) {
        super(new Coord(x, y), 1);
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
    public void draw(Canvas canvas, int cellsize) {

        Paint paint = new Paint();
        //paint.setColor(Color.RED);
        //canvas.drawRect(pos.getX() * cellsize, pos.getY() * cellsize, (pos.getX() + 1) * cellsize, (pos.getY() + 1) * cellsize, paint);


        int offset = (int) Math.round(cellsize * 0.10);


        if (isInConflict())
            paint.setColor(board.getColorConflictPos());
        else
            paint.setColor(board.getColorShips());


        paint.setStrokeCap(Paint.Cap.ROUND);
        int left = pos.getX() * cellsize + offset;
        int top = pos.getY() * cellsize + offset;
        int right = (pos.getX() + 1) * cellsize - offset + 1;
        int bottom = (pos.getY() + 1) * cellsize - offset + 1;
        Rect rect = new Rect(left, top, right, bottom);
        RectF rectF = new RectF(rect);
        // canvas.drawRect(rect, paint);
        canvas.drawRoundRect(rectF, cellsize / 4, cellsize / 4, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(board.getColorBorderShips());
        paint.setStrokeWidth(cellsize / 17);
        canvas.drawRoundRect(rectF, cellsize / 4, cellsize / 4, paint);
    }

    @Override
    public void setPos(Coord pos) {
        Coord old = this.pos;
        super.setPos(pos);
        //   if (pos.equals(old))
        //        return;
        if (old != null) {
            board.getSquare(old).removeShip(this);
        }
        board.getSquare(pos).addShip(this);
    }

    @Override
    public void changeDir() {
    }

    public boolean isInConflict() {

        List<Coord> posicoes = new ArrayList<>();

        for (int x = -1; x <= 1; ++x) {
            for (int y = -1; y <= 1; ++y) {

                posicoes.add(new Coord(pos.getX() + x, pos.getY() + y));
            }
        }

        for (Coord posicao : posicoes) {
            if (board.isboardCoord(posicao) && board.getSquare(posicao).getNumberShips() > 0 && !board.getSquare(posicao).isFirstShip(this))
                return true;
        }

        return false;
    }
}
