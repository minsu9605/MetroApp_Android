package com.example.please;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Infoparsing extends AppCompatActivity {

    ListView listView;
    ArrayAdapter adapter;
    public static Activity AActivity;
    public static String targetStation;
    public static String[] arr = new String[10];

    ArrayList<String> items=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infoparsing_main);
        AActivity = Infoparsing.this;


        listView=findViewById(R.id.listview);
        adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,items);
        //원래 layout을 .xml을 만들어야 하지만 예제이므로 안드로이에서 제공하는 것(android.R.layout.simple_list_item_1)을 사용
        listView.setAdapter(adapter);

        new Thread(){
            @Override
            public void run() {

                items.clear();

                String adress="http://swopenapi.seoul.go.kr/api/subway/4555696364676a7736354a51416548/xml/realtimeStationArrival/1/1/"+MainActivity.map.get("정보")+"/";

                //api 주소
                try {
                    //URL객체생성
                    URL url= new URL(adress);

                    //Stream 열기                                     //is는 바이트 스트림이라 문자열로 받기위해 isr이 필요하고 isr을 pullparser에게 줘야하는데
                    InputStream is = url.openStream(); //바이트스트림
                    //문자스트림으로 변환
                    InputStreamReader isr = new InputStreamReader(is);

                    //읽어들인 XML문서를 분석(parse)해주는 객체 생성    //pullparser를 만들려면 Factory가 필요해서 팩토리 만들고 pullparser를 만들었다. 결론, 그리고 pullparser에게 isr연결
                    XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
                    XmlPullParser xpp=factory.newPullParser();
                    xpp.setInput(isr);

                    //xpp를 이용해서 xml문서를 분석

                    //xpp.next();   //XmlPullParser는 시작부터 문서의 시작점에 있으므로 next해주면 START_DOCUMENT를 못만난다.
                    int eventType= xpp.getEventType();

                    String tagName;
                    StringBuffer buffer=null;


                    while(eventType!=XmlPullParser.END_DOCUMENT){
                        switch (eventType){
                            case XmlPullParser.START_DOCUMENT:

                                runOnUiThread(new Runnable() {  //여기는 별도 스레드이므로 화면 구성을 하려면 runOnUiThread 필요
                                    @Override
                                    public void run() {
                                        //Toast.makeText(infoparsing.this,"파싱을 시작했다.",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;

                            case XmlPullParser.START_TAG:
                                tagName=xpp.getName();
                                if(tagName.equals("row")){
                                    buffer=new StringBuffer();

                                }else if(tagName.equals("subwayId")){  // 0
                                    buffer.append("지하철 호선 ID:");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                    arr[0]=xpp.getText();

                                }else if(tagName.equals("updnLine")){  // 8
                                    buffer.append("상하행선 구분:");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                    arr[8]=xpp.getText();

                                }else if(tagName.equals("trainLineNm")){  // 1
                                    buffer.append("도착지방면:");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                    arr[1]=xpp.getText();

                                }else if(tagName.equals("subwayHeading")){
                                    buffer.append("내리는 문 방향:");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");

                                }else if(tagName.equals("statnFid")){
                                    buffer.append("이전지하철역ID :");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");

                                }else if(tagName.equals("statnTid")){
                                    buffer.append("다음지하철역ID :");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");

                                }else if(tagName.equals("statnId")){
                                    buffer.append("지하철역ID :");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");

                                }else if(tagName.equals("statnNm")){  // 2
                                    buffer.append("지하철역명  :");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                    arr [2]=xpp.getText();

                                }else if(tagName.equals("ordkey")){
                                    buffer.append("도착예정열차순번 :");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");

                                }else if(tagName.equals("subwayList")){ // 7
                                    buffer.append("연계호선ID :");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                    arr [7]=xpp.getText();

                                }else if(tagName.equals("statnList")){
                                    buffer.append("연계지하철역ID  :");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");

                                }else if(tagName.equals("barvlDt")){   // 3
                                    buffer.append("열차도착예정시간 :");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                    arr[3]=xpp.getText();

                                }else if(tagName.equals("btrainNo")){
                                    buffer.append("열차번호 :");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");

                                }else if(tagName.equals("bstatnId")){
                                    buffer.append("종착지하철역ID :");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");

                                }else if(tagName.equals("bstatnNm")){
                                    buffer.append("종착지하철역명 :");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");

                                }else if(tagName.equals("recptnDt")){
                                    buffer.append("열차도착정보를 생성한 시각 :");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");

                                }else if(tagName.equals("arvlMsg2")){   // 4
                                    buffer.append("첫번째도착메세지 :");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                    arr[4]=xpp.getText();

                                }else if(tagName.equals("arvlMsg3")){   // 5
                                    buffer.append("두번째도착메세지 :");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                    arr[5]=xpp.getText();

                                }else if(tagName.equals("arvlCd")){   // 6
                                    buffer.append("도착코드 :");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                    arr[6]=xpp.getText();

                                }
                                break;

                            case XmlPullParser.TEXT:
                                break;

                            case XmlPullParser.END_TAG:
                                tagName = xpp.getName();
                                if(tagName.equals("row")){

                                    items.add(buffer.toString());

                                    //리스트뷰 갱신
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                                break;
                        }

                        eventType=xpp.next();
                        //                        xpp.next();   //두줄을 한줄로 쓸 수 있다.
                        //                        eventType=xpp.getEventType();
                    }//while ..

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(infoparsing.this, "파싱종료!!",Toast.LENGTH_SHORT).show();

                        }
                    });
                    startActivity(new Intent(Infoparsing.this, SubActivity.class));
                    finish();

                } catch (MalformedURLException e) { e.printStackTrace();} catch (IOException e) {e.printStackTrace();} catch (XmlPullParserException e) {e.printStackTrace();}


            }// run() ..
        }.start();


    }
}