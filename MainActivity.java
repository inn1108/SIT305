package com.android.timer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private final static int NOT_START = 0;
    private final static int ING = 1;
    private final static int PAUSE = 2;


    private int status = 0;


    private long hasCountTime = 0;

    private Disposable countdownDisposable;

    private TextView timeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences("test",MODE_MULTI_PROCESS);

        TextView tips = findViewById(R.id.tips);

        String dataStr = sp.getString("data","");
        if(!TextUtils.isEmpty(dataStr)) {
            tips.setText(dataStr);
        }

        timeTv = findViewById(R.id.timer);

        EditText edit_text = findViewById(R.id.edit_text);


        findViewById(R.id.Start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (status) {
                    case ING:
                        showShortToast("That is start");
                        break;

                    case PAUSE:

                        status = ING;
                        resetTimer(hasCountTime);
                        break;

                    default:
                        if(TextUtils.isEmpty(edit_text.getText().toString().trim())) {
                            showShortToast("please write this task name");
                        } else {

                            status = ING;
                            resetTimer(0);
                        }
                        break;
                }
            }
        });

        findViewById(R.id.Stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (status) {
                    case PAUSE:
                        showShortToast(" That is ready pause");
                        break;
                    default:

                        status = PAUSE;
                        countdownDisposable.dispose();
                        break;
                }
            }
        });

        findViewById(R.id.keep).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (status) {
                    case NOT_START:
                        showShortToast("That will ready stop");
                        break;
                    default:
                        if(TextUtils.isEmpty(edit_text.getText().toString().trim())) {
                            showShortToast("please input task name");
                        } else {

                            status = NOT_START;

                            hasCountTime = 0;

                            countdownDisposable.dispose();

                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("data","You spent "+ timeTv.getText().toString() +" on " + edit_text.getText().toString() + " last time.");
                            editor.apply();

                            String dataStr = sp.getString("data","");
                            if(!TextUtils.isEmpty(dataStr)) {
                                tips.setText(dataStr);
                            }
                        }
                        break;
                }
            }
        });
    }

    public void showShortToast(String text){
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }


    private void resetTimer(long start){
        countdownDisposable = Flowable.intervalRange(start, 999999, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long time) throws Exception {
                        hasCountTime = time;
                        String hh = new DecimalFormat("00").format(time / 3600);
                        String mm = new DecimalFormat("00").format(time % 3600 / 60);
                        String ss = new DecimalFormat("00").format(time % 60);
                        timeTv.setText(hh + ":" + mm + ":" + ss);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                    }
                })
                .subscribe();
    }
}