package com.jike.uikit.uiengine;

public class FlexUiEngine {

    public static BezierInterpolator EaseInterpolator(){
        return new BezierInterpolator(.25,.1,.25,1);
    }

    public static BezierInterpolator OverInterpolator(){
        return new BezierInterpolator(.25,.1,.18,1.3);
    }

    public static BezierInterpolator ScrollInterpolator(){
        return new BezierInterpolator(.24f,.75f,.53f,1.1f);
    }

}