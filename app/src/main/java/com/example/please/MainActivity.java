package com.example.please;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    Cursor c = null;
    private GestureDetectorCompat gestureDetector;
    private SubsamplingScaleImageView imageView;
    private QuickActionHelper q;
    public static Map<String,String> map = new HashMap<String, String>();
    public static String targetStation;
    public static ImageButton CrowdednessButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        q = new QuickActionHelper(this);
        q.SetActionBar(getSupportActionBar());

        getSupportActionBar().hide();

        gestureDetector = new GestureDetectorCompat(this, new MyGestureListener());

        //<editor-fold desc="지하철이미지">
        imageView = findViewById(R.id.sub);
        imageView.setImage(ImageSource.resource(R.drawable.sub));
        imageView.setOnTouchListener(touchListener); // 이미지에 터치 가능하게
        //</editor-fold>

        //<editor-fold desc="Receive DB">
        SubwayDatabaseHelper myDbHelper = new SubwayDatabaseHelper(MainActivity.this); // Reading SQLite database.
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
        c = myDbHelper.query("subwayData", null, null, null, null, null, null); // SQLDataRead
        //</editor-fold>

        //<editor-fold desc="이미지 버튼 화면 전환">
        CrowdednessButton = (ImageButton) findViewById(R.id.CrowdednessButton);
        MainActivity.CrowdednessButton.setVisibility(View.GONE);
        CrowdednessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Congestion.class);
                startActivity(intent);
            }
        });
        //</editor-fold>
    }


    //터치 가능하게
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            return gestureDetector.onTouchEvent(event);
        }
    };

    //<editor-fold desc="역 클릭">
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent event) {
            if (imageView.isReady()) {
                PointF sCoord = imageView.viewToSourceCoord(event.getX(), event.getY());
                int x_cor = (int) sCoord.x;
                int y_cor = (int) sCoord.y;

                //좌표에 맞는 역
                if (c.moveToFirst()) {
                    do {
                        if ((x_cor > c.getInt(2)) && (x_cor < c.getInt(4)) && (y_cor > c.getInt(3)) && (y_cor < c.getInt(5))) {
                            targetStation = c.getString(1); // 좌표를 통해 얻은 유저가 클릭한 지하철역
                            getSupportActionBar().show();
                            getSupportActionBar().setTitle(targetStation);

                            q.SetStation(targetStation);
                            q.Show();
                        }
                    } while (c.moveToNext());
                }
            }
            return super.onSingleTapUp(event); //true;
        }
    }
}
