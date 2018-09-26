package com.hanium.chj.finedustpj;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapPlacemark;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainMenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent BeforeIntent = getIntent();
        final String userID = BeforeIntent.getStringExtra("userID");
        final String userPassword = BeforeIntent.getStringExtra("userPassword");

        final Button MenuButton = (Button) findViewById(R.id.MenuButton);
        final TextView dustText = (TextView) findViewById(R.id.dustText);
        final TextView userIDText = (TextView) findViewById(R.id.userIDText);
        final TextView addrText = (TextView) findViewById(R.id.addrText);


        userIDText.setText("ID : " + userID);


        MenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu p = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(R.menu.menu_main, p.getMenu());


                // 이벤트 처리
                p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.Map:
                                Intent UserMapIntent = new Intent(MainMenuActivity.this, UserMapActivity.class);
                                startActivity(UserMapIntent);
                                break;
                        }
                        return false;
                    }
                });
                p.show();
            }
        });

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Random rand = new Random();
                int random = rand.nextInt(99-10 +1) + 10;
                final String dust = random +"";
                dustText.setText(dust);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
                                builder.setMessage("먼지정보 저장 성공! 먼지값 :" + dust)
                                        .setPositiveButton("확인",null)
                                        .create()
                                        .show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
                                builder.setMessage("저장 실패!")
                                        .setNegativeButton("다시시도",null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                DustInRequest dustInRequest = new DustInRequest(userID,dust,responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainMenuActivity.this);
                queue.add(dustInRequest);
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,0,10000);
    }


}
