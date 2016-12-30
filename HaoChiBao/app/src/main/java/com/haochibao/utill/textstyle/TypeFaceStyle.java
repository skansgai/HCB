package com.haochibao.utill.textstyle;

import android.graphics.Typeface;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by David on 2016/12/27.
 */
@IntDef({Typeface.NORMAL, Typeface.BOLD, Typeface.BOLD_ITALIC, Typeface.ITALIC})
@Retention(RetentionPolicy.SOURCE)
@interface TypeFaceStyle {
}