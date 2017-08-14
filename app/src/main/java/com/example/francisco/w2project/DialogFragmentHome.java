package com.example.francisco.w2project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by FRANCISCO on 14/08/2017.
 */

public class DialogFragmentHome extends DialogFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle("Welcome");
        Button dismiss = (Button) view.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(this);
    }

    public void dismissmethod(){
        try {
            dismiss();
        }catch(Exception ex){}
    }

    @Override
    public void onClick(View v) {
        dismissmethod();
    }
}