package com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Board;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Coord;

import java.util.ArrayList;
import java.util.List;

public class Ship_3 extends Ship {


    public Ship_3(Coord pos, Dir dir) {
        super(pos, dir, 3);
    }

    public Ship_3(int x, int y, Dir dir) {
        super(x, y, dir, 3);
    }


    @Override
    protected Coord isValidPos(Coord newPos) {
        Coord size = board.getSize();
        int xOffset = dir == Dir.Horizontal || dir == Dir.Horizontal_Invert ? 1 : 0;
        int yOffset = dir == Dir.Horizontal || dir == Dir.Horizontal_Invert ? 0 : 1;
        if (newPos.getX() < (1 + xOffset))
            newPos.setX(1 + xOffset);
        if (newPos.getY() < (1 + yOffset))
            newPos.setY(1 + yOffset);
        if (newPos.getX() > size.getX() - xOffset)
            newPos.setX(size.getX() - xOffset);

        if (newPos.getY() > size.getY() - yOffset)
            newPos.setY(size.getY() - yOffset);
        return newPos;
    }

    @Override
    public void draw(Canvas canvas, int cellsize) {
        Paint paint = new Paint();


        if (isInConflict())
            paint.setColor(board.getColorConflictPos());
        else
            paint.setColor(board.getColorShips());


        int offset = (int) Math.round(cellsize * 0.10);
        int sizeInX = dir == Dir.Horizontal || dir == Dir.Horizontal_Invert ? 3 : 1;
        int sizeInY = dir == Dir.Vertical || dir == Dir.Vertical_Invert ? 3 : 1;
        int startX = dir == Dir.Horizontal || dir == Dir.Horizontal_Invert ? -1 : 0;
        int startY = dir == Dir.Vertical || dir == Dir.Vertical_Invert ? -1 : 0;


        paint.setStrokeCap(Paint.Cap.ROUND);
        int left = (pos.getX() + startX) * cellsize + offset;
        int top = (pos.getY() + startY) * cellsize + offset;
        int right = (pos.getX() + sizeInX + startX) * cellsize - offset + 1;
        int bottom = (pos.getY() + sizeInY + startY) * cellsize - offset + 1;
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
       // if (pos.equals(oldPos))
       //     return;
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
                board.getSquare(new Coord(oldPos.getX() - 1, oldPos.getY())).removeShip(this);
            } else {
                board.getSquare(new Coord(oldPos.getX(), oldPos.getY() + 1)).removeShip(this);
                board.getSquare(new Coord(oldPos.getX(), oldPos.getY() - 1)).removeShip(this);
            }
        }
        board.getSquare(pos).addShip(this);
        if (dir == Dir.Horizontal || dir == Dir.Horizontal_Invert) {
            board.getSquare(new Coord(pos.getX() + 1, pos.getY())).addShip(this);
            board.getSquare(new Coord(pos.getX() - 1, pos.getY())).addShip(this);
        } else {
            board.getSquare(new Coord(pos.getX(), pos.getY() + 1)).addShip(this);
            board.getSquare(new Coord(pos.getX(), pos.getY() - 1)).addShip(this);
        }
    }

    public boolean isInConflict() {
        List<Coord> posicoes = new ArrayList<>();

        int startX;
        int endX;
        int startY;
        int endY;

        if (dir == Dir.Horizontal || dir == Dir.Horizontal_Invert) {
            startX = -2;
            endX = 2;
            startY = -1;
            endY = 1;
        } else {
            startX = -1;
            endX = 1;
            startY = -2;
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
