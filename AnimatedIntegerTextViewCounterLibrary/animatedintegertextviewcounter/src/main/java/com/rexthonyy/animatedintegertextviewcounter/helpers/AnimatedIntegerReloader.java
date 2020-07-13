package com.rexthonyy.animatedintegertextviewcounter.helpers;

import com.rexthonyy.animatedintegertextviewcounter.AnimatedIntegerTextViewCounter;

public class AnimatedIntegerReloader extends Reloader{
    private AnimatedIntegerTextViewCounter textViewCounter;

    public AnimatedIntegerReloader(AnimatedIntegerTextViewCounter textViewCounter, int timeInMilliseconds){
        super(timeInMilliseconds);
        this.textViewCounter = textViewCounter;
    }

    @Override
    public void execute() {
        textViewCounter.doAnimation();
    }
}
