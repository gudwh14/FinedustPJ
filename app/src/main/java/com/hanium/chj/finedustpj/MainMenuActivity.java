package com.hanium.chj.finedustpj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button MenuButton = (Button) findViewById(R.id.MenuButton);

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
    }


}
