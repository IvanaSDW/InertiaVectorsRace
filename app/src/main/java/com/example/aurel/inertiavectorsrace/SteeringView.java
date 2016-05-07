package com.example.aurel.inertiavectorsrace;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by aurel on 5/2/2016.
 */
public class SteeringView extends View {
    private SteeringWheel steeringWheel;
    private float svSize;
    private Path touchedPoint;
    private int touchedMove;

    public SteeringView(Context context) {
        super(context);
        init(context, null);
    }

    public SteeringView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SteeringView, 0, 0);
        svSize = ta.getDimensionPixelSize(R.styleable.SteeringView_svSize, 5);
        steeringWheel = new SteeringWheel(svSize);
        touchedPoint = new Path();
        touchedMove = 5;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(resolveSizeAndState((int) svSize, widthMeasureSpec, 0), resolveSizeAndState((int) svSize, heightMeasureSpec, 0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(steeringWheel, steeringWheel.getPaint());
        canvas.drawPath(touchedPoint, steeringWheel.getTouchedPaint());
        Paint garbagePaint = new Paint();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (steeringWheel.getPt1().contains(event.getX(), event.getY())){
            touchedPoint = steeringWheel.getC1();
            touchedMove = 1;
        }
        if (steeringWheel.getPt2().contains(event.getX(), event.getY())){
            touchedPoint = steeringWheel.getC2();
            touchedMove = 2;
        }
        if (steeringWheel.getPt3().contains(event.getX(), event.getY())){
            touchedPoint = steeringWheel.getC3();
            touchedMove = 3;
        }
        if (steeringWheel.getPt4().contains(event.getX(), event.getY())){
            touchedPoint = steeringWheel.getC4();
            touchedMove = 4;
        }
        if (steeringWheel.getPt5().contains(event.getX(), event.getY())){
            touchedPoint = steeringWheel.getC5();
            touchedMove = 5;
        }
        if (steeringWheel.getPt6().contains(event.getX(), event.getY())){
            touchedPoint = steeringWheel.getC6();
            touchedMove = 6;
        }
        if (steeringWheel.getPt7().contains(event.getX(), event.getY())){
            touchedPoint = steeringWheel.getC7();
            touchedMove = 7;
        }
        if (steeringWheel.getPt8().contains(event.getX(), event.getY())){
            touchedPoint = steeringWheel.getC8();
            touchedMove = 8;
        }
        if (steeringWheel.getPt9().contains(event.getX(), event.getY())){
            touchedPoint = steeringWheel.getC9();
            touchedMove = 9;
        }
        invalidate();
        return true;
    }

    public int getTouchedMove() {
        return touchedMove;
    }
}
