package com.dyzs.compassservant;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements CompassServant.ServantListener{

    private HandlerThread mHandlerThread;
    private String mHtName = "compass_servant";
    private Handler mLooper;
    private Handler mUIHandler;
    private static int MESSAGE = 0x110;
    CompassServant compass_servant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initHandlerThread();
        compass_servant = (CompassServant) findViewById(R.id.compass_servant);
        compass_servant.setServantListener(this);
        compass_servant.setPointerDecibel(118);
    }


    private void initHandlerThread() {
        mUIHandler = new Handler();
        mHandlerThread = new HandlerThread(mHtName, Process.THREAD_PRIORITY_DEFAULT);
        mHandlerThread.start();
        mLooper = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == MESSAGE && i > 0) {
                    doWithMainUI();
                    i--;
                }
            }
        };
    }
    private int i = 1000;
    private void doWithMainUI() {
        try {
            mUIHandler.post(new Runnable() {
                @Override
                public void run() {
                    Double d = Math.random() * 89;
                    int iRandom = d.intValue() + 30;
                    compass_servant.setPointerDecibel(iRandom);
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public void onDestroy() {
        mHandlerThread.quit();
        super.onDestroy();
    }

    @Override
    public void startTension() {
        mLooper.sendEmptyMessage(MESSAGE);
    }
}
