package Jogo.Nave;

import Jogo.Objetos.ObjetoJogo;

public abstract class Nave extends ObjetoJogo {
    private int vidas;
    private int dano;

    public Nave(int vidas, int dano) {
        this.vidas = vidas;
        this.dano = dano;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public void perderVida(int quantidade) {
        vidas -= quantidade;
    }

    public void aumentarVida(int quantidade) {
        vidas += quantidade;
    }
}
