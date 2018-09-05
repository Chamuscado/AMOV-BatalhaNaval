package com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships;

import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Board;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Coord;

import java.util.ArrayList;
import java.util.List;

public class Ship_5 extends Ship {

    public Ship_5(Coord pos, Dir dir) {
        super(pos, dir, 5);
    }

    public Ship_5(int x, int y, Dir dir) {
        super(x, y, dir, 5);
    }

    @Override
    protected Coord isValidPos(Coord newPos) {
        Coord size = board.getSize();
        if (newPos.getX() < 2)
            newPos.setX(2);
        if (newPos.getY() < 2)
            newPos.setY(2);
        if (newPos.getX() > size.getX() - 1)
            newPos.setX(size.getX() - 1);
        if (newPos.getY() > size.getY() - 1)
            newPos.setY(size.getY() - 1);
        return newPos;
    }

    @Override
    public void draw(Canvas canvas, int cellsize) {
        Paint paint = new Paint();

        if (isInConflict())
            paint.setColor(board.getColorConflictPos());
        else
            paint.setColor(board.getColorShips());

        Log.d("pos", "Pos: " + pos);
        int offset = (int) Math.round(cellsize * 0.10);
        int startX = dir == Dir.Horizontal ? -1 : 0;
        int startY = dir == Dir.Vertical ? -1 : 0;


        CornerPathEffect corEffect = new CornerPathEffect(cellsize / 4);
        paint.setPathEffect(corEffect);

        float startPointX = (pos.getX() + startX) * cellsize + offset;
        float startPointY = (pos.getY() + startY) * cellsize + offset;
        float x = startPointX;
        float y = startPointY;
        Path path = new Path();
        path.moveTo(x, y);
        x += 2 * cellsize - 2 * offset;
        path.lineTo(x, y);
        y += cellsize - 2 * offset;
        path.lineTo(x, y);
        x -= cellsize;
        path.lineTo(x, y);
        y += cellsize * 2;
        path.lineTo(x, y);
        x -= cellsize - 2 * offset;
        path.lineTo(x, y);
        y -= cellsize * 2;
        path.lineTo(x, y);
        x -= cellsize;
        path.lineTo(x, y);
        y -= cellsize - 2 * offset;
        path.lineTo(x, y);
        path.lineTo(startPointX, startPointY);
        path.close();

        Matrix mMatrix = new Matrix();
        RectF bounds = new RectF();
        path.computeBounds(bounds, true);

        int angle;

        switch (dir) {
            case Horizontal:
                angle = 90;
                mMatrix.postRotate(angle, bounds.centerX(), bounds.centerY());
                path.transform(mMatrix);
                mMatrix.setTranslate(cellsize, -cellsize);
                break;
            case Horizontal_Invert:
                angle = 270;
                mMatrix.postRotate(angle, bounds.centerX(), bounds.centerY());
                path.transform(mMatrix);
                mMatrix.setTranslate(0, -cellsize);
                break;
            case Vertical_Invert:
                angle = 180;
                mMatrix.postRotate(angle, bounds.centerX(), bounds.centerY());
                path.transform(mMatrix);
                mMatrix.setTranslate(0, -cellsize);
                break;
            case Vertical:
                angle = 0;
                mMatrix.postRotate(angle, bounds.centerX(), bounds.centerY());
                break;
        }


        path.transform(mMatrix);
        canvas.drawPath(path, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(board.getColorBorderShips());
        paint.setStrokeWidth(cellsize / 17);
        canvas.drawPath(path, paint);
    }

    @Override
    public void setPos(Coord newpos) {
        Coord oldPos = null;
        if (pos != null)
            oldPos = pos.Clone();
        super.setPos(newpos);
     //   if (pos.equals(oldPos))
     //       return;
        correctBoard(oldPos, dir);
    }

    @Override
    public void changeDir() {
        Coord oldPos = null;
        if (pos != null)
            oldPos = pos.Clone();
        Dir oldDir = this.dir;
        switch (dir) {
            case Vertical:
                dir = Dir.Horizontal;
                break;
            case Vertical_Invert:
                dir = Dir.Horizontal_Invert;
                break;
            case Horizontal:
                dir = Dir.Vertical_Invert;
                break;
            case Horizontal_Invert:
                dir = Dir.Vertical;
                break;
        }
        pos = isValidPos(pos);
        correctBoard(oldPos, oldDir);
    }


    private void correctBoard(Coord oldPos, Dir oldDir) {
        if (oldPos != null) {
            board.getSquare(oldPos).removeShip(this);
            if (oldDir == Dir.Horizontal) {
                board.getSquare(new Coord(oldPos.getX() + 1, oldPos.getY() - 1)).removeShip(this);
                board.getSquare(new Coord(oldPos.getX() + 1, oldPos.getY())).removeShip(this);
                board.getSquare(new Coord(oldPos.getX() + 1, oldPos.getY() + 1)).removeShip(this);
                board.getSquare(new Coord(oldPos.getX() - 1, oldPos.getY())).removeShip(this);
            } else if (oldDir == Dir.Vertical) {
                board.getSquare(new Coord(oldPos.getX() - 1, oldPos.getY() - 1)).removeShip(this);
                board.getSquare(new Coord(oldPos.getX(), oldPos.getY() - 1)).removeShip(this);
                board.getSquare(new Coord(oldPos.getX() + 1, oldPos.getY() - 1)).removeShip(this);
                board.getSquare(new Coord(oldPos.getX(), oldPos.getY() + 1)).removeShip(this);
            } else if (oldDir == Dir.Horizontal_Invert) {
                board.getSquare(new Coord(oldPos.getX() - 1, oldPos.getY() - 1)).removeShip(this);
                board.getSquare(new Coord(oldPos.getX() - 1, oldPos.getY())).removeShip(this);
                board.getSquare(new Coord(oldPos.getX() - 1, oldPos.getY() + 1)).removeShip(this);
                board.getSquare(new Coord(oldPos.getX() + 1, oldPos.getY())).removeShip(this);
            } else {
                board.getSquare(new Coord(oldPos.getX() - 1, oldPos.getY() + 1)).removeShip(this);
                board.getSquare(new Coord(oldPos.getX(), oldPos.getY() + 1)).removeShip(this);
                board.getSquare(new Coord(oldPos.getX() + 1, oldPos.getY() + 1)).removeShip(this);
                board.getSquare(new Coord(oldPos.getX(), oldPos.getY() - 1)).removeShip(this);
            }
        }
        board.getSquare(pos).addShip(this);
        if (dir == Dir.Horizontal) {
            board.getSquare(new Coord(pos.getX() + 1, pos.getY() - 1)).addShip(this);
            board.getSquare(new Coord(pos.getX() + 1, pos.getY())).addShip(this);
            board.getSquare(new Coord(pos.getX() + 1, pos.getY() + 1)).addShip(this);
            board.getSquare(new Coord(pos.getX() - 1, pos.getY())).addShip(this);
        } else if (dir == Dir.Vertical) {
            board.getSquare(new Coord(pos.getX() - 1, pos.getY() - 1)).addShip(this);
            board.getSquare(new Coord(pos.getX(), pos.getY() - 1)).addShip(this);
            board.getSquare(new Coord(pos.getX() + 1, pos.getY() - 1)).addShip(this);
            board.getSquare(new Coord(pos.getX(), pos.getY() + 1)).addShip(this);
        } else if (dir == Dir.Horizontal_Invert) {
            board.getSquare(new Coord(pos.getX() - 1, pos.getY() - 1)).addShip(this);
            board.getSquare(new Coord(pos.getX() - 1, pos.getY())).addShip(this);
            board.getSquare(new Coord(pos.getX() - 1, pos.getY() + 1)).addShip(this);
            board.getSquare(new Coord(pos.getX() + 1, pos.getY())).addShip(this);
        } else {
            board.getSquare(new Coord(pos.getX() - 1, pos.getY() + 1)).addShip(this);
            board.getSquare(new Coord(pos.getX(), pos.getY() + 1)).addShip(this);
            board.getSquare(new Coord(pos.getX() + 1, pos.getY() + 1)).addShip(this);
            board.getSquare(new Coord(pos.getX(), pos.getY() - 1)).addShip(this);
        }
    }


    @Override
    public boolean isInConflict() {
        List<Coord> posicoes = new ArrayList<>();

        int startX = -2;
        int endX = 2;
        int startY = -2;
        int endY = 2;


        for (int x = startX; x <= endX; ++x) {
            for (int y = startY; y <= endY; ++y) {

                posicoes.add(new Coord(pos.getX() + x, pos.getY() + y));
            }
        }


        if (dir == Dir.Horizontal) {
            posicoes.remove(new Coord(pos.getX() - 2, pos.getY() - 2));
            posicoes.remove(new Coord(pos.getX() - 2, pos.getY() + 2));
            posicoes.remove(new Coord(pos.getX() - 1, pos.getY() - 2));
            posicoes.remove(new Coord(pos.getX() - 1, pos.getY() + 2));
        } else if (dir == Dir.Vertical) {
            posicoes.remove(new Coord(pos.getX() - 2, pos.getY() + 2));
            posicoes.remove(new Coord(pos.getX() + 2, pos.getY() + 2));
            posicoes.remove(new Coord(pos.getX() - 2, pos.getY() + 1));
            posicoes.remove(new Coord(pos.getX() + 2, pos.getY() + 1));
        } else if (dir == Dir.Horizontal_Invert) {
            posicoes.remove(new Coord(pos.getX() + 2, pos.getY() - 2));
            posicoes.remove(new Coord(pos.getX() + 2, pos.getY() + 2));
            posicoes.remove(new Coord(pos.getX() + 1, pos.getY() - 2));
            posicoes.remove(new Coord(pos.getX() + 1, pos.getY() + 2));
        } else {
            posicoes.remove(new Coord(pos.getX() - 2, pos.getY() - 2));
            posicoes.remove(new Coord(pos.getX() + 2, pos.getY() - 2));
            posicoes.remove(new Coord(pos.getX() - 2, pos.getY() - 1));
            posicoes.remove(new Coord(pos.getX() + 2, pos.getY() - 1));
        }

        for (Coord posicao : posicoes) {
            if (board.isboardCoord(posicao) && board.getSquare(posicao).getNumberShips() > 0 && !board.getSquare(posicao).isFirstShip(this))
                return true;
        }

        return false;
    }
}
