package com.example.mobileoperatorapp.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DpToPixels {
    public static int convert(int dp, Context context) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
    }
}
