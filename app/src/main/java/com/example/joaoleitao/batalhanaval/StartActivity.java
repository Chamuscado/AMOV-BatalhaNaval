package com.example.joaoleitao.batalhanaval;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        BatalhaNavalApp.lerPerfis(this);
    }

    public void OnOnePlayerClick(View view) {
        Toast.makeText(this, "OnOnePlayerClick", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PrepareGameActivity.class);
        startActivity(intent);
    }

    public void OnTwoPlayerClick(View view) {
        Toast.makeText(this, "OnTwoPlayerClick", Toast.LENGTH_SHORT).show();
    }

    public void OnProfilesClick(View view) {
        //Toast.makeText(this,"OnProfilesClick",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ProfilesActivity.class);
        startActivity(intent);
    }

    public void OnHistClick(View view) {
        Toast.makeText(this, "OnHistClick", Toast.LENGTH_SHORT).show();
    }

    public void OnCreditsClick(View view) {
        Toast.makeText(this, "OnCreditsClick", Toast.LENGTH_SHORT).show();
    }
}
