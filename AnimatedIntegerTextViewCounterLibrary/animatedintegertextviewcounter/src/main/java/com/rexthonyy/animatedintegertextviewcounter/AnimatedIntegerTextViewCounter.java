package com.rexthonyy.animatedintegertextviewcounter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.rexthonyy.animatedintegertextviewcounter.helpers.AnimatedIntegerReloader;
import com.rexthonyy.animatedintegertextviewcounter.helpers.Utils;

public class AnimatedIntegerTextViewCounter extends android.support.v7.widget.AppCompatTextView {

    private int animationDurationInMilliseconds;
    private int reloadTimeInMilliseconds;
    private float currentValue;
    private float targetValue;
    private float incrementBy;
    private String appendWith;
    private AnimatedIntegerReloader reloader;

    public AnimatedIntegerTextViewCounter(Context context) {
        super(context);
        init(context, null);
    }

    public AnimatedIntegerTextViewCounter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AnimatedIntegerTextViewCounter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        animationDurationInMilliseconds = 0;
        reloadTimeInMilliseconds = 0;
        currentValue = 0.0f;
        targetValue = 0.0f;
        appendWith = "";

        String text = getText().toString();
        if(Utils.isInteger(text)){
            int value = Integer.parseInt(text);
            currentValue = (float)value;
            targetValue = (float)value;
        }

        if(attrs != null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AnimatedIntegerTextViewCounter);
            int numAttributes = a.getIndexCount();
            for(int i = 0; i < numAttributes; i++){
                int attr = a.getIndex(i);
                if (attr == R.styleable.AnimatedIntegerTextViewCounter_animationDurationInMilliseconds) {
                    animationDurationInMilliseconds = a.getInteger(attr, 0);
                } else if (attr == R.styleable.AnimatedIntegerTextViewCounter_reloadTimeInMilliseconds) {
                    reloadTimeInMilliseconds = a.getInteger(attr, 0);
                } else if (attr == R.styleable.AnimatedIntegerTextViewCounter_startValue) {
                    currentValue = a.getFloat(attr, 0);
                } else if (attr == R.styleable.AnimatedIntegerTextViewCounter_targetValue) {
                    targetValue = a.getFloat(attr, 0);
                } else if (attr == R.styleable.AnimatedIntegerTextViewCounter_appendWith) {
                    appendWith = a.getString(attr);
                    appendWith = appendWith == null ? "" : appendWith;
                }
            }
            a.recycle();
        }

        reloader = new AnimatedIntegerReloader(this, reloadTimeInMilliseconds);
        updateAnimation();
    }

    @SuppressLint("SetTextI18n")
    public void setValue(String text) {
        String textVal = text.toString();
        targetValue = Utils.isInteger(textVal)?Integer.parseInt(textVal):0;
        updateAnimation();
        setText();
    }

    private void updateAnimation(){
        incrementBy = (targetValue - currentValue) * ((float) reloadTimeInMilliseconds / (float) animationDurationInMilliseconds);
        checkCondition();
    }

    private void checkCondition(){
        if(Math.abs(targetValue - currentValue) < 0.5){
            currentValue = targetValue;
            if(reloader != null) reloader.onPause();
        }else{
            if(reloader != null) reloader.onResume();
        }
    }

    @SuppressLint("SetTextI18n")
    public void doAnimation(){
        checkCondition();
        currentValue += incrementBy;
        setText();
    }

    private void setText(){
        setText((int)currentValue + appendWith);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        reloader.onDestroy();
    }
}
