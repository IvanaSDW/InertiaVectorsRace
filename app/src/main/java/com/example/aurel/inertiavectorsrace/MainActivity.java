package com.example.aurel.inertiavectorsrace;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
    //Flags
    private boolean setMode, goMode, finisMode;
    private boolean autoPilot;
    //Views
    private RacingTrackView racingTrackView;
    private RaceChronometerView raceChronometerView;
    private Button startButton, goButton;
    private View rooView;
    private SteeringView steeringView;
    //Vars
    private long startTime;
    private android.os.Handler handler;
    private long speed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        init();
        setListeners();
        handler = new Handler();
    }

    private void init() {
        //Flags
        setMode = false;
        goMode = false;
        finisMode = false;
        autoPilot = false;
        speed = 1000;

    }

    private void setListeners() {
        steeringView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                racingTrackView.setTouchedMove(steeringView.getTouchedMove());
                racingTrackView.setFlags(null, true, null);
                //racingTrackView.computePosibleMoves(null);
                racingTrackView.invalidate();
                return false;
            }
        });
        goButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (autoPilot) autoPilot = false;
                else engageAutopilot();
                return true;
            }
        });
    }

    private void engageAutopilot() {
        autoPilot = true;
        handler.post(new AutoPilot());
    }

    private void bindViews() {
        rooView = findViewById(R.id.rootView);
        racingTrackView = (RacingTrackView) findViewById(R.id.view);
        raceChronometerView = (RaceChronometerView) findViewById(R.id.myChrono);
        steeringView = (SteeringView) findViewById(R.id.steeringView);
        startButton = (Button) findViewById(R.id.startBtn);
        goButton = (Button) findViewById(R.id.goBtn);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            rooView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}

    }

    public void startBtnPressed(View view) {
        startButton.setVisibility(View.GONE);
        raceChronometerView.toggleStartStop();
        goMode = true;
        setMode = false;
        finisMode = false;
        steeringView.setVisibility(View.VISIBLE);
        goButton.setVisibility(View.VISIBLE);
        racingTrackView.setLapStartTime(raceChronometerView.getStartTime());
        racingTrackView.computePosibleMoves();
        racingTrackView.setFlags(null, true, false);
    }

    public void goButtonPressed(View view) {
        racingTrackView.setMoveFinishTime(System.currentTimeMillis());
        racingTrackView.computeMovement();
        racingTrackView.setFlags(null, false, true);
        if (racingTrackView.isLapFinished()) {
            raceChronometerView.toggleStartStop();
            goButton.setVisibility(View.GONE);
            steeringView.setVisibility(View.GONE);
        }
    }

    private class AutoPilot implements Runnable {

        @Override
        public void run() {
            if (autoPilot && !racingTrackView.isLapFinished()){
                goButtonPressed(null);
                handler.postDelayed(new AutoPilot(), speed);
            }
        }
    }
}
