package com.timaimee.arcview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.timaimee.arcview.view.ArcView;

public class MainActivity extends Activity {
    private ArcView   mArcView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mArcView=(ArcView) findViewById(R.id.arcView);
        mArcView.setProgress(0.2f);
        mArcView.setStartAngle(100f);
        mArcView.setArcWidth(25);
        mArcView.setTextSize(65);
        mArcView.setTextColor(Color.BLACK);
        mArcView.setCircleColor(Color.YELLOW);
        mArcView.setBehindColor(Color.RED);
        mArcView.setFrontColor(Color.WHITE);
        mArcView.setBehindArcWidth(20);
        mArcView.setFrontArcWidth(10);
        mArcView.setArcCapModel(ArcView.CAP_ROUND);
        mArcView.setCircleRadius(15);
        mArcView.setCircleStyle(ArcView.STYLE_FILL);

   }

}
