package com.example.aurel.inertiavectorsrace;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

/**
 * Created by aurel on 5/1/2016.
 */
public class RunnerPath extends Path {
    private Path runnerTip, tip, foo1, foo2;
    private Float gridSaize;
    private Paint paint = new Paint();

    public RunnerPath(Float gridSaize) {
        this.gridSaize = gridSaize;
        lineTo(-6,-2);
        moveTo(0,0);
        lineTo(-6,2);
        scaleSelf(gridSaize);
        tip = new Path();
        tip.addCircle(0,0,1, Direction.CW);
        addPath(tip);
        setPaint();

        foo1 = new Path();
        foo2 = new Path();
        foo1.addCircle(600, 380, 60, Direction.CW);
        foo2.addCircle(650, 380, 60, Direction.CW);

    }

    public Path getFoo1(){
        return foo1;
    }
    public Path getFoo2(){
        return foo2;
    }

    public Paint getPaint() {
        return paint;
    }

    private void setPaint() {
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1.6f);

    }

    public Path getTip() {
        return tip;
    }

    private void scaleSelf(Float gridSaize) {
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(gridSaize*0.2f, gridSaize*0.2f);
        this.transform(scaleMatrix);
    }
}
