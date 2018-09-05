package com.example.joaoleitao.batalhanaval.BatalhaNaval;

import java.io.Serializable;

public class Coord implements Serializable {
    protected int X, Y;

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public Coord(int x, int y) {
        X = x;
        Y = y;
    }

    public static final int maxX = 7;
    public static final int maxY = 7;
    public static final int minX = 0;
    public static final int minY = 0;

    public Coord Clone(){
        return new Coord(X,Y);
    }

    @Override
    public String toString() {
        return "(" + X + "," + Y + ")";
    }

    public boolean isValid() {
        return X < minX || X > maxX || Y < minY || Y > maxY ? false : true;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coord))
            return false;
        Coord cord = (Coord) obj;
        return X == cord.getX() && Y == cord.getY();
    }

}
