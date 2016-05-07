package com.example.aurel.inertiavectorsrace;

import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;

import java.util.ArrayList;

/**
 * Created by aurel on 4/29/2016.
 */
public abstract class InertiaRaceTrack extends Path {
    private float gridSize;
    private final float CHICANA_SIZE = 3;
    private Path startLine = new Path(), finishLine = new Path(), trackPath = new Path();
    private Point startPoint = new Point();
    private Paint paint = new Paint();
    private Paint chicanaPaint = new Paint();
    private Matrix scaleMatrix;
    private ArrayList<Region> regionsArray;
    private ArrayList<RectF> chicanasArray;
    private Region clip, fullRegion, finishRegion;

    public InertiaRaceTrack(float gridSize, int columns, int rows) {
        regionsArray = new ArrayList<>();
        chicanasArray = new ArrayList<RectF>();
        fullRegion = new Region();
        finishRegion = new Region();

        this.gridSize = gridSize;
        scaleMatrix = new Matrix();
        scaleMatrix.setScale(gridSize, gridSize, 0, 0);

        setClip(columns, rows);
        setStartLine(startLine);
        setTrackPath(chicanasArray);
        createChicanas(chicanasArray);
        setFinisLine(finishLine);
        setStartPoint(startPoint);
        setFullRegion(fullRegion, scaleMatrix);
        setPaints(paint, chicanaPaint);

        scaleSelf();
    }

    protected void setFullRegion(Region fullRegion, Matrix scaleMatrix){
        Path fullPath = getTrackPath();
        fullPath.addPath(getStartLine());
        //fullPath.addPath(getFinishLine());
        fullPath.transform(scaleMatrix);
        fullRegion.setPath(fullPath, getClip());
        Path finishPath = getFinishLine();
        finishPath.transform(scaleMatrix);
        finishRegion.setPath(finishPath, getClip());
    }

    public void setClip(int columns, int rows) {
        clip = new Region(0, 0, (int)(columns * gridSize), (int)(rows * gridSize));
    }

    abstract void setStartPoint(Point startPoint);

    abstract void setPaints(Paint paint, Paint chicanaPaint);

    abstract void setStartLine(Path startLine);

    abstract void setFinisLine(Path finishLine);

    abstract void setTrackPath(ArrayList<RectF> chicanasArray);

    public Path scaleSelf(){
        this.transform(scaleMatrix);
        startLine.transform(scaleMatrix);
        //finishLine.transform(scaleMatrix);
        //trackPath.transform(scaleMatrix);
        return this;
    }

    public void createChicanas(ArrayList<RectF> chicanasArray){
        RectF block = new RectF();
        Region region = new Region();
        float chicanaSize = CHICANA_SIZE / gridSize;
        for (RectF chicana :
                chicanasArray) {
            block.set(chicana.left + chicanaSize, chicana.top + chicanaSize, chicana.right - chicanaSize, chicana.bottom - chicanaSize);
            trackPath.addRect(block, Direction.CW);
            scaleMatrix.mapRect(block);
            scaleMatrix.mapRect(chicana);
            region.set((int)(block.left), (int)(block.top), (int)(block.right), (int)(block.bottom));
            regionsArray.add(region);
        }
    }

    public ArrayList<Region> getRegionsArray() {
        return regionsArray;
    }

    public ArrayList<RectF> getChicanasArray() {
        return chicanasArray;
    }

    public Paint getPaint() {
        return paint;
    }

    public Paint getChicanaPaint() {
        return chicanaPaint;
    }

    public Path getStartLine() {
        return startLine;
    }

    public Path getFinishLine() {
        return finishLine;
    }

    public Path getTrackPath() {
        return trackPath;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Region getClip() {
        return clip;
    }

    public Region getFullRegion() {
        return fullRegion;
    }

    public Region getFinishRegion() { return finishRegion; }

}
