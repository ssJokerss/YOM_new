package com.jnu.yomtab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ButtonMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_main);
        final Button startButtonActivity = (Button)findViewById(R.id.button_save);
        startButtonActivity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(ButtonMainActivity.this,upFragment.class);
                startActivity(intent);
            }
        });
    }
}