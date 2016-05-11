# ArcView
![](http://7u2mnh.com1.z0.glb.clouddn.com/arcView.gif)
#项目包含属性有：

 	<declare-styleable name="ArcView">
        <attr name="progress" format="float" />
        <attr name="startAngle" format="float" />
        <attr name="arcWidth" format="dimension" />
        <attr name="behindArcWidth" format="dimension" />
        <attr name="behindColor" format="color" />
        <attr name="frontArcWidth" format="dimension" />
        <attr name="frontColor" format="color" />
        <attr name="textSize" format="dimension" />
        <attr name="textColor" format="color" />
        <attr name="circleColor" format="color" />
        <attr name="circleRadius" format="dimension" />
        <attr name="circleArcWidth" format="dimension"/>
        <attr name="arcCapModel" format="enum">
            <enum name="round" value="0" />
            <enum name="square" value="1" />
            <enum name="butt" value="2" />
        </attr>
        <attr name="arcCircleStyle" format="enum">
            <enum name="fill" value="0" />
            <enum name="stroke" value="1" />
            <enum name="fill_and_stroke" value="2" />
        </attr>
    </declare-styleable>
    
#可在Activity设置属性
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
        mArcView.setCircleArcWidth(5f);

#可在XML设置属性

 <com.timaimee.arcview.view.ArcView
        android:id="@+id/arcView"
        android:layout_width="260dp"
        android:layout_height="260dp"
        android:layout_centerInParent="true"
        arcview:progress="0.8"
        arcview:startAngle="150"
        arcview:arcWidth="25dp"
        arcview:behindArcWidth="15dp"
        arcview:frontArcWidth="10dp"
        arcview:circleRadius="5dp"
        arcview:textSize="50dp"
        arcview:frontColor="#ffffff"
        arcview:arcCapModel="round"
        arcview:arcCircleStyle="stroke"
        />

