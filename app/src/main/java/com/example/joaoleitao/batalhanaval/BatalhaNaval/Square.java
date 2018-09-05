package com.example.joaoleitao.batalhanaval.BatalhaNaval;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.graphics.drawable.VectorDrawableCompat;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.BoardElement;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.Ship;
import com.example.joaoleitao.batalhanaval.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Square implements Serializable {
    private Board board;
    private int x, y;
    private List<BoardElement> boardElements;
    private boolean shoted = false;

    public Square(Board board, int x, int y) {
        this.board = board;
        boardElements = new ArrayList<>();
        this.x = x;
        this.y = y;
    }

    public Coord getPos() {
        return new Coord(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Ship getFirstShip() {
        for (BoardElement element : boardElements) {
            if (element instanceof Ship)
                return (Ship) element;
        }
        return null;
    }

    public void addShip(Ship ship) {
        if (!boardElements.contains(ship))
            boardElements.add(ship);
    }

    public void removeShip(Ship ship) {
        boardElements.remove(ship);
    }

    public void draw(Canvas canvas, float cellsize, Paint boardPaint) {

        if (!shoted)
            boardPaint.setColor(board.getColorSea());

        canvas.drawRect(x * cellsize, y * cellsize, (x + 1) * cellsize, (y + 1) * cellsize, boardPaint);

    }

    public void drawShots(Canvas canvas, float cellsize, Paint boardPaint) {

        if (!shoted)
            return;

        VectorDrawableCompat vector = VectorDrawableCompat.create(board.getResources(), R.drawable.cross, null);
        vector.setBounds(Math.round(x * cellsize), Math.round(y * cellsize), Math.round((x + 1) * cellsize), Math.round((y + 1) * cellsize));
        if (getNumberShips() > 0)
            vector.setTint(board.getColorSuccessfulShot());
        else
            vector.setTint(board.getColorFailedShot());
        vector.draw(canvas);


    }

    public int getNumberShips() {
        int number = 0;
        for (BoardElement element : boardElements) {
            if (element instanceof Ship)
                ++number;
        }
        return number;
    }

    public int getNumberElements() {
        return boardElements.size();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("");// + getPos() + " ");
        for (BoardElement element : boardElements) {
            str.append(element.toString()).append("");
        }
        if (boardElements.isEmpty())
            str.append(" ");
        return str.toString();
    }

    public boolean shot() {
        if (!shoted) {
            Ship ship = getFirstShip();
            if (ship != null)
                ship.shot();
            return this.shoted = true;
        }
        return false;
    }

    public boolean isFirstShip(Ship ship) {
        Ship thisShip = getFirstShip();
        if (thisShip != null) {
            return thisShip.getId() == ship.getId();
        }
        return false;
    }

    public List<BoardElement> getElementsList() {
        return boardElements;
    }
}
