package com.example.memorygame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

public class GameActivity extends Activity {
    public static int[] rozmer = {4, 4};
    private GridLayout gridLayout;
    private ArrayList<ImageButton> pexeso;
    private TextView hrac1;
    private TextView hrac2;
    private TextView body1;
    private TextView body2;
    Activity activity;

    int hracS = 0;
    private ArrayList<Integer> odhalene = new ArrayList<>(2);
    private ArrayList<Integer> stlacene = new ArrayList<>();
    private ArrayList<Integer> najdene = new ArrayList<>();
    private Game game;
    private int[] obrPexesa1;
    private ArrayList<Integer> zvolene = new ArrayList<>();
    private TextView vyhral;
    boolean par;
    int cakaj = 2000;
    Handler handler = new Handler();
    int kto = 0;
    int bodyH1 = 0;
    int bodyH2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        activity = this;

        initPexeso();
        game();
    }


    @SuppressLint("NewApi")
    public void initPexeso() {
        game = (Game) getIntent().getSerializableExtra(MainActivity.EXTRA_GAME);
        obrPexesa1 = game.getColection();
        hrac1 = findViewById(R.id.player1);
        hrac1.setText(game.getPlayer1());
        hrac2 = findViewById(R.id.player2);
        hrac2.setText(game.getPlayer2());
        body1 = findViewById(R.id.body1);
        body2 = findViewById(R.id.body2);
        pexeso = new ArrayList<>();
        gridLayout = findViewById(R.id.gridLayout);
        gridLayout.setColumnCount(rozmer[0]);

        gridLayout.setRowCount(rozmer[1]);

        for (int i = 0; i < rozmer[0] * rozmer[1]; i++) {
            ImageButton button = new ImageButton(activity);

            pexeso.add(button);

            pexeso.get(i).requestLayout();
            gridLayout.addView(pexeso.get(i));
        }
        for (int i = 0; i < rozmer[0] * rozmer[1]; i++) {
            int minHeight = 150;
            int minWidth = 150;

            pexeso.get(i).setMinimumHeight(minHeight);
            pexeso.get(i).setMinimumWidth(minWidth);
            pexeso.get(i).setBackground(activity.getDrawable(R.mipmap.pozadie_pexesa));

        }
    }

    @SuppressLint("NewApi")
    public void flip() {
        hracS = 0;
        stlacene.clear();
        for (int i = 0; i < 16; i++) {
            pexeso.get(i).setBackground(activity.getDrawable(R.mipmap.pozadie_pexesa));
        }
        if (bodyH1 > 0 || bodyH2 > 0) {
            for (int n : najdene) {
                pexeso.get(n).setClickable(false);

                pexeso.get(n).setBackground(activity.getDrawable(obrPexesa1[zvolene.get(n)]));

            }
        }
    }

    public void blink(int a) {
        switch (a) {
            case 1:
                body1.setBackgroundColor(Color.TRANSPARENT);
                break;
            case 2:
                body2.setBackgroundColor(Color.TRANSPARENT);
                break;

        }
    }

    public void found() {


        if (odhalene.get(1).equals(odhalene.get(0))) {

            if (kto % 2 == 0 || kto == 0) {
                bodyH1++;
                body1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        blink(1);
                    }
                }, 500);
                par = true;

            } else {
                bodyH2++;
                body2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        blink(2);
                    }
                }, 500);
                par = true;
            }
        } else {

            par = false;
        }
        odhalene.clear();
    }

    public void control(boolean x) {

        if (hracS > 2) {
            flip();
        }
        if (hracS == 2) {

            found();
            if (par) {
                najdene.addAll(stlacene);
                stlacene.clear();
                hracS = 0;
                playGame();

            } else {
                stlacene.clear();
                if (!x) {
                    kto++;
                }


                if (kto % 2 == 0) {
                    hrac1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    hrac2.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    hrac2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    hrac1.setBackgroundColor(Color.TRANSPARENT);
                }
                hracS = 0;

                flip();
            }
        }
        if (najdene.size() == 16) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    result();
                }
            }, 2500);

        }
    }

    public void playGame() {
        if (kto == 0) {
            hrac1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        body1.setText(getString(R.string.uhadnute) + " " + String.valueOf(bodyH1));
        body2.setText(getString(R.string.uhadnute) + " " + String.valueOf(bodyH2));


        for (int i = 0; i < rozmer[0] * rozmer[1]; i++) {
            final int finalI = i;

            pexeso.get(i).setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NewApi")
                @Override
                public void onClick(View view) {
                    hracS++;
                    pexeso.get(finalI).setBackground(activity.getDrawable(obrPexesa1[zvolene.get(finalI)]));
                    odhalene.add(zvolene.get(finalI));
                    stlacene.add(finalI);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            control(false);
                        }
                    }, cakaj);

                }
            });
        }


    }

    public void game() {
        //initPexeso();

        hrac1.setText(game.getPlayer1());
        hrac2.setText(game.getPlayer2());
        stlacene.clear();

        for (int i = 0; i < 8; i++) {

            zvolene.add(i);
        }
        for (int i = 0; i < 8; i++) {

            zvolene.add(i);
        }
        Collections.shuffle(zvolene);


        playGame();


    }

    public void result() {


        if (bodyH1 > bodyH2) {

            Toast.makeText(getApplicationContext(), getString(R.string.player) + " " +game.getPlayer1() + " " + getString(R.string.vyhral), Toast.LENGTH_SHORT).show();
        } else {
            if (bodyH1 < bodyH2) {
                Toast.makeText(getApplicationContext(), getString(R.string.player) + " " +game.getPlayer2() + " " + getString(R.string.vyhral), Toast.LENGTH_SHORT).show();
            }
        }
        if (bodyH1 == bodyH2) {
            Toast.makeText(getApplicationContext(),  getString(R.string.remiza), Toast.LENGTH_SHORT).show();
        }


        handler.postDelayed(new Runnable() {
        @Override
        public void run () {
            activity.finish();

        }
    },5000);
}
    }

