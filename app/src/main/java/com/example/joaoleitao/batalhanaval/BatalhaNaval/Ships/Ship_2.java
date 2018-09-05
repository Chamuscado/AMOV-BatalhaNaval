package com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Board;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Coord;

import java.util.ArrayList;
import java.util.List;

public class Ship_2 extends Ship {

    public Ship_2(Coord pos, Dir dir) {
        super(pos, dir, 2);
    }

    public Ship_2(int x, int y, Dir dir) {
        super(x, y, dir, 2);
    }


    @Override
    protected Coord isValidPos(Coord newPos) {
        Coord size = board.getSize();
        if (newPos.getX() < 1)
            newPos.setX(1);
        if (newPos.getY() < 1)
            newPos.setY(1);
        int xOffset = dir == Dir.Horizontal || dir == Dir.Horizontal_Invert ? 1 : 0;
        int yOffset = dir == Dir.Horizontal || dir == Dir.Horizontal_Invert ? 0 : 1;
        if (newPos.getX() > (size.getX() - xOffset))
            newPos.setX(size.getX() - xOffset);
        if (newPos.getY() > (size.getY() - yOffset))
            newPos.setY(size.getY() - yOffset);
        return newPos;
    }

    @Override
    public void draw(Canvas canvas, int cellsize) {
        Paint paint = new Paint();
        //paint.setColor(Color.RED);
        //canvas.drawRect(pos.getX() * cellsize, pos.getY() * cellsize, (pos.getX() + 1) * cellsize, (pos.getY() + 1) * cellsize, paint);


        if (isInConflict())
            paint.setColor(board.getColorConflictPos());
        else
            paint.setColor(board.getColorShips());

        int offset = (int) Math.round(cellsize * 0.10);
        int sizeInX = dir == Dir.Horizontal || dir == Dir.Horizontal_Invert ? 2 : 1;
        int sizeInY = dir == Dir.Vertical || dir == Dir.Vertical_Invert ? 2 : 1;


        paint.setStrokeCap(Paint.Cap.ROUND);
        int left = pos.getX() * cellsize + offset;
        int top = pos.getY() * cellsize + offset;
        int right = (pos.getX() + sizeInX) * cellsize - offset + 1;
        int bottom = (pos.getY() + sizeInY) * cellsize - offset + 1;
        Rect rect = new Rect(left, top, right, bottom);
        RectF rectF = new RectF(rect);
        canvas.drawRoundRect(rectF, cellsize / 4, cellsize / 4, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(board.getColorBorderShips());
        paint.setStrokeWidth(cellsize / 17);
        canvas.drawRoundRect(rectF, cellsize / 4, cellsize / 4, paint);
    }

    @Override
    public void setPos(Coord newpos) {
        Coord oldPos = null;
        if (pos != null)
            oldPos = pos.Clone();
        super.setPos(newpos);
        //if (pos.equals(oldPos))
        //    return;
        correctBoard(oldPos, dir);
    }

    @Override
    public void changeDir() {
        Coord oldPos = null;
        if (pos != null)
            oldPos = pos.Clone();
        Dir oldDir = this.dir;
        dir = dir == Dir.Horizontal ? Dir.Vertical : Dir.Horizontal;
        pos = isValidPos(pos);
        correctBoard(oldPos, oldDir);
    }

    private void correctBoard(Coord oldPos, Dir oldDir) {
        if (oldPos != null) {
            board.getSquare(oldPos).removeShip(this);
            if (oldDir == Dir.Horizontal || oldDir == Dir.Horizontal_Invert) {
                board.getSquare(new Coord(oldPos.getX() + 1, oldPos.getY())).removeShip(this);
            } else {
                board.getSquare(new Coord(oldPos.getX(), oldPos.getY() + 1)).removeShip(this);
            }
        }
        board.getSquare(pos).addShip(this);
        if (dir == Dir.Horizontal || dir == Dir.Horizontal_Invert) {
            board.getSquare(new Coord(pos.getX() + 1, pos.getY())).addShip(this);
        } else {
            board.getSquare(new Coord(pos.getX(), pos.getY() + 1)).addShip(this);
        }
    }

    public boolean isInConflict() {
        List<Coord> posicoes = new ArrayList<>();

        int startX;
        int endX;
        int startY;
        int endY;

        if (dir == Dir.Horizontal || dir == Dir.Horizontal_Invert) {
            startX = -1;
            endX = 2;
            startY = -1;
            endY = 1;
        } else {
            startX = -1;
            endX = 1;
            startY = -1;
            endY = 2;
        }

        for (int x = startX; x <= endX; ++x) {
            for (int y = startY; y <= endY; ++y) {

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
