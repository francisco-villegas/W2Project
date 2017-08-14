package com.example.francisco.w2project;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class W2Project extends Fragment implements View.OnClickListener {

    private static final String TAG = "W2Project";
    Button btnDefault, btnCustom, btnArray;
    AlertDialog dialog;
    TextView tvTextArray;
    String[] foodList;
    boolean[] checkedItems;
    ArrayList<Integer> selectedItems = new ArrayList<>();
    LinearLayout container;

    public W2Project() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_w2_project, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnDefault = (Button) view.findViewById(R.id.btnDefault);
        btnCustom = (Button) view.findViewById(R.id.btnCustom);
        btnArray = (Button) view.findViewById(R.id.btnArray);

        Functions.setFragment(new w2_project_timer_text(),R.id.frag_timer_text,getActivity().getSupportFragmentManager(),getActivity());
        Functions.setFragment(new w2_project_timer_buttons(),R.id.frag_timer_buttons,getActivity().getSupportFragmentManager(),getActivity());



        tvTextArray = (TextView) view.findViewById(R.id.tvTextArray);

        container = (LinearLayout) view.findViewById(R.id.container);

        btnDefault.setOnClickListener(this);
        btnCustom.setOnClickListener(this);
        btnArray.setOnClickListener(this);

        foodList = getResources().getStringArray(R.array.food);
        checkedItems = new boolean[foodList.length];
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDefault:
                AlertDialog defaultDialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Default Alert Dialog")
                        .setMessage("This is the Default Alert dialog")
                        .setNeutralButton("OK", null)
                        .show();

                break;
            case R.id.btnCustom:
                final AlertDialog.Builder customDialog = new AlertDialog.Builder(getActivity());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.cdialog, null);
                Button btnOk = (Button) mView.findViewById(R.id.btnOk);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                customDialog.setView(mView);
                dialog = customDialog.setTitle("Custom Layout").setCancelable(false).create();
                dialog.show();
                break;
            case R.id.btnArray:
                final AlertDialog aDialog;
                final AlertDialog.Builder arrayDialog = new AlertDialog.Builder(getActivity());

                arrayDialog.setTitle("Array Dialog").setMultiChoiceItems(foodList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            if (!selectedItems.contains(i)) {
                                selectedItems.add(i);
                            } else {
                                selectedItems.remove(i);
                            }
                        }
                    }
                });
                arrayDialog.setCancelable(false);
                arrayDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String item = "";
                        for (int j = 0; j < selectedItems.size(); j++) {
                            item = item + foodList[selectedItems.get(j)];
                            if (j != selectedItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        tvTextArray.setText(item);
                    }
                });
                arrayDialog.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                arrayDialog.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < checkedItems.length; j++) {
                            checkedItems[j] = false;
                            selectedItems.clear();
                            tvTextArray.setText("");
                        }
                    }
                });
                aDialog = arrayDialog.create();
                aDialog.show();
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
        super.onStop();
        EventBus.getDefault().post(new MessageEvent("StopOnDestroycalled",""));
        EventBus.getDefault().unregister(this);
    }

    @Subscribe( threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent){
        if(messageEvent.getAction().equals("sendLapseTimer")){
            Log.d(TAG, "onMessageEvent: "+messageEvent.getAction() + " " + messageEvent.getMessage());
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View addView = inflater.inflate(R.layout.row,null);
            TextView tvLapse = (TextView) addView.findViewById(R.id.tvLapse);
            EventBus.getDefault().post(new MessageEvent("getTextViewData",""));
            tvLapse.setText(messageEvent.getMessage());
            container.addView(addView);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
