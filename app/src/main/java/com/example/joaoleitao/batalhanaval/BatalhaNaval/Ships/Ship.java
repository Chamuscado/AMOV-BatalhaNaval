package com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Board;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Coord;

import java.io.Serializable;

public abstract class Ship extends BoardElement {
    private static int CountId = 0;
    private int id;
    private int life;
    private int totalLife;
    Dir dir;

    public Dir getDir() {
        return dir;
    }


    public Ship(Coord pos, Dir dir, int life) {
        super(pos);
        this.dir = dir;
        this.totalLife = this.life = life;
        id = CountId++;
    }

    public Ship(int x, int y, Dir dir, int life) {
        this(new Coord(x, y), dir, life);
    }

    public Ship(Coord pos, int life) {
        this(pos, Dir.Horizontal, life);
    }

    public Ship(int x, int y, int life) {
        this(new Coord(x, y), life);
    }

    public abstract void changeDir();

    public void shot() {
        --life;
    }

    public boolean isSunken() {
        return life <= 0;
    }

    public enum Dir {
        Vertical,
        Vertical_Invert,
        Horizontal,
        Horizontal_Invert
    }

    @Override
    public String toString() {
        return id + "";
    }

    public int getId() {
        return id;
    }
}
