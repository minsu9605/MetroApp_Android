package com.example.please;

import android.app.Activity;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Parsing extends AppCompatActivity {

    public String[][] stationInfo = new String[7][7];
    public static Activity BActivity;
        public Parsing(String station)
    {
        SetControl2(station);
    }
    public void SetControl2(String station)
    {
        BActivity = Parsing.this;

        String selected_line = SubActivity.selected_line.toString();
        String selected_direction = SubActivity.selected_direction.toString();

        try
        {
            if (android.os.Build.VERSION.SDK_INT>9)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

            }
            // parsing할 url 지정(API 키 포함해서)
            String url = "http://swopenapi.seoul.go.kr/api/subway/4555696364676a7736354a51416548/xml/realtimeStationArrival/1/6/"+station+"/";

            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            // root tag
            doc.getDocumentElement().normalize();

            // 파싱할 tag
            NodeList nList = doc.getElementsByTagName("row");

            for(int temp = 0; temp < 8; temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    stationInfo[temp][2] = getTagValue("subwayId", eElement);
                    if ((1000 < Integer.parseInt(stationInfo[temp][2])) && Integer.parseInt(stationInfo[temp][2]) < 1010)
                    {
                        stationInfo[temp][2] = ((Integer.parseInt(stationInfo[temp][2]) - 1000) + "호선");
                    } else if (stationInfo[temp][2].equals("1071"))
                    {
                        stationInfo[temp][2] = ("수인분당선");
                    } else if (stationInfo[temp][2].equals("1063"))
                    {
                        stationInfo[temp][2] = ("경의중앙선");
                    } else if (stationInfo[temp][2].equals("1065"))
                    {
                        stationInfo[temp][2] = ("공항철도");
                    } else if (stationInfo[temp][2].equals("1067"))
                    {
                        stationInfo[temp][2] = ("경춘선");
                    } else if (stationInfo[temp][2].equals("1077"))
                    {
                        stationInfo[temp][2] = ("신분당선");
                    } else if (stationInfo[temp][2].equals("1092"))
                    {
                        stationInfo[temp][2] = ("우이신설선");
                    }

                    stationInfo[temp][0] = getTagValue("statnNm", eElement);
                    stationInfo[temp][1] = getTagValue("trainLineNm", eElement);
                    stationInfo[temp][3] = getTagValue("barvlDt", eElement);
                    stationInfo[temp][4] = getTagValue("arvlMsg2", eElement);
                    stationInfo[temp][5] = getTagValue("arvlMsg3", eElement);
                    stationInfo[temp][6] = getTagValue("updnLine", eElement);

                    if (stationInfo[temp][2].equals(selected_line)) {
                        if (stationInfo[temp][6].equals(selected_direction)) {
                            stationInfo[6][0] = getTagValue("statnNm", eElement);
                            stationInfo[6][1] = getTagValue("trainLineNm", eElement);
                            stationInfo[6][2] = getTagValue("subwayId", eElement);
                            stationInfo[6][3] = getTagValue("barvlDt", eElement);
                            stationInfo[6][4] = getTagValue("arvlMsg2", eElement);
                            stationInfo[6][5] = getTagValue("arvlMsg3", eElement);
                            stationInfo[6][6] = getTagValue("updnLine", eElement);
                        }
                    } // if end
                } // if end
            } // for end
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }	// try~catch end
    }

    // tag값의 정보를 가져오는 메소드
    private static String getTagValue(String tag, Element eElement)
    {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }
}	// class end

