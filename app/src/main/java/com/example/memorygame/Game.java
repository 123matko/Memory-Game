package com.example.memorygame;

import java.io.Serializable;

public class Game implements Serializable {
    private int[] colection;

    private String player1 ;
    private String Player2;

    public Game(String player1,String player2){
        this.player1=player1;
        this.Player2=player2;

        colection= new int[]{R.mipmap.byk, R.mipmap.kon, R.mipmap.koza, R.mipmap.krava,
                R.mipmap.pes, R.mipmap.prasa, R.mipmap.macka, R.mipmap.ovca};
    }

    public Game(int[] colection,String player1,String player2){
        this.player1=player1;
        this.Player2=player2;

        this.colection=colection;
    }

    public int[] getColection(){
        return colection;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return Player2;
    }
}
