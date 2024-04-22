package com.example.rollcube.gameobjects.staticobjects;

import com.example.rollcube.Drawable;
import com.example.rollcube.GameBoard;
import com.example.rollcube.managers.MainManager;


public class StaticObjects implements Drawable {

    private final BoardSide boardSide;
    private final SmallCap[][] smallCapArr;
    private final LongCapX[][] longCapXArr;
    private final LongCapZ[][] longCapZArr;


    public StaticObjects(int N, int M, MainManager mainManager) {
        boardSide = new BoardSide(mainManager);
        smallCapArr = new SmallCap[N + 1][M + 1];
        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < M + 1; j++) {
                smallCapArr[i][j] = new SmallCap(mainManager, i, j);
            }
        }
        longCapXArr = new LongCapX[N][M + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M + 1; j++) {
                longCapXArr[i][j] = new LongCapX(mainManager, i, j);
            }
        }
        longCapZArr = new LongCapZ[N + 1][M];
        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < M; j++) {
                longCapZArr[i][j] = new LongCapZ(mainManager, i, j);
            }
        }
    }


    @Override
    public void draw() {
        boardSide.draw();
        for (int i = 0; i < GameBoard.N + 1; i++) {
            for (int j = 0; j < GameBoard.M + 1; j++) {
                smallCapArr[i][j].draw();
            }
        }
        for (int i = 0; i < GameBoard.N; i++) {
            for (int j = 0; j < GameBoard.M + 1; j++) {
                longCapXArr[i][j].draw();
            }
        }
        for (int i = 0; i < GameBoard.N + 1; i++) {
            for (int j = 0; j < GameBoard.M; j++) {
                longCapZArr[i][j].draw();
            }
        }
    }











}


