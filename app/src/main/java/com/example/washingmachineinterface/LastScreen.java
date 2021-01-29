package com.example.washingmachineinterface;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class LastScreen extends AppCompatActivity {
    String program, temp, dry, stages;
    boolean prewash, rinse;

    TextView txv_function, txv_timer, txv_temperature, txv_program, txv_stage, txv_stagePart, txt_start_pause;
    ImageButton b_start_pause, b_stop;
    CountDownTimer timer;
    Dialog dialog;    // for popup
    boolean isWorking = true;

    // milliseconds per stage (set to 1 minute for presentation)
    final long PREWASH = 60*1000;
    final long MAIN = 60*1000;
    final long RINSE_DRY = 60*1000;

    // main washing and dry stages are standard
    long time_in_mills = MAIN + RINSE_DRY;
    long initialTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(MainActivity.isSetToNightMode()){
            setTheme(R.style.DarkScreen);
        }else{
            setTheme(R.style.LightScreen);
        }
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_last_screen);

        dialog = new Dialog(this);

        //Get washing details
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        program = bundle.getString("program");
        dry = bundle.getString("dry");
        temp = bundle.getString("temp");
        prewash = bundle.getBoolean("prewash");
        rinse = bundle.getBoolean("rinse");

        // keep only number of rotations
        dry = dry.substring(0, dry.trim().indexOf(" στροφές")+1);

        stages = " 1/"+(2 + (prewash ? 1 : 0) + (rinse ? 1 : 0));

        // compute total time of washing
        time_in_mills += (prewash ? PREWASH : 0);
        time_in_mills += (rinse ? RINSE_DRY : 0);
        initialTime = time_in_mills;

        txt_start_pause = findViewById(R.id.txt_start_pause);
        b_start_pause = findViewById(R.id.b_start_pause);
        b_stop = findViewById(R.id.b_stop);
        txv_function = findViewById(R.id.title_function);
        txv_timer = findViewById(R.id.txv_timer);
        txv_temperature = findViewById(R.id.title_temperature);
        txv_program = findViewById(R.id.title_sp_program);
        txv_stage = findViewById(R.id.title_stage);
        txv_stagePart = findViewById(R.id.title_stagepart);

        txv_temperature.setText(temp);
        txv_program.setText(program);
        txv_stage.setText((prewash ? R.string.radio_prewash : R.string.s_main_washing));
        txv_stagePart.setText(stages);

        b_start_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWorking) {
                    b_start_pause.setBackgroundResource(R.drawable.b_play);
                    txt_start_pause.setText(R.string.s_start);
                    txv_function.setText(R.string.s_on_pause);
                    stopTimer();
                    isWorking = false;
                }
                else {
                    b_start_pause.setBackgroundResource(R.drawable.b_pause);
                    txt_start_pause.setText(R.string.s_pause);
                    txv_function.setText(R.string.s_functioning);
                    startTimer();
                    isWorking = true;
                }
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showPopup(getCurrentFocus());
            }
        });

        //Show detergent Reminder Popup first
        detergentReminder();
    }

    public void detergentReminder(){
        dialog.setContentView(getLayoutInflater().inflate(R.layout.popup_detergent_reminder, null));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView image = dialog.findViewById(R.id.detergent_image);
        if(prewash){
            image.setImageResource(R.drawable.rem_prewash_detergent);
        }else{
            image.setImageResource(R.drawable.rem_main_detergent);
        }

        Button ready = dialog.findViewById(R.id.b_ready);
        Button close = dialog.findViewById(R.id.b_close);

        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startTimer();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startTimer();
            }
        });

        dialog.show();
    }

    // Popup
    public void showPopup(View v){
        stopTimer();
        b_start_pause.setBackgroundResource(R.drawable.b_play);
        txt_start_pause.setText(R.string.s_start);
        txv_function.setText(R.string.s_on_pause);

        Button yes;
        Button no;
        dialog.setContentView(R.layout.popup_last_screen);
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);

        yes = dialog.findViewById(R.id.b_yes_stop);
        no = dialog.findViewById(R.id.b_no_continue);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                b_start_pause.setBackgroundResource(R.drawable.b_pause);
                txt_start_pause.setText(R.string.s_pause);
                txv_function.setText(R.string.s_functioning);
                isWorking = true;
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to main activity
                Intent main = new Intent(LastScreen.this, MainActivity.class);
                startActivity(main);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void startTimer(){
        timer = new CountDownTimer(time_in_mills, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // update timer each second
                time_in_mills = millisUntilFinished;
                updateStage();
                updateTimer();
            }
            @Override
            public void onFinish() {
                // on finish popup that washer has finished
                // pause washing
                b_start_pause.setBackgroundResource(R.drawable.b_play);
                txt_start_pause.setText(R.string.s_start);
                txv_function.setText(R.string.s_on_pause);

                dialog.setContentView(R.layout.popup_finish);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button b_ok = dialog.findViewById(R.id.b_ok);
                b_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent main = new Intent(LastScreen.this, MainActivity.class);
                        startActivity(main);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }.start();
    }

    public void stopTimer(){
        timer.cancel();
    }

    public void updateTimer(){
        int hours = (int) (time_in_mills/1000) / 3600;
        int mins = (int) ((time_in_mills/1000) % 3600)/ 60;
        int secs = (int) (time_in_mills/1000) % 60;
        String time = "" + (hours<10 ? "0"+hours : hours) +
                      ":" + (mins<10 ? "0"+mins : mins) +
                      ":" + (secs<10 ? "0"+secs : secs);
        txv_timer.setText(time);
    }

    private void updateStage(){
        // keep previous stage
        String previous = (String) txv_stage.getText();

        if (prewash) {
            if (time_in_mills >= initialTime - PREWASH)
                txv_stage.setText(R.string.radio_prewash);
            else if (time_in_mills >= initialTime - (PREWASH+MAIN))
                txv_stage.setText(R.string.s_main_washing);
            else if (time_in_mills >= initialTime - (PREWASH+MAIN+RINSE_DRY))
                if (rinse)
                    txv_stage.setText(getResources().getString(R.string.radio_rinse).replace("\n"," "));
                else if (dry.equals("0"))
                    txv_stage.setText(R.string.s_drainage);
                else
                    txv_stage.setText(R.string.radio_dry);
            else if (time_in_mills >= initialTime - (PREWASH+MAIN+2*RINSE_DRY))
                if (dry.equals("0"))
                    txv_stage.setText(R.string.s_drainage);
                else
                    txv_stage.setText(R.string.radio_dry);
        }
        else {
            if (time_in_mills >= initialTime - MAIN)
                txv_stage.setText(R.string.s_main_washing);
            else if (time_in_mills >= initialTime - (MAIN+RINSE_DRY))
                if (rinse)
                    txv_stage.setText(getResources().getString(R.string.radio_rinse).replace("\n"," "));
                else if (dry.equals("0"))
                    txv_stage.setText(R.string.s_drainage);
                else
                    txv_stage.setText(R.string.radio_dry);
            else if (time_in_mills >= initialTime - (MAIN+2*RINSE_DRY))
                if (dry.equals("0"))
                    txv_stage.setText(R.string.s_drainage);
                else
                    txv_stage.setText(R.string.radio_dry);
        }

        // update stage part if stage has changed
        if (!txv_stage.getText().equals(previous)){
            stages = stages.replace(stages.charAt(1), (char) (Integer.parseInt(String.valueOf(stages.charAt(1)))+1 +'0'));
            txv_stagePart.setText(stages);
        }
    }
}