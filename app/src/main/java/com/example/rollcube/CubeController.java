package com.example.rollcube;

import com.example.rollcube.gameobjects.MainBackground;
import com.example.rollcube.gameobjects.gamecubes.GameCube;

import java.util.LinkedList;


public class CubeController implements Runnable{
    private enum Status { WAITING_FOR_MOVE, WAITING_FOR_ANIMATION_END }

    private Status status = Status.WAITING_FOR_MOVE;
    boolean isRunning;
    private final GameBoard gameBoard;
    private GameCube gameCube;
    private Camera camera = null;
    private MainBackground mainBackground = null;
    private final Queue queue;


    public CubeController(GameBoard gameBoard, GameCube gameCube, Queue queue) {
        this.gameBoard = gameBoard;
        this.gameCube = gameCube;
        this.queue = queue;
        new Thread(this).start();
    }



    @Override
    public void run() {
        Queue.MoveDirection move = null;
        isRunning = true;
        while (isRunning) {

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            if (status == Status.WAITING_FOR_MOVE) {
                synchronized (queue) {
                    move = queue.getFirst();
                }
                if (move != null) {
                    status = Status.WAITING_FOR_ANIMATION_END;
                }
            }
            else if (status == Status.WAITING_FOR_ANIMATION_END){
                if(move != null) {
                    if (gameCube != null && camera != null && mainBackground != null &&
                            !gameCube.isAnimating() &&
                            !camera.isAnimating() &&
                            !mainBackground.isAnimating() &&
                            !gameBoard.isCubeBlocked()) {
                        if (gameBoard.isMoveLegal(move)) {
                            gameCube.moveCube(move);
                            camera.setAnimationMoveParams(move, mainBackground);
                        }
                        status = Status.WAITING_FOR_MOVE;
                    }
                }
            }

        }
    }

    public void endThread() {
        isRunning = false;
    }

    public void setGameCube(GameCube gameCube) {
        this.gameCube = gameCube;
    }
    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    public void setMainBackground(MainBackground mainBackground) {
        this.mainBackground = mainBackground;
    }




    public static class Queue {
        public enum MoveDirection { LEFT, RIGHT, BACK, FORWARD;
            public static MoveDirection copy(MoveDirection move) {
                MoveDirection move_ = null;
                for (MoveDirection moveItr: MoveDirection.values()) {
                    if (moveItr == move) {
                        move_ = moveItr;
                        break;
                    }
                }
                return move_;
            }
        }

        private final LinkedList<MoveDirection> moveList;


        public Queue() {
            moveList = new LinkedList<>();
        }



        public MoveDirection getFirst() {
            if(!moveList.isEmpty()) {
                return moveList.removeFirst();
            }
            else {
                return null;
            }
        }

        public void put(MoveDirection move) {
            if (moveList.size() > 0) {
                moveList.removeLast();
            }
            moveList.addLast(move);
        }

        public void deleteLast() {
            if(!moveList.isEmpty()) {
                moveList.removeLast();
            }
        }





    }




}
