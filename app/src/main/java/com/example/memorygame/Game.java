package com.example.memorygame;

import java.io.Serializable;

public class Game implements Serializable {
    private int[] colection;
    private int state;
    private String player ;

    public Game(String player){
        this.player=player;
        state=0;
        colection= new int[]{R.mipmap.byk, R.mipmap.kon, R.mipmap.koza, R.mipmap.krava,
                R.mipmap.pes, R.mipmap.prasa, R.mipmap.macka, R.mipmap.ovca};
    }

    public Game(int[] colection,String player){
        this.player=player;
        state=0;
        this.colection=colection;
    }
}
