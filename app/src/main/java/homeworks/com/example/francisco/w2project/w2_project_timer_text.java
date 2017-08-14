package com.example.francisco.w2project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class w2_project_timer_text extends Fragment {

    private static final String TAG = "Timer Text";
    TextView tvTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_w2_project_timer_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTimer = (TextView) view.findViewById(R.id.tvTimer);
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
        if(messageEvent.getAction().equals("updateTextView"))
            tvTimer.setText(messageEvent.getMessage());
        if(messageEvent.getAction().equals("getTextViewData")) {
            EventBus.getDefault().post(new MessageEvent("sendTextViewData", tvTimer.getText().toString()));
            Log.d(TAG, "onMessageEvent: "+messageEvent.getAction() + " " + messageEvent.getMessage());
        }
        if(messageEvent.getAction().equals("sendLapseTimerAction")) {
            EventBus.getDefault().post(new MessageEvent("sendLapseTimer", tvTimer.getText().toString()));
            Log.d(TAG, "onMessageEvent: "+messageEvent.getAction() + " " + messageEvent.getMessage());
        }
    }
}
