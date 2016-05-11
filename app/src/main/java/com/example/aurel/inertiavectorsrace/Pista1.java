package com.example.aurel.inertiavectorsrace;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

import java.util.ArrayList;

/**
 * Created by aurel on 4/30/2016.
 */
public class Pista1 extends InertiaRaceTrack {

    public Pista1(Float gridSize, int columns, int rows) {
        super(gridSize, columns, rows);
    }

    @Override
    void setStartPoint(Point startPoint, int direction) {
        startPoint.set(5, 22);
        super.direction = Dir.UP;
    }

    @Override
    void setStartLine(Path startLine) {
        startLine.moveTo(3, 22);
        startLine.lineTo(8, 22);
    }

    @Override
    void setFinisLine(Path finishLine) {
        finishLine.moveTo(3, 29.6f);
        finishLine.lineTo(8, 29.6f);
        finishLine.lineTo(8, 30f);
        finishLine.lineTo(3, 30f);
        finishLine.close();
    }

    @Override
    void setTrackPath(ArrayList<RectF> chicanasArray) {
        RectF reg1 = new RectF(0, 0, 38, 2);
        chicanasArray.add(reg1);

        RectF reg2 = new RectF(0, 7, 3, 30);
        chicanasArray.add(reg2);

        RectF reg3 = new RectF(8, 7, 38, 14);
        chicanasArray.add(reg3);

        RectF reg4 = new RectF(8, 28, 28, 30);
        chicanasArray.add(reg4);

        RectF reg5 = new RectF(45, 0, 50, 15);
        chicanasArray.add(reg5);

        RectF reg6 = new RectF(8, 14, 23, 23);
        chicanasArray.add(reg6);

        RectF reg7 = new RectF(28, 20, 50, 30);
        chicanasArray.add(reg7);

    }

    @Override
    void setPaints(Paint paint, Paint chicanaPaint) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(0);
        chicanaPaint.setStyle(Paint.Style.STROKE);
        chicanaPaint.setColor(Color.YELLOW);
        chicanaPaint.setStrokeWidth(8);
        chicanaPaint.setAlpha(200);
        //DashPathEffect dashPathEffect = new DashPathEffect(new float[]{10, 2, 8, 2, 6, 2, 4, 2, 2, 2, 4, 2, 6, 2, 8, 2, 10}, 0);
        //PathDashPathEffect pathDashPathEffect = new PathDashPathEffect(this, 50, 50, PathDashPathEffect.Style.MORPH);
        //paint.setPathEffect(pathDashPathEffect);
    }

    @Override
    public int getDirection() {
        return super.getDirection();
    }
}
