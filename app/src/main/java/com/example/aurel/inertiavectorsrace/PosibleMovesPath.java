package com.example.aurel.inertiavectorsrace;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by aurel on 5/2/2016.
 */
public class PosibleMovesPath extends Path {
    private Path c1, c2, c3, c4, c5, c6, c7, c8, c9;
    private ArrayList<Path> movesPath;
    private ArrayList<Path> movePoints; //Puntos representados por circulos de radio = radius
    private Path v1, v2, v3, v4, v5, v6, v7, v8, v9;
    private Paint paint, paintNext, touchedPaint, moveLinePaint, chosenPointPaint, moveLineNextPaint;

    public PosibleMovesPath(PosibleMovesSet movesSet, float gridSize) {
        initFields();
        float radius = gridSize / 4f;
        setPaints();
        c1.addCircle(movesSet.get(0).getToPoint().x, movesSet.get(0).getToPoint().y, radius, Direction.CW);
        c2.addCircle(movesSet.get(1).getToPoint().x, movesSet.get(1).getToPoint().y, radius, Direction.CW);
        c3.addCircle(movesSet.get(2).getToPoint().x, movesSet.get(2).getToPoint().y, radius, Direction.CW);
        c4.addCircle(movesSet.get(3).getToPoint().x, movesSet.get(3).getToPoint().y, radius, Direction.CW);
        c5.addCircle(movesSet.get(4).getToPoint().x, movesSet.get(4).getToPoint().y, radius, Direction.CW);
        c6.addCircle(movesSet.get(5).getToPoint().x, movesSet.get(5).getToPoint().y, radius, Direction.CW);
        c7.addCircle(movesSet.get(6).getToPoint().x, movesSet.get(6).getToPoint().y, radius, Direction.CW);
        c8.addCircle(movesSet.get(7).getToPoint().x, movesSet.get(7).getToPoint().y, radius, Direction.CW);
        c9.addCircle(movesSet.get(8).getToPoint().x, movesSet.get(8).getToPoint().y, radius, Direction.CW);

        this.addPath(c1);
        this.addPath(c2);
        this.addPath(c3);
        this.addPath(c4);
        this.addPath(c5);
        this.addPath(c6);
        this.addPath(c7);
        this.addPath(c8);
        this.addPath(c9);

        movePoints.add(c1);
        movePoints.add(c2);
        movePoints.add(c3);
        movePoints.add(c4);
        movePoints.add(c5);
        movePoints.add(c6);
        movePoints.add(c7);
        movePoints.add(c8);
        movePoints.add(c9);


        Point startPoint = movesSet.get(0).getFromPoint();
        v1.moveTo(startPoint.x, startPoint.y); v1.lineTo(movesSet.get(0).getToPoint().x, movesSet.get(0).getToPoint().y);
        v2.moveTo(startPoint.x, startPoint.y); v2.lineTo(movesSet.get(1).getToPoint().x, movesSet.get(1).getToPoint().y);
        v3.moveTo(startPoint.x, startPoint.y); v3.lineTo(movesSet.get(2).getToPoint().x, movesSet.get(2).getToPoint().y);
        v4.moveTo(startPoint.x, startPoint.y); v4.lineTo(movesSet.get(3).getToPoint().x, movesSet.get(3).getToPoint().y);
        v5.moveTo(startPoint.x, startPoint.y); v5.lineTo(movesSet.get(4).getToPoint().x, movesSet.get(4).getToPoint().y);
        v6.moveTo(startPoint.x, startPoint.y); v6.lineTo(movesSet.get(5).getToPoint().x, movesSet.get(5).getToPoint().y);
        v7.moveTo(startPoint.x, startPoint.y); v7.lineTo(movesSet.get(6).getToPoint().x, movesSet.get(6).getToPoint().y);
        v8.moveTo(startPoint.x, startPoint.y); v8.lineTo(movesSet.get(7).getToPoint().x, movesSet.get(7).getToPoint().y);
        v9.moveTo(startPoint.x, startPoint.y); v9.lineTo(movesSet.get(8).getToPoint().x, movesSet.get(8).getToPoint().y);
        movesPath.add(v1);
        movesPath.add(v2);
        movesPath.add(v3);
        movesPath.add(v4);
        movesPath.add(v5);
        movesPath.add(v6);
        movesPath.add(v7);
        movesPath.add(v8);
        movesPath.add(v9);

    }

    public Path getV1() {
        return v1;
    }

    public Path getV2() {
        return v2;
    }

    public Path getV3() {
        return v3;
    }

    public Path getV4() {
        return v4;
    }

    public Path getV5() {
        return v5;
    }

    public Path getV6() {
        return v6;
    }

    public Path getV7() {
        return v7;
    }

    public Path getV8() {
        return v8;
    }

    public Path getV9() {
        return v9;
    }

    public ArrayList<Path> getMovesPath() {
        return movesPath;
    }

    public ArrayList<Path> getMovePoints() {
        return movePoints;
    }

    private void setPaints() {
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.parseColor("#F238B1"));
        paint.setAlpha(140);
        paint.setStrokeWidth(3);

        paintNext.setStyle(Paint.Style.STROKE);
        paintNext.setColor(Color.LTGRAY);
        paintNext.setAlpha(170);
        paintNext.setStrokeWidth(2);

        chosenPointPaint.setStyle(Paint.Style.STROKE);
        chosenPointPaint.setColor(Color.parseColor("#42BF08"));
        chosenPointPaint.setAlpha(180);
        chosenPointPaint.setStrokeWidth(3);

        touchedPaint.setStyle(Paint.Style.FILL);
        touchedPaint.setColor(Color.parseColor("#90FC65"));

        moveLinePaint.setStyle(Paint.Style.STROKE);
        moveLinePaint.setStrokeWidth(3);
        moveLinePaint.setColor(Color.BLACK);

        moveLineNextPaint.setStyle(Paint.Style.STROKE);
        moveLineNextPaint.setStrokeWidth(3);
        moveLineNextPaint.setColor(Color.LTGRAY);
        moveLineNextPaint.setAlpha(150);
    }

    private void initFields() {
        paint = new Paint();
        paintNext = new Paint();
        touchedPaint = new Paint();
        moveLinePaint = new Paint();
        chosenPointPaint = new Paint();
        moveLineNextPaint = new Paint();
        c1 = new Path();
        c2 = new Path();
        c3 = new Path();
        c4 = new Path();
        c5 = new Path();
        c6 = new Path();
        c7 = new Path();
        c8 = new Path();
        c9 = new Path();
        v1 = new Path();
        v2 = new Path();
        v3 = new Path();
        v4 = new Path();
        v5 = new Path();
        v6 = new Path();
        v7 = new Path();
        v8 = new Path();
        v9 = new Path();
        movesPath = new ArrayList<>(9);
        movePoints = new ArrayList<>(9);
    }

    public Paint getPaint() {
        return paint;
    }

    public Paint getPaintNext() {
        return paintNext;
    }

    public Paint getTouchedPaint() {
        return touchedPaint;
    }

    public Paint getMoveLinePaint() {
        return moveLinePaint;
    }

    public Paint getChosenPointPaint() {
        return moveLinePaint;
    }

    public Paint getMoveLineNextPaint() {
        return moveLineNextPaint;
    }
}
