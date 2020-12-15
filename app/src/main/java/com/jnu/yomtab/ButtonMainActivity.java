package com.jnu.yomtab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ButtonMainActivity extends AppCompatActivity {
    private Button startButtonActivity,buttonCancel;
    private EditText editName;
    private int insertPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_main);
        startButtonActivity = (Button)findViewById(R.id.button_save);
        editName = (EditText) findViewById(R.id.name_edit);
        editName.setText(getIntent().getStringExtra("title"));
        //insertPosition = getIntent().getIntExtra("insert_position",0);
        insertPosition=0;
        startButtonActivity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.putExtra("title",editName.getText().toString());
                intent.putExtra("insert_position",insertPosition);
                setResult(RESULT_OK,intent);
                ButtonMainActivity.this.finish();
            }
        });
    }
}