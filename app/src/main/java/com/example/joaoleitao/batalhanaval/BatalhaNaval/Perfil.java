package com.example.joaoleitao.batalhanaval.BatalhaNaval;

import android.graphics.Color;

import java.io.Serializable;
import java.util.Date;

public class Perfil implements Serializable{
    private String strNome;
    private int corFundo;
    private String imagemFundo;
    private Date dataCriacao;
    private int wins;
    private int defeats;

    public Perfil(String strNome, String imagemFundo) {
        this(strNome, imagemFundo, 0, 0);
    }

    public Perfil(String strNome, String imagemFundo, int wins, int defeats) {
        this.strNome = strNome;
        this.corFundo = Color.WHITE;
        this.imagemFundo = imagemFundo;
        dataCriacao = new Date();
        this.wins = wins;
        this.defeats = defeats;
    }


    public boolean isvalid() {
        return !(strNome.isEmpty() || imagemFundo.isEmpty());
    }

    public String getStrNome() {
        return strNome;
    }

    public void setStrNome(String strNome) {
        this.strNome = strNome;
    }

    public int getWins() {
        return wins;
    }

    public void addWin() {
        ++wins;
    }

    public int getDefeats() {
        return defeats;
    }

    public void addDefeats() {
        ++defeats;
    }

    public int getCorFundo() {
        return corFundo;
    }

    public void setCorFundo(int corFundo) {
        this.corFundo = corFundo;
    }

    public String getImagemFundo() {
        return imagemFundo;
    }

    public void setImagemFundo(String imagemFundo) {
        this.imagemFundo = imagemFundo;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
