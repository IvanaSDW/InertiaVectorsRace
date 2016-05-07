package com.example.aurel.inertiavectorsrace;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.text.ParseException;
import java.util.logging.Handler;

import static android.content.Context.WINDOW_SERVICE;


public class RacingTrackView extends View {
    private static final long CRASH_PENALTY = 5000;
    private static final int FINISH_POSTER_WIDTH = 500;
    private Rect finishPosterRect;
    private Rect finishBGRect;
    //Set-able fields
    private InertiaRaceTrack raceTrack;
    private Path runnerPath;
    private int rows, columns;
    //¬¬

    //Flags
    private boolean cluesEnabled;
    private boolean moveAllowed;
    private boolean performMove;
    private boolean lapFinished;
    private boolean crashMayOccur, crashWillOccur, crashOccurred, gettingOutOfCrash;
    //¬¬

    //Paints
    private Paint gridBoundsPaint = new Paint();
    private Paint gridPaint = new Paint();
    private Paint startPaint = new Paint();
    private Paint finishPaint = new Paint();
    private Paint runnerPaint = new Paint();
    private Paint lapPaint = new Paint();
    private Paint finishPosterPaint = new Paint();
    private Paint finishBGPaint = new Paint();
    private Paint finishTitlePaint = new Paint();
    private Paint finishTextPaint = new Paint();

    //Vars
    private VectorLap lap;
    private VectorMove lastMove;
    private PosibleMovesSet posibleMoves, movesNext;
    private PosibleMovesPath movesPath, movesPathNext;
    private long lapStartTime, moveStartTime;
    private long moveFinishTime, prevMoveFinishTime;
    private int touchedMove;
    private int gridTotalWidth, gridTotalHeight;
    private float gridSize;
    private float minZoom;
    private final float MAX_ZOOM = 3f;
    private RectF gridBounds;
    private float movToX, movToY, lastTouchX, lastTouchY;
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1f;
    private float leftClearance, rightClearance;
    private final int NONE = 0, DRAG = 1, ZOOM = 2;
    private int touchMode = 0;
    private Bitmap runner;
    private Bitmap runner1;
    private float angleToRotate, currentAngle;
    private float arrowLength, arrowAngle;
    private Matrix runnerRotator, runnerMatrix;
    private Region fullClip;
    private Matrix currentRunnerPos;

    //Helpers
    SVGPathParser pathParser;

    //Fonts
    private Typeface finishFont;

