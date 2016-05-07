package com.example.aurel.inertiavectorsrace;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by aurel on 5/2/2016.
 * Guarda todos los movimientos realizados en una vuelta de una carrera de vectores
 */
public class VectorLap {
    private ArrayList<VectorMove> tracedPath;

    public VectorLap() {
        tracedPath = new ArrayList<>();
    }

    public void addMove(VectorMove vectorMove){
        tracedPath.add(vectorMove);
    }

    public VectorMove getMove(int index){
        return tracedPath.get(index);
    }

    public ArrayList<VectorMove> getTracedPath() {
        return tracedPath;
    }

    public void setTracedPath(ArrayList<VectorMove> tracedPath) {
        this.tracedPath = tracedPath;
    }

    public long getTotalTime(){
        long elapsedTime = 0;
        for (VectorMove move :
                tracedPath) {
            elapsedTime += move.getElapsedTime();
        }
        return elapsedTime;
    }

    public float getTotalDistance(){
        float totalDistance = 0;
        for (VectorMove move :
                tracedPath) {
            totalDistance += move.getDistance();
        }
        return new BigDecimal(totalDistance).setScale(3, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public double getAverageSpeedPerMils(){
        return getTotalDistance() / getTotalTime();
    }
    public double getAverageSpeedPerSec(){
        return getTotalDistance() / (getTotalTime()/1000f);
    }
    public double getAverageSpeedPerMin(){
        return new BigDecimal(getTotalDistance() / (getTotalTime()/60000f)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
