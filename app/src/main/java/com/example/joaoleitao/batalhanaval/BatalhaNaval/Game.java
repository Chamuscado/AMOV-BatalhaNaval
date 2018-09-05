package com.example.joaoleitao.batalhanaval.BatalhaNaval;

import android.content.Context;
import android.content.res.Resources;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.Ship;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.Ship_1;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.Ship_2;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.Ship_3;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.Ship_5;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Ships.Target;
import com.example.joaoleitao.batalhanaval.R;

import java.util.ArrayList;

public class Game {
    ShipsBoard shipsBoard;
    TargetBoard targetsBoard;
    private Resources resources;

    public Game(Context context) {
        resources = context.getResources();
        shipsBoard = new ShipsBoard(Board.BoardType.Ships, this, 8);
        targetsBoard = new TargetBoard(Board.BoardType.Targests, this, 8);


        shipsBoard.addShip(new Ship_5(2, 7, Ship.Dir.Vertical));
        shipsBoard.addShip(new Ship_3(7, 2, Ship.Dir.Horizontal));
        shipsBoard.addShip(new Ship_3(6, 5, Ship.Dir.Vertical));
        shipsBoard.addShip(new Ship_2(3, 4, Ship.Dir.Horizontal));
        shipsBoard.addShip(new Ship_2(4, 1, Ship.Dir.Vertical));
        shipsBoard.addShip(new Ship_1(1, 1));
        shipsBoard.addShip(new Ship_1(8, 7));


        shipsBoard.setColorBoardLines(context.getResources().getColor(R.color.boardLines));
        shipsBoard.setColorSea(context.getResources().getColor(R.color.sea));
        shipsBoard.setColorConflictPos(context.getResources().getColor(R.color.conflictPos));

        shipsBoard.setColorFailedShot(context.getResources().getColor(R.color.failedShot));
        shipsBoard.setColorSuccessfulShot(context.getResources().getColor(R.color.successfulShot));

        shipsBoard.setColorFailedShot(context.getResources().getColor(R.color.successfulShot));
        shipsBoard.setColorSuccessfulShot(context.getResources().getColor(R.color.failedShot));

        shipsBoard.setColorShips(context.getResources().getColor(R.color.ship));
        shipsBoard.setColorBorderShips(context.getResources().getColor(R.color.borderShips));

        shipsBoard.setColorTarget(context.getResources().getColor(R.color.target));

        shipsBoard.shot(new Coord(5, 3));
        shipsBoard.shot(new Coord(2, 7));
        shipsBoard.shot(new Coord(4, 8));
        shipsBoard.shot(new Coord(7, 2));

        targetsBoard.setColorBoardLines(context.getResources().getColor(R.color.boardLines));
        targetsBoard.setColorSea(context.getResources().getColor(R.color.sea));
        targetsBoard.setColorConflictPos(context.getResources().getColor(R.color.conflictPos));

        targetsBoard.setColorFailedShot(context.getResources().getColor(R.color.failedShot));
        targetsBoard.setColorSuccessfulShot(context.getResources().getColor(R.color.successfulShot));

        targetsBoard.setColorFailedShot(context.getResources().getColor(R.color.successfulShot));
        targetsBoard.setColorSuccessfulShot(context.getResources().getColor(R.color.failedShot));

        targetsBoard.setColorShips(context.getResources().getColor(R.color.ship));
        targetsBoard.setColorBorderShips(context.getResources().getColor(R.color.borderShips));
        targetsBoard.setColorTarget(context.getResources().getColor(R.color.target));


        targetsBoard.addTarget(new Target(new Coord(4, 5)));
        targetsBoard.addTarget(new Target(new Coord(3, 7)));
        targetsBoard.addTarget(new Target(new Coord(6, 2)));
    }

    public Board getShipsBoard() {
        return shipsBoard;
    }

    public Board getTargetsBoard() {
        return targetsBoard;
    }

    public Resources getResources() {
        return resources;
    }

    public Board getBoardByType(Board.BoardType board) {
        switch (board) {

            case Ships:
                return shipsBoard;
            case Targests:
                return targetsBoard;
        }
        return null;
    }
}
