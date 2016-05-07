package com.example.aurel.inertiavectorsrace;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

/**
 * Created by aurel on 5/2/2016.
 */
public class SteeringWheel extends Path {
    private Path c1, c2, c3, c4, c5, c6, c7, c8, c9;
    private RectF pt1, pt2, pt3, pt4, pt5, pt6, pt7, pt8, pt9;
    private Paint paint, touchedPaint;

    public SteeringWheel(float steerSize) {
        initFields();
        float radius = steerSize / 10f;
        float padding = (steerSize - (radius * 6)) / 6;
        float touchRectSize = steerSize / 3;
        setPaints();
        c1.addCircle(padding + radius, padding + radius, radius, Direction.CW);
        c2.addCircle(3 * (padding + radius), padding + radius, radius, Direction.CW);
        c3.addCircle(5 * (padding + radius), padding + radius, radius, Direction.CW);
        c4.addCircle(padding + radius, 3 * (padding + radius), radius,Direction.CW);
        c5.addCircle(3 * (padding + radius), 3 * (padding + radius), radius, Direction.CW);
        c6.addCircle(5 * (padding + radius), 3 * (padding + radius), radius, Direction.CW);
        c7.addCircle(padding + radius, 5 * (padding + radius), radius,Direction.CW);
        c8.addCircle(3 * (padding + radius), 5 * (padding + radius), radius, Direction.CW);
        c9.addCircle(5 * (padding + radius), 5 * (padding + radius), radius, Direction.CW);

        this.addPath(c1);
        this.addPath(c2);
        this.addPath(c3);
        this.addPath(c4);
        this.addPath(c5);
        this.addPath(c6);
        this.addPath(c7);
        this.addPath(c8);
        this.addPath(c9);

        pt1.set(0, 0, touchRectSize, touchRectSize);
        pt2.set(touchRectSize, 0, 2 * touchRectSize, touchRectSize);
        pt3.set(2 * touchRectSize,  0,  3 * touchRectSize, touchRectSize);
        pt4.set(0, touchRectSize, touchRectSize, 2 * touchRectSize);
        pt5.set(touchRectSize, touchRectSize, 2 * touchRectSize, 2 * touchRectSize);
        pt6.set(2 * touchRectSize, touchRectSize, 3 * touchRectSize, 2 * touchRectSize);
        pt7.set(0, 2 * touchRectSize, touchRectSize, 3 * touchRectSize);
        pt8.set(touchRectSize, 2 * touchRectSize, 2 * touchRectSize, 3 * touchRectSize);
        pt9.set(2 * touchRectSize, 2 * touchRectSize, 3 * touchRectSize, 3 * touchRectSize);

    }

    private void initFields() {
        c1 = new Path();
        c2 = new Path();
        c3 = new Path();
        c4 = new Path();
        c5 = new Path();
        c6 = new Path();
        c7 = new Path();
        c8 = new Path();
        c9 = new Path();
        pt1 = new RectF();
        pt2 = new RectF();
        pt3 = new RectF();
        pt4 = new RectF();
        pt5 = new RectF();
        pt6 = new RectF();
        pt7 = new RectF();
        pt8 = new RectF();
        pt9 = new RectF();

        paint = new Paint();
        touchedPaint = new Paint();

    }


    private void setPaints() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.LTGRAY);
        touchedPaint.setStyle(Paint.Style.FILL);
        touchedPaint.setColor(Color.parseColor("#90FC65"));
    }

    public Paint getPaint() {
        return paint;
    }

    public Paint getTouchedPaint() {
        return touchedPaint;
    }

    public RectF getPt1() {
        return pt1;
    }

    public RectF getPt2() {
        return pt2;
    }

    public RectF getPt3() {
        return pt3;
    }

    public RectF getPt4() {
        return pt4;
    }

    public RectF getPt5() {
        return pt5;
    }

    public RectF getPt6() {
        return pt6;
    }

    public RectF getPt7() {
        return pt7;
    }

    public RectF getPt8() {
        return pt8;
    }

    public RectF getPt9() {
        return pt9;
    }

    public Path getC1() {
        return c1;
    }

    public Path getC2() {
        return c2;
    }

    public Path getC3() {
        return c3;
    }

    public Path getC4() {
        return c4;
    }

    public Path getC5() {
        return c5;
    }

    public Path getC6() {
        return c6;
    }

    public Path getC7() {
        return c7;
    }

    public Path getC8() {
        return c8;
    }

    public Path getC9() {
        return c9;
    }
}
