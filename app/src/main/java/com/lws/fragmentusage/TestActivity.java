package com.lws.fragmentusage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class TestActivity extends AppCompatActivity {

    public static final String ARG_NAME = "argName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button btnReturn = findViewById(R.id.btn_return);
        Random random = new Random();
        final int value = random.nextInt(10000);
        btnReturn.setText("返回值：" + value);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(ARG_NAME, value);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
