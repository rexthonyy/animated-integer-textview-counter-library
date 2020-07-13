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
        if(Utils.isFloat(text)){
            float value = Float.parseFloat(text);
            currentValue = value;
            targetValue = value;
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

            if(reloadTimeInMilliseconds == 0){
                reloadTimeInMilliseconds = animationDurationInMilliseconds / 100;
            }
            a.recycle();
        }

        reloader = new AnimatedIntegerReloader(this, reloadTimeInMilliseconds);
        updateAnimation();
    }

    public void setValue(String text) {
        targetValue = Utils.isFloat(text)?Float.parseFloat(text):0;
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
            setText();
            if(reloader != null) reloader.onPause();
        }else{
            if(reloader != null) reloader.onResume();
        }
    }

    public void doAnimation(){
        currentValue += incrementBy;
        setText();
        checkCondition();
    }

    @SuppressLint("SetTextI18n")
    private void setText(){
        setText((int)currentValue + appendWith);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        reloader.onDestroy();
    }
}