    //Constructors
    public RacingTrackView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RacingTrackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public RacingTrackView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }
    // ** end Constructors

    private void init(Context context, AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.RacingTrackView, defStyle, 0);
        int gridColor = a.getColor(R.styleable.RacingTrackView_gridColor, Color.BLACK);
        float gridThickness = a.getDimensionPixelSize(R.styleable.RacingTrackView_gridThickness, 0);
        a.recycle();
        // ** end load attributes

        //Measure device screen
        WindowManager wm = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point lowerRightCorner = new Point();
        display.getSize(lowerRightCorner);

        //UI Parameters
        columns = 50;
        rows = 30;
        gridTotalWidth = lowerRightCorner.x;
        gridTotalHeight = lowerRightCorner.y;
        gridSize = gridTotalWidth / 50;

        //GameParameters
        raceTrack = new Pista1(gridSize, columns, rows); //Set the track to use
        pathParser = new SVGPathParser();
        runnerPath = new Path();
        try {
            runnerPath = pathParser.parsePath(Paths.TINY_CAR); //Set the runner figure to use
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RectF runnerRect = new RectF();
        runnerPath.computeBounds(runnerRect, true);
        float runnerScale = gridSize * 2f / runnerRect.width();
        runnerMatrix = new Matrix();
        runnerMatrix.setScale(runnerScale, runnerScale);
        runnerPath.transform(runnerMatrix);
        runnerPath.computeBounds(runnerRect, true);
        runnerPath.offset(raceTrack.getStartPoint().x * gridSize - runnerRect.width(),
                raceTrack.getStartPoint().y * gridSize - (runnerRect.height() / 2));
        lastMove = new VectorMove(new Point((int) (raceTrack.getStartPoint().x * gridSize - gridSize),
                (int) (raceTrack.getStartPoint().y * gridSize)), new Point((int) (raceTrack.getStartPoint().x * gridSize),
                (int) (raceTrack.getStartPoint().y * gridSize)), 0, 0);
        touchedMove = 5;
        lap = new VectorLap();
        posibleMoves = new PosibleMovesSet(lastMove, gridSize, crashOccurred, rows, columns);
        movesNext = new PosibleMovesSet(posibleMoves.get(touchedMove - 1), gridSize, crashOccurred, rows, columns);
        movesPath = new PosibleMovesPath(posibleMoves, gridSize);
        movesPathNext = new PosibleMovesPath(movesNext, gridSize);
        prevMoveFinishTime = lapStartTime;
        currentAngle = 0;
        runnerRotator = new Matrix();
        currentRunnerPos = new Matrix();
        fullClip = new Region();
        fullClip.set(getLeft(), getTop(), getRight(), getBottom());
        finishBGRect = new Rect(0, 0, lowerRightCorner.x, lowerRightCorner.y);
        finishPosterRect = new Rect(finishBGRect.centerX() - (FINISH_POSTER_WIDTH / 2), finishBGRect.centerY() - (int)(FINISH_POSTER_WIDTH / 1.62 / 2),
                finishBGRect.centerX() + (FINISH_POSTER_WIDTH / 2), finishBGRect.centerY() + (int)(FINISH_POSTER_WIDTH / 1.62 / 2));

        //Init Flags
        cluesEnabled = false;
        moveAllowed = false;
        performMove = false;
        crashMayOccur = false;
        crashOccurred = false;
        crashWillOccur = false;
        gettingOutOfCrash = false;
        lapFinished = false;
        //¬¬

        scaleGestureDetector = new ScaleGestureDetector(getContext(), new zoomGestureListener());
        gridBounds = new RectF(0, 0, gridSize * columns, gridSize * rows);

        //Init fonts
        finishFont = Typeface.createFromAsset(getResources().getAssets(), "fonts/digitaldream_fat.ttf");
        Typeface clockFont = Typeface.createFromAsset(getResources().getAssets(), "fonts/sf_digitalreadout_light_obliq.ttf");


        //Init Paints
        gridBoundsPaint.setAntiAlias(true);
        gridBoundsPaint.setColor(gridColor);
        gridBoundsPaint.setAlpha(255);
        gridBoundsPaint.setStyle(Paint.Style.STROKE);
        gridBoundsPaint.setStrokeWidth(3);

        gridPaint.setAntiAlias(true);
        gridPaint.setColor(gridColor);
        gridPaint.setAlpha(130);
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(gridThickness);

        startPaint.setColor(Color.GREEN);
        startPaint.setStyle(Paint.Style.STROKE);
        startPaint.setStrokeWidth(8);
        startPaint.setAntiAlias(true);

        finishPaint.setColor(Color.RED);
        finishPaint.setStyle(Paint.Style.STROKE);
        finishPaint.setStrokeWidth(8);
        finishPaint.setAntiAlias(true);

        runnerPaint.setAntiAlias(true);
        runnerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        runnerPaint.setColor(Color.RED);

        lapPaint.setStyle(Paint.Style.STROKE);
        lapPaint.setColor(Color.GREEN);
        lapPaint.setAlpha(200);
        lapPaint.setAntiAlias(true);

        finishBGPaint.setStyle(Paint.Style.FILL);
        finishBGPaint.setColor(Color.DKGRAY);
        finishBGPaint.setAlpha(200);
        finishBGPaint.setAntiAlias(true);

        finishPosterPaint.setStyle(Paint.Style.FILL);
        finishPosterPaint.setColor(getResources().getColor(R.color.colorPrimary));
        finishPosterPaint.setAlpha(240);
        finishPosterPaint.setAntiAlias(true);

        finishTextPaint.setTypeface(finishFont);
        finishTextPaint.setFakeBoldText(true);
        finishTextPaint.setColor(Color.WHITE);
        finishTextPaint.setAntiAlias(true);
        finishTextPaint.setTextSize(18);

        finishTitlePaint.setTypeface(finishFont);
        finishTitlePaint.setColor(Color.BLACK);
        finishTitlePaint.setTextAlign(Paint.Align.CENTER);
        finishTitlePaint.setFakeBoldText(true);
        finishTitlePaint.setAntiAlias(true);
        finishTitlePaint.setTextSize(30);
        //¬¬
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = resolveSizeAndState(gridTotalWidth, widthMeasureSpec, 0);
        int height = resolveSizeAndState(gridTotalHeight, heightMeasureSpec, 0);
        setMeasuredDimension(width, height);
        minZoom = Math.min((getMeasuredHeight()/(float)gridTotalHeight), (getMeasuredWidth()/(float)gridTotalWidth));
        scaleFactor = minZoom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);
        canvas.translate(movToX / scaleFactor, movToY / scaleFactor);
        drawGrid(canvas);
        drawTrack(canvas);
        drawChicanas(canvas);
        drawLap(canvas);
        drawRunner(canvas);
        if (performMove) {
            drawMove(canvas);
        }
        if (moveAllowed && !lapFinished) {
            drawPosibleMoves(canvas);
        }

        if (lapFinished) drawFinished(canvas);
        canvas.restore();
    }

    private void drawFinished(Canvas canvas) {
        canvas.drawRect(finishBGRect, finishBGPaint);
        canvas.drawRect(finishPosterRect, finishPosterPaint);
        canvas.drawText("FINISHED!!", finishPosterRect.centerX(), finishPosterRect.top + finishPosterRect.height()*0.17f, finishTitlePaint);
        canvas.drawText("Distance", finishPosterRect.left + finishPosterRect.width()*0.07f, finishPosterRect.top + finishPosterRect.height()*0.35f, finishTextPaint);
        canvas.drawText("" + lap.getTotalDistance() + " px", finishPosterRect.left + finishPosterRect.width()*0.55f, finishPosterRect.top + finishPosterRect.height()*0.35f, finishTextPaint);
        canvas.drawText("Time", finishPosterRect.left + finishPosterRect.width()*0.07f, finishPosterRect.top + finishPosterRect.height()*0.5f, finishTextPaint);
        canvas.drawText(toHMSC(lap.getTotalTime()), finishPosterRect.left + finishPosterRect.width()*0.55f, finishPosterRect.top + finishPosterRect.height()*0.5f, finishTextPaint);
        canvas.drawText("Average speed", finishPosterRect.left + finishPosterRect.width()*0.07f, finishPosterRect.top + finishPosterRect.height()*0.65f, finishTextPaint);
        canvas.drawText(String.valueOf(lap.getAverageSpeedPerMin()) + " px/min", finishPosterRect.left + finishPosterRect.width()*0.55f, finishPosterRect.top + finishPosterRect.height()*0.65f, finishTextPaint);
        canvas.drawText("Your score", finishPosterRect.left + finishPosterRect.width()*0.07f, finishPosterRect.top + finishPosterRect.height()*0.8f, finishTextPaint);

    }

    private String toHMSC(long timeMils) {
        int hours = (int) (timeMils / 3600000);
        long resto = timeMils % 3600000;
        String strHours = String.valueOf(hours) + ":";
        int minutes = (int) (resto / 60000);
        resto = resto % 60000;
        String strMinutes = String .valueOf(minutes) + "'";
        int seconds = (int) (resto / 1000);
        resto = resto % 1000;
        String strSeconds = String.valueOf(seconds) + "\"";
        int hundreths = (int) resto/10;
        String strHundreths = String.valueOf(hundreths);
        return strHours + strMinutes + strSeconds + strHundreths;
    }

    private void drawChicanas(Canvas canvas) {
        for (RectF chicana :
                raceTrack.getChicanasArray()) {
            canvas.drawRect(chicana, raceTrack.getChicanaPaint());
        }
    }

    private void drawRunner(Canvas canvas) {
        canvas.drawPath(runnerPath, runnerPaint);
    }

    private void drawLap(Canvas canvas) {
        for (VectorMove move :
                lap.getTracedPath()) {
            canvas.drawPath(move.getPath(), lapPaint);
        }
    }

    private void drawMove(Canvas canvas) {
        canvas.drawPath(movesPath.getMovesPath().get(touchedMove-1), movesPath.getPaint());
        setFlags(null, true, false);
    }

    private void drawPosibleMoves(Canvas canvas) {
        canvas.drawPath(movesPath, movesPath.getPaint());
        canvas.drawPath(movesPath.getMovesPath().get(touchedMove-1), movesPath.getMoveLinePaint());
        canvas.drawPath(movesPath.getMovePoints().get(touchedMove-1), movesPath.getChosenPointPaint());
        if (cluesEnabled){
            canvas.drawPath(movesPathNext, movesPathNext.getPaintNext());
            canvas.drawPath(movesPathNext.getMovesPath().get(4), movesPathNext.getMoveLineNextPaint());
        }
    }

    private void drawTrack(Canvas canvas) {
        canvas.drawPath(raceTrack.getStartLine(), startPaint);
        canvas.drawPath(raceTrack.getTrackPath(), raceTrack.getPaint() );
        canvas.drawPath(raceTrack.getFinishLine(), finishPaint);
    }

    private void drawGrid(Canvas canvas) {
        canvas.drawRect(gridBounds, gridBoundsPaint);
        for (int i = 0; i < gridBounds.height(); i+=gridSize) {
            canvas.drawLine(0, i, gridBounds.width(), i, gridPaint);
        }
        for (int i = 0; i < gridBounds.width(); i+=gridSize) {
            canvas.drawLine(i, 0, i, gridBounds.height(), gridPaint);
        }
    }

    public void setLapStartTime(long lapStartTime) {
        this.lapStartTime = lapStartTime;
        prevMoveFinishTime = lapStartTime;
    }

    public void setMoveStartTime(long moveStartTime) {
        this.moveStartTime = moveStartTime;
    }

    public void setMoveFinishTime(long moveFinishTime) {
        this.moveFinishTime = moveFinishTime;
    }

    public void computeMovement() {
        float pivotX; float pivotY;
        VectorMove moveToPerform = posibleMoves.get(touchedMove-1);
        moveToPerform.setStartTime(prevMoveFinishTime);
        moveToPerform.setEndTime(moveFinishTime);
        prevMoveFinishTime = moveFinishTime;
        Region crashRegion = new Region();
        Region hitFinish = new Region();
        crashOccurred = crashRegion.op(raceTrack.getFullRegion(), moveToPerform.getRegion(), Region.Op.INTERSECT);
        lapFinished = hitFinish.op(raceTrack.getFinishRegion(), moveToPerform.getRegion(), Region.Op.INTERSECT);
        if (isLapFinished())
        if (gettingOutOfCrash){
            crashOccurred = false;
            gettingOutOfCrash = false;
        }
        if (crashOccurred) {
            Point pointA = new Point();
            pointA.set(moveToPerform.getFromPoint().x, moveToPerform.getFromPoint().y);
            Point pointB = new Point();
            gettingOutOfCrash = true;
            if (lastMove.getAngleDeg() <= 0 && lastMove.getAngleDeg() >= -90) { //se estrello hacia arriba y hacia la derecha
                pointB.set((int) (fitToGrids(crashRegion.getBounds().left, false) - gridSize), (int) (fitToGrids(crashRegion.getBounds().bottom, true) + gridSize));
            } else if (lastMove.getAngleDeg() <= -90 && lastMove.getAngleDeg() >= -180){  // se estrello hacia arriba - izquierda
                pointB.set((int) (fitToGrids(crashRegion.getBounds().right, true) + gridSize), (int) (fitToGrids(crashRegion.getBounds().bottom, true) + gridSize));
            } else if (lastMove.getAngleDeg() >= 90 && lastMove.getAngleDeg() <= 180) { // se estrello hacia abajo - izquierda
                pointB.set((int) (fitToGrids(crashRegion.getBounds().right, true) + gridSize), (int) (fitToGrids(crashRegion.getBounds().top, false) - gridSize));
            } else if (lastMove.getAngleDeg() >= 0 && lastMove.getAngleDeg() <= 90) { // se estrello hacia abajo - derecha
                pointB.set((int) (fitToGrids(crashRegion.getBounds().left, false) - gridSize), (int) (fitToGrids(crashRegion.getBounds().top, false) - gridSize));
            }
            VectorMove fixedMove = new VectorMove(pointA, pointB, moveToPerform.getEndTime(), moveToPerform.getEndTime() + CRASH_PENALTY);
            runnerPath.offset(fixedMove.getDx(), fixedMove.getDy());
            angleToRotate = fixedMove.getAngleDeg() - currentAngle;
            currentAngle = fixedMove.getAngleDeg();
            pivotX = fixedMove.getToPoint().x;
            pivotY = fixedMove.getToPoint().y;
            lap.addMove(moveToPerform);
            lap.addMove(fixedMove);
            lastMove = fixedMove;
        } else {
            runnerPath.offset(moveToPerform.getDx(), moveToPerform.getDy());
            angleToRotate = moveToPerform.getAngleDeg() - currentAngle;
            currentAngle = moveToPerform.getAngleDeg();
            pivotX = moveToPerform.getToPoint().x;
            pivotY = moveToPerform.getToPoint().y;
            lap.addMove(moveToPerform);
            lastMove = moveToPerform;
        }
        int color = (crashOccurred ? Color.BLACK : Color.RED);
        runnerPaint.setColor(color);
        RectF runnerRect = new RectF();
        runnerPath.computeBounds(runnerRect, true);
        currentRunnerPos = runnerRotator;
        runnerRotator.reset();
        runnerRotator.postRotate(angleToRotate, pivotX, pivotY);
        runnerPath.transform(runnerRotator);
        if (!isLapFinished()) computePosibleMoves();
    }

    public void computePosibleMoves() {
        posibleMoves = new PosibleMovesSet(lastMove, gridSize, crashOccurred, rows, columns);
        movesPath = new PosibleMovesPath(posibleMoves, gridSize);
        for (Path possibleMove :
                movesPath.getMovesPath()) {
            Region moveRegion = new Region();
            Region possibleCrashRegion = new Region();
            moveRegion.setPath(possibleMove,fullClip);
            crashMayOccur = possibleCrashRegion.op(moveRegion, raceTrack.getFullRegion(), Region.Op.INTERSECT);
            if (crashMayOccur){
                getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Warning, crash may occur!!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        if (cluesEnabled && !crashOccurred) {
            movesNext = new PosibleMovesSet(posibleMoves.get(touchedMove-1), gridSize, crashOccurred, rows, columns);
            movesPathNext = new PosibleMovesPath(movesNext, gridSize);
        }
    }

    public void computeLap() {

    }

    private int fitToGrids(float value, boolean up) {
        if (up) return (int) ((value - value % gridSize) + gridSize);
        else return (int) (value - value % gridSize);
    }

    public void setFlags(Boolean cluesEnabled, Boolean moveAllowed, Boolean performMove){
        if (cluesEnabled != null) {
            this.cluesEnabled = cluesEnabled;
        }
        if (moveAllowed != null) {
            this.moveAllowed = moveAllowed;
        }
        if (performMove != null) {
            this.performMove = performMove;
        }
        invalidate();
    }

    private class zoomGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.min(Math.max(minZoom, scaleFactor), MAX_ZOOM);
            invalidate();
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (true == true) {
            scaleGestureDetector.onTouchEvent(event);
            switch (event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN: {
                    final float x = event.getX();
                    final float y = event.getY();

                    lastTouchX = x;
                    lastTouchY = y;
                    touchMode = NONE;
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    final float x = event.getX();
                    final float y = event.getY();

                    final float dX = x - lastTouchX;
                    final float dY = y - lastTouchY;

                    lastTouchX = x;
                    lastTouchY = y;

                    movToX += dX;
                    movToY += dY;

                    float maxPanClearance = 0.1f;
                    if (gridBounds.width()*scaleFactor > getMeasuredWidth()) {
                        leftClearance = gridBounds.width()* maxPanClearance;
                    } else {
                        leftClearance = (getMeasuredWidth() - gridBounds.width()*scaleFactor) / 2f;
                    }
                    float topClearance;
                    if (gridBounds.height()*scaleFactor > getMeasuredHeight()) {
                        topClearance = gridBounds.height() * maxPanClearance;
                    } else {
                        topClearance = (getMeasuredHeight() - gridBounds.height()*scaleFactor) / 2f;
                    }

                    if (gridBounds.width()*scaleFactor > getMeasuredWidth()) {
                        rightClearance = ((gridBounds.width()*scaleFactor)-(getMeasuredWidth() - leftClearance)) * -1;
                    }else {
                        rightClearance = (getMeasuredWidth() - gridBounds.width()*scaleFactor) / 2f;
                    }
                    float bottomClearance;
                    if (gridBounds.height()*scaleFactor > getMeasuredHeight()) {
                        bottomClearance = ((gridBounds.height()*scaleFactor)-(getMeasuredHeight() - leftClearance)) * -1;
                    } else {
                        bottomClearance = (getMeasuredHeight() - gridBounds.height()*scaleFactor) / 2f;
                    }

                    movToX = movToX > leftClearance ? leftClearance : movToX;
                    movToX = movToX < rightClearance ? rightClearance : movToX;
                    movToY = movToY > topClearance ? topClearance : movToY;
                    movToY = movToY < bottomClearance ? bottomClearance : movToY;
                    touchMode = DRAG;
                    break;
                }
                case MotionEvent.ACTION_POINTER_DOWN:{
                    touchMode = ZOOM;
                    break;
                }
                case MotionEvent.ACTION_POINTER_UP:{
                    touchMode = DRAG;
                    break;
                }
                case MotionEvent.ACTION_UP:{
                    break;
                }
            }
            if (touchMode == DRAG || touchMode == ZOOM){
                invalidate();
            }
        } else {
        }

        return true;
    }

    public void setTouchedMove(int touchedMove) {
        this.touchedMove = touchedMove;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public boolean isLapFinished() {
        return lapFinished;
    }
}
