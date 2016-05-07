package com.example.aurel.inertiavectorsrace;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by aurel on 4/29/2016.
 */
public class RaceChronometerView extends View {
    //Flags
    private boolean timeRunning = false;
    private Handler handler;
    private int textColor;
    private float textSize, chronoHeight, chronoWidth;
    private RectF caseRect;
    private RectF hoursRect, minutesRect, secondsRect, hundrethsRect;
    private Paint casePaint;
    private Paint backPaint;
    private Paint hoursPaint, minutesPaint, secondsPaint, hundrethsPaint;
    private LinearGradient gradientDisplay, gradientText;
    private String strHours = "00";
    private String strMinutes = "00";
    private String strSeconds = "00";
    private String strHundreths = "00";
    private float esquinaX;
    private float esquinaY;
    private float displayPadding;
    private Paint paint;
    private long startTime;
    private long elapsedTime;

    public RaceChronometerView(Context context) {
        super(context);
    }

    public RaceChronometerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCase(canvas);
        drawTime(canvas);

    }

    private void drawTime(Canvas canvas) {
        //canvas.drawRect(hoursRect, paint);
        canvas.drawText(strHours, hoursRect.centerX(), hoursRect.bottom, hoursPaint);
        //canvas.drawRect(minutesRect, paint);
        canvas.drawText(strMinutes, minutesRect.centerX(), minutesRect.bottom-minutesRect.height()*0.1f, minutesPaint);
        //canvas.drawRect(secondsRect, paint);
        canvas.drawText(strSeconds, secondsRect.centerX(), secondsRect.bottom-secondsRect.height()*0.1f, secondsPaint);
        //canvas.drawRect(hundrethsRect, paint);
        canvas.drawText(strHundreths, hundrethsRect.centerX(), hundrethsRect.bottom, hundrethsPaint);

    }

    private void drawCase(Canvas canvas) {
        caseRect.set(esquinaX, esquinaY, getPaddingLeft()+casePaint.getStrokeWidth()/2 + chronoWidth,
                getPaddingTop()+casePaint.getStrokeWidth()/2 + chronoHeight);
        canvas.drawRect(caseRect, backPaint);
        canvas.drawRect(caseRect, casePaint);

    }

    public void toggleStartStop(){
        timeRunning = !timeRunning ? true : false;
        startTime = System.currentTimeMillis();
        elapsedTime = 0;
        handler.post(new MoveOneHundreth());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float ancho = caseRect.width() + casePaint.getStrokeWidth()  + getPaddingLeft() + getPaddingRight();
        float alto = caseRect.height() + casePaint.getStrokeWidth() + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(resolveSizeAndState((int) ancho, widthMeasureSpec, 0),
                resolveSizeAndState((int) alto, heightMeasureSpec, 0));
        invalidate();
    }

    private void init(AttributeSet attrs) {
        handler = new Handler();
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.RaceChronometerView);
        chronoWidth = array.getDimensionPixelSize(R.styleable.RaceChronometerView_chronoWidth, 150);
        chronoHeight = chronoWidth*0.3f;
        textColor = array.getColor(R.styleable.RaceChronometerView_textColor, Color.GREEN);
        textSize = array.getDimensionPixelSize(R.styleable.RaceChronometerView_textSize, 20);
        array.recycle();

        //Fonts
        //Typeface clockFont = Typeface.createFromAsset(getResources().getAssets(), "fonts/digital_7.ttf");
        //Typeface clockFont = Typeface.createFromAsset(getResources().getAssets(), "fonts/digital_7_italic.ttf");
        //Typeface clockFont = Typeface.createFromAsset(getResources().getAssets(), "fonts/digital_7_mono_italic.ttf");
        //Typeface clockFont = Typeface.createFromAsset(getResources().getAssets(), "fonts/digital_7_mono.ttf");
        //Typeface clockFont = Typeface.createFromAsset(getResources().getAssets(), "fonts/digitaldream.ttf");
        //Typeface clockFont = Typeface.createFromAsset(getResources().getAssets(), "fonts/digitaldream_fat.ttf");
        //Typeface clockFont = Typeface.createFromAsset(getResources().getAssets(), "fonts/digitaldream_narrow.ttf");
        //Typeface clockFont = Typeface.createFromAsset(getResources().getAssets(), "fonts/led_real.ttf");
        //Typeface clockFont = Typeface.createFromAsset(getResources().getAssets(), "fonts/led_sas.ttf");
        //Typeface clockFont = Typeface.createFromAsset(getResources().getAssets(), "fonts/ledreali.ttf");
        //Typeface clockFont = Typeface.createFromAsset(getResources().getAssets(), "fonts/tickingtimebomb_bb.ttf");
        //Typeface clockFont = Typeface.createFromAsset(getResources().getAssets(), "fonts/trs_million_rg.ttf");
        Typeface clockFont = Typeface.createFromAsset(getResources().getAssets(), "fonts/sf_digitalreadout_light_obliq.ttf");

        //Paints & gradients
        gradientDisplay = new LinearGradient(getPaddingLeft(), getPaddingTop(), getPaddingLeft(),
                getPaddingTop() + chronoHeight*0.6f, Color.WHITE, Color.BLACK, Shader.TileMode.CLAMP);
        gradientText = new LinearGradient(getPaddingLeft(), getPaddingTop(), getPaddingLeft(),
                getPaddingTop() + chronoHeight, Color.parseColor("#46915C"), Color.parseColor("#2DEB66"), Shader.TileMode.CLAMP);
        casePaint = new Paint();
        casePaint.setAntiAlias(true);
        casePaint.setStyle(Paint.Style.STROKE);
        casePaint.setColor(Color.parseColor("#8D968C"));
        casePaint.setAlpha(140);
        casePaint.setStrokeWidth(8f);
        //casePaint.setDither(true);
        //casePaint.setShader(gradientDisplay);
        backPaint = new Paint();
        backPaint.setAntiAlias(true);
        backPaint.setStyle(Paint.Style.FILL);
        backPaint.setDither(true);
        backPaint.setShader(gradientDisplay);
        //backPaint.setColor(Color.parseColor("#41484A"));
        //backPaint.setShadowLayer(10, -15, -15, Color.BLACK);
        hoursPaint = new Paint();
        hoursPaint.setShader(gradientText);
        //hoursPaint.setColor(Color.parseColor("#7AE866"));
        hoursPaint.setTextAlign(Paint.Align.CENTER);
        hoursPaint.setTypeface(clockFont);
        minutesPaint = new Paint();
        minutesPaint.setShader(gradientText);
        //minutesPaint.setColor(Color.parseColor("#7AE866"));
        minutesPaint.setTextAlign(Paint.Align.CENTER);
        minutesPaint.setTypeface(clockFont);
        secondsPaint = new Paint();
        secondsPaint.setShader(gradientText);
        //secondsPaint.setColor(Color.parseColor("#7AE866"));
        secondsPaint.setTextAlign(Paint.Align.CENTER);
        secondsPaint.setTypeface(clockFont);
        hundrethsPaint = new Paint();
        hundrethsPaint.setShader(gradientText);
        //hundrethsPaint.setColor(Color.parseColor("#7AE866"));
        hundrethsPaint.setTextAlign(Paint.Align.CENTER);
        hundrethsPaint.setTypeface(clockFont);

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.YELLOW);

        //Shapes
        caseRect = new RectF();
        caseRect.set(getLeft(), getTop(), getLeft()+chronoWidth, getTop()+chronoHeight);
        displayPadding = chronoWidth * 0.05f;
        esquinaX = getPaddingLeft()+casePaint.getStrokeWidth()/2;
        esquinaY = getPaddingTop()+casePaint.getStrokeWidth()/2;
        hoursRect = new RectF();
        minutesRect = new RectF();
        secondsRect = new RectF();
        hundrethsRect = new RectF();
        hoursRect.set(esquinaX + displayPadding, esquinaY + displayPadding,
                esquinaX + displayPadding + chronoWidth*0.15f, esquinaY + displayPadding + chronoWidth*0.13f);
        hoursPaint.setTextSize(hoursRect.height()*1.07f);
        minutesRect.set(hoursRect.right, esquinaY + displayPadding, hoursRect.right + chronoWidth*0.28f,
                esquinaY + displayPadding + (chronoHeight - 2*displayPadding));
        minutesPaint.setTextSize(minutesRect.height()*1.18f);
        secondsRect.set(minutesRect.right, esquinaY + displayPadding, minutesRect.right + chronoWidth*0.32f,
                esquinaY + displayPadding + (chronoHeight - 2*displayPadding));
        secondsPaint.setTextSize(secondsRect.height()*1.18f);
        hundrethsRect.set(secondsRect.right, esquinaY + displayPadding,
                secondsRect.right + chronoWidth*0.15f, esquinaY + displayPadding + chronoWidth*0.13f);
        hundrethsPaint.setTextSize(hundrethsRect.height()*1.07f);

    }
    public class MoveOneHundreth implements Runnable{

        @Override
        public void run() {
            if (timeRunning){
                elapsedTime = System.currentTimeMillis() - startTime;
                int hours = (int) (elapsedTime / 3600000);
                long resto = elapsedTime % 3600000;
                strHours = String.valueOf(hours) + ":";
                int minutes = (int) (resto / 60000);
                resto = resto % 60000;
                strMinutes = String .valueOf(minutes) + "'";
                int seconds = (int) (resto / 1000);
                resto = resto % 1000;
                strSeconds = String.valueOf(seconds) + "\"";
                int hundreths = (int) resto/10;
                strHundreths = String.valueOf(hundreths);
                handler.postDelayed( new MoveOneHundreth(), 10);
                invalidate();
            }
        }
    }

    public long getStartTime() {
        return startTime;
    }
}
