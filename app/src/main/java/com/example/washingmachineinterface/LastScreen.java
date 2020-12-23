package com.example.washingmachineinterface;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class LastScreen extends AppCompatActivity {
    TextView txv_function;
    TextView txv_timer;
    TextView txv_temperature;
    TextView txv_program;
    ImageButton b_start_pause;
    ImageButton b_stop;
    CountDownTimer timer;
    Dialog dialog;    // for popup
    boolean isWorking = true;
    ArrayList<Object> inputs;

    /* time_in_mills holds the time shown in the activity
       1 sec = 1000 milliseconds
       1 min = 60 seconds = 60 * 1000 mills (60000)
       1 hour = 60 mins = 60 * 60000 mills (3600000)*/
    long time_in_mills = 2 * 60 * 60000; // 2 hours

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_last_screen);
        dialog = new Dialog(this);

        /* TODO get inputs from previous activity (time, Name, Temperature, and Stages)
         * the time should either initially be or be converted into milliseconds*/

        // example of input (time(ex. 2mins), name, temperature, Prewash, MainWash, Stir, WringOut)
        inputs = new ArrayList<Object>(Arrays.asList(time_in_mills,"Βαμβακερά","40 ℃","Πρόπλυση","Κύρια Πλύση","Στύψιμο"));
        // TODO the time will be distributed between the stages
        int num_stages = inputs.size()-3;  //how many stages
        long time_stage = time_in_mills/num_stages;  //time per stage (if time is distributed)

        b_start_pause = findViewById(R.id.b_start_pause);
        b_stop = findViewById(R.id.b_stop);
        txv_function = findViewById(R.id.title_function);
        txv_timer = findViewById(R.id.txv_timer);
        txv_temperature = findViewById(R.id.title_temperature);
        txv_program = findViewById(R.id.title_sp_program);

        txv_temperature.setText((String)inputs.get(2)); //40 ℃
        txv_program.setText((String)inputs.get(1));     //Βαμβακερά

        b_start_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWorking) {
                    b_start_pause.setBackgroundResource(R.drawable.b_play);
                    txv_function.setText(R.string.s_on_pause);
                    stopTimer();
                    isWorking = false;
                }
                else {
                    b_start_pause.setBackgroundResource(R.drawable.b_pause);
                    txv_function.setText(R.string.s_functioning);
                    startTimer();
                    isWorking = true;
                }
            }
        });
        startTimer();
    }

    // Popup
    public void showPopup(View v){
        stopTimer();
        b_start_pause.setBackgroundResource(R.drawable.b_play);
        txv_function.setText(R.string.s_on_pause);

        TextView text;
        Button yes;
        Button no;
        dialog.setContentView(R.layout.popup_last_screen);

        yes = dialog.findViewById(R.id.b_yes_stop);
        no = dialog.findViewById(R.id.b_no_continue);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                b_start_pause.setBackgroundResource(R.drawable.b_pause);
                txv_function.setText(R.string.s_functioning);
                isWorking = true;
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
                b_start_pause.setBackgroundResource(R.drawable.b_play);
                txv_function.setText(R.string.s_on_pause);
                isWorking = false;
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
                updateTimer();
            }
            @Override
            public void onFinish() {
                // TODO on finish popup that washer has finished
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
}