package com.rexthonyy.animatedintegertextviewcounterlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rexthonyy.animatedintegertextviewcounter.AnimatedIntegerTextViewCounter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AnimatedIntegerTextViewCounter textViewCounter = findViewById(R.id.textView1);
        final EditText editText1 = findViewById(R.id.editText1);
        final EditText editText2 = findViewById(R.id.editText2);
        Button button1 = findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewCounter.setValue(editText1.getText().toString());
            }
        });
    }
}
