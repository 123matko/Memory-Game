package com.example.memorygame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

public class MainActivity extends Activity {
    private int[] colection={};
    private Button play;

    private EditText name1,name2;
    public static final String EXTRA_GAME= "com.example.memorygame.EXTRA_GAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        init();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pName1=name1.getText().toString();
                String pName2=name2.getText().toString();
                Game game;
                if (colection.length==0){
                    game=new Game( pName1.isEmpty()?getResources().getString(R.string.player1):pName1,pName2.isEmpty()?getResources().getString(R.string.player2):pName2);
                }else {
                    game = new Game(colection, pName1.isEmpty()?getResources().getString(R.string.player1):pName1,pName2.isEmpty()?getResources().getString(R.string.player2):pName2);
                }
                startGame(game);
            }
        });

    }

    private void init(){
        play=findViewById(R.id.play);

        name1=findViewById(R.id.meno1);
        name2=findViewById(R.id.meno2);
    }

    private void startGame(Game game){
        Intent intent=new Intent(this,GameActivity.class);
        intent.putExtra(EXTRA_GAME,game);
        startActivity(intent);
    }


}


