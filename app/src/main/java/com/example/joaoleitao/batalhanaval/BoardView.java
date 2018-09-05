package com.example.joaoleitao.batalhanaval;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Board;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Coord;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.Ship;

import java.util.List;

class BoardView extends View {
    final static int MAX = 10;

    float cellSize;
    float cellSizeX;
    float cellSizeY;
    Board board;
    GestureDetector doubleClickListener;

    public BoardView(Context context, Board board) {
        super(context);
        this.board = board;


        doubleClickListener = new GestureDetector(context, new DoubleClickListener());
        setOnTouchListener(new DragTouchListener());

    }

    @Override
    public void draw(Canvas canvas) {               // TODO -> é preciso refazer todo o método
        super.draw(canvas);
        cellSizeX = canvas.getWidth() / MAX;
        cellSizeY = canvas.getHeight() / MAX;
        cellSize = cellSizeX < cellSizeY ? cellSizeX : cellSizeY;

        board.draw(canvas, cellSize, MAX);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(Math.min(getMeasuredWidth(), getMeasuredHeight()),
                Math.min(getMeasuredWidth(), getMeasuredHeight()));
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return doubleClickListener.onTouchEvent(event);
    }

    @Override
    public void setOnLongClickListener(@Nullable OnLongClickListener l) {
        super.setOnLongClickListener(l);
    }

    class DragTouchListener implements View.OnTouchListener {
        Ship selectShip = null;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int x = Math.round((motionEvent.getX() - cellSizeX / 2) / cellSizeX);
            int y = Math.round((motionEvent.getY() - cellSizeY / 2) / cellSizeY);

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    selectShip = board.getShip(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    selectShip = null;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (cellSizeX != 0 && cellSizeY != 0 && selectShip != null) {
                        Coord pos = new Coord(x, y);
                        selectShip.setPos(pos);
                        Log.d("matriz", "matriz" + board);
                        invalidate();
                    }
                    break;

            }

            return doubleClickListener.onTouchEvent(motionEvent);
        }

    }


    private class DoubleClickListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            int x = Math.round((e.getX() - cellSizeX / 2) / cellSizeX);
            int y = Math.round((e.getY() - cellSizeY / 2) / cellSizeY);
            Ship ship = board.getShip(x, y);
            if (ship != null) {
                ship.changeDir();
                Log.d("matriz", "matriz" + board);
                invalidate();
            }
            return true;
        }
    }
}
