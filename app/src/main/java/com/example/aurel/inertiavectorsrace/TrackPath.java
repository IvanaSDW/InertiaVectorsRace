package com.example.aurel.inertiavectorsrace;

import android.graphics.Paint;
import android.graphics.Path;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by aurel on 4/30/2016.
 */
public class TrackPath {
    private Path track;
    private float gridSize;

    public TrackPath() {
        setGridSize(gridSize);
        setTrack(track);
    }

    public Path getTrack() {
        return track;
    }

    public void setTrack(Path path) {
        this.track = path;
    }

    public void setGridSize(float gridSize) {
        this.gridSize = gridSize;
    }

    public Paint getPaint() {
        Paint paint;
        try {
            paint = (Paint) track.getClass().getMethod("getPaint").invoke(track);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            paint = new Paint();
        }
        return paint;
    }

    public Path getStartLine() {
        Path startLine;
        try {
            startLine = (Path) track.getClass().getMethod("getStartLine").invoke(track);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            startLine = new Path();
        }
        return startLine;
    }

    public Path getFinishLine() {
        Path finishLine;
        try {
            finishLine = (Path) track.getClass().getMethod("getFinishLine").invoke(track);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            finishLine = new Path();
        }
        return finishLine;
    }

    public Path getTrackPath() {
        Path trackPath;
        try {
            trackPath = (Path) track.getClass().getMethod("getTrackPath").invoke(track);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            trackPath = new Path();
        }
        return trackPath;
    }
}
