package com.haochibao.utill.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.haochibao.R;

/**
 * Created by Administrator on 2016/12/12.
 */

public class DialogUtill {
    static Context context;
    static View view;
    static AlertDialog alertDialog;
    public DialogUtill(Context context,View view,AlertDialog alertDialog){
        this.context=context;
        this.view=view;
        this.alertDialog=alertDialog;
    }
    public static AlertDialog createDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setView(view);
        alertDialog=builder.create();
        alertDialog.show();
        return alertDialog;
    }
}
