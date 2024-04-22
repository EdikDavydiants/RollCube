package com.example.rollcube.gameinterface.okpuzzle;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameinterface.Button;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.Picture;
import com.example.rollcube.gameinterface.PictureGroup;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class OkPuzzle extends PictureGroup {

    private final int TYPE_1 = 1;
    private final int TYPE_2 = 2;
    private final int TYPE_3 = 3;
    private final int[] okArr;
    private final OkPiece[] okPieceArr;


    public OkPuzzle(MainManager mainManager) {
        okArr = new int[]
                {
                        1, 1, 1,   1, 0, 1,
                        1, 0, 1,   1, 0, 1,
                        1, 0, 1,   1, 1, 0,
                        1, 0, 1,   1, 0, 1,
                        1, 1, 1,   1, 0, 1,
                };

        okPieceArr = new OkPiece[30];
        Point2DFloat pieceSize = new Point2DFloat(0.1f, 0.1f);
        RectF bottomRightMrgn = new RectF(0f, 0f, 0.01f, 0.01f);
        RectF bottomBigRightMrgn = new RectF(0f, 0f, 0.12f, 0.01f);
        RectF rightMrgn = new RectF(0f, 0f, 0.01f, 0f);
        RectF bigRightMrgn = new RectF(0f, 0f, 0.12f, 0f);
        RectF bottomMrgn = new RectF(0f, 0f, 0f, 0.01f);
        RectF noMrgn = new RectF(0f, 0f, 0f, 0f);

        okPieceArr[0] = new OkPiece(mainManager, pieceSize, bottomRightMrgn, Picture.Orientation.LEFT_ROTATION, TYPE_3);
        okPieceArr[1] = new OkPiece(mainManager, pieceSize, bottomRightMrgn, Picture.Orientation.NORMAL, TYPE_1);
        okPieceArr[2] = new OkPiece(mainManager, pieceSize, bottomBigRightMrgn, Picture.Orientation.NORMAL, TYPE_3);

        okPieceArr[3] = new OkPiece(mainManager, pieceSize, bottomBigRightMrgn, Picture.Orientation.NORMAL, TYPE_2);
        okPieceArr[4] = null;
        okPieceArr[5] = new OkPiece(mainManager, pieceSize, bottomMrgn, Picture.Orientation.LEFT_ROTATION, TYPE_3);


        okPieceArr[6] = new OkPiece(mainManager, pieceSize, bottomBigRightMrgn, Picture.Orientation.NORMAL, TYPE_1);
        okPieceArr[7] = null;
        okPieceArr[8] = new OkPiece(mainManager, pieceSize, bottomBigRightMrgn, Picture.Orientation.NORMAL, TYPE_1);

        okPieceArr[9] = new OkPiece(mainManager, pieceSize, bottomBigRightMrgn, Picture.Orientation.NORMAL, TYPE_1);
        okPieceArr[10] = null;
        okPieceArr[11] = new OkPiece(mainManager, pieceSize, bottomMrgn, Picture.Orientation.RIGHT_ROTATION, TYPE_3);


        okPieceArr[12] = new OkPiece(mainManager, pieceSize, bottomBigRightMrgn, Picture.Orientation.NORMAL, TYPE_1);
        okPieceArr[13] = null;
        okPieceArr[14] = new OkPiece(mainManager, pieceSize, bottomBigRightMrgn, Picture.Orientation.NORMAL, TYPE_1);

        okPieceArr[15] = new OkPiece(mainManager, pieceSize, bottomRightMrgn, Picture.Orientation.NORMAL, TYPE_1);
        okPieceArr[16] = new OkPiece(mainManager, pieceSize, bottomBigRightMrgn, Picture.Orientation.LEFT_ROTATION, TYPE_2);
        okPieceArr[17] = null;


        okPieceArr[18] = new OkPiece(mainManager, pieceSize, bottomBigRightMrgn, Picture.Orientation.NORMAL, TYPE_1);
        okPieceArr[19] = null;
        okPieceArr[20] = new OkPiece(mainManager, pieceSize, bottomBigRightMrgn, Picture.Orientation.NORMAL, TYPE_1);

        okPieceArr[21] = new OkPiece(mainManager, pieceSize, bottomBigRightMrgn, Picture.Orientation.NORMAL, TYPE_1);
        okPieceArr[22] = null;
        okPieceArr[23] = new OkPiece(mainManager, pieceSize, bottomMrgn, Picture.Orientation.NORMAL, TYPE_3);


        okPieceArr[24] = new OkPiece(mainManager, pieceSize, rightMrgn, Picture.Orientation.TWICE_RIGHT_ROTATION, TYPE_3);
        okPieceArr[25] = new OkPiece(mainManager, pieceSize, rightMrgn, Picture.Orientation.NORMAL, TYPE_1);
        okPieceArr[26] = new OkPiece(mainManager, pieceSize, bigRightMrgn, Picture.Orientation.RIGHT_ROTATION, TYPE_3);

        okPieceArr[27] = new OkPiece(mainManager, pieceSize, bigRightMrgn, Picture.Orientation.NORMAL, TYPE_1);
        okPieceArr[28] = null;
        okPieceArr[29] = new OkPiece(mainManager, pieceSize, noMrgn, Picture.Orientation.TWICE_RIGHT_ROTATION, TYPE_3);

        //region 1st row
        addRelative(okPieceArr[0],
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(okPieceArr[1],
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[0],
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(okPieceArr[2],
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[1],
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);

        addRelative(okPieceArr[3],
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[2],
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
//        addRelative(okPieceArr[4],
//                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[3],
//                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(okPieceArr[5],
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[3],
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        //endregion
        //region 2st row
        addRelative(okPieceArr[6],
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[0]);
//        addRelative(okPieceArr[7],
//                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[6],
//                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[0]);
        addRelative(okPieceArr[8],
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[6],
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[0]);

        addRelative(okPieceArr[9],
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[8],
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[0]);
//        addRelative(okPieceArr[10],
//                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[9],
//                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[0]);
        addRelative(okPieceArr[11],
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[9],
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[0]);
        //endregion
        //region 3st row
        addRelative(okPieceArr[12],
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[6]);
//        addRelative(okPieceArr[13],
//                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[12],
//                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[6]);
        addRelative(okPieceArr[14],
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[12],
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[6]);

        addRelative(okPieceArr[15],
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[14],
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[6]);
        addRelative(okPieceArr[16],
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[15],
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[6]);
//        addRelative(okPieceArr[17],
//                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[16],
//                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[6]);
        //endregion
        //region 4st row
        addRelative(okPieceArr[18],
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[12]);
//        addRelative(okPieceArr[19],
//                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[18],
//                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[12]);
        addRelative(okPieceArr[20],
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[18],
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[12]);

        addRelative(okPieceArr[21],
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[20],
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[12]);
//        addRelative(okPieceArr[22],
//                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[21],
//                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[12]);
        addRelative(okPieceArr[23],
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[21],
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[12]);
        //endregion
        //region 5st row
        addRelative(okPieceArr[24],
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[18]);
        addRelative(okPieceArr[25],
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[24],
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[18]);
        addRelative(okPieceArr[26],
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[25],
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[18]);

        addRelative(okPieceArr[27],
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[26],
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[18]);
//        addRelative(okPieceArr[28],
//                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[27],
//                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[18]);
        addRelative(okPieceArr[29],
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, okPieceArr[27],
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPieceArr[18]);
        //endregion
    }



    private boolean checkAllMatches() {
        for (int i = 0; i < 30; i++) {
            if (okPieceArr[i] != null && okPieceArr[i].value != okArr[i]) {
                return false;
            }
        }
        return true;
    }

    public void setGreyOk() {
        for (OkPiece okPiece: okPieceArr) {
            if(okPiece != null) {
                okPiece.value = 0;
            }
        }
    }




    public class OkPiece extends Button {
        private final int TYPE;
        private int value = 0;

        public OkPiece(MainManager mainManager, Point2DFloat inSize, RectF margins,
                       Orientation orientation, int type) {
            super(mainManager, inSize, margins, orientation);
            TYPE = type;
        }


        @Override
        public void draw() {
            glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mainManager.INTERFACE_matrix, 0);
            if (value == 0) {
                if (TYPE == TYPE_1) {
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.ok_board_piece_grey1);
                } else if (TYPE == TYPE_2) {
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.ok_board_piece_grey2);
                } else {
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.ok_board_piece_grey3);
                }
            } else {
                if (TYPE == TYPE_1) {
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.ok_board_piece_green1);
                } else if (TYPE == TYPE_2) {
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.ok_board_piece_green2);
                } else {
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.ok_board_piece_green3);
                }
            }
            mainManager.buffers.indexBuffer_t.position(startIndex);
            glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_t);
        }


        @Override
        public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
            if(value == 0) {
                value = 1;
            } else {
                value = 0;
            }
            ((OkBoard)(OkPuzzle.this.parent)).setResetButtonStatus(checkAllMatches());
            return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
        }
    }



}
