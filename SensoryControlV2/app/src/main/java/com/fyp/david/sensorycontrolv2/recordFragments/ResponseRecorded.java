package com.fyp.david.sensorycontrolv2.recordFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fyp.david.sensorycontrolv2.R;
import com.fyp.david.sensorycontrolv2.StatsActivity;

/**
 * Created by DAVID on 3/28/2017.
 */

public class ResponseRecorded extends AppCompatActivity {

    private TextView thanks;
    private TextView remember;
    private Button okBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.response_recorded);

        thanks = (TextView) findViewById(R.id.thanks);
        remember = (TextView) findViewById(R.id.remember);
        okBtn = (Button) findViewById(R.id.ok_btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResponseRecorded.this, StatsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
