package com.example.please;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Congestion extends AppCompatActivity {
    public static ImageButton RefreshButton;
    long mNow = System.currentTimeMillis();
    Date mReDate = new Date(mNow);
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formatDate = mFormat.format(mReDate);

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congestion);

        getSupportActionBar().setTitle("칸별 지하철 혼잡도");

        ImageView bad = findViewById(R.id.imageView1);
        bad.setImageResource(R.drawable.bad);
        ImageView normal = findViewById(R.id.imageView2);
        normal.setImageResource(R.drawable.normal);
        ImageView good = findViewById(R.id.imageView3);
        good.setImageResource(R.drawable.good);

        TextView time = findViewById(R.id.textView1);
        time.setText(formatDate + " 조회 기준");

        TextView start_station = findViewById(R.id.textView2);
        start_station.setText(MainActivity.map.get("출발") + " 출발 기준");

        TextView first = findViewById(R.id.textView3);
        first.setText("현재 역에 가장 먼저 들어오는 열차");

        TextView second = findViewById(R.id.textView4);
        second.setText("다음으로 들어오는 열차");


        //<editor-fold desc="새로고침 버튼">
        RefreshButton = (ImageButton) findViewById(R.id.RefreshButton);
        RefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onResume();
                Intent Congestion = getIntent();
                finish();
                startActivity(Congestion);

                Toast.makeText(getApplicationContext(), "새로 고침", Toast.LENGTH_SHORT).show();
            }
        });

        DocumentReference docRef1 = db.collection("train1").document("compartment1");
        docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                String s1 = ""+ document.getData();
                String s2 = s1.replace("weight=", "");
                String s3 = s2.replace("{", "");
                String s4 = s3.replace("}", "");
                int weight1 = Integer.parseInt(s4);

                if (task.isSuccessful()) {
                    try {
                        if (weight1 <= 200) {
                            ImageView compartment1 = findViewById(R.id.compartment1_1);
                            compartment1.setImageResource(R.drawable.first_good);
                        } else if ((weight1 > 200) && (weight1 <= 400)) {
                            ImageView compartment1 = findViewById(R.id.compartment1_1);
                            compartment1.setImageResource(R.drawable.first_normal);
                        } else if (weight1 > 400) {
                            ImageView compartment1 = findViewById(R.id.compartment1_1);
                            compartment1.setImageResource(R.drawable.first_bad);
                        }
                    } catch (Exception ex) {
                        return;
                    }
                }
            }
        });

        DocumentReference docRef2 = db.collection("train1").document("compartment2");
        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                String s1 = "" + document.getData();
                String s2 = s1.replace("weight=", "");
                String s3 = s2.replace("{", "");
                String s4 = s3.replace("}", "");
                int weight2 = Integer.parseInt(s4);

                if (task.isSuccessful()) {
                    try {
                        if (weight2 <= 200) {
                            ImageView compartment2 = findViewById(R.id.compartment1_2);
                            compartment2.setImageResource(R.drawable.second_good);
                        } else if ((weight2 > 200) && (weight2 <= 400)) {
                            ImageView compartment2 = findViewById(R.id.compartment1_2);
                            compartment2.setImageResource(R.drawable.second_normal);
                        } else if (weight2 > 400) {
                            ImageView compartment2 = findViewById(R.id.compartment1_2);
                            compartment2.setImageResource(R.drawable.second_bad);
                        }
                    } catch (Exception ex) {
                        return;
                    }
                }
            }
        });

        DocumentReference docRef3 = db.collection("train1").document("compartment3");
        docRef3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                String s1 = "" + document.getData();
                String s2 = s1.replace("weight=", "");
                String s3 = s2.replace("{", "");
                String s4 = s3.replace("}", "");
                int weight3 = Integer.parseInt(s4);

                if (task.isSuccessful()) {
                    try {
                        if (weight3 <= 200) {
                            ImageView compartment3 = findViewById(R.id.compartment1_3);
                            compartment3.setImageResource(R.drawable.third_good);
                        } else if ((weight3 > 200) && (weight3 <= 400)) {
                            ImageView compartment3 = findViewById(R.id.compartment1_3);
                            compartment3.setImageResource(R.drawable.third_normal);
                        } else if (weight3 > 400) {
                            ImageView compartment3 = findViewById(R.id.compartment1_3);
                            compartment3.setImageResource(R.drawable.third_bad);
                        }
                    } catch (Exception ex) {
                        return;
                    }
                }
            }
        });

        DocumentReference docRef4 = db.collection("train2").document("compartment1");
        docRef4.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                String s1 = ""+ document.getData();
                String s2 = s1.replace("weight=", "");
                String s3 = s2.replace("{", "");
                String s4 = s3.replace("}", "");
                int weight4 = Integer.parseInt(s4);

                if (task.isSuccessful()) {
                    try {
                        if (weight4 <= 200) {
                            ImageView compartment1 = findViewById(R.id.compartment2_1);
                            compartment1.setImageResource(R.drawable.first_good);
                        } else if ((weight4 > 200) && (weight4 <= 400)) {
                            ImageView compartment1 = findViewById(R.id.compartment2_1);
                            compartment1.setImageResource(R.drawable.first_normal);
                        } else if (weight4 > 400) {
                            ImageView compartment1 = findViewById(R.id.compartment2_1);
                            compartment1.setImageResource(R.drawable.first_bad);
                        }
                    } catch (Exception ex) {
                        return;
                    }
                }
            }
        });

        DocumentReference docRef5 = db.collection("train2").document("compartment2");
        docRef5.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                String s1 = "" + document.getData();
                String s2 = s1.replace("weight=", "");
                String s3 = s2.replace("{", "");
                String s4 = s3.replace("}", "");
                int weight5 = Integer.parseInt(s4);

                if (task.isSuccessful()) {
                    try {
                        if (weight5 <= 200) {
                            ImageView compartment2 = findViewById(R.id.compartment2_2);
                            compartment2.setImageResource(R.drawable.second_good);
                        } else if ((weight5 > 200) && (weight5 <= 400)) {
                            ImageView compartment2 = findViewById(R.id.compartment2_2);
                            compartment2.setImageResource(R.drawable.second_normal);
                        } else if (weight5 > 400) {
                            ImageView compartment2 = findViewById(R.id.compartment2_2);
                            compartment2.setImageResource(R.drawable.second_bad);
                        }
                    } catch (Exception ex) {
                        return;
                    }
                }
            }
        });

        DocumentReference docRef6 = db.collection("train2").document("compartment3");
        docRef6.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                String s1 = "" + document.getData();
                String s2 = s1.replace("weight=", "");
                String s3 = s2.replace("{", "");
                String s4 = s3.replace("}", "");
                int weight6 = Integer.parseInt(s4);

                if (task.isSuccessful()) {
                    try {
                        if (weight6 <= 200) {
                            ImageView compartment3 = findViewById(R.id.compartment2_3);
                            compartment3.setImageResource(R.drawable.third_good);
                        } else if ((weight6 > 200) && (weight6 <= 400)) {
                            ImageView compartment3 = findViewById(R.id.compartment2_3);
                            compartment3.setImageResource(R.drawable.third_normal);
                        } else if (weight6 > 400) {
                            ImageView compartment3 = findViewById(R.id.compartment2_3);
                            compartment3.setImageResource(R.drawable.third_bad);
                        }
                    } catch (Exception ex) {
                        return;
                    }
                }
            }
        });
        //</editor-fold?

    }
}



