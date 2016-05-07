package com.example.aurel.inertiavectorsrace;

import android.graphics.Point;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by aurel on 5/2/2016.
 */
public class PosibleMovesSet extends ArrayList<VectorMove> {
    private VectorMove lastMove;
    private float gridSize;
    private Type cell;

    public PosibleMovesSet(VectorMove lastMove, float gridSize, boolean crashOccurred, int rows, int columns) {
        this.lastMove = lastMove;
        this.gridSize = gridSize;
        Point startPoint = lastMove.getToPoint();
        float lastX = startPoint.x;
        float lastY = startPoint.y;
        float lastDx = lastMove.getDx();
        float lastDy = lastMove.getDy();
        Point nextPoint1 = new Point((int)(lastX + lastDx - gridSize), (int)(lastY + lastDy - gridSize));
        if (nextPoint1.x < 0) nextPoint1.x = 0; if (nextPoint1.x > gridSize * columns) nextPoint1.x = (int) (gridSize * columns);
        if (nextPoint1.y < 0) nextPoint1.y = 0; if (nextPoint1.y > gridSize * rows) nextPoint1.y = (int) (gridSize * rows);
        Point nextPoint2 = new Point((int)(lastX + lastDx), (int)(lastY + lastDy - gridSize));
        if (nextPoint2.x < 0) nextPoint2.x = 0; if (nextPoint2.x > gridSize * columns) nextPoint2.x = (int) (gridSize * columns);
        if (nextPoint2.y < 0) nextPoint2.y = 0; if (nextPoint2.y > gridSize * rows) nextPoint2.y = (int) (gridSize * rows);
        Point nextPoint3 = new Point((int)(lastX + lastDx + gridSize), (int)(lastY + lastDy - gridSize));
        if (nextPoint3.x < 0) nextPoint3.x = 0; if (nextPoint3.x > gridSize * columns) nextPoint3.x = (int) (gridSize * columns);
        if (nextPoint3.y < 0) nextPoint3.y = 0; if (nextPoint3.y > gridSize * rows) nextPoint3.y = (int) (gridSize * rows);
        Point nextPoint4 = new Point((int)(lastX + lastDx - gridSize), (int)(lastY + lastDy));
        if (nextPoint4.x < 0) nextPoint4.x = 0; if (nextPoint4.x > gridSize * columns) nextPoint4.x = (int) (gridSize * columns);
        if (nextPoint4.y < 0) nextPoint4.y = 0; if (nextPoint4.y > gridSize * rows) nextPoint4.y = (int) (gridSize * rows);
        Point nextPoint5 = new Point((int)(lastX + lastDx), (int)(lastY + lastDy));
        if (nextPoint5.x < 0) nextPoint5.x = 0; if (nextPoint5.x > gridSize * columns) nextPoint5.x = (int) (gridSize * columns);
        if (nextPoint5.y < 0) nextPoint5.y = 0; if (nextPoint5.y > gridSize * rows) nextPoint5.y = (int) (gridSize * rows);
        Point nextPoint6 = new Point((int)(lastX + lastDx + gridSize), (int)(lastY + lastDy));
        if (nextPoint6.x < 0) nextPoint6.x = 0; if (nextPoint6.x > gridSize * columns) nextPoint6.x = (int) (gridSize * columns);
        if (nextPoint6.y < 0) nextPoint6.y = 0; if (nextPoint6.y > gridSize * rows) nextPoint6.y = (int) (gridSize * rows);
        Point nextPoint7 = new Point((int)(lastX + lastDx - gridSize), (int)(lastY + lastDy + gridSize));
        if (nextPoint7.x < 0) nextPoint7.x = 0; if (nextPoint7.x > gridSize * columns) nextPoint7.x = (int) (gridSize * columns);
        if (nextPoint7.y < 0) nextPoint7.y = 0; if (nextPoint7.y > gridSize * rows) nextPoint7.y = (int) (gridSize * rows);
        Point nextPoint8 = new Point((int)(lastX + lastDx), (int)(lastY + lastDy + gridSize));
        if (nextPoint8.x < 0) nextPoint8.x = 0; if (nextPoint8.x > gridSize * columns) nextPoint8.x = (int) (gridSize * columns);
        if (nextPoint8.y < 0) nextPoint8.y = 0; if (nextPoint8.y > gridSize * rows) nextPoint8.y = (int) (gridSize * rows);
        Point nextPoint9 = new Point((int)(lastX + lastDx + gridSize), (int)(lastY + lastDy + gridSize));
        if (nextPoint9.x < 0) nextPoint9.x = 0; if (nextPoint9.x > gridSize * columns) nextPoint9.x = (int) (gridSize * columns);
        if (nextPoint9.y < 0) nextPoint9.y = 0; if (nextPoint9.y > gridSize * rows) nextPoint9.y = (int) (gridSize * rows);

        VectorMove nextMove1 = new VectorMove(startPoint, nextPoint1, 0, 0);
        VectorMove nextMove2 = new VectorMove(startPoint, nextPoint2, 0, 0);
        VectorMove nextMove3 = new VectorMove(startPoint, nextPoint3, 0, 0);
        VectorMove nextMove4 = new VectorMove(startPoint, nextPoint4, 0, 0);
        VectorMove nextMove5 = new VectorMove(startPoint, nextPoint5, 0, 0);
        VectorMove nextMove6 = new VectorMove(startPoint, nextPoint6, 0, 0);
        VectorMove nextMove7 = new VectorMove(startPoint, nextPoint7, 0, 0);
        VectorMove nextMove8 = new VectorMove(startPoint, nextPoint8, 0, 0);
        VectorMove nextMove9 = new VectorMove(startPoint, nextPoint9, 0, 0);
        add(nextMove1);
        add(nextMove2);
        add(nextMove3);
        add(nextMove4);
        add(nextMove5);
        add(nextMove6);
        add(nextMove7);
        add(nextMove8);
        add(nextMove9);
    }
}
