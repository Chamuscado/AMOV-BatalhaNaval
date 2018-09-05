package com.example.joaoleitao.batalhanaval.BatalhaNaval;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.Log;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.BoardElement;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.Ship;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.Ship_1;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.Ship_2;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.Ship_3;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.Ship_5;
import com.example.joaoleitao.batalhanaval.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable {

    protected List<BoardElement> boardElementsList;
    protected Coord size;
    protected Square[][] board;
    protected Game parent;
    protected int colorSea = 0;
    protected int colorBoardLines = 0;
    protected int colorConflictPos = 0;
    protected int colorSuccessfulShot = 0;
    protected int colorFailedShot = 0;
    protected int colorShips = 0;
    protected int colorBorderShips = 0;
    protected int colorTarget = 0;

    private BoardType boardType;
    private boolean ready;

    public Board(BoardType boardType, Game parent, Coord size) {
        this.parent = parent;
        this.boardType = boardType;
        this.size = size;
        boardElementsList= new ArrayList<>();
        board = new Square[size.X][size.Y];
        for (int i = 0; i < size.X; ++i)
            for (int j = 0; j < size.X; ++j) {
                board[i][j] = new Square(this, i + 1, j + 1);
            }
    }


    public Board(BoardType boardType, Game parent, int size) {
        this(boardType, parent, new Coord(size, size));
    }

    public Ship getShip(Coord pos) {

        if (pos.getX() > 0 && pos.getX() <= size.getX() && pos.getY() > 0 && pos.getY() <= size.getY())
            return board[pos.getX() - 1][pos.getY() - 1].getFirstShip();
        return null;
    }

    public void draw(Canvas canvas, float cellsize, int MAX) {
        Paint boardPaint = new Paint();
        Paint textPaint = new Paint();
        float textOffset = 0.30f * cellsize;
        float coordSize = 0.80f * cellsize;

        canvas.drawLine(0, 0, cellsize, cellsize, textPaint);
        canvas.drawLine(cellsize * MAX, 0, cellsize * (MAX - 1), cellsize, textPaint);
        canvas.drawLine(0, cellsize * MAX, cellsize, cellsize * (MAX - 1), textPaint);
        canvas.drawLine(cellsize * MAX, cellsize * MAX, cellsize * (MAX - 1), cellsize * (MAX - 1), textPaint);


        for (int x = 0; x < MAX; ++x) {
            for (int y = 0; y < MAX; ++y) {
                Coord pos = new Coord(x, y);
                if (x == 0 || y == 0 || x == MAX - 1 || y == MAX - 1) {
                    // insere no canvas as letras e numeros das conrdenadas
                    if ((x == 0 || x == MAX - 1) && y != 0 && y != (MAX - 1)) {
                        textPaint.setTextSize(coordSize);
                        textPaint.setColor(Color.BLACK);
                        canvas.drawText(Integer.toString(y), x * cellsize + textOffset, (y + 1) * cellsize - textOffset, textPaint);

                    } else if (x != 0 && x != (MAX - 1)) {
                        textPaint.setTextSize(coordSize);
                        textPaint.setColor(Color.BLACK);
                        canvas.drawText(Character.toString((char) ('a' + x - 1)), x * cellsize +
                                textOffset, (y + 1) * cellsize - textOffset, textPaint);
                    }
                } else {
                    getSquare(pos).draw(canvas, cellsize, boardPaint);
                }
            }
        }
        for (BoardElement element : boardElementsList)
            element.draw(canvas, Math.round(cellsize));


        for (Square[] line : board) {
            for (Square square : line) {
                square.drawShots(canvas, cellsize, boardPaint);
            }
        }

        boardPaint.setColor(colorBoardLines);
        for (int x = 0; x < MAX; ++x) {
            canvas.drawLine(x * cellsize, 0, x * cellsize, cellsize * MAX, boardPaint);
            for (int y = 0; y < MAX; ++y) {
                canvas.drawLine(0, cellsize * y, MAX * cellsize, cellsize * y, boardPaint);
            }
        }
    }

    @Deprecated
    public Ship getShip(int x, int y) {
        return getShip(new Coord(x, y));
    }

    public Coord getSize() {
        return size;
    }


    public Square getSquare(Coord pos) {

        if (pos.getX() > 0 && pos.getX() <= size.getX() && pos.getY() > 0 && pos.getY() <= size.getY()) {
            Square sqr = board[pos.getX() - 1][pos.getY() - 1];
            Log.d("square", "Pedido: " + pos + " Dado: " + sqr.getPos());
            return sqr;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int j = 0; j < size.Y; ++j) {
            str.append("\n").append(j + 1).append(" -> ");
            for (int i = 0; i < size.X; ++i) {
                str.append(" <").append(board[i][j].toString()).append("> ");
            }
        }
        return str.toString();
    }

    public boolean shot(Coord pos) {
        return getSquare(pos).shot();
    }

    public int getColorSea() {
        return colorSea;
    }

    public void setColorSea(int colorSea) {
        this.colorSea = colorSea;
    }

    public int getColorBoardLines() {
        return colorBoardLines;
    }

    public void setColorBoardLines(int colorBoardLines) {
        this.colorBoardLines = colorBoardLines;
    }

    public int getColorConflictPos() {
        return colorConflictPos;
    }

    public void setColorConflictPos(int colorConflictPos) {
        this.colorConflictPos = colorConflictPos;
    }

    public int getColorSuccessfulShot() {
        return colorSuccessfulShot;
    }

    public void setColorSuccessfulShot(int colorSuccessfulShot) {
        this.colorSuccessfulShot = colorSuccessfulShot;
    }

    public int getColorFailedShot() {
        return colorFailedShot;
    }

    public void setColorFailedShot(int colorFailedShot) {
        this.colorFailedShot = colorFailedShot;
    }

    public int getColorShips() {
        return colorShips;
    }

    public void setColorShips(int colorShips) {
        this.colorShips = colorShips;
    }

    public boolean isboardCoord(Coord posicao) {
        return posicao.getX() >= 1 && posicao.getX() <= size.getX() && posicao.getY() >= 1 && posicao.getY() <= size.getY();
    }

    public int getColorBorderShips() {
        return colorBorderShips;
    }

    public void setColorBorderShips(int colorBorderShips) {
        this.colorBorderShips = colorBorderShips;
    }

    public int getColorTarget() {
        return colorTarget;
    }

    public void setColorTarget(int colorTarget) {
        this.colorTarget = colorTarget;
    }

    public Resources getResources() {
        return parent.getResources();
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady() {
        this.ready = true;
    }

    public enum BoardType {
        Ships,
        Targests
    }

    public BoardType getBoardType() {
        return boardType;
    }
}
