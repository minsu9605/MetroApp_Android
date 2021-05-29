package com.example.please;

import android.content.Context;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import me.piruin.quickaction.ActionItem;
import me.piruin.quickaction.QuickAction;


public class QuickActionHelper extends AppCompatActivity {
    Context context;
    View v;
    private ActionBar actionBar;

    private static final int ID_UP = 1;
    private static final int ID_DOWN = 2;
    private static final int ID_INFO = 3;
    private static final int ID_OK = 4;
    private static final int ID_RESET = 5;

    private QuickAction quickAction;
    private String targetStation;

    public QuickActionHelper(Context context)
    {
        super();
        this.context=context;
        v= new View(this.context);

        ActionItem startItem = new ActionItem(ID_DOWN, "출발");
        ActionItem arriveItem = new ActionItem(ID_UP, "도착");
        ActionItem infoItem = new ActionItem(ID_INFO, "정보");
        ActionItem resetItem = new ActionItem(ID_RESET, "초기화");
        ActionItem okItem = new ActionItem(ID_OK, "닫기");

        quickAction = new QuickAction(context, QuickAction.HORIZONTAL);
        quickAction.setColorRes(R.color.black);
        quickAction.setTextColorRes(R.color.white);
        quickAction.addActionItem(startItem, arriveItem, infoItem, resetItem, okItem);

        //선택됨 문구
        quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override
            public void onItemClick(ActionItem item) {
                //here we can filter which action item was clicked with pos or actionId parameter

                switch(item.getTitle())
                {
                    case "출발":
                        OnStart();
                        break;
                    case "도착":
                        OnArrive();
                        break;

                    case "정보":
                        OnInformation();
                        break;

                    case "초기화":
                        OnReset();
                        break;

                    case "닫기":
                        actionBar.hide();
                        break;

                    default:
                        break;

                }
            }
        });

        //빈공간 문구
        quickAction.setOnDismissListener(new QuickAction.OnDismissListener() {
            @Override
            public void onDismiss() {
                actionBar.hide();
            }
        });
    }

    //<editor-fold desc="메서드함수">

    private void OnStart() {
        //출발
        MainActivity.map.put("출발",targetStation);

        Toast.makeText(context, MainActivity.map.get("출발") +" 역을 출발역으로 설정합니다", Toast.LENGTH_SHORT).show();
        try{
            if ((MainActivity.map.containsKey("출발") && (MainActivity.map.containsKey("도착")))){
                MainActivity.CrowdednessButton.setVisibility(View.VISIBLE);
            }else{
                MainActivity.CrowdednessButton.setVisibility(View.GONE);
            }
        }
        catch(Exception ex)
        {
            return;
        }
    }

    private void OnArrive() {
        //도착
        MainActivity.map.put("도착",targetStation);

        Toast.makeText(context, MainActivity.map.get("도착") +" 역을 도착역으로 설정합니다", Toast.LENGTH_SHORT).show();
        try{
            if ((MainActivity.map.containsKey("출발") && (MainActivity.map.containsKey("도착")))){
                MainActivity.CrowdednessButton.setVisibility(View.VISIBLE);
            }else{
                MainActivity.CrowdednessButton.setVisibility(View.GONE);
            }
        }
        catch(Exception ex)
        {
            return;
        }

    }

    private void OnInformation() {
        //정보
        MainActivity.map.put("정보",targetStation);
        Intent intent = new Intent(context, Infoparsing.class);
        context.startActivity(intent);
    }

    private void OnReset() {
        //초기화
        Toast.makeText(context, "초기화", Toast.LENGTH_SHORT).show();
        MainActivity.map.clear();
        MainActivity.CrowdednessButton.setVisibility(View.GONE);
        actionBar.hide();
    }

    //</editor-fold>


    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            quickAction.show(v);
            return true;
        }

    };

    public void SetStation(String station) {
        this.targetStation=station;
        return;
    }

    public void SetActionBar(ActionBar actionBar) {
        this.actionBar = actionBar;
    }

    public void Show() {
        this.quickAction.show(v);
    }
}


