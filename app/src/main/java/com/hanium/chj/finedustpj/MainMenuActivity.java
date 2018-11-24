package com.hanium.chj.finedustpj;

        import android.app.Activity;
        import android.bluetooth.BluetoothAdapter;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import app.akexorcist.bluetotohspp.library.BluetoothSPP;
        import app.akexorcist.bluetotohspp.library.BluetoothState;
        import app.akexorcist.bluetotohspp.library.DeviceList;

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

    private BluetoothSPP bt;

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
        final Button F1Button = (Button)findViewById(R.id.FavoriteButton1);
        final Button F2Button = (Button)findViewById(R.id.FavoriteButton2);
        final TextView F1_userIDText = (TextView)findViewById(R.id.F1_userIDText);
        final TextView F2_userIDText = (TextView)findViewById(R.id.F2_userIDText);
        final TextView F1_dustText = (TextView)findViewById(R.id.F1_dustText);
        final TextView F2_dustText = (TextView)findViewById(R.id.F2_dustText);

        bt = new BluetoothSPP(this); //Initializing


        userIDText.setText("ID : " + userID);


        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
            public void onDataReceived(byte[] data, String message) {
                Toast.makeText(MainMenuActivity.this, message, Toast.LENGTH_SHORT).show();
                dustText.setText(message);

            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnConnect = findViewById(R.id.btnConnect); //연결시도
        btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });



        /* MenuButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                PopupMenu p = new PopupMenu(getApplicationContext(), view);
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
        }); */

        F1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText edittext = new EditText(MainMenuActivity.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
                builder.setTitle("즐겨찾기 추가하기");
                builder.setMessage("즐겨찾에 추가할 유저 ID");
                builder.setView(edittext);
                builder.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                F1_userIDText.setText(edittext.getText().toString());
                            }
                        });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
            }
        });

        F2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText edittext = new EditText(MainMenuActivity.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
                builder.setTitle("즐겨찾기 추가하기");
                builder.setMessage("즐겨찾에 추가할 유저 ID");
                builder.setView(edittext);
                builder.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                F2_userIDText.setText(edittext.getText().toString());
                            }
                        });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
            }
        });


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                final String dust = dustText.getText().toString();
                final String F1_userID = F1_userIDText.getText().toString();
                final String F2_userID = F2_userIDText.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(MainMenuActivity.this,"미세먼지 정보 저장 성공!",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainMenuActivity.this,"미세먼지 정보 저장실패",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                DustInRequest dustInRequest = new DustInRequest(userID, dust, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainMenuActivity.this);
                queue.add(dustInRequest);

                // 즐겨찾기 1
                Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String F1_dust = jsonResponse.getString("dust");
                            if (success) {
                                F1_dustText.setText(F1_dust);
                            } else {
                                //Toast.makeText(MainMenuActivity.this,"즐겨찾기1 불러오기 실패",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                FavoriteRequest favoriteRequest1 = new FavoriteRequest(F1_userID, responseListener2);
                RequestQueue queue2 = Volley.newRequestQueue(MainMenuActivity.this);
                queue2.add(favoriteRequest1);

                // 즐겨찾기 2
                Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String F2_dust = jsonResponse.getString("dust");
                            if (success) {
                                F2_dustText.setText(F2_dust);
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                FavoriteRequest favoriteRequest2 = new FavoriteRequest(F2_userID, responseListener3);
                RequestQueue queue3 = Volley.newRequestQueue(MainMenuActivity.this);
                queue3.add(favoriteRequest2);
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 10000);
    }
    public void onDestroy () {
        super.onDestroy();
        bt.stopService(); //블루투스 중지
    }

    public void onStart () {
        super.onStart();
        if (!bt.isBluetoothEnabled()) { //
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
                //setup();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                //setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}