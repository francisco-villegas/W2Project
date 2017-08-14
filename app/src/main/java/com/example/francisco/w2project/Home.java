package com.example.francisco.w2project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

/**
 * Created by FRANCISCO on 05/08/2017.
 */

public class Home extends Fragment implements View.OnClickListener {

    private static final String TAG = "Home";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);

        //ImageView imageView = (ImageView) view.findViewById(R.id.my_image);
        String htmlText = " %s ";
        String myData = "" +
                "<html><header><meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\" /></header><body style=\"text-align:justify\">" +
                "IT specialist with 5+ years of professional experience as a senior software developer in android development and web environemnt." +

                "<br /><br /><b style=\"font-size:20px;\">Previous Work:</b>" +
                "<br /><b>CIECO UNAM, Morelia</b>" +
                "<br />Preventive and corrective maintenance, I realized the project \"Location based service (LBS)\". It consists in an android application that showed the routes of interest sites and how to reach them inside the campus of the UNAM Morelia." +
                "<br /><b>Web developer</b>" +
                "<br />Working as Software Engineer in a web application to provide privileged access to users in all the OS (Windows, Linux, and Unix) where approvers can approve/deny the request of the user. Programming in Javascript, JQuery, Ajax and PHP using Bootstrap, a BD in Postgresql, and a framework that was made for our team. Translating some code from perl to php/jquery/html5. Working with LDAP directory and Active Directory. Programming scripts in Perl." +

                "<br /><br /><b style=\"font-size:20px;\">Education:</b>" +
                "<br />&middot; Engineer in computer systems (Esp. Informatic security) | (2010 - 2014) in Instituto Tecnol&oacute;gico de Morelia" +
                "<br />&middot; Master in computer science (Esp. Informatic security) thesis \"Feature selection to detect botnets using machine learning algorithms\" | (2014 - 2016)" +
                " in Centro de Investigaci&oacute;n en Computaci&oacute;n, Instituto Polit&eacute;cnico Nacional, CDMX" +

                "<br /><br /><b style=\"font-size:20px;\">Certifications</b>" +
                "<br />&middot; Certification in Linux Administrator (25hrs) | (2014)" +
                "<br />&middot; Certification in Python Jr. (25hrs) | (2015)" +
                "<br />&middot; Certification in HTML 5 (25hrs) | (2015)" +
                "</body></html>";
        WebView webView = (WebView) view.findViewById(R.id.vw);
        webView.loadData(String.format(htmlText, myData), "text/html", "utf-8");

        Button btn = (Button) view.findViewById(R.id.btn);

        btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");
    }

    @Override
    public void onClick(View view) {
        Functions.setFragment(new CV(),R.id.content_main,getActivity().getSupportFragmentManager(),getActivity());
    }
}
