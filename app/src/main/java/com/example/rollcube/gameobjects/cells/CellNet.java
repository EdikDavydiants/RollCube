package com.example.rollcube.gameobjects.cells;

import com.example.rollcube.Drawable;
import com.example.rollcube.GameBoard;
import com.example.rollcube.gameobjects.gamecubes.GameCube;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameobjects.walls.Wall;


public class CellNet implements Drawable {

    private final Cell[][] cellArr;


    public CellNet(MainManager mainManager, int N, int M, GameBoard.GameType gameType) {
        cellArr = new Cell[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                cellArr[i][j] = new Cell(mainManager, i, j, gameType);

                if (i == 0 && j == 0) {
                    cellArr[i][j].createWall(Wall.WallSide.LEFT);
                    cellArr[i][j].createWall(Wall.WallSide.FORWARD);
                    cellArr[i][j].createWall(Wall.WallSide.RIGHT);
                    cellArr[i][j].createWall(Wall.WallSide.BACK);
                }

                else if (i == 0) {
                    cellArr[i][j].createWall(Wall.WallSide.LEFT);
                    cellArr[i][j].setForwardWallContainer(cellArr[i][j - 1].getBackWallContainer());
                    cellArr[i][j].createWall(Wall.WallSide.RIGHT);
                    cellArr[i][j].createWall(Wall.WallSide.BACK);
                }
                else if (j == 0) {
                    cellArr[i][j].setLeftWallContainer(cellArr[i - 1][j].getRightWallContainer());
                    cellArr[i][j].createWall(Wall.WallSide.FORWARD);
                    cellArr[i][j].createWall(Wall.WallSide.RIGHT);
                    cellArr[i][j].createWall(Wall.WallSide.BACK);
                }

                else {
                    cellArr[i][j].setLeftWallContainer(cellArr[i - 1][j].getRightWallContainer());
                    cellArr[i][j].setForwardWallContainer(cellArr[i][j - 1].getBackWallContainer());
                    cellArr[i][j].createWall(Wall.WallSide.RIGHT);
                    cellArr[i][j].createWall(Wall.WallSide.BACK);
                }
            }
        }
    }



    @Override
    public void draw() {
        for (int i = 0; i < GameBoard.N; i++) {
            for (int j = 0; j < GameBoard.M; j++) {
                cellArr[i][j].draw();
            }
        }
    }



    public Cell getCell(int n, int m) {
        return cellArr[n][m];
    }

    public int checkDetails(GameCube gameCube) {
        CellObject.CellCoords cubeCoords = gameCube.getCoords();
        int cube_n = cubeCoords.n;
        int cube_m = cubeCoords.m;

        return cellArr[cube_n][cube_m].checkDetails(gameCube);
    }

    public void changeTilesGameType(GameBoard.GameType gameType) {
        for (int i = 0; i < GameBoard.N; i++) {
            for (int j = 0; j < GameBoard.M; j++) {
                cellArr[i][j].setTileGameType(gameType);
            }
        }
    }

}
