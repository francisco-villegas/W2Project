package com.example.francisco.w2project;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class w2_project_timer_buttons extends Fragment implements View.OnClickListener {

    private static final String TAG = "Timer Buttons";
    Handler customHandler = new Handler();
    ImageButton btnPlayTimer, btnPauseTimer, btnStopTimer, btnLapseTimer;

    long startTime = 0L, timeInMiliseconds = 0L, timeSwapBuff=0L, updateTime = 0L;

    Runnable updateTimerThread = new Runnable(){
        @Override
        public void run() {
            timeInMiliseconds = SystemClock.uptimeMillis()-startTime;
            updateTime = timeSwapBuff+timeInMiliseconds;
            int secs = (int) (updateTime/1000);
            int mins = secs/60;
            secs%=60;
            int miliseconds = (int) (updateTime%1000);
            String mins2=""+mins;
            if(mins2.length()==1)
                mins2 = "0"+mins2;
            EventBus.getDefault().post(new MessageEvent("updateTextView",""+mins2+":"+String.format("%02d",secs)+":"
                    +String.format("%03d",miliseconds)));
            customHandler.postDelayed(this,0);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_w2_project_timer_buttons, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnPlayTimer = (ImageButton) view.findViewById(R.id.btnPlayTimer);
        btnPauseTimer = (ImageButton) view.findViewById(R.id.btnPauseTimer);
        btnStopTimer = (ImageButton) view.findViewById(R.id.btnStopTimer);
        btnLapseTimer = (ImageButton) view.findViewById(R.id.btnLapseTimer);

        btnPlayTimer.setOnClickListener(this);
        btnPauseTimer.setOnClickListener(this);
        btnStopTimer.setOnClickListener(this);
        btnLapseTimer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPlayTimer:
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread,0);
                break;
            case R.id.btnPauseTimer:
                timeSwapBuff += timeInMiliseconds;
                customHandler.removeCallbacks(updateTimerThread);
                break;
            case R.id.btnStopTimer:
                timeSwapBuff = 0L;
                customHandler.removeCallbacks(updateTimerThread);
                EventBus.getDefault().post(new MessageEvent("updateTextView","00:00:00"));
                break;
            case R.id.btnLapseTimer:
                EventBus.getDefault().post(new MessageEvent("sendLapseTimerAction",""));
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        try {
            String confilctive_fragments[] = Constant.confilctive_fragments;
            boolean still = true;
            do {
                for (int x = 0; x < confilctive_fragments.length; x++) {
                    if (manager.getBackStackEntryAt(manager.getBackStackEntryCount() - 1).getName().toLowerCase().contains(confilctive_fragments[x])) {
                        manager.popBackStackImmediate();
                    } else {
                        still = false;
                    }
                }
            } while (still);
        }catch(Exception ex){}
        super.onStop();
    }

    @Subscribe( threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent){
        if(messageEvent.getAction().equals("StopOnDestroycalled")){
            timeSwapBuff = 0L;
            customHandler.removeCallbacks(updateTimerThread);
            EventBus.getDefault().post(new MessageEvent("updateTextView","00:00:00"));
        }

        //Log.d(TAG, "onMessageEvent: "+messageEvent.getAction() + " " + messageEvent.getMessage());
    }
}
