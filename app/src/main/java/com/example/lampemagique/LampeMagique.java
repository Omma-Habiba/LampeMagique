package com.example.lampemagique;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LampeMagique extends AppCompatActivity implements View.OnClickListener {

    private Button rouge, vert, bleu;

    // methode a faire comme le MainActivity.java
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("onStart()", this.getLocalClassName());
    }

    // methode a faire comme le MainActivity.java
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume()", this.getLocalClassName());
    }

    // methode a faire comme le MainActivity.java
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("onPause()", this.getLocalClassName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreate()", this.getLocalClassName());
        setContentView(R.layout.activity_lampe_magique);

        rouge = findViewById(R.id.rouge);
        vert = findViewById(R.id.vert);
        bleu = findViewById(R.id.bleu);

        // on veut cliquer sur ces trois boutons
        rouge.setOnClickListener(this);
        vert.setOnClickListener(this);
        bleu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);

        if (v.getId() == R.id.rouge) {
            intent.putExtra("rouge", Color.red(getColor(R.color.red)));
            intent.putExtra("vert", Color.green(getColor(R.color.red)));
            intent.putExtra("bleu", Color.blue(getColor(R.color.red)));
        }
        else if (v.getId() == R.id.vert) {
            intent.putExtra("rouge", Color.red(getColor(R.color.vert)));
            intent.putExtra("vert", Color.green(getColor(R.color.vert)));
            intent.putExtra("bleu", Color.blue(getColor(R.color.vert)));
        }
        else if (v.getId() == R.id.bleu) {
            intent.putExtra("rouge", Color.red(getColor(R.color.bleu)));
            intent.putExtra("vert", Color.green(getColor(R.color.bleu)));
            intent.putExtra("bleu", Color.blue(getColor(R.color.bleu)));
        }
        startActivity(intent);
    }

    // methode a faire comme le MainActivity.java
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop()", this.getLocalClassName());
    }

    // methode a faire comme le MainActivity.java
    @Override
    protected void onDestroy() {
        Log.i("onDestroy()", this.getLocalClassName());
        super.onDestroy();
    }

    // methode a faire comme le MainActivity.java
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("onRestart()", this.getLocalClassName());
    }
}