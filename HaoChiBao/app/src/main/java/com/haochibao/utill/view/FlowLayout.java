package com.haochibao.utill.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 2016/12/11.
 */

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        this(context,null);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //matchparent时设置的宽高
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        //wrapcontent时设置宽高
        //控件的宽高
        int width = 0;
        int height = 0;
        //未添加控件时每行的宽高
        int lineWidth = 0;
        int lineHeight = 0;
        //获得控件的个数
        int childCount = getChildCount();
        for (int i=0;i<childCount;i++){
            View child = getChildAt(i);
            //测量子View的宽高
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //子View占据的宽高
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            //换行
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()){//在xml中设wrapContent试一下；算了，还是设死吧
                width = Math.max(width,lineWidth);
                //重置lineWidth(下一行lineWidth = childWidth)
                lineWidth = childWidth;
                //记录行高
                height = height + lineHeight;
                lineHeight = childHeight;
            }else {//未换行的情况
                //叠加行宽
                lineWidth += childWidth;
                //得到当前最大宽度
                lineHeight = Math.max(lineHeight,childHeight);
            }
            //最后一个控件
            if (i == childCount-1){
                width = Math.max(lineWidth,width);
                height += lineHeight;
            }
        }
        Log.i("sizeWidth","sizeWidth="+sizeWidth);
        Log.i("sizeHeight","sizeHeight="+sizeHeight);
        //判断xml设置的是wrap_content还是match_parent,设置相应的宽高
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY?sizeWidth:width + getPaddingLeft() +getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY?sizeHeight:height + getPaddingTop() +getPaddingBottom());

    }

    /**
     * 一行一行的存储所有子控件
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 存储每一行的高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();
        //当前view的宽度
        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        List<View> lineViews = new ArrayList<View>();
        int childCount = getChildCount();
        for (int i=0;i<childCount;i++){
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();//和getWidth()有什么区别
            int childHeight = child.getMeasuredHeight();
            //如果需要换行
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()){
                //记录LineHeight
                mLineHeight.add(lineHeight);
                //记录当前行的View
                mAllViews.add(lineViews);
                //重置我们的行宽和行高
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                //重置View集合
                lineViews = new ArrayList<View>();
            }
            lineWidth = lineWidth + childWidth +lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight,childHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(child);
        }//for end
        //处理最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);
        //设置子View的位置
        int left = getPaddingLeft();
        int top = getPaddingTop();
        //行数
        int lineNum = mAllViews.size();
        for (int i = 0;i<lineNum;i++){
            //当前行的所有View
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);
            for (int j = 0;j<lineViews.size();j++){
                View child = lineViews.get(j);
                //判断child状态
                if (child.getVisibility() == GONE){
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                //为子View设置布局
                child.layout(lc,tc,rc,bc);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
