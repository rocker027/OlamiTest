package com.coors.olamitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ai.olami.android.IRecorderSpeechRecognizerListener;
import ai.olami.android.RecorderSpeechRecognizer;
import ai.olami.cloudService.APIConfiguration;
import ai.olami.cloudService.APIResponse;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String appKey = "f452b94a07dd4b1784287a7a32cc4ede";
    public static final String appSecret = "f608d70cccf44decb94a0508ec90bc97";
    private static final String TAG = MainActivity.class.getSimpleName();
    private APIConfiguration config;
    private Button btnStart;
    private Button btnStop;
    private TextView textView;
    private RecorderSpeechRecognizer recognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initOlami();
        recognizer = RecorderSpeechRecognizer.create(new IRecorderSpeechRecognizerListener() {
            @Override
            public void onRecordStateChange(RecorderSpeechRecognizer.RecordState state) {
                Log.d(TAG, "onRecordStateChange: "+state);
            }

            @Override
            public void onRecognizeStateChange(RecorderSpeechRecognizer.RecognizeState state) {
                Log.d(TAG, "onRecognizeStateChange: "+state);
            }

            @Override
            public void onRecognizeResultChange(APIResponse response) {
                Log.d(TAG, "onRecognizeResultChange: "+response.toString());
            }

            @Override
            public void onRecordVolumeChange(int volumeValue) {
                Log.d(TAG, "onRecordVolumeChange: "+volumeValue);
            }

            @Override
            public void onServerError(APIResponse response) {
                Log.d(TAG, "onServerError: "+response.toString());
            }

            @Override
            public void onError(RecorderSpeechRecognizer.Error error) {
                Log.d(TAG, "onError: " + error.toString());
            }

            @Override
            public void onException(Exception e) {
                Log.d(TAG, "onException: " + e.toString());
            }
        }, config);
    }

    private void findViews() {
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        textView = findViewById(R.id.textView);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    private void initOlami() {
        config = new APIConfiguration(appKey, appSecret, APIConfiguration.LOCALIZE_OPTION_TRADITIONAL_CHINESE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                try {
                    Toast.makeText(this,"開啟麥克風",Toast.LENGTH_SHORT).show();
                    recognizer.start();
                } catch (InterruptedException e) {
                    Toast.makeText(this,"開啟麥克風 Error",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case R.id.btn_stop:
                recognizer.stop();
                Toast.makeText(this,"停止麥克風",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
