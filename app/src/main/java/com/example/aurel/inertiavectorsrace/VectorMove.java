package com.example.aurel.inertiavectorsrace;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Representa un movimiento realizado por el jugador  en un turno determindado
 * Created by aurel on 5/2/2016.
 */
public class VectorMove implements Parcelable {
    private Point fromPoint, toPoint;
    private long startTime, endTime;
    private Region moveRegion;
    private Path inflatedMove;

    public VectorMove() {

    }

    public VectorMove(Point fromPoint, Point toPoint, long startTime, long endTime) {
        this.fromPoint = fromPoint;
        this.toPoint = toPoint;
        this.startTime = startTime;
        this.endTime = endTime;
        createMoveRegion();
    }

    private void createMoveRegion() {
        inflatedMove = new Path();
        inflatedMove.moveTo((float)(fromPoint.x - Math.sin(getAngleRad())), (float) (fromPoint.y + Math.cos(getAngleRad())));
        inflatedMove.lineTo((float)(toPoint.x - Math.sin(getAngleRad())), (float) (toPoint.y + Math.cos(getAngleRad())));
        inflatedMove.lineTo((float)(toPoint.x + Math.sin(getAngleRad())), (float) (toPoint.y - Math.cos(getAngleRad())));
        inflatedMove.lineTo((float)(fromPoint.x + Math.sin(getAngleRad())), (float) (fromPoint.y - Math.cos(getAngleRad())));
        inflatedMove.close();
        //inflatedMove.setFillType(Path.FillType.EVEN_ODD);
        RectF moveRect = new RectF();
        inflatedMove.computeBounds(moveRect, true);
        moveRegion = new Region();
        moveRegion.setPath(inflatedMove, new Region((int) (moveRect.left), (int)(moveRect.top), (int)(moveRect.right), (int)(moveRect.bottom)));
    }

    protected VectorMove(Parcel in) {
        fromPoint = in.readParcelable(Point.class.getClassLoader());
        toPoint = in.readParcelable(Point.class.getClassLoader());
        startTime = in.readLong();
        endTime = in.readLong();
    }

    public static final Creator<VectorMove> CREATOR = new Creator<VectorMove>() {
        @Override
        public VectorMove createFromParcel(Parcel in) {
            return new VectorMove(in);
        }

        @Override
        public VectorMove[] newArray(int size) {
            return new VectorMove[size];
        }
    };

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public Point getFromPoint() {
        return fromPoint;
    }

    public void setFromPoint(Point fromPoint) {
        this.fromPoint = fromPoint;
    }

    public Point getToPoint() {
        return toPoint;
    }

    public void setToPoint(Point toPoint) {
        this.toPoint = toPoint;
    }

    public float getDx() {
        return (float) (toPoint.x - fromPoint.x);
    }

    public float getDy() {
        float dy = toPoint.y - fromPoint.y;
        return dy;
    }

    public double getDistance() {
        return Math.hypot(getDx(), getDy());
        //return Math.sqrt(Math.pow(getDx(), 2) + Math.pow(getDy(), 2));
    }


    public long getElapsedTime() {
        return endTime - startTime;
    }


    public double getSpeed() {
        return getDistance() / getElapsedTime();
    }

    public float getAngleDeg() {
        return (float) Math.toDegrees(getAngleRad());
    }

    public float getAngleRad() {
        return (float) Math.atan2(getDy(),getDx());
    }

    public Path getPath() {
        Path path = new Path();
        path.moveTo(fromPoint.x, fromPoint.y);
        path.lineTo(toPoint.x, toPoint.y);
        return inflatedMove;
    }

    public Region getRegion() {
        return moveRegion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(fromPoint, flags);
        dest.writeParcelable(toPoint, flags);
        dest.writeLong(startTime);
        dest.writeLong(endTime);
    }
}
