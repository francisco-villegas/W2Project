package com.example.francisco.w2project;

import java.util.Hashtable;

/**
 * Created by ksunil on 12/23/16.
 */

public class Constant {

    public static String[] name = {"Home", "Week1", "Week2", "Homework", "Extra"};
    public static int[] nameIcons = {R.drawable.ic_home, R.drawable.ic_1, R.drawable.ic_2, R.drawable.ic_check, R.drawable.ic_more};
    public static String[][] subName = {
            {"Home"},
            {"W1Day3", "W1Day4_1", "W1Day4_2"},
            {"W2Day1", "W2Day2", "W2Day3", "W2Day4", "W2Test"},
            {"Calculator", "CameraEMI", "W2Project"},
            {"Personal GIT", "Living Location", "Curriculum Vitae"}
    };
    public static int[][] subIcons = {
            {R.drawable.ic_home},
            {R.drawable.ic_3, R.drawable.ic_4, R.drawable.ic_5},
            {R.drawable.ic_1, R.drawable.ic_2, R.drawable.ic_3, R.drawable.ic_4, R.drawable.ic_5},
            {R.drawable.ic_calculator, R.drawable.ic_camera, R.drawable.ic_weekend},
            {R.drawable.ic_personal, R.drawable.ic_location, R.drawable.ic_pdf}
    };

    public static Hashtable<String, String> classSubName = new Hashtable<String, String>();
    static {
        classSubName.put("Home","Home");

        classSubName.put("W1Day3","W1D3");
        classSubName.put("W1Day4_1","W1D4_1");
        classSubName.put("W1Day4_2","W1D4_2");

        classSubName.put("W2Day1","W2D1");
        classSubName.put("W2Day2","W2D2");
        classSubName.put("W2Day3","W2D3");
        classSubName.put("W2Day4","W2D4");
        classSubName.put("W2Test","W2Test");

        classSubName.put("Calculator","MainActivityCalculator");
        classSubName.put("CameraEMI","MainCameraProject");
        classSubName.put("W2Project","W2Project");

        classSubName.put("Personal GIT","GIT");
        classSubName.put("Living Location","MapViewFragment");
        classSubName.put("Curriculum Vitae","CV");

    }

    public static String[] confilctive_fragments = {
            "w2_project_timer_buttons",
            "w2_project_timer_text"
    };
}
