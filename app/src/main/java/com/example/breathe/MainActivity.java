package com.example.breathe;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.breathe.util.Prefs;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView breathsText, timeText, sessionText, guideText;
    private Button startButton;
    private Prefs prefs;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.lotusImage);
        breathsText = findViewById(R.id.breathsTakenText);
        timeText = findViewById(R.id.lastBreathText);
        sessionText = findViewById(R.id.todayMinutesText);
        guideText = findViewById(R.id.guideText);
        prefs = new Prefs(this);

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);

        startIntroAnimation();

        sessionText.setText(prefs.getSessions() + " min today");
        breathsText.setText(prefs.getBreaths() + " Breaths");
        timeText.setText(prefs.getDate());



        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
                startButton.setVisibility(View.INVISIBLE);
            }
        });



    }

    private void startIntroAnimation() {
        ViewAnimator
                .animate(guideText)
                .scale(0, 1)
                .duration(1500)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideText.setText("Breathe");

                    }
                })
                .start();

    }

    private void startAnimation() {
        ViewAnimator
                .animate(imageView)
                .alpha(0,1)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideText.setText("Inhale... Exhale...");
                    }
                })
                .decelerate()
                .duration(1000)
                .thenAnimate(imageView)
                .scale(0.02f, 1.5f, 0.02f)
                .rotation(360)
                .repeatCount(5)
                .accelerate()
                .duration(5000)
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        if (mediaPlayer != null )
                            mediaPlayer.start();

                        startButton.setVisibility(View.VISIBLE);

                        guideText.setText("Good job");
                        imageView.setScaleX(1.0f);
                        imageView.setScaleY(1.0f);

                        prefs.setSessions(prefs.getSessions() + 1);
                        prefs.setBreaths(prefs.getBreaths() + 6);
                        prefs.setDate(System.currentTimeMillis());

                        //refresh activity
                        new CountDownTimer(2000, 1000) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                //put code to show ticking
                            }

                            @Override
                            public void onFinish() {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }.start();


                    }
                })
                .start();
    }
}
