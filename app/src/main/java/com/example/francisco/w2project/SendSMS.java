package com.example.francisco.w2project;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Admin on 8/13/2017.
 */

public class SendSMS extends DialogFragment {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private static final String SENT = "Message Sent!!!!";
    private static final String DELIVERED = "Message Delivered";
    EditText etMessage;
    TextView tvNumber;
    Button btnSMS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_sm, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        etMessage = (EditText) view.findViewById(R.id.etMessage);
        tvNumber = (TextView) view.findViewById(R.id.tvNumber);
        btnSMS = (Button) view.findViewById(R.id.btnSMS);
        getDialog().setTitle("Send Message");
        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
                dismiss();
                etMessage.setText("");
                showNotification();
            }
        });

        return view;
    }

    private void sendMessage() {
        // Get the default instance of the SmsManager
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(tvNumber.getText().toString(), null, etMessage.getText().toString(), null, null);
        Toast.makeText(getActivity(), "Your sms was successfully sent, also a notification was created, go an check it!",
                Toast.LENGTH_LONG).show();

    }


    public void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        builder.setSmallIcon(R.drawable.mobile_apps_logo);
        builder.setContentTitle("Message sent");
        builder.setContentText("Click this notification in order to discover new functions");
        Intent intent = new Intent(getActivity(), ActivityAfterMSG.class);
        intent.putExtra("key1",etMessage.getText().toString());
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),0,intent,0);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
        //Toast.makeText(getActivity(), " Alert.. Notification Sent!!!", Toast.LENGTH_SHORT).show();
    }
}
