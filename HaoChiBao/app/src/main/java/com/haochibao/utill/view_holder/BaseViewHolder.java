package com.haochibao.utill.view_holder;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by Administrator on 2016/12/23.
 */
public class BaseViewHolder {
    public static <T extends View> T get(View view,int id){
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null){
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null){
            childView = view.findViewById(id);
            viewHolder.put(id,childView);
        }
        return (T) childView;
    }
}
