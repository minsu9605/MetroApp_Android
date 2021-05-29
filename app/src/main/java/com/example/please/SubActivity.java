package com.example.please;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class SubActivity extends AppCompatActivity {

    public static String[] arr = Infoparsing.arr;
    public static String[] line;
    public static String[] num;
    public static Object selected_line;
    public static Object selected_direction;
    public static String[] sub_direction = new String[2];
    public static double result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity_main);
        getSupportActionBar().setTitle("지하철 정보");

        Infoparsing aActivity = (Infoparsing) Infoparsing.AActivity;
        aActivity.finish();

        sub_direction[0] = "방향 선택";
        sub_direction[1] = "방향 선택";

        num = arr[7].split(",");
        line = new String[num.length];
        for (int i = 0; i < num.length; i++) {
            if ((1000 < Integer.parseInt(num[i])) && Integer.parseInt(num[i]) < 1010) {
                line[i] = ((Integer.parseInt(num[i]) - 1000) + "호선");
            } else if (num[i] == Integer.toString(1071)) {
                line[i] = ("수인분당선");
            } else if (num[i] == Integer.toString(1063)) {
                line[i] = ("경의중앙선");
            } else if (num[i] == Integer.toString(1065)) {
                line[i] = ("공항철도");
            } else if (num[i] == Integer.toString(1067)) {
                line[i] = ("경춘선");
            } else if (num[i] == Integer.toString(1077)) {
                line[i] = ("신분당선");
            } else if (num[i] == Integer.toString(1092)) {
                line[i] = ("우이신설선");
            } else {
                line[i] = null;
            }
        }


        //<editor-fold desc="스피너 설정">
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        TextView spinner_text = (TextView) findViewById(R.id.spiner_text);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                R.layout.custom_simple_dropdown_item_1line, line);
        spinner1.setAdapter(adapter1);
        spinner1.setSelection(0);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                R.layout.custom_simple_dropdown_item_1line, sub_direction);
        spinner2.setAdapter(adapter2);
        spinner2.setSelection(0);
        //</editor-fold>

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_line = parent.getItemAtPosition(position);

                if (selected_line.equals("2호선")) {
                    sub_direction[0] = "외선";
                    sub_direction[1] = "내선";
                } else {
                    sub_direction[0] = "상행";
                    sub_direction[1] = "하행";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner_text.setText("호선을 선택해주세요");
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_direction = parent.getItemAtPosition(position);
                Log.d("SubActivity", "selected_ "  + selected_direction);
                Parsing bActivity = new Parsing(MainActivity.targetStation);
                Log.d("SubActivity", "Parsing: " + MainActivity.targetStation);
                TextView textView1 = (TextView) findViewById(R.id.text1);
                textView1.setText("지하철역명 : " + bActivity.stationInfo[6][0]);

                for(int i=0; i<bActivity.stationInfo[6].length; i++){
                    Log.d("SubActivity", "stationInfo"+i+": " + bActivity.stationInfo[6][i]);
                }
                TextView textView2 = (TextView) findViewById(R.id.text2);

                if ((1000 < Integer.parseInt(bActivity.stationInfo[6][2])) && Integer.parseInt(bActivity.stationInfo[6][2]) < 1010) {
                    bActivity.stationInfo[6][2] = ((Integer.parseInt(bActivity.stationInfo[6][2]) - 1000) + "호선");
                } else if (bActivity.stationInfo[6][2].equals("1071")) {
                    bActivity.stationInfo[6][2] = ("수인분당선");
                } else if (bActivity.stationInfo[6][2].equals("1063")) {
                    bActivity.stationInfo[6][2] = ("경의중앙선");
                } else if (bActivity.stationInfo[6][2].equals("1065")) {
                    bActivity.stationInfo[6][2] = ("공항철도");
                } else if (bActivity.stationInfo[6][2].equals("1067")) {
                    bActivity.stationInfo[6][2] = ("경춘선");
                } else if (bActivity.stationInfo[6][2].equals("1077")) {
                    bActivity.stationInfo[6][2] = ("신분당선");
                } else if (bActivity.stationInfo[6][2].equals("1092")) {
                    bActivity.stationInfo[6][2] = ("우이신설선");
                }
                textView2.setText("호선 : " + bActivity.stationInfo[6][2]);

                TextView textView3 = (TextView) findViewById(R.id.text3);
                textView3.setText("도착지방면 : " + bActivity.stationInfo[6][1]);

                TextView textView4 = (TextView) findViewById(R.id.text4);
                textView4.setText("열차도착예정시간 : " + bActivity.stationInfo[6][3] + "초");

                TextView textView5 = (TextView) findViewById(R.id.text5);
                textView5.setText("도착메세지 : " + bActivity.stationInfo[6][4]);

                TextView textView6 = (TextView) findViewById(R.id.text6);
                textView6.setText("현재 위치한 역 : " + bActivity.stationInfo[6][5]);

                TextView textView7 = (TextView) findViewById(R.id.text7);
                textView7.setText("상하행구분 : " + bActivity.stationInfo[6][6]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner_text.setText("상하행구분을 선택해주세요");
            }
        });
    }
}


