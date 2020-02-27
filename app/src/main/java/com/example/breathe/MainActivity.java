package com.example.breathe;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
    private int breathPosition, timePosition;
    private Boolean isBreathing;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.lotusImage);
        cardView = findViewById(R.id.cardView);

        guideText = findViewById(R.id.guideText);
        cardView.setVisibility(View.INVISIBLE);
        prefs = new Prefs(this);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.values, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
                breathPosition = position;
                Log.d("position", String.valueOf(breathPosition));
                switch (breathPosition) {
                    case 0:
                        prefs.setInhale(2);
                        prefs.setInhalehold(0);
                        prefs.setExhaleHold(0);
                        prefs.setExhale(4);
                        return;
                    case 1:
                        prefs.setInhale(4);
                        prefs.setExhale(8);
                        prefs.setInhalehold(0);
                        prefs.setExhaleHold(0);
                        return;
                    case 2:
                        prefs.setInhale(5);
                        prefs.setExhale(10);
                        prefs.setInhalehold(0);
                        prefs.setExhaleHold(0);
                        return;
                    case 3:
                        prefs.setInhale(6);
                        prefs.setExhale(12);
                        prefs.setInhalehold(0);
                        prefs.setExhaleHold(0);
                        return;
//                    case 4:
//                        prefs.setInhale(2);
//                        prefs.setInhalehold(2);
//                        prefs.setExhale(4);
//                        prefs.setExhaleHold(0);
//                        return;
//                    case 5:
//                        prefs.setInhale(4);
//                        prefs.setInhalehold(4);
//                        prefs.setExhale(8);
//                        prefs.setExhaleHold(0);
//                        return;
//                    case 6:
//                        prefs.setInhale(5);
//                        prefs.setInhalehold(5);
//                        prefs.setExhale(10);
//                        prefs.setExhaleHold(0);
//                        return;
//                    case 7:
//                        prefs.setInhale(6);
//                        prefs.setInhalehold(6);
//                        prefs.setExhale(12);
//                        prefs.setExhaleHold(0);
//                        return;
//                    case 8:
//                        prefs.setInhale(2);
//                        prefs.setInhalehold(0);
//                        prefs.setExhaleHold(2);
//                        prefs.setExhale(4);
//                        return;
//                    case 9:
//                        prefs.setInhale(4);
//                        prefs.setInhalehold(0);
//                        prefs.setExhaleHold(4);
//                        prefs.setExhale(8);
//                        return;
//                    case 10:
//                        prefs.setInhale(5);
//                        prefs.setInhalehold(0);
//                        prefs.setExhaleHold(5);
//                        prefs.setExhale(10);
//                        return;
//                    case 11:
//                        prefs.setInhale(6);
//                        prefs.setInhalehold(0);
//                        prefs.setExhaleHold(6);
//                        prefs.setExhale(12);
//                        return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });

        Spinner timeSpinner = findViewById(R.id.spinnerTimer);
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(this,
                R.array.time_values, R.layout.simple_spinner_item_time);
        timeAdapter.setDropDownViewResource(R.layout.simple_spinner_item_time);
        timeSpinner.setAdapter(timeAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
                timePosition = position;
                for (int i = 0; i<10; i++) {
                    if (timePosition == i) {
                        prefs.setTime(i + 1);

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });


//        mediaPlayer = new MediaPlayer();
//        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);

//        startIntroAnimation();

        isBreathing = false;
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isBreathing) {
                    startAnimation();
                    CountDownTimer countDownTimer = new CountDownTimer(prefs.getTime() * 60000 + 100, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            updateTimer((int) millisUntilFinished / 1000);
                        }

                        @Override
                        public void onFinish() {

                        }
                    };
                    countDownTimer.start();
                    startButton.setText("STOP");
                    isBreathing = true;
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    isBreathing = false;
                    startButton.setText("START");
                }

            }
        });



    }

    private void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondString = "0" + secondString;
        }

        guideText.setText(Integer.toString(minutes) + ":" + secondString);
    }

    private void startIntroAnimation() {
        ViewAnimator
                .animate(guideText)
                .scale(0, 1)
                .duration(1500)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideText.setText("Breathe for " + prefs.getTime() + "min");

                    }
                })
                .start();

    }

    private void startAnimation() {

        ViewAnimator
                .animate(imageView)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        cardView.setVisibility(View.VISIBLE);
                    }
                })
                .accelerate()
                .duration(prefs.getInhale() * 1000)
                .thenAnimate(imageView)
                .scale(0.02f, 1.0f, 0.2f)
                .rotation(360)
                .decelerate()
                .duration(prefs.getInhaleHold() + prefs.getExhale() * 1000)
                .repeatMode(ViewAnimator.RESTART)
                .repeatCount((prefs.getTime() * 60) / (prefs.getInhale() + prefs.getInhaleHold() + prefs.getExhale() + prefs.getExhaleHold()))
                .duration((prefs.getInhale() + prefs.getExhale() + prefs.getExhaleHold() + prefs.getInhaleHold()) * 1000)
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        guideText.setText("Good Job");
                        imageView.setScaleX(1.0f);
                        imageView.setScaleY(1.0f);


                        prefs.setSessions(prefs.getSessions() + 1);
                        prefs.setBreaths(prefs.getBreaths() + 1);
                        prefs.setDate(System.currentTimeMillis());

                        //refresh activity
                        new CountDownTimer(2000, 1000) {

                            @Override
                            public void onTick(long l) {
                                //put code to show ticking... 1, 2, 3...
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
